package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public class Linha extends JPanel {
	private JPanel panelIcon;

	private JPanel panelDescricao;

	public Linha(JPanel descricao, JPanel icon) {
		panelDescricao = descricao;
		panelIcon = icon;

		this.setLayout(new GridBagLayout());

		add(panelIcon, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));

		add(panelDescricao, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 0), 0, 0));

		add(panelIcon);
		add(panelDescricao);
	}

	public JPanel getPanelDescricao() {
		return panelDescricao;
	}

	public void setPanelDescricao(JPanel panelDescricao) {
		this.panelDescricao = panelDescricao;
	}

	public JPanel getPanelIcon() {
		return panelIcon;
	}

	public void setPanelIcon(JPanel panelIcon) {
		this.panelIcon = panelIcon;
	}
}