package pedrociarlini.marcao.caixa.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class JanelaFechamentoDeCaixa extends JInternalFrame {

	private static JanelaFechamentoDeCaixa instance;  //  @jve:decl-index=0:visual-constraint="643,7"
	private JPanel contentPaneMain = null;
    private PanelVendasDoDia panelVendasDoDia = null;
    private JPanel panelCadastro = null;
    private JLabel labelValorVenda = null;
    private JTextField textFieldValorVenda = null;
    private JLabel labelData = null;
    private JComboBox comboBoxData = null;
    private JPanel panelBotoes = null;
    private JButton buttonSalvar = null;
    private JButton Alterar = null;
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
		this.setSize(488, 392);
		this.setTitle("Fechamento de Caixa");
		this.setContentPane(getContentPaneMain());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getContentPaneMain() {
		if (contentPaneMain == null) {
			contentPaneMain = new JPanel();
			contentPaneMain.setLayout(new BorderLayout());
			contentPaneMain.add(getPanelVendasDoDia(), java.awt.BorderLayout.NORTH);
			contentPaneMain.add(getPanelCadastro(), java.awt.BorderLayout.CENTER);
		}
		return contentPaneMain;
	}

	public static JanelaFechamentoDeCaixa getInstance() {
		if (instance == null) {
			instance = new JanelaFechamentoDeCaixa();
		}
		return instance;
	}

    /**
     * This method initializes panelVendasDoDia	
     * 	
     * @return pedrociarlini.marcao.caixa.view.PanelVendasDoDia	
     */
    private PanelVendasDoDia getPanelVendasDoDia() {
        if (panelVendasDoDia == null) {
            panelVendasDoDia = new PanelVendasDoDia();
            panelVendasDoDia.setPreferredSize(new java.awt.Dimension(453,150));
        }
        return panelVendasDoDia;
    }

   /**
     * This method initializes panelCadastro @ gridBagCo
     * jLabel.setText("JLabel"); nstraints5.gridy = 3;
     * panelCadastro.add(getPanelBotoes(), gridBagConstraints5);
     * labelTipoVenda.setText("JLabel"); gridBagConstraints5.gridwidth = 2;
     * gridBagConstraints5.weightx = 1.0; gridBagConstraints5.fill =
     * java.awt.GridBagConstraints.BOTH; panelCadastro.add(getPanelBotoes
     * panelCadastro.add(labelTipoVenda, gridBagConstraints6); a(),
     * gridBagConstraints4); return javax.swing.JPanel
     */
    private JPanel getPanelCadastro() {
        if (panelCadastro == null) {
            GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
            gridBagConstraints8.gridx = 0;
            gridBagConstraints8.gridy = 2;
            GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
            gridBagConstraints7.gridx = 0;
            gridBagConstraints7.gridwidth = 2;
            gridBagConstraints7.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints7.gridy = 3;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.gridy = 1;
            gridBagConstraints2.weightx = 1.0;
            gridBagConstraints2.gridx = 1;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 0;
            gridBagConstraints11.anchor = java.awt.GridBagConstraints.EAST;
            gridBagConstraints11.gridy = 1;
            labelData = new JLabel();
            labelData.setText("Data");
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.weightx = 0.7;
            gridBagConstraints1.gridwidth = 1;
            gridBagConstraints1.insets = new java.awt.Insets(2,2,2,2);
            gridBagConstraints1.gridx = 1;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.weightx = 0.4;
            gridBagConstraints.ipadx = 2;
            gridBagConstraints.ipady = 2;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
            gridBagConstraints.insets = new java.awt.Insets(2,2,2,2);
            gridBagConstraints.gridy = 0;
            labelValorVenda = new JLabel();
            labelValorVenda.setText("Valor");
            panelCadastro = new JPanel();
            panelCadastro.setLayout(new GridBagLayout());
            panelCadastro.add(labelValorVenda, gridBagConstraints);
            panelCadastro.add(getTextFieldValorVenda(), gridBagConstraints1);
            panelCadastro.add(labelData, gridBagConstraints11);
            panelCadastro.add(getComboBoxData(), gridBagConstraints2);
            panelCadastro.add(getPanelBotoes(), gridBagConstraints7);
        }
        return panelCadastro;
    }

    /**
     * This method initializes textFieldValorVenda	
     * 	
     * @return javax.swing.JTextField	
     */
    private JTextField getTextFieldValorVenda() {
        if (textFieldValorVenda == null) {
            textFieldValorVenda = new JTextField();
        }
        return textFieldValorVenda;
    }

    /**
     * This method initializes comboBoxData	
     * 	
     * @return javax.swing.JComboBox	
     */
    private JComboBox getComboBoxData() {
        if (comboBoxData == null) {
            comboBoxData = new JComboBox();
        }
        return comboBoxData;
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
            panelBotoes.add(getAlterar(), null);
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
            buttonSalvar.setText("Salvar");
        }
        return buttonSalvar;
    }

    /**
     * This method initializes Alterar	
     * 	
     * @return javax.swing.JButton	
     */
    private JButton getAlterar() {
        if (Alterar == null) {
            Alterar = new JButton();
            Alterar.setText("Alterar");
        }
        return Alterar;
    }

}  //  @jve:decl-index=0:visual-constraint="14,7"