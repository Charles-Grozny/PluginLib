package kz.charles_grozny.examplebukkitlib.data;

import kz.charles_grozny.bukkitlib.data.impl.Config;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 17.12.2021 | 21:43 ⭐
 */
public class FileMessage extends Config {

    public String text;

    public FileMessage(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public static FileMessage create(JavaPlugin plugin, String fileName) {
        return new FileMessage(plugin, fileName);
    }

    public void init() {
        text = file.getString("text");
    }


}