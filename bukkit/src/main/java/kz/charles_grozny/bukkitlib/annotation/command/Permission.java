package kz.charles_grozny.bukkitlib.annotation.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 27.02.2022 | 21:26 ⭐
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    String value();

    String message() default "§cНедостаточно прав.";
}