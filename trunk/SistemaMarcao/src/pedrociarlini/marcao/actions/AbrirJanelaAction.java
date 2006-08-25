package pedrociarlini.marcao.actions;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class AbrirJanelaAction extends AbstractAction {

	public static final String KEY_JANELA = "KEY_JANELA";
	
	public void actionPerformed(ActionEvent ev) {
		Window janela = (Window) getValue(KEY_JANELA);
		configurarJanela(janela);
		janela.setVisible(true);
	}
	
	protected void configurarJanela(Window janela) {
		
	}

}
