package kz.charles_grozny.bukkitlib.model.command;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 27.02.2022 | 0:16 ⭐
 */

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum Target {
    PLAYER("§cЭто можно использовать только игроку."),
    SERVER("§cЭто можно использовать только с консоли."),
    UNKNOWN("");



    String message;
}
