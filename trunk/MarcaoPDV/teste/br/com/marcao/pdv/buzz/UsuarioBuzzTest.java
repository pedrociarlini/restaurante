package br.com.marcao.pdv.buzz;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.com.marcao.pdv.entity.UsuarioEntity;

public class UsuarioBuzzTest {

    private UsuarioBuzz usuBuzz;

    @Before
    public void setUp() throws Exception {
        usuBuzz = BuzzFactory.getInstance().retrieveBuzzInstance("Usuario");
    }

    @Test
    public void testInsertUsuario() {
        try {
            UsuarioEntity usu = new UsuarioEntity("teste", "12345");
            usuBuzz.insertUsuario(usu);

            assertTrue("Id deve ser gerado pelo BD.", 0 != usu.getId());
            assertEquals("Senha está errada.", usu.getSenha(), "827ccb0eea8a706c4c34a16891f84e7b");

        } catch (Exception ex) {
            ex.printStackTrace();
            assertTrue("Não foi possível inserir.", false);
        }
    }

    @Test
    public void testAutenticaUsuario_Sucesso() {
        UsuarioEntity usu = new UsuarioEntity("adm", "12345");
        assertTrue("A autenticação deveria obeter sucesso.", usuBuzz.autenticaUsuario(usu));
    }

    @Test
    public void testAutenticaUsuario_Fracasso_senha() {
        UsuarioEntity usu = new UsuarioEntity("adm", "1");
        assertFalse(usuBuzz.autenticaUsuario(usu));
    }

    @Test
    public void testAutenticaUsuario_Fracasso_login() {
        UsuarioEntity usu = new UsuarioEntity("adm", "1");
        assertFalse(usuBuzz.autenticaUsuario(usu));
    }
}
