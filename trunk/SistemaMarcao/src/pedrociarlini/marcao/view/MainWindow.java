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
	private AbrirFechamentoDeCaixa abrirFechamentoDeCaixa = null;  //  @jve:decl-index=0:visual-constraint="628,58"
	private JDesktopPane mainPane = null;
	private JMenu menuCadastro = null;
	private JMenuItem menuItemTipoDeVenda = null;
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
		this.setSize(559, 320);
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
			menuOperacoes.setActionCommand("Fechar Caixa...");
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
			mainMenuBar.add(getMenuCadastro());
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

	/**
	 * This method initializes menuCadastro	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMenuCadastro() {
		if (menuCadastro == null) {
			menuCadastro = new JMenu();
			menuCadastro.setText("Cadastro");
			menuCadastro.setMnemonic(java.awt.event.KeyEvent.VK_C);
			menuCadastro.add(getMenuItemTipoDeVenda());
		}
		return menuCadastro;
	}

	/**
	 * This method initializes menuItemTipoDeVenda	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMenuItemTipoDeVenda() {
		if (menuItemTipoDeVenda == null) {
			menuItemTipoDeVenda = new JMenuItem();
			menuItemTipoDeVenda.setText("Tipo de Venda...");
			menuItemTipoDeVenda.setMnemonic(java.awt.event.KeyEvent.VK_T);
		}
		return menuItemTipoDeVenda;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
