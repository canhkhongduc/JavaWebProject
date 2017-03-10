/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import model.Choice;
import model.Question;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.hibernate.HibernateUtil;

/**
 *
 * @author Lam
 */
public class ChoiceManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChoiceManager.class);

    private final SessionFactory sessionFactory;

    public ChoiceManager() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Choice> getAllChoice(Question question) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(Choice.class);
        criteria.add(Restrictions.eq("question", question));
        List<Choice> choiceList = criteria.list();
        session.getTransaction().commit();
        return choiceList;
    }
}
