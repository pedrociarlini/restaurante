package br.com.marcao.pdv.ui.actions;

import java.awt.event.ActionEvent;

public class CriarUsuarioAction extends MainAbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CriarUsuarioAction() {
        putValue(NAME, "Criar usu�rio");
        putValue(SHORT_DESCRIPTION, "Cria��o de usu�rio.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new RuntimeException("N�o implementado ainda.");
        // UsuarioBuzz bussiness = BuzzFactory.getInstance().retrieveBuzzInstance(BuzzFactory.BUZZ_USUARIO);
        // bussiness.insertUsuario(new UsuarioEntity("pedro", "12345"));
    }
}
