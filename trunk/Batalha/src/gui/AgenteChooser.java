package gui;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListDataListener;

import model.AgenteVO;

import algoritmo.ProgramaAbstract;
import algoritmo.agentes.AgentesManager;

@SuppressWarnings("unchecked")
public class AgenteChooser extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JComboBox listAgentes = null;

	private JLabel labelAgente = null;

	private JButton buttonOk = null;

	private JPanel panelBotoes = null;

	private JButton buttonCancelar = null;

	private AgenteVO agenteVO;

	/**
	 * This is the default constructor
	 */
	public AgenteChooser() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(459, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("Escolher agente");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 0;
			gridBagConstraints2.gridwidth = 2;
			gridBagConstraints2.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints2.gridy = 3;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints11.gridy = 0;
			labelAgente = new JLabel();
			labelAgente.setText("Agentes disponiveis:");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.gridwidth = 1;
			gridBagConstraints1.gridy = 0;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(getListAgentes(), gridBagConstraints1);
			jContentPane.add(labelAgente, gridBagConstraints11);
			jContentPane.add(getPanelBotoes(), gridBagConstraints2);
		}
		return jContentPane;
	}

	/**
	 * This method initializes textFieldNome
	 * 
	 * @return javax.swing.JTextField
	 */
	private JComboBox getListAgentes() {
		if (listAgentes == null) {
			listAgentes = new JComboBox();
			listAgentes.setModel(new AgentesListModel());
			listAgentes.updateUI();
		}
		return listAgentes;
	}

	/**
	 * This method initializes buttonOk
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getButtonOk() {
		if (buttonOk == null) {
			buttonOk = new JButton();
			buttonOk.setText("Ok");
			buttonOk.addActionListener(this);
		}
		return buttonOk;
	}

	/**
	 * This method initializes panelBotoes
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelBotoes() {
		if (panelBotoes == null) {
			panelBotoes = new JPanel();
			panelBotoes.setLayout(new FlowLayout());
			panelBotoes.add(getButtonOk(), null);
			panelBotoes.add(getButtonCancelar(), null);
		}
		return panelBotoes;
	}

	/**
	 * This method initializes buttonCancelar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getButtonCancelar() {
		if (buttonCancelar == null) {
			buttonCancelar = new JButton();
			buttonCancelar.setText("Cancelar");
			buttonCancelar.addActionListener(this);
		}
		return buttonCancelar;
	}

	public static AgenteVO showJogadorDialog() {
		AgenteChooser janela = new AgenteChooser(); // @ivj
		janela.setModal(true);
		janela.setVisible(true);
		return janela.agenteVO;
	} // @ijv

	public static AgenteVO showJogadorDialog(ProgramaAbstract atual) {
		AgenteChooser janela = new AgenteChooser(); // @ivj
		janela.setModal(true);
		janela.setVisible(true);
		return janela.agenteVO;
	} // @ijv

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getButtonOk()) {
			this.agenteVO = (AgenteVO) AgentesManager
					.getClassByName((String) getListAgentes().getSelectedItem());
			dispose();
		} else if (e.getSource() == getButtonCancelar()) {
			dispose();
		}
	}

	public static class AgentesListModel implements ComboBoxModel {
		private List<String> elements = new ArrayList<String>(AgentesManager
				.getClassesNameSet());
		int selectedIndex = 0;

		public Object getSelectedItem() {
			return (elements.size() > 0) ? elements.get(selectedIndex) : -1;
		}

		public void setSelectedItem(Object anItem) {
			selectedIndex = elements.indexOf(anItem);
		}

		public void addListDataListener(ListDataListener l) {
		}

		public Object getElementAt(int index) {
			return elements.get(index);
		}

		public int getSize() {
			return elements.size();
		}

		public void removeListDataListener(ListDataListener l) {
		}

	}
}
