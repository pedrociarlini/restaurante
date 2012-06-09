package br.com.marcao.pdv.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.marcao.pdv.entity.UsuarioEntity;

public class UsuarioDAOTest {

    @Test
    public void testFindByLogin() {
        UsuarioDAO usuDao = new UsuarioDAO();
        UsuarioEntity usuActual, usu = new UsuarioEntity("adm", "12345");
        
        usuActual = usuDao.findByLogin(usu);
        
        assertNotNull("Usu‡rio adm deveria existir.", usuActual);
        
        assertEquals("ID esperado incorreto.", 1, usuActual.getId());
    }

}
