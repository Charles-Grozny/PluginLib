package kz.charles_grozny.examplebukkitlib.data;

import kz.charles_grozny.bukkitlib.data.impl.Config;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 17.12.2021 | 21:43 ⭐
 */

public class FileConfig extends Config {

    public int test;

    public List<String> list;

    public FileConfig(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
    }

    public static FileConfig create(JavaPlugin plugin, String fileName) {
        return new FileConfig(plugin, fileName);
    }


    @SneakyThrows
    public void init() {
        for (Field field : this.getClass().getFields()) {
            field.set(this, file.get(field.getName()));
        }


        //  count = file.getInt("test");
    }

}