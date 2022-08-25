package kz.charles_grozny.bukkitlib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 17.12.2021 | 2:18 ⭐
 */
public interface ICommand {

    Command getBukkitCommand();

    String getName();

    List<String> getAliases();

    default List<Method> getSubCommandMethods() {
        return new ArrayList<>();
    }


    /**
     * Executes the command, returning its success
     *
     * @param sender Source object which is executing this command
     * @param args   All arguments passed to the command, split via ' '
     */
    void run(CommandSender sender, String[] args);

    /**
     * Executed on tab completion for this command, returning a list of options the player can tab through.
     *
     * @param sender Source object which is executing this command
     * @param args   All arguments passed to the command, split via ' '
     * @return a list of tab-completions that will be displayed to the player
     */
    List<String> tabComplete(CommandSender sender, String... args);
}