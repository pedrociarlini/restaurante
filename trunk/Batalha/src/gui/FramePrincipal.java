package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

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

import model.AgenteVO;

import algoritmo.Agentes;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import controle.Controlador;

@SuppressWarnings("serial")
public class FramePrincipal extends JFrame implements ActionListener,
		MouseListener {

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

	// JLabel
	private JLabel labelTempo;
	private JLabel labelTempoValor;
	private JLabel labelEquipe1;
	private JLabel labelEquipe2;

	private Legenda legenda;

	private AgenteVO agenteEquipe1;

	private AgenteVO agenteEquipe2;

	public FramePrincipal() {
		// mudaLookAndFeel();
		initialize();
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

	@SuppressWarnings("unused")
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
			geracoesFit.addColuna("Dire��o");
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
					.setToolTipText("Inicia ou retorna a execu��o da simula��o.");
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

	private Legenda getLegenda() {
		if (legenda == null) {
			legenda = new Legenda("Legenda");
			legenda.addLinha(Color.BLUE, 8, 8, "Parede");
			legenda.addLinha(Color.YELLOW, 8, 8, "Energia");
			getLabelEquipe1();
			getLabelEquipe2();
		}
		return legenda;
	}

	public JLabel getLabelEquipe1() {
		if (labelEquipe1 == null) {
			labelEquipe1 = getLegenda().addLinha(Color.RED, 8, 8, "<escolher equipe>");
			labelEquipe1.addMouseListener(this);
		}
		return labelEquipe1;
	}

	public JLabel getLabelEquipe2() {
		if (labelEquipe2 == null) {
			labelEquipe2 = getLegenda().addLinha(Color.WHITE, 8, 8, "<escolher equipe>");
			labelEquipe2.addMouseListener(this);
		}
		return labelEquipe2;
	}

	private JPanel getPanelLeste() {
		if (panelLeste == null) {
			panelLeste = new JPanel();
			panelLeste.add(getLegenda());
		}
		return panelLeste;
	}

	/**
	 * Executado quando alguma al��o dos bot�es dessa janela s�o clicados.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		try {
			if (source == getButtonPlay()) {
				controlador.selecionaEquipes(agenteEquipe1, agenteEquipe2);
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
		// painelLabirinto = null;
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

	/**
	 * Utilizado para selecionar a classe que cont�m o algoritmo que ser�
	 * utilizado para a simula��o.
	 * @param ev Evento.
	 */
	@SuppressWarnings("unchecked")
	public void mouseClicked(MouseEvent ev) {
		if (ev.getSource() == getLabelEquipe1()) {
			// TODO Exibir o nome do agente, e n�o o nome da classe.
			agenteEquipe1 = AgenteChooser.showJogadorDialog();
			if (agenteEquipe1 != null) {
				getLabelEquipe1().setText(agenteEquipe1.toString());
				// TODO Enviar os agentes para a classe controladora e outras
				// necess�rias
			}
		} else if (ev.getSource() == getLabelEquipe2()) {
			agenteEquipe2 = AgenteChooser.showJogadorDialog();
			if (agenteEquipe2 != null) {
				getLabelEquipe2().setText(agenteEquipe2.toString());
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public static void main(String[] args) {
		new FramePrincipal();
	}
}