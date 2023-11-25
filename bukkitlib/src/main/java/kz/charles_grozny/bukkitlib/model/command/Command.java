package kz.charles_grozny.bukkitlib.model.command;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.HashMap;

/**
 * @author Ferius_057 (Charles_Grozny)
 * @since ⭐ 30.12.2022 | 22:00 ⭐
 */
@Getter
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Command {
    String name, permission, permissionMessage;
    int minimalArgs;
    Target target;

    HashMap<String, SubCommand> subCommands;
}