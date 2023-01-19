package kz.charles_grozny.examplebukkitlib;

import kz.charles_grozny.bukkitlib.command.Manager;
import kz.charles_grozny.examplebukkitlib.data.ConfigData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 06.08.2022 | 0:07 ⭐
 */
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
public class ManagerImpl implements Manager {

    @Getter
    JavaPlugin plugin;

    @Getter
    String packageName;

    ConfigData config;

    public static ManagerImpl create(final JavaPlugin plugin, final String packageName, final ConfigData config) {
        return new ManagerImpl(plugin, packageName, config);
    }
}