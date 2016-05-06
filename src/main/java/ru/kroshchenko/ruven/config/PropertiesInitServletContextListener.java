package ru.kroshchenko.ruven.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Logger;

import static ru.kroshchenko.ruven.config.ContextProperties.ContextProperty.DB_DRIVER;

/**
 * @author Kroshchenko
 *         2015.11.19
 */
public class PropertiesInitServletContextListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger("PropertiesInitServletContextListener");

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Properties properties = loadPropertiesIfExists();
        if (properties == null) {
            // no properties found. So, load default from web.xml
            properties = new Properties();
            Enumeration initParameterNames = servletContextEvent.getServletContext().getInitParameterNames();
            while (initParameterNames.hasMoreElements()) {
                String initParameterName = (String) initParameterNames.nextElement();
                String initParameterValue = servletContextEvent.getServletContext().getInitParameter(initParameterName);
                properties.put(initParameterName, initParameterValue);
            }
        }
        ContextProperties.init(properties);
        try {
            loadDbDriver();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    private Properties loadPropertiesIfExists() {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream resourceAsStream = classLoader.getResourceAsStream("app.properties");
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return null;
        }
        return properties;
    }

    private void loadDbDriver() throws ClassNotFoundException {
        Class.forName(ContextProperties.getProperty(DB_DRIVER));
    }
}
