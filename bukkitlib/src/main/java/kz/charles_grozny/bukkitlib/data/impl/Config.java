package kz.charles_grozny.bukkitlib.data.impl;

import kz.charles_grozny.bukkitlib.data.IConfig;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 17.12.2021 | 2:19 ⭐
 */

@FieldDefaults(level = AccessLevel.PROTECTED)
public class Config implements IConfig {
    final Path path;
    FileConfiguration file;

    public Config(@NotNull JavaPlugin plugin, @NotNull String fileName) {
        Path path = plugin.getDataFolder()
                .toPath()
                .resolve(fileName);
        this.path = path;

        if (!Files.exists(path)) {
            plugin.saveResource(fileName, false);
        }

        reload();
    }

    @Override
    public void reload() {
        file = YamlConfiguration.loadConfiguration(path.toFile());
    }
}