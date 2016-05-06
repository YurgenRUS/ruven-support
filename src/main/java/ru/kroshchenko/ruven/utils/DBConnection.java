package ru.kroshchenko.ruven.utils;

import ru.kroshchenko.ruven.config.ContextProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

import static ru.kroshchenko.ruven.config.ContextProperties.ContextProperty.DB_CONNECTION;
import static ru.kroshchenko.ruven.config.ContextProperties.ContextProperty.DB_PASSWORD;
import static ru.kroshchenko.ruven.config.ContextProperties.ContextProperty.DB_USER;

public class DBConnection {

    static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    /**
     * Connection with default properties
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(ContextProperties.getProperty(DB_CONNECTION),
                    ContextProperties.getProperty(DB_USER),
                    ContextProperties.getProperty(DB_PASSWORD));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Can not create connection");
        }
    }
}
