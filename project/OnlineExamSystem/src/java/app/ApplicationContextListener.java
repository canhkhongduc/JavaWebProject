/*
 * Copyright © 2017 Six Idiots Team
 */
package app;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import util.hibernate.HibernateUtil;

/**
 *
 * @author Le Cao Nguyen
 */
public class ApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Initialize session factory
        HibernateUtil.getSessionFactory();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.getSessionFactory().close();
    }
    
}
