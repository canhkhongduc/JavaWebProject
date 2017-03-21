/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import model.Choice;
import model.Question;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author Le Cao Nguyen
 */
public class ChoiceManager extends TransactionPerformer {

    public List<Choice> getChoices(Question question) {
        return performTransaction((session) -> {
            Criteria criteria = session.createCriteria(Choice.class);
            criteria.addOrder(Order.asc("id"));
            criteria.add(Restrictions.eq("question", question));
            return criteria.list();
        });
    }

    public Choice getChoice(Long id) {
        return performTransaction((session) -> {
            return (Choice) session.get(Choice.class, id);
        });
    }
}
