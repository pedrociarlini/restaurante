package br.com.marcao.pdv.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDesktopPane;

import br.com.marcao.pdv.ui.GerenciarPedidosWindow;

public class AbrirGerenciarPedidosAction extends AbstractAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JDesktopPane pane;

    public AbrirGerenciarPedidosAction(JDesktopPane pane) {
        this.pane = pane;
        putValue(NAME, "Gerenciar Pedidos");
        putValue(SHORT_DESCRIPTION, "Abre a Janela de gerenciar pedidos.");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        GerenciarPedidosWindow.getInstance().setVisible(true);
        pane.add(GerenciarPedidosWindow.getInstance());
    }
}
