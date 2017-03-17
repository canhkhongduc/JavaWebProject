/*
 * Copyright © 2017 Six Idiots Team
 */
package app;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Le Cao Nguyen
 */
public class ApplicationContextListener implements ServletContextListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("contextPath", context.getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }

    // <editor-fold defaultstate="collapsed" desc="Unused methods">
    /**
     * Deregister any JDBC driver registered by the web app.
     */
    private void deregisterJDBCDriver() {
        // Get the webapp's ClassLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // Loop through all drivers
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            if (driver.getClass().getClassLoader() == classLoader) {
                // This driver was registered by the webapp's ClassLoader, so deregister it:
                try {
                    LOGGER.info("Deregistering JDBC driver {}", driver);
                    DriverManager.deregisterDriver(driver);
                } catch (SQLException ex) {
                    LOGGER.error("Error deregistering JDBC driver {}", driver, ex);
                }
            } else {
                // driver was not registered by the webapp's ClassLoader and may be in use elsewhere
                LOGGER.trace("Not deregistering JDBC driver {} as it does not belong to this webapp's ClassLoader", driver);
            }
        }
    }
    // </editor-fold>
}
