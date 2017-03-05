package dao;

import util.hibernate.HibernateUtil;
import java.util.List;
import model.Permission;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nguyen
 */
public class PermissionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionManager.class);
    
    private final SessionFactory sessionFactory;

    public PermissionManager() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Permission> getAllPermissions() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Permission.class);
        List<Permission> permissions = criteria.list();
        session.getTransaction().commit();
        return permissions;
    }
    
    public Permission getPermission(String name) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Permission permission = (Permission) session.get(Permission.class, name);
        session.getTransaction().commit();
        return permission;
    }
}
