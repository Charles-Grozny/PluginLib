package kz.charles_grozny.bukkitlib.command;

import kz.charles_grozny.bukkitlib.command.impl.ManagerDefault;
import kz.charles_grozny.bukkitlib.data.IConfig;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 06.08.2022 | 0:02 ⭐
 */
public interface Manager {
    JavaPlugin getPlugin();

    String getPackageName();


    static <C extends IConfig> ManagerDefault<C> create(@NotNull final JavaPlugin plugin, @NotNull final String packageName, final C config) {
        return new ManagerDefault<>(plugin, packageName, config);
    }
}