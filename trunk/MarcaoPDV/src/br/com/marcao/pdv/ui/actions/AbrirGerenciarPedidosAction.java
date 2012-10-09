package br.com.marcao.pdv.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.JDesktopPane;

import pedrociarlini.reuse.ui.helpers.SwingHelper;
import br.com.marcao.pdv.ui.GerenciarPedidosWindow;

public class AbrirGerenciarPedidosAction extends MainAbstractAction {
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
        try {
            GerenciarPedidosWindow.getInstance().setVisible(true);
            pane.add(GerenciarPedidosWindow.getInstance());
        } catch (Exception ex) {
            SwingHelper.showErrorMessage("N‹o foi poss’vel abrir a janela de pedidos.");
            ex.printStackTrace();
        }
    }
}
