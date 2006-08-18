package pedrociarlini.marcao.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import pedrociarlini.marcao.caixa.view.JanelaFechamentoDeCaixa;
import pedrociarlini.marcao.caixa.view.actions.AbrirFechamentoDeCaixa;

import javax.swing.JDesktopPane;

public class MainWindow extends JFrame {

	private JMenu mainMenu = null;
	private JMenuItem menuOperacoes = null;
	private JMenuBar mainMenuBar = null;
	private AbrirFechamentoDeCaixa abrirFechamentoDeCaixa = null;  //  @jve:decl-index=0:visual-constraint="553,370"
	private JDesktopPane mainPane = null;
	/**
	 * This is the default constructor
	 */
	public MainWindow() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(680, 348);
		this.setContentPane(getMainPane());
		this.setJMenuBar(getMainMenuBar());
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sistema da Vandona");
	}

	/**
	 * This method initializes mainMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMainMenu() {
		if (mainMenu == null) {
			mainMenu = new JMenu();
			mainMenu.setText("Operações");
			mainMenu.setMnemonic(java.awt.event.KeyEvent.VK_O);
			mainMenu.add(getMenuOperacoes());
		}
		return mainMenu;
	}

	/**
	 * This method initializes menuOperacoes	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuOperacoes() {
		if (menuOperacoes == null) {
			menuOperacoes = new JMenuItem();
			menuOperacoes.setAction(getAbrirFechamentoDeCaixa());
			menuOperacoes.setText("Fechar Caixa");
			menuOperacoes.setMnemonic(java.awt.event.KeyEvent.VK_C);
		}
		return menuOperacoes;
	}

	/**
	 * This method initializes mainMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMainMenuBar() {
		if (mainMenuBar == null) {
			mainMenuBar = new JMenuBar();
			mainMenuBar.add(getMainMenu());
		}
		return mainMenuBar;
	}

	/**
	 * This method initializes abrirFechamentoDeCaixa	
	 * 	
	 * @return pedrociarlini.marcao.actions.AbrirFechamentoDeCaixa	
	 */
	private AbrirFechamentoDeCaixa getAbrirFechamentoDeCaixa() {
		if (abrirFechamentoDeCaixa == null) {
			abrirFechamentoDeCaixa = new AbrirFechamentoDeCaixa();
		}
		return abrirFechamentoDeCaixa;
	}

	/**
	 * This method initializes mainPane	
	 * 	
	 * @return javax.swing.JDesktopPane	
	 */
	private JDesktopPane getMainPane() {
		if (mainPane == null) {
			mainPane = new JDesktopPane();
			mainPane.add(JanelaFechamentoDeCaixa.getInstance());
		}
		return mainPane;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
