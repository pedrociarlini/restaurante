package gui;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 * Implmenta a tabela que mostra as informações sobre os soldados.
 * 
 * @author <Aluno do André>
 */
@SuppressWarnings("serial")
public class GridPanel extends JPanel {

	private JTable table;
	private DefaultTableModel dtm;

	public GridPanel() {
		mudaLookAndFeel();
		dtm = new DefaultTableModel();
		table = new JTable(dtm);
		this.setLayout(new GridLayout(1, 1));
		this.add(new JScrollPane(table));
	}

	public void addColuna(String nomeColuna) {
		dtm.addColumn(nomeColuna);
	}

	public void addLinha(String[] linha) {
		dtm.addRow(linha);
	}

	public void removeLinha(int lin) {
		if (dtm.getRowCount() > 0) {
			dtm.removeRow(lin);
		}
	}

	public void mudaLookAndFeel() {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {

		String arrayValores[] = { "AAA", "BBB", "CCC", "DDD" };

		GridPanel geracoes = new GridPanel();

		geracoes.addColuna("C1");
		geracoes.addColuna("C2");
		geracoes.addColuna("C3");
		geracoes.addColuna("C4");

		for (int i = 0; i < 500; i++) {
			geracoes.addLinha(arrayValores);
		}

		JFrame frame = new JFrame("Teste");
		frame.add(geracoes);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
