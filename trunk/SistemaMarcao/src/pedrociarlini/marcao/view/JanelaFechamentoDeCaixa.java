package pedrociarlini.marcao.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;

public class JanelaFechamentoDeCaixa extends JInternalFrame {

	private static JanelaFechamentoDeCaixa instance;  //  @jve:decl-index=0:visual-constraint="386,8"
	private JPanel jContentPane = null;

	/**
	 * This is the default constructor
	 */
	public JanelaFechamentoDeCaixa() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setTitle("Fechamento de Caixa");
		this.setContentPane(getJContentPane());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
		}
		return jContentPane;
	}

	public static JanelaFechamentoDeCaixa getInstance() {
		if (instance == null) {
			instance = new JanelaFechamentoDeCaixa();
		}
		return instance;
	}

}  //  @jve:decl-index=0:visual-constraint="14,7"