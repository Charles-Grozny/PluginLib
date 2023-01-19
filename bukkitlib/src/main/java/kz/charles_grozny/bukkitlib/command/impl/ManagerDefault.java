package kz.charles_grozny.bukkitlib.command.impl;

import kz.charles_grozny.bukkitlib.command.Manager;
import kz.charles_grozny.bukkitlib.data.IConfig;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 07.08.2022 | 23:02 ⭐
 */

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
public class ManagerDefault<C extends IConfig> implements Manager {
    @Getter
    JavaPlugin plugin;

    @Getter
    String packageName;

    C config;
}