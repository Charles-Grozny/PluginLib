package kz.charles_grozny.bukkitlib.command.impl;

import kz.charles_grozny.bukkitlib.annotation.command.Command;
import kz.charles_grozny.bukkitlib.command.Manager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 17.12.2021 | 2:19 ⭐
 */

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public abstract class AbstractCommand<T extends Manager> extends SimplyCommandManager<T> {
    @Getter
    String name;

    @Getter
    List<String> aliases;

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public AbstractCommand() {
        super((T) managerG, Collections.emptyList());

        Command commandInfo = getClass().getDeclaredAnnotation(Command.class);

        this.name = commandInfo.name();
        this.aliases = Arrays.asList(commandInfo.aliases());
    }
}