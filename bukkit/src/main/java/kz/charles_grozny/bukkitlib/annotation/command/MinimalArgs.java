package kz.charles_grozny.bukkitlib.annotation.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 26.02.2022 | 20:29 ⭐
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinimalArgs {
    int value();

    String message() default "§cНедостаточно аргументов.";
}
