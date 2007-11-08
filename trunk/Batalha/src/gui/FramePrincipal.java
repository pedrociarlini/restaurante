package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import algoritmo.Agentes;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import controle.Controlador;

@SuppressWarnings("serial")
public class FramePrincipal extends JFrame implements ActionListener {

	// Container
	private Container container;

	// JPanel
	private JPanel panelNorte;
	private JPanel panelNorteEsq;
	private JPanel panelNorteEsqEsq;
	private JPanel panelNorteEsqDir;
	private JPanel panelNorteDir;
	private JPanel panelCentro;
	private JPanel panelLeste;
	private JPanel panelSul;

	// JToolBar
	private JToolBar toolBarControle;

	// JLabel
	private JLabel labelTempo;
	private JLabel labelTempoValor;

	// JButton
	private JButton buttonPlay;
	private JButton buttonPause;
	private JButton buttonStop;
	private JButton buttonCarregar;
	private JButton buttonClose;

	private GridPanel geracoesFit;

	// Controlador
	private Controlador controlador;

	// PainelLabirinto
	private PainelLabirinto painelLabirinto;

	public FramePrincipal() {
		// mudaLookAndFeel();
		initialize();
		constroiFrame();
		this.setVisible(true);
	}

	public void atualizaGrid(List<Agentes> equipes) {

		for (int i = 1; i <= equipes.size(); i++) {
			getGeracoesFit().removeLinha(0);
		}

		for (Agentes element : equipes) {
			String m[] = new String[3];
			m[0] = "Agente_" + element.getArquitetura().getNumeroAgente();
			m[1] = element.getArquitetura().getEnergiaIndividual() + "";
			m[2] = element.getArquitetura().getDirecao();

			getGeracoesFit().addLinha(m);
		}
	}

