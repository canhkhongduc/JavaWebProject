package dao;

import util.hibernate.HibernateUtil;
import java.util.List;
import model.Group;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nguyen
 */
public class GroupManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupManager.class);
    
    private final SessionFactory sessionFactory;

    public GroupManager() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Group> getAllGroups() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Group.class);
        List<Group> accounts = criteria.list();
        session.getTransaction().commit();
        return accounts;
    }
    
    public Group getGroup(String name) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Group account = (Group) session.get(Group.class, name);
        session.getTransaction().commit();
        return account;
    }
}
