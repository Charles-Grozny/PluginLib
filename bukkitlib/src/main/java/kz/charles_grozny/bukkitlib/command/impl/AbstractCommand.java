package kz.charles_grozny.bukkitlib.command.impl;

import kz.charles_grozny.bukkitlib.annotation.command.Command;
import kz.charles_grozny.bukkitlib.command.Manager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 17.12.2021 | 2:19 ⭐
 */

@Getter
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public abstract class AbstractCommand<T extends Manager> extends SimplyCommandManager<T> {
    String name;
    List<String> aliases, names;

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public AbstractCommand() {
        super((T) managerG, Collections.emptyList());

        val commandInfo = getClass().getDeclaredAnnotation(Command.class);
        val names = Arrays.asList(commandInfo.value());

        this.name = names.get(0);
        this.aliases = names.subList(1, names.size());
        this.names = names;
    }
}