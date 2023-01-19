package kz.charles_grozny.examplebukkitlib.command;

import kz.charles_grozny.bukkitlib.annotation.command.*;
import kz.charles_grozny.bukkitlib.command.impl.AbstractCommand;
import kz.charles_grozny.bukkitlib.command.impl.ManagerDefault;
import kz.charles_grozny.bukkitlib.model.command.Target;
import kz.charles_grozny.examplebukkitlib.data.ConfigData;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 23.02.2022 | 21:44 ⭐
 */

@MinimalArgs(1)
@Command({"kek", "кек"})
public class Kek extends AbstractCommand<ManagerDefault<ConfigData>> {

    @Override
    public void run(CommandSender sender, String[] args) {
        manager.config.reload();
        sender.sendMessage("done");
    }

    @TargetSender(Target.PLAYER)
    @Permission(value = "pluginlib.cmd.kek.send", message = "нельзя")
    @SubCommand({"send"})
    public void send(CommandSender sender, String[] args) {
        sender.sendMessage("send");
    }


    @SubCommand({"get"})
    public void get(CommandSender sender, String[] args) {
        sender.sendMessage("get");
    }

    @TargetSender(Target.SERVER)
    @SubCommand({"read"})
    public void read(CommandSender sender, String[] args) {
        sender.sendMessage("read");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String... args) {
        List<String> strings = super.tabComplete(sender, args);
        strings.add("read");
        strings.add("send");
        strings.add("get");
        return strings;
    }
}