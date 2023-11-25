package kz.charles_grozny.bukkitlib.model.command;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.lang.reflect.Method;

/**
 * @author Ferius_057 (Charles_Grozny)
 * @since ⭐ 30.12.2022 | 22:00 ⭐
 */
@Getter
@Builder
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SubCommand {
    String name, permission, permissionMessage;
    Target target;
    Method method;
}