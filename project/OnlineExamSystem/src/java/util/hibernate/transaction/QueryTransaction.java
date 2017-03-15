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
public interface QueryTransaction<T> {
    public T apply(Session session) throws RuntimeException;
}
