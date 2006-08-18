package pedrociarlini.marcao.caixa.view.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import pedrociarlini.marcao.caixa.view.JanelaFechamentoDeCaixa;

public class AbrirFechamentoDeCaixa extends AbstractAction {

	public void actionPerformed(ActionEvent e) {
		JanelaFechamentoDeCaixa janela = JanelaFechamentoDeCaixa.getInstance();
		janela.setVisible(true);
	}

}