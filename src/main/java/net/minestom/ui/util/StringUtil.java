package net.minestom.ui.util;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class StringUtil {
    private StringUtil() {}

    public static String toHumanReadable(String camelCase) {
        return camelCase.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    public static String toHumanReadable(Class<?> className) {
        return toHumanReadable(className.getSimpleName());
    }

    public static String toHumanReadable(Enum<?> enumValue) {
        // Seems like theres a fancy regex way to do this that i dont want to come up with
        String name = enumValue.name()
                .toLowerCase(Locale.ROOT)
                .replace('_', ' ');
        return Arrays.stream(name.split(" "))
                .map(str -> str.substring(0, 1).toUpperCase() + str.substring(1))
                .collect(Collectors.joining(" "));
    }
}
