package kz.charles_grozny.bukkitlib.utility;

import com.google.common.reflect.ClassPath;
import kz.charles_grozny.bukkitlib.annotation.command.Command;
import kz.charles_grozny.bukkitlib.command.impl.AbstractCommand;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 05.08.2022 | 23:31 ⭐
 */

@UtilityClass
public class AccessingAllClassesInPackage {

    @SneakyThrows
    private List<Class<?>> getClassesInPackage(final ClassLoader classLoader, final String packageName, Predicate<Class<?>> filter) {

        return ClassPath.from(classLoader)
                .getAllClasses()
                .stream()
                .filter(x -> x.getPackageName().startsWith(packageName))
                .map(ClassPath.ClassInfo::load)
                .filter(filter)
                .collect(Collectors.toList());
    }

    public List<Class<?>> getClassesCommand(final ClassLoader classLoader, final String packageName) {
        return getClassesInPackage(classLoader, packageName, filter -> AbstractCommand.class.isAssignableFrom(filter) && filter.getDeclaredAnnotation(Command.class) != null);
    }
}