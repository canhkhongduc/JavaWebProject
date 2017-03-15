/*
 * Copyright © 2017 Six Idiots Team
 */
package util.hibernate.transaction;

import org.hibernate.Session;

/**
 *
 * @author Le Cao Nguyen
 */
@FunctionalInterface
public interface UpdateTransaction {
    public void apply(Session session) throws RuntimeException;
}
