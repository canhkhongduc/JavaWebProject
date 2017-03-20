/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import model.Role;
import util.hibernate.transaction.TransactionPerformer;

/**
 *
 * @author nguyen
 */
public class RoleManager extends TransactionPerformer {

    public List<Role> getAllRoles() {
        return performTransaction((session) -> {
            return session.createCriteria(Role.class).list();
        });
    }

    public Role getRole(String name) {
        return performTransaction((session) -> {
            return (Role) session.get(Role.class, name);
        });
    }
}
