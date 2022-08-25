package kz.charles_grozny.bukkitlib.command;

import kz.charles_grozny.bukkitlib.command.impl.ManagerDefault;
import kz.charles_grozny.bukkitlib.command.impl.SimplyCommandManager;
import kz.charles_grozny.bukkitlib.data.IConfig;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 23.02.2022 | 21:56 ⭐
 */
public interface CommandManager {

    void register(List<String> aliases, Command bukkitCommand);


    static @NotNull <M extends Manager> SimplyCommandManager<M> register(M manager) {
        return SimplyCommandManager.create(manager);
    }

    static @NotNull <C extends IConfig> SimplyCommandManager<ManagerDefault<C>> register(@NotNull final JavaPlugin plugin, @NotNull final String packageName, final C config) {
        return register(Manager.create(plugin, packageName, config));
    }

    static @NotNull <C extends IConfig> SimplyCommandManager<ManagerDefault<C>> register(@NotNull final JavaPlugin plugin, @NotNull final String packageName) {
        return register(plugin, packageName, null);
    }

    static @NotNull <C extends IConfig> SimplyCommandManager<ManagerDefault<C>> register(@NotNull final JavaPlugin plugin, @NotNull final C config) {
        String[] splitPackageName = plugin.getDescription().getMain().split("\\.");
        return register(plugin, plugin.getDescription().getMain().replace(splitPackageName[splitPackageName.length - 1], "command"), config);
    }

    static @NotNull <C extends IConfig> SimplyCommandManager<ManagerDefault<C>> register(@NotNull final JavaPlugin plugin) {
        String[] splitPackageName = plugin.getDescription().getMain().split("\\.");
        return register(plugin, plugin.getDescription().getMain().replace(splitPackageName[splitPackageName.length - 1], "command"), null);
    }
}
