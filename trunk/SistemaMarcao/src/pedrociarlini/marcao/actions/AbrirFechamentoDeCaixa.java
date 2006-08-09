package pedrociarlini.marcao.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import pedrociarlini.marcao.view.JanelaFechamentoDeCaixa;

public class AbrirFechamentoDeCaixa extends AbstractAction {

	public void actionPerformed(ActionEvent e) {
		JanelaFechamentoDeCaixa janela = JanelaFechamentoDeCaixa.getInstance();
		janela.setVisible(true);
	}

}