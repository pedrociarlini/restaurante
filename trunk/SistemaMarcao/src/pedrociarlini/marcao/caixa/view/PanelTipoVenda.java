package pedrociarlini.marcao.caixa.view;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class PanelTipoVenda extends JPanel {

	private JTextField textFieldDescricaoTipoVenda = null;
	private JCheckBox checkBoxPagamentoAVista = null;
	private JLabel labelDescricao = null;
	private JLabel jLabel = null;

	/**
	 * This is the default constructor
	 */
	public PanelTipoVenda() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.insets = new java.awt.Insets(5,5,5,5);
		gridBagConstraints3.anchor = java.awt.GridBagConstraints.EAST;
		gridBagConstraints3.gridy = 1;
		jLabel = new JLabel();
		jLabel.setText("A vista");
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.weightx = 0.5;
		gridBagConstraints2.insets = new java.awt.Insets(5,5,5,5);
		gridBagConstraints2.anchor = java.awt.GridBagConstraints.EAST;
		gridBagConstraints2.gridy = 0;
		labelDescricao = new JLabel();
		labelDescricao.setText("Descrição");
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints1.insets = new java.awt.Insets(5,5,5,5);
		gridBagConstraints1.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.NONE;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 0.5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5,5,5,5);
		gridBagConstraints.gridx = 1;
		this.setLayout(new GridBagLayout());
		this.setSize(300, 95);
		this.add(getTextFieldDescricaoTipoVenda(), gridBagConstraints);
		this.add(getCheckBoxPagamentoAVista(), gridBagConstraints1);
		this.add(labelDescricao, gridBagConstraints2);
		this.add(jLabel, gridBagConstraints3);
	}

	/**
	 * This method initializes textFieldDescricaoTipoVenda	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	public JTextField getTextFieldDescricaoTipoVenda() {
		if (textFieldDescricaoTipoVenda == null) {
			textFieldDescricaoTipoVenda = new JTextField();
			textFieldDescricaoTipoVenda.setPreferredSize(new java.awt.Dimension(120,20));
		}
		return textFieldDescricaoTipoVenda;
	}

	/**
	 * This method initializes checkBoxPagamentoAVista	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	public JCheckBox getCheckBoxPagamentoAVista() {
		if (checkBoxPagamentoAVista == null) {
			checkBoxPagamentoAVista = new JCheckBox();
		}
		return checkBoxPagamentoAVista;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
