package pedrociarlini.marcao.caixa.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.JButton;

import pedrociarlini.marcao.caixa.model.TipoPagamento;
import pedrociarlini.marcao.caixa.model.Venda;
import pedrociarlini.marcao.caixa.view.actions.SalvarTipoDePagamentoAction;

public class JanelaTipoDeVenda extends JInternalFrame {

	private JPanel jContentPane = null;
	private JTabbedPane tabbedPane = null;
	private JPanel panelTiposDeVenda = null;
	private JPanel panelEdicao = null;
	private JPanel panelBotoes = null;
	private JButton buttonSalvar = null;
	private PanelTipoVenda panelTipoVenda = null;
	private SalvarTipoDePagamentoAction salvarTipoDePagamentoAction = null;  //  @jve:decl-index=0:visual-constraint=""

	/**
	 * This is the default constructor
	 */
	public JanelaTipoDeVenda() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(336, 202);
		this.setIconifiable(true);
		this.setPreferredSize(new java.awt.Dimension(50,40));
		this.setTitle("Tipo de Venda");
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
			jContentPane.add(getTabbedPane(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes tabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane();
			tabbedPane.addTab("Cadastrados", null, getPanelTiposDeVenda(), null);
			tabbedPane.addTab("Edição", null, getPanelEdicao(), null);
		}
		return tabbedPane;
	}

	/**
	 * This method initializes panelTiposDeVenda	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelTiposDeVenda() {
		if (panelTiposDeVenda == null) {
			panelTiposDeVenda = new JPanel();
		}
		return panelTiposDeVenda;
	}

	/**
	 * This method initializes panelEdicao	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelEdicao() {
		if (panelEdicao == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(5);
			borderLayout.setVgap(5);
			panelEdicao = new JPanel();
			panelEdicao.setLayout(borderLayout);
			panelEdicao.add(getPanelBotoes(), java.awt.BorderLayout.SOUTH);
			panelEdicao.add(getPanelTipoVenda(), java.awt.BorderLayout.CENTER);
		}
		return panelEdicao;
	}

	/**
	 * This method initializes panelBotoes	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPanelBotoes() {
		if (panelBotoes == null) {
			panelBotoes = new JPanel();
			panelBotoes.add(getButtonSalvar(), null);
		}
		return panelBotoes;
	}

	/**
	 * This method initializes buttonSalvar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getButtonSalvar() {
		if (buttonSalvar == null) {
			buttonSalvar = new JButton();
			buttonSalvar.setActionCommand("Salvar");
			buttonSalvar.setAction(getSalvarTipoDePagamentoAction());
			buttonSalvar.setText("Salvar");
			buttonSalvar.setMnemonic(java.awt.event.KeyEvent.VK_S);
			buttonSalvar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					TipoPagamento tipo = new TipoPagamento(getPanelTipoVenda()
							.getTextFieldDescricaoTipoVenda().getText());
					getSalvarTipoDePagamentoAction().putValue(
							SalvarTipoDePagamentoAction.KEY_TIPO_DE_PAGAMENTO,
							tipo);
				}
			});
		}
		return buttonSalvar;
	}

	/**
	 * This method initializes panelTipoVenda	
	 * 	
	 * @return pedrociarlini.marcao.caixa.view.PanelTipoVenda	
	 */
	private PanelTipoVenda getPanelTipoVenda() {
		if (panelTipoVenda == null) {
			panelTipoVenda = new PanelTipoVenda();
		}
		return panelTipoVenda;
	}

	/**
	 * This method initializes salvarTipoDePagamentoAction	
	 * 	
	 * @return pedrociarlini.marcao.caixa.view.actions.SalvarTipoDePagamentoAction	
	 */
	private SalvarTipoDePagamentoAction getSalvarTipoDePagamentoAction() {
		if (salvarTipoDePagamentoAction == null) {
			salvarTipoDePagamentoAction = new SalvarTipoDePagamentoAction();
		}
		return salvarTipoDePagamentoAction;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
