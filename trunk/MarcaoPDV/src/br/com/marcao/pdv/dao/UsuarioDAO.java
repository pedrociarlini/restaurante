package br.com.marcao.pdv.dao;

import javax.persistence.Query;

import br.com.marcao.pdv.entity.UsuarioEntity;

public class UsuarioDAO extends MainDAO {
    
    public UsuarioEntity insertUsuario(UsuarioEntity usu) {
        UsuarioEntity newUsu;

        getDBSession().getTransaction().begin();
        
        getDBSession().persist(usu);
        newUsu = usu;        
        
        getDBSession().getTransaction().commit();
        
        return newUsu;
    }

    public UsuarioEntity findByLogin(UsuarioEntity usu) {
        UsuarioEntity usuResult = null;
        
        Query q = getDBSession().createNamedQuery("findUsuarioByLogin");
        q.setParameter("login", usu.getLogin());
        
        usuResult = (UsuarioEntity) q.getSingleResult();
        
        return usuResult;
    }
}
