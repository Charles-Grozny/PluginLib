package kz.charles_grozny.bukkitlib.listener;

import kz.charles_grozny.bukkitlib.command.CommandManager;
import kz.charles_grozny.bukkitlib.command.ICommand;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * @author Ferius_057 (Charles_Grozny)
 * @since ⭐ 30.12.2022 | 20:06 ⭐
 */
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BukkitLibListener implements Listener {
    CommandManager commandManager;
    List<ICommand> commands;
    JavaPlugin plugin;

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (plugin == event.getPlugin()) {
            commandManager.unregister(commands);
        }
    }
}