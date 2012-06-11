package br.com.marcao.pdv.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

import br.com.marcao.pdv.entity.UsuarioEntity;
import br.com.marcao.pdv.ui.MarcaoPDVMainWindow;
import br.com.marcao.pdv.ui.MarcaoPDVSessionVariables;
import br.com.marcao.ui.helpers.SwingHelper;

/**
 * Efetuas as opeações necessárias para entrar nbo sistema, autenticando o usuário, e iniciando a janela principal.
 * @author pedrociarlini
 *
 */
public class EntrarNoSistemaAction extends MainAbstractAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField usuario;
    private JTextField senha;
    private JFrame janelaLogin;
    
    public EntrarNoSistemaAction() {
        putValue(NAME, "Entrar");
        putValue(SHORT_DESCRIPTION, "Entrar no sistema autenticando o usuário.");
    }

    public EntrarNoSistemaAction(JTextField usuario, JTextField senha, JFrame janelaLogin) {
        this();
        this.usuario = usuario;
        this.senha = senha;
        this.janelaLogin = janelaLogin;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            UsuarioEntity usu = new UsuarioEntity(usuario.getText(), senha.getText());
            
            if (getUsuarioBuzz().autenticaUsuario(usu)) {
                MarcaoPDVSessionVariables.usuarioLogado = usu;
                janelaLogin.setVisible(false);
                janelaLogin.dispose();
                new MarcaoPDVMainWindow().setVisible(true);
            }
        } catch (Exception ex) {
            SwingHelper.showErrorMessage("Erro no login:" + ex.getMessage());
        }
    }
}