package com.phoenix.util;

import java.util.Optional;
import java.util.Properties;

public class PropertyLoader {
    public static Optional<Properties> loadProperties() {
        try (var input = PropertyLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            var prop = new Properties();
            if (input == null) {
                return Optional.empty();
            }
            prop.load(input);
            return Optional.of(prop);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
