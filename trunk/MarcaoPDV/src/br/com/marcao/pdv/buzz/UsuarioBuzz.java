package br.com.marcao.pdv.buzz;

import br.com.marcao.helpers.EncryptHelper;
import br.com.marcao.pdv.dao.UsuarioDAO;
import br.com.marcao.pdv.entity.UsuarioEntity;

public class UsuarioBuzz extends MainBuzz<UsuarioEntity> {

    private UsuarioDAO usuDao;

    UsuarioBuzz() {
    }

    @Override
    protected UsuarioDAO getMainDao() {
        if (usuDao == null) {
            usuDao = new UsuarioDAO();
        }
        return usuDao;
    }

    public UsuarioEntity insertUsuario(UsuarioEntity usu) {
        String newSenha = EncryptHelper.convertToMd5(usu.getSenha());
        usu.setSenha(newSenha);
        usu = getMainDao().insertUsuario(usu);
        return usu;
    }

    /**
     * Verufica o login e senha passdos por par‰metros.
     * 
     * @param usu
     * @return
     */
    public boolean autenticaUsuario(UsuarioEntity usu) {
        UsuarioEntity usuResult = null;

        usu.setSenha(EncryptHelper.convertToMd5(usu.getSenha()));

        usuResult = getMainDao().findByLogin(usu);

        if (usuResult != null) {
            return usu.getSenha().equals(usuResult.getSenha());
        } else {
            return false;
        }
    }
}
