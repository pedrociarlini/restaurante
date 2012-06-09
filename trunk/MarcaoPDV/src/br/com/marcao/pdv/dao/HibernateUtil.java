package br.com.marcao.pdv.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static EntityManager manager;

    public static void setUpHibernate() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MarcaoPDV");
        manager = factory.createEntityManager();
    }

    public static EntityManager getSession() {
        if (manager == null) {
            setUpHibernate();
        }

        return manager;
    }
}