	private void mudaLookAndFeel() {
		/* LookAndFeel - Windows */
		try {
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initialize() {
		this.setTitle("Labirinto :: v2.0beta");
		this.setSize(400, 395);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Container
		container = this.getContentPane();
		container.setLayout(new BorderLayout());

		container.add(getPanelNorte(), BorderLayout.NORTH);
		container.add(getPanelCentro(), BorderLayout.CENTER);
		container.add(getPanelLeste(), BorderLayout.EAST);
		container.add(getPanelSul(), BorderLayout.SOUTH);

		controlador = new Controlador(this);
	}

	private JPanel getPanelSul() {
		if (panelSul == null) {
			panelSul = new JPanel(new FlowLayout(FlowLayout.LEFT));
			panelSul.setBorder(new BevelBorder(BevelBorder.LOWERED));
			panelSul.setPreferredSize(new Dimension(10, 130));
			panelSul.add(getGeracoesFit());
		}
		return panelSul;
	}

	private GridPanel getGeracoesFit() {
		if (geracoesFit == null) {
			geracoesFit = new GridPanel();
			geracoesFit.addColuna("Agente");
			geracoesFit.addColuna("Energia");
			geracoesFit.addColuna("Direção");
			geracoesFit.setPreferredSize(new Dimension(450, 120));
		}
		return geracoesFit;
	}

	private JPanel getPanelNorte() {
		if (panelNorte == null) {
			panelNorte = new JPanel(new BorderLayout());
			panelNorte.add(getPanelNorteEsq(), BorderLayout.WEST);
			panelNorte.add(getPanelNorteDir(), BorderLayout.EAST);
		}
		return panelNorte;
	}

	private JPanel getPanelNorteDir() {
		if (panelNorteDir == null) {
			panelNorteDir = new JPanel();
			panelNorteDir.add(getToolBarControle(), BorderLayout.EAST);

		}
		return panelNorteDir;
	}

	private JToolBar getToolBarControle() {
		if (toolBarControle == null) {
			toolBarControle = new JToolBar("Controle");
			toolBarControle.add(getButtonPlay());
			toolBarControle.add(getButtonPause());
			toolBarControle.add(getButtonStop());
			toolBarControle.add(getButtonCarregar());
			toolBarControle.add(getButtonClose());
		}
		return toolBarControle;
	}

	private JButton getButtonClose() {
		if (buttonClose == null) {
			buttonClose = new JButton(new ImageIcon("src/image/exit.jpg"));
			buttonClose.setToolTipText("Close");
			buttonClose.addActionListener(this);
		}
		return buttonClose;
	}

	private JButton getButtonCarregar() {
		if (buttonCarregar == null) {
			buttonCarregar = new JButton(new ImageIcon(
					"src/image/carregaArquivo.jpg"));
			buttonCarregar.setToolTipText("Carregar");
			buttonCarregar.addActionListener(this);
		}
		return buttonCarregar;
	}

	private JButton getButtonPause() {
		if (buttonPause == null) {
			buttonPause = new JButton(new ImageIcon("src/image/pause.jpg"));
			buttonPause.setToolTipText("Pause");
			buttonPause.addActionListener(this);
		}
		return buttonPause;
	}

	private JButton getButtonPlay() {
		if (buttonPlay == null) {
			buttonPlay = new JButton(new ImageIcon("src/image/play.jpg"));
			buttonPlay
					.setToolTipText("Inicia ou retorna a execução da simulação.");
			buttonPlay.addActionListener(this);
		}
		return buttonPlay;
	}

	private JPanel getPanelNorteEsq() {
		if (panelNorteEsq == null) {
			panelNorteEsq = new JPanel();
			panelNorteEsq.add(getPanelNorteEsqEsq(), BorderLayout.EAST);
			panelNorteEsq.add(getPanelNorteEsqDir(), BorderLayout.EAST);
		}
		return panelNorteEsq;
	}

	private JPanel getPanelNorteEsqDir() {
		if (panelNorteEsqDir == null) {
			panelNorteEsqDir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			panelNorteEsqDir.setBorder(new BevelBorder(BevelBorder.LOWERED));
			panelNorteEsqDir.setPreferredSize(new Dimension(80, 30));
			panelNorteEsqDir.add(getLabelTempoValor());
		}
		return panelNorteEsqDir;
	}

	private JLabel getLabelTempoValor() {
		if (labelTempoValor == null) {
			labelTempoValor = new JLabel("0");
		}
		return labelTempoValor;
	}

	private JPanel getPanelNorteEsqEsq() {
		if (panelNorteEsqEsq == null) {
			panelNorteEsqEsq = new JPanel();
			panelNorteEsqEsq.add(getLabelTempo());
		}
		return panelNorteEsqEsq;
	}

	private JLabel getLabelTempo() {
		if (labelTempo == null) {
			labelTempo = new JLabel("Tempo: ");
		}
		return labelTempo;
	}

	private void constroiFrame() {
		// adiciona componentes no panelLest
		Legenda l = new Legenda("Legenda");
		l.addLinha(Color.BLUE, 8, 8, "Parede");
		l.addLinha(Color.YELLOW, 8, 8, "Energia");
		l.addLinha(Color.RED, 8, 8, "Equipe 1");
		l.addLinha(Color.WHITE, 8, 8, "Equipe 2");

		getPanelLeste().add(l);
	}

	private JPanel getPanelLeste() {
		if (panelLeste == null) {
			panelLeste = new JPanel();
		}
		return panelLeste;
	}

	/**
	 * Executado quando alguma alção dos botões dessa janela são clicados.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		try {
			if (source == getButtonPlay()) {
				controlador.play();
			} else if (source == getButtonPause()) {
				controlador.pause();
			} else if (source == getButtonStop()) {
				controlador.stop();
			} else if (source == getButtonCarregar()) {
				String caminhoArquivo = abrirArquivo();
				if (!caminhoArquivo.equals("")) {
					controlador.carregaSimulacao(caminhoArquivo);
				}
			} else if (source == getButtonClose()) {
				System.exit(0);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "ERRO ("
					+ ex.getClass().getSimpleName() + ")",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private JButton getButtonStop() {
		if (buttonStop == null) {
			buttonStop = new JButton(new ImageIcon("src/image/stop.jpg"));
			buttonStop.setToolTipText("Stop");
			buttonStop.addActionListener(this);
		}
		return buttonStop;
	}

	private String abrirArquivo() {

		String caminhoArquivo = "";

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("."));

		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			public boolean accept(File f) {
				return f.getName().toLowerCase().endsWith(".txt")
						|| f.isDirectory();
			}

			public String getDescription() {
				return "Text file";
			}
		});

		int r = chooser.showOpenDialog(new JFrame());
		if (r == JFileChooser.APPROVE_OPTION) {
			caminhoArquivo = chooser.getSelectedFile().getPath();
			System.out.println(caminhoArquivo);
		}

		return caminhoArquivo;

	}

	public void carregaSimulacao(int[][] matrizSimulacao) {
		//painelLabirinto = null;
		getPainelLabirinto().setGrid(matrizSimulacao);
		getPanelCentro().removeAll();
		getPanelCentro().add(getPainelLabirinto());
		SwingUtilities.updateComponentTreeUI(getPanelCentro());
		this.pack();
	}

	private JPanel getPanelCentro() {
		if (panelCentro == null) {
			panelCentro = new JPanel();
			panelCentro.setBackground(Color.WHITE);
			panelCentro.setBorder(new BevelBorder(BevelBorder.LOWERED));
			panelCentro.add(getPainelLabirinto());
		}
		return panelCentro;
	}

	private PainelLabirinto getPainelLabirinto() {
		if (painelLabirinto == null) {
			painelLabirinto = new PainelLabirinto();
			painelLabirinto.setPixel(15);
		}
		return painelLabirinto;
	}

	public void atualizaAmbiente(int[][] matrizSimulacao) {
		getPainelLabirinto().setGrid(matrizSimulacao);
		getPanelCentro().removeAll();
		getPanelCentro().add(getPainelLabirinto());
		SwingUtilities.updateComponentTreeUI(getPanelCentro());
	}

	/**
	 * Altera o valor exibido pelo label que indica o turno.
	 * 
	 * @param valor
	 */
	public void setLabelTempoValorText(String valor) {
		getLabelTempoValor().setText(valor);
	}

	public static void main(String[] args) {
		new FramePrincipal();
	}
}
