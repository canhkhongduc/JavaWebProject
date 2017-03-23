/*
 * Copyright © 2017 Six Idiots Team
 */
package util.hibernate;

import model.Account;
import model.Role;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HashingUtil;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author nguyen
 */
public class HibernateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUtil.class);
    
    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
            // Create session factory
            Configuration configuration = new Configuration();
            configuration.configure();
            ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            SESSION_FACTORY = configuration.buildSessionFactory(registry);
            
            // Initialize data if hbm2ddl.auto is create or create-drop
            String hbm2ddl = configuration.getProperty("hibernate.hbm2ddl.auto");
            if (hbm2ddl != null && hbm2ddl.startsWith("create")) {
                if (initializeDataSource()) {
                    LOGGER.info("Initialize data successful.");
                } else {
                    LOGGER.warn("Initialize data failed.");
                }
            }
        } catch (HibernateException ex) {
            LOGGER.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
    
    public static boolean initializeDataSource() {
        TransactionPerformer performer = new TransactionPerformer();
        return performer.performTransaction((session) -> {
            Role adminRole = new Role("admin", "Administrator");
            Role testMasterRole = new Role("testmaster", "Test Master");
            Role studentRole = new Role("student", "Student");

            session.save(adminRole);
            session.save(testMasterRole);
            session.save(studentRole);

            Account adminAccount = new Account("admin", HashingUtil.generateSHA512Hash("admin"));
            adminAccount.getProfile().setFullName("Administrator");
            adminAccount.addRole((Role) session.get(Role.class, "admin"));
            session.save(adminAccount);
        });
    }
}
