package br.com.marcao.pdv.ui.actions;

import java.awt.event.ActionEvent;

public class CriarUsuarioAction extends MainAbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CriarUsuarioAction() {
        putValue(NAME, "Criar usuário");
        putValue(SHORT_DESCRIPTION, "Criação de usuário.");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new RuntimeException("Não implementado ainda.");
        // UsuarioBuzz bussiness = BuzzFactory.getInstance().retrieveBuzzInstance(BuzzFactory.BUZZ_USUARIO);
        // bussiness.insertUsuario(new UsuarioEntity("pedro", "12345"));
    }
}
