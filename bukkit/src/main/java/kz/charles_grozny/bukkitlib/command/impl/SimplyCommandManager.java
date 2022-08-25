package kz.charles_grozny.bukkitlib.command.impl;

import kz.charles_grozny.bukkitlib.annotation.command.MinimalArgs;
import kz.charles_grozny.bukkitlib.annotation.command.Permission;
import kz.charles_grozny.bukkitlib.annotation.command.SubCommand;
import kz.charles_grozny.bukkitlib.annotation.command.TargetSender;
import kz.charles_grozny.bukkitlib.command.CommandManager;
import kz.charles_grozny.bukkitlib.command.ICommand;
import kz.charles_grozny.bukkitlib.command.Manager;
import kz.charles_grozny.bukkitlib.model.command.Target;
import kz.charles_grozny.bukkitlib.utility.AccessingAllClassesInPackage;
import kz.charles_grozny.bukkitlib.utility.version.NMSVersion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    List<Method> subCommandMethods;

    static Manager managerG;


    public SimplyCommandManager(M manager, List<ICommand> commands) {
        super("", "", "", Collections.emptyList());

        this.command = this;
        this.manager = manager;
        this.plugin = manager.getPlugin();
        this.bukkitCommand = this;
        this.subCommandMethods = new ArrayList<>();

        commands.forEach(command -> {
            setName(command.getName());
            register(command.getAliases(), (command.getBukkitCommand()));

            Arrays.stream(command.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(SubCommand.class))
                    .forEach(command.getSubCommandMethods()::add);
        });
    }

    @SneakyThrows
    public static <M extends Manager> SimplyCommandManager<M> create(M manager) {
        managerG = manager;

        List<ICommand> commands = new ArrayList<>();
        for (val aClass : AccessingAllClassesInPackage.getClassesCommand(manager.getPlugin().getClass().getClassLoader(), manager.getPackageName())) {
            commands.add((ICommand) aClass.getConstructor().newInstance());
        }

        return new SimplyCommandManager<>(manager, commands);
    }


    @SneakyThrows
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!checkPermission(sender)) return true;
        if (!checkSyntaxCommand(sender, args)) return true;

        for (val subCommandMethod : subCommandMethods) {
            val subCommand = subCommandMethod.getDeclaredAnnotation(SubCommand.class);
            if (args[0].equalsIgnoreCase(subCommand.name())) {
                if (checkPermission(subCommandMethod, sender)) {
                    if (checkSender(subCommandMethod, sender)) {
                        subCommandMethod.invoke(command, sender, args);
                    }
                }
                return true;
            }
        }

        command.run(sender, args);

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
    public void register(List<String> aliases, org.bukkit.command.Command bukkitCommand) {
        switch (NMSVersion.onCheckVersion(plugin.getLogger())) {
            case VERSION_1_12_R1:
                aliases.forEach(alias -> ((org.bukkit.craftbukkit.v1_12_R1.CraftServer) plugin.getServer())
                        .getCommandMap().register(alias, bukkitCommand));
                break;
            case VERSION_1_16_R1:
                aliases.forEach(alias -> ((org.bukkit.craftbukkit.v1_16_R1.CraftServer) plugin.getServer())
                        .getCommandMap().register(alias, bukkitCommand));
                break;
            case VERSION_1_16_R2:
                aliases.forEach(alias -> ((org.bukkit.craftbukkit.v1_16_R2.CraftServer) plugin.getServer())
                        .getCommandMap().register(alias, bukkitCommand));
                break;
            case VERSION_1_16_R3:
                aliases.forEach(alias -> ((org.bukkit.craftbukkit.v1_16_R3.CraftServer) plugin.getServer())
                        .getCommandMap().register(alias, bukkitCommand));
                break;
        }
    }



    /**
     * Проверяет достаточно ли кол-во аргументов для использования команды
     *
     * @param sender Source object which is executing this command
     * @param args   All arguments passed to the command
     * @return true если нет аннотации или минимальное кол-во аргументов больше чем кол-во аргументов в команде
     */
    private boolean checkSyntaxCommand(@NotNull CommandSender sender, @NotNull String... args) {
        if (!command.getClass().isAnnotationPresent(MinimalArgs.class)) return true;

        val declaredAnnotation = command.getClass().getDeclaredAnnotation(MinimalArgs.class);
        if (declaredAnnotation.value() > args.length) {
            sender.sendMessage(declaredAnnotation.message());
            return false;
        } else return true;
    }

    /**
     * Проверяет есть ли перм у sender'а, используется для команды
     *
     * @param sender Source object which is executing this command
     * @return true если есть этот перм у sender'а
     */
    private boolean checkPermission(@NotNull CommandSender sender) {
        if (!command.getClass().isAnnotationPresent(Permission.class)) return true;

        val declaredAnnotation = command.getClass().getDeclaredAnnotation(Permission.class);
        if (!sender.hasPermission(declaredAnnotation.value())) {
            sender.sendMessage(declaredAnnotation.message());
            return false;
        } else return true;
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
     * Проверяет отправителя и сравнивает с аннотацией если она установлена в суб-команде
     *
     * @param subCommandMethod метод суб-команды
     * @param sender           Source object which is executing this command
     * @return true если нет аннотации и нужный target отправил команду
     */
    private boolean checkSender(@NotNull Method subCommandMethod, @NotNull CommandSender sender) { // @todo переделать
        if (subCommandMethod.isAnnotationPresent(TargetSender.class)) {
            val declaredAnnotation = subCommandMethod.getDeclaredAnnotation(TargetSender.class);
            if (declaredAnnotation.value().equals(Target.PLAYER)) {
                if (sender instanceof Player) {
                    return true;
                } else {
                    sender.sendMessage(Target.PLAYER.getMessage());
                    return false;
                }
            } else {
                if (sender instanceof Player) {
                    sender.sendMessage(Target.SERVER.getMessage());
                    return false;
                } else {
                    return true;
                }
            }
        } else return true;
    }
}
