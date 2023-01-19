package kz.charles_grozny.examplebukkitlib.data;

import kz.charles_grozny.bukkitlib.data.IConfig;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 17.12.2021 | 3:48 ⭐
 */

@FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
public class ConfigData implements IConfig {
    FileConfig fileConfig;
    FileMessage fileMessages;

    public ConfigData(JavaPlugin plugin) {
        fileMessages = FileMessage.create(plugin, "messages.yml");
        fileConfig = FileConfig.create(plugin, "config.yml");

        reload();
    }

    public static ConfigData create(JavaPlugin plugin) {
        return new ConfigData(plugin);
    }

    @Override
    public void reload() {
        fileConfig.reload();
        fileMessages.reload();
        fileMessages.init();
        fileConfig.init();
    }
}