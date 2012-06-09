package br.com.marcao.pdv.dao;

import javax.persistence.EntityManager;

public class MainDAO {
    protected EntityManager getDBSession() {
        return HibernateUtil.getSession();
    }

}
