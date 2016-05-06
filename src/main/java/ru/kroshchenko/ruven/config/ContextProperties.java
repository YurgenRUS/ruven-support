package ru.kroshchenko.ruven.config;

import org.apache.commons.lang3.BooleanUtils;

import java.util.Map;
import java.util.Properties;

/**
 * @author Kroshchenko
 *         2015.11.19
 */
public class ContextProperties {

    private static Properties properties;

    private ContextProperties() {
    }

    public static void init(Properties source) {
        if (source != null) {
            properties = new Properties();
            for (Map.Entry<Object, Object> propertyEntry : source.entrySet()) {
                properties.put(
                        propertyEntry.getKey().toString().toLowerCase(),
                        propertyEntry.getValue()
                );
            }
        } else {
            throw new IllegalArgumentException("Context properties source is null");
        }
    }

    public static String getProperty(ContextProperty property, String defaultValue) {
        String result = getProperty(property);
        if (result == null) {
            return defaultValue;
        }
        return result;
    }

    public static String getProperty(ContextProperty property) {
        return getProperty(property.getName());
    }

    public static String getProperty(String name) {
        if (name != null && name.trim().length() > 0) {
            if (properties != null) {
                return properties.getProperty(name.toLowerCase());
            } else {
                throw new IllegalStateException("Properties need to initialize first");
            }
        } else {
            throw new IllegalArgumentException("Name argument is empty");
        }
    }

    public static Long getPropertyAsLong(ContextProperty property) {
        return getPropertyAsLong(property.getName());
    }

    public static Integer getPropertyAsInteger(ContextProperty property) {
        return getPropertyAsInteger(property.getName());
    }

    public static Long getPropertyAsLong(String name) {
        try {
            return Long.parseLong(getProperty(name));
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
    }

    public static Integer getPropertyAsInteger(String name) {
        try {
            return Integer.parseInt(getProperty(name));
        } catch (NullPointerException | NumberFormatException e) {
            return null;
        }
    }

    public static Boolean getPropertyAsBoolean(ContextProperty name) {
        return BooleanUtils.toBooleanObject(getProperty(name));
    }

    public static Boolean getPropertyAsBoolean(ContextProperty name, boolean defaultValue) {
        Boolean result = BooleanUtils.toBooleanObject(getProperty(name));
        if (result == null) {
            return defaultValue;
        }
        return result;
    }

    public static void putProperty(ContextProperty property, Object value) {
        properties.put(property.getName(), value);
    }

    public enum ContextProperty {
//        DB
        DB_CONNECTION("db.connection"),
        DB_DRIVER("db.driver"),
        DB_USER("db.user"),
        DB_PASSWORD("db.pwd"),

        ROOT_LOGIN("root.login"),
        ROOT_PASSWORD("root.password");

        /**
         * enum.name already exists, so..
         */
        private String propertyName;

        ContextProperty(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getName() {
            return propertyName;
        }
    }
}
