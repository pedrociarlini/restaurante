package br.com.marcao.pdv.ui.actions;

import javax.swing.AbstractAction;

import br.com.marcao.pdv.buzz.BuzzFactory;
import br.com.marcao.pdv.buzz.UsuarioBuzz;

@SuppressWarnings("serial")
public abstract class MainAbstractAction extends AbstractAction {

    private UsuarioBuzz usuBuzz = null;

    protected UsuarioBuzz getUsuarioBuzz() {
        if (usuBuzz == null) {
            usuBuzz = BuzzFactory.getInstance().retrieveBuzzInstance(BuzzFactory.BUZZ_USUARIO);
        }
        
        return usuBuzz;
    }
}
