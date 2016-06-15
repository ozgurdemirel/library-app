package com.library.app.commontests.utils;

import org.junit.Ignore;

import javax.persistence.EntityManager;

/**
 * Created by ozgur.demirel on 24.03.2016.
 */
@Ignore
public class DBCommandTransactionalExecutor {
    private EntityManager em;

    public DBCommandTransactionalExecutor(EntityManager em){
        this.em = em;
    }

    public <T> T executeCommand(DBCommand<T> dbCommand){
        try {
            em.getTransaction().begin();
            T toReturn = dbCommand.execute();
            em.getTransaction().commit();
            em.clear();
            return toReturn;
        } catch (Exception ex) {
            ex.printStackTrace();
            em.getTransaction().rollback();
            throw new IllegalStateException(ex);
        }
    }

}
