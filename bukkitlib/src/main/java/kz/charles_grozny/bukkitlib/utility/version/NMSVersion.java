package kz.charles_grozny.bukkitlib.utility.version;

import kz.charles_grozny.bukkitlib.exception.LibUnknownVersionException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;

import java.util.logging.Logger;

/**
 * @author Ferius_057 (Charles_Grozny)
 * ⭐ 04.08.2022 | 23:53 ⭐
 */

@Getter
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public enum NMSVersion {
    VERSION_1_12_R1("v1_12_R1"),
    VERSION_1_16_R1("v1_16_R1"),
    VERSION_1_16_R2("v1_16_R2"),
    VERSION_1_16_R3("v1_16_R3");


    String isNMS;

    @SneakyThrows
    public static NMSVersion onCheckVersion(final Logger logger) {
        for (NMSVersion value : values()) {
            if (Bukkit.getServer().getClass().getPackage().getName().contains(value.isNMS))
                return value;
        }

        logger.warning("Unknown version, maybe this version is not supported. " +
                "Your version: " + Bukkit.getServer().getClass().getPackage().getName());
        throw new LibUnknownVersionException("Unknown core version.");
    }
}