/*
 * Copyright © 2017 Six Idiots Team
 */
package util.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author nguyen
 */
public class HibernateUtil {
    private static final Configuration CONFIGURATION;
    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
            CONFIGURATION = new Configuration();
            CONFIGURATION.configure();
            ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(CONFIGURATION.getProperties()).build();
            SESSION_FACTORY = CONFIGURATION.buildSessionFactory(registry);
        } catch (HibernateException ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
    
    public static Configuration getConfiguration() {
        return CONFIGURATION;
    }
}
