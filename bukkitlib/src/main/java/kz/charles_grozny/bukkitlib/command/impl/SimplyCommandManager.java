package kz.charles_grozny.bukkitlib.command.impl;

import kz.charles_grozny.bukkitlib.annotation.command.Permission;
import kz.charles_grozny.bukkitlib.annotation.command.SubCommand;
import kz.charles_grozny.bukkitlib.annotation.command.TargetSender;
import kz.charles_grozny.bukkitlib.command.CommandManager;
import kz.charles_grozny.bukkitlib.command.ICommand;
import kz.charles_grozny.bukkitlib.command.Manager;
import kz.charles_grozny.bukkitlib.listener.BukkitLibListener;
import kz.charles_grozny.bukkitlib.model.command.Target;
import kz.charles_grozny.bukkitlib.utility.AccessingAllClassesInPackage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 23.02.2022 | 22:26 ⭐
 */

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class SimplyCommandManager<M extends Manager> extends org.bukkit.command.Command implements CommandManager, ICommand {
    ICommand command;

    JavaPlugin plugin;

    M manager;

    @Getter
    org.bukkit.command.Command bukkitCommand;

    @Getter
    List<String> names = new ArrayList<>();

    @Getter
    List<Method> subCommandMethods;

    public static CommandMap commandMap;
    public static HashMap<String, kz.charles_grozny.bukkitlib.model.command.Command> commandsCache = new HashMap<>();

    public static List<ICommand> commands;

    static Manager managerG;



    public SimplyCommandManager(M manager, List<ICommand> commands) {
        super("");

        this.command = this;
        this.manager = manager;
        this.plugin = manager.getPlugin();
        this.bukkitCommand = this;
        this.subCommandMethods = new ArrayList<>();
        SimplyCommandManager.commands = commands;

        commands.forEach(command -> {
            setName(command.getNames().get(0));

            register(command.getBukkitCommand());

            Arrays.stream(command.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(SubCommand.class))
                    .forEach(command.getSubCommandMethods()::add);

            HashMap<String, kz.charles_grozny.bukkitlib.model.command.SubCommand> subCommands = new HashMap<>();
            for (val method : command.getClass().getMethods()) {
                val subCommandInfo = method.getDeclaredAnnotation(SubCommand.class);
                if (Optional.ofNullable(subCommandInfo).isPresent()) {
                    for (val name : subCommandInfo.value()) {
                        val permissionInfo = method.getDeclaredAnnotation(Permission.class);
                        val targetInfo = method.getDeclaredAnnotation(TargetSender.class);

                        subCommands.put(name,
                                kz.charles_grozny.bukkitlib.model.command.SubCommand.builder()
                                        .name(name)
                                        .permission(Optional.ofNullable(permissionInfo).isPresent() ? permissionInfo.value() : "")
                                        .permissionMessage(Optional.ofNullable(permissionInfo).isPresent() ? permissionInfo.message() : "")
                                        .target(Optional.ofNullable(targetInfo).isPresent() ? targetInfo.value() : Target.UNKNOWN)
                                        .method(method)
                                        .build()
                        );
                    }
                }
            }

            for (val name : command.getNames()) {
                val permissionInfo = command.getClass().getDeclaredAnnotation(Permission.class);
                val targetInfo = command.getClass().getDeclaredAnnotation(TargetSender.class);

                commandsCache.put(name,
                        kz.charles_grozny.bukkitlib.model.command.Command.builder()
                                .name(name)
                                .permission(Optional.ofNullable(permissionInfo).isPresent() ? permissionInfo.value() : "")
                                .permissionMessage(Optional.ofNullable(permissionInfo).isPresent() ? permissionInfo.message() : "")
                                .target(Optional.ofNullable(targetInfo).isPresent() ? targetInfo.value() : Target.UNKNOWN)
                                .subCommands(subCommands)
                                .build()
                );
            }
        });

        // register a listener for automatic switching off of the command
        Bukkit.getPluginManager().registerEvents(new BukkitLibListener(this, commands, plugin), plugin);
    }

    @SneakyThrows
    public static <M extends Manager> SimplyCommandManager<M> create(M manager) {
        managerG = manager;
        JavaPlugin managerPlugin = manager.getPlugin();

        List<ICommand> commands = new ArrayList<>();
        for (val aClass : AccessingAllClassesInPackage.getClassesCommand(managerPlugin.getClass().getClassLoader(), manager.getPackageName())) {
            commands.add((ICommand) aClass.getConstructor().newInstance());
        }

        return new SimplyCommandManager<>(manager, commands);
    }


    @SneakyThrows
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        val commandCache = commandsCache.get(commandLabel);

        if (!isAllowed(commandCache.getTarget(),
                commandCache.getPermission(),
                commandCache.getPermissionMessage(),
                sender)) return true;

        if (args.length > 0) {
            val subCommandCache = commandCache.getSubCommands().get(args[0]);
            if (subCommandCache != null) {

                if (isAllowed(subCommandCache.getTarget(),
                        subCommandCache.getPermission(),
                        subCommandCache.getPermissionMessage(),
                        sender)) {
                    subCommandCache.getMethod().invoke(command, sender, args);
                }
                return true;
            }
        }

        command.run(sender, args);
        return true;
    }
    private boolean isAllowed(Target target, String permission, String permissionMessage, CommandSender sender) {
        if (!checkSender(target, sender)) {
            sender.sendMessage(target.getMessage());
            return false;
        }

        if (!checkPermission(permission, sender)) {
            sender.sendMessage(permissionMessage);
            return false;
        }
        return true;
    }

    @Override
    public void run(CommandSender sender, String[] args) {}

    @Override
    public List<String> tabComplete(CommandSender sender, String... args) {
        return super.tabComplete(sender, getName(), args);
    }

    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String... args) {
        return command.tabComplete(sender, args);
    }


    @Override
    @SneakyThrows
    public void register(Command bukkitCommand) {
        val commandMap = getCommandMap();

        val commandMapCommand = commandMap.getCommand(bukkitCommand.toString());
        if (Optional.ofNullable(commandMapCommand).isEmpty()
                || !commandMapCommand.isRegistered()) {
            commandMap.register(bukkitCommand.toString(), bukkitCommand);
        }
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public void unregister(List<ICommand> commands) {
        if (Optional.ofNullable(commandMap).isEmpty()) getCommandMap();

        val simpleCommandMap = SimpleCommandMap.class.getDeclaredField("knownCommands");
        simpleCommandMap.setAccessible(true);

        val knownCommands = (Map<String, Command>) simpleCommandMap.get(commandMap);

        commands.forEach(iCommand -> iCommand.getNames().forEach(knownCommands::remove));
        simpleCommandMap.set(commandMap, knownCommands);
    }

    @SneakyThrows
    private CommandMap getCommandMap() {
        val pluginManager = Bukkit.getPluginManager();
        val cmdMap = pluginManager.getClass().getDeclaredField("commandMap");
        cmdMap.setAccessible(true);
        val commandMapResult = (CommandMap) cmdMap.get(pluginManager);
        commandMap = commandMapResult;
        return commandMapResult;
    }



    /**
     * Проверяет достаточно ли кол-во аргументов для использования команды
     *
     * @param sender Source object which is executing this command
     * @param args   All arguments passed to the command
     * @return true если нет аннотации или минимальное кол-во аргументов больше чем кол-во аргументов в команде
     */
    private boolean checkSyntaxCommand(@NotNull CommandSender sender, @NotNull String... args) {
     /*   if (!command.getClass().isAnnotationPresent(MinimalArgs.class)) return true;

        val declaredAnnotation = command.getClass().getDeclaredAnnotation(MinimalArgs.class);
        if (declaredAnnotation.value() > args.length) {
            sender.sendMessage(declaredAnnotation.message());
            return false;
        } else return true;*/
        return true;
    }

    /**
     * Проверяет есть ли перм у sender'а, используется для команды
     *
     * @param sender Source object which is executing this command
     * @return true если есть этот перм у sender'а
     */
    private boolean checkPermission(@NotNull String permission, @NotNull CommandSender sender) {
        return permission.isEmpty() || sender.hasPermission(permission);
    }

    /**
     * Проверяет есть ли аннотация перма у суб-команды, если есть то проверяет есть ли этот перм у sender'а
     *
     * @param subCommandMethod метод суб-команды
     * @param sender           Source object which is executing this command
     * @return true если нет аннотации или установлен перм и у sender'а он есть
     */
    private boolean checkPermission(@NotNull Method subCommandMethod, @NotNull CommandSender sender) {
        if (subCommandMethod.isAnnotationPresent(Permission.class)) {
            val declaredAnnotation = subCommandMethod.getDeclaredAnnotation(Permission.class);
            if (!sender.hasPermission(declaredAnnotation.value())) {
                sender.sendMessage(declaredAnnotation.message());
                return false;
            } else return true;
        } else return true;
    }


    /**
     * Проверяет отправителя и сравнивает с аннотацией если она установлена в команде
     *
     * @param sender           Source object which is executing this command
     * @return true если нет аннотации и нужный target отправил команду
     */
    private boolean checkSender(@NotNull Target target, @NotNull CommandSender sender) {
        switch (target) {
            case PLAYER: return sender instanceof Player;
            case SERVER: return sender instanceof ConsoleCommandSender;
            default: return true;
        }
    }
}
