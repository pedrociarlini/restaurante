package br.com.marcao.pdv.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.marcao.pdv.entity.MesaEntity;

public class MainDAO {
    
    protected EntityManager getDBSession() {
        return HibernateUtil.getSession();
    }
    
    /**
     * Retorna uma lista de todos os objetos da classe selecionada.
     * @param classe
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getAll(Class<MesaEntity> classe) {
        Query q = getDBSession().createQuery("select o from " + classe.getName() + " o");
        
        return (List<T>) q.getResultList();
    }
}
