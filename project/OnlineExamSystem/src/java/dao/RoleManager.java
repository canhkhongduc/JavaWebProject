/*
 * Copyright © 2017 Six Idiots Team
 */
package dao;

import java.util.List;
import model.Account;
import model.Role;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
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

    public boolean saveRole(Role role) {
        return performTransaction((session) -> {
            session.save(role);
        });
    }

    public boolean updateRole(Role role) {
        return performTransaction((session) -> {
            session.update(role);
        });
    }

    public boolean deleteRole(Role role) {
        if (!removeAllReferences(role)) {
            throw new HibernateException("Could not remove all references to role.");
        }
        return performTransaction((session) -> {
            session.delete(role);
        });
    }

    /**
     * Reference removal for role:<br>
     * - All accounts belong to this role -> remove role from account.roles<br>
     */
    public boolean removeAllReferences(Role role) {
        return performTransaction((session) -> {
            List<Account> assignedAccounts = session.createCriteria(Account.class).createAlias("roles", "rolesAlias")
                    .add(Restrictions.eq("rolesAlias.name", role.getName())).list();
            assignedAccounts.forEach((account) -> {
                account.removeRole(role);
                session.update(account);
            });
        });
    }
}
