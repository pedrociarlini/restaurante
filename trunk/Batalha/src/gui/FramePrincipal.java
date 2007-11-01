package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

import algoritmo.Agentes;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;

import controle.Constantes;
import controle.Controlador;

public class FramePrincipal extends JFrame implements ActionListener{

	//Container
	private Container container;
	
	//JPanel
	private JPanel panelNorte;
	private JPanel panelNorteEsq;
	private JPanel panelNorteEsqEsq;
	private JPanel panelNorteEsqDir;
	private JPanel panelNorteDir;
	private JPanel panelCentro;
	private JPanel panelLeste;
	private JPanel panelSul;
	
	//JToolBar
	private JToolBar toolBarControle; 
	
	//JLabel
	private JLabel labelTempo;
	private JLabel labelTempoValor;
	
	//Icon
	private Icon iconPlay;
	private Icon iconPause;
	private Icon iconStop;
	private Icon iconCarregar;
	private Icon iconClose;

	//JButton
	private JButton buttonPlay;
	private JButton buttonPause;
	private JButton buttonStop;
	private JButton buttonCarregar;
	private JButton buttonClose;
	
	private frmGrid geracoesFit;
	
	//Controlador
	private Controlador controlador;
	
	//PainelLabirinto 
	private PainelLabirinto painelLabirinto; 
	public FramePrincipal() {
//		mudaLookAndFeel();
		configuraFrame();
		instanciaVariaveis();
		constroiFrame();
		criarGridInformacoes();
		registraEventos();
		this.setVisible(true);
	}
	
	public void criarGridInformacoes() {
		
		geracoesFit = new frmGrid();

		geracoesFit.addColuna("Agente");
		geracoesFit.addColuna("Energia");
		geracoesFit.addColuna("Direção");
		
		geracoesFit.setPreferredSize(new Dimension(450,120));
		
		//panelLeste.add(geracoesFit.returnPanel());
		panelSul.add(geracoesFit.returnPanel());
		
		this.pack();
	}

	public void atualizaGrid(List equipes){

		for (int i = 1; i <= equipes.size() ; i++) {
			geracoesFit.removeLinha(0);	
		}
		
		for (Iterator iter = equipes.iterator(); iter.hasNext();) {
			Agentes element = (Agentes)iter.next();

			String m[] = new String[3];
			m[0] = "Agente_"+ element.getArquitetura().getNumeroAgente();
			m[1] = element.getArquitetura().getEnergiaIndividual()+""; 
			m[2] = element.getArquitetura().getDirecao(); 
			
			geracoesFit.addLinha(m);
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

	private void configuraFrame() {
		this.setTitle("Labirinto");
		this.setSize(400,395);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		this.setLocationRelativeTo(null);
	}

	private void instanciaVariaveis() {
		//Container
		container = this.getContentPane();
		container.setLayout(new BorderLayout());
		
		//JPanel
		panelNorte = new JPanel(new BorderLayout());
		panelNorteEsq = new JPanel();
		panelNorteEsqEsq = new JPanel();
		panelNorteEsqDir = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelNorteEsqDir.setBorder(new BevelBorder(BevelBorder.LOWERED));
		panelNorteEsqDir.setPreferredSize(new Dimension(80,30));
		panelNorteDir = new JPanel();
		
		panelCentro = new JPanel();
		panelCentro.setBackground(Color.WHITE);
		panelCentro.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		panelLeste = new JPanel();
		panelSul  = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelSul.setBorder(new BevelBorder(BevelBorder.LOWERED));
		panelSul.setPreferredSize(new Dimension(10,130));
		
		
		
		//JToolBar
		toolBarControle = new JToolBar("Controle"); 
		
		//JLabel
		labelTempo = new JLabel("Tempo : ");
		labelTempoValor = new JLabel("0");
		
		//Icon
		iconPlay = new ImageIcon("src/image/play.jpg");
		iconPause = new ImageIcon("src/image/pause.jpg");
		iconStop = new ImageIcon("src/image/stop.jpg");
		iconCarregar = new ImageIcon("src/image/carregaArquivo.jpg");
		iconClose = new ImageIcon("src/image/exit.jpg");
		
		//JButton
		buttonPlay = new JButton(iconPlay);
		buttonPlay.setToolTipText("Play");
		buttonPause = new JButton(iconPause);
		buttonPause.setToolTipText("Pause");
		buttonStop = new JButton(iconStop);
		buttonStop.setToolTipText("Stop");
		buttonCarregar = new JButton(iconCarregar);
		buttonCarregar.setToolTipText("Carregar");
		buttonClose = new JButton(iconClose);
		buttonClose.setToolTipText("Close");
		
		iniciarJogo();
		//Controlador
		controlador = new Controlador(this);

	}

	private void constroiFrame() {
		//Adiciona componentes no Container
		container.add(panelNorte, BorderLayout.NORTH);
		container.add(panelCentro, BorderLayout.CENTER);
		container.add(panelLeste, BorderLayout.EAST);
		container.add(panelSul , BorderLayout.SOUTH);
		
		//Adiciona componentes no panelNorte
		panelNorte.add(panelNorteEsq, BorderLayout.WEST);
		panelNorte.add(panelNorteDir, BorderLayout.EAST);
		
		//Adiciona componentes no panelNorteEsq
		panelNorteEsq.add(panelNorteEsqEsq, BorderLayout.EAST);
		panelNorteEsq.add(panelNorteEsqDir, BorderLayout.EAST);
		
		//Adiciona componentes no panelNorteEsqEsq
		panelNorteEsqEsq.add(labelTempo);
		
		//Adiciona componentes no panelNorteEsqDir
		panelNorteEsqDir.add(labelTempoValor);
		
		//Adiciona componentes no panelNorteDir
		panelNorteDir.add(toolBarControle, BorderLayout.EAST);
		
		//adiciona componentes no panelLest
		Legenda l = new Legenda("Legenda");
		l.addLinha(Color.BLUE,8,8,"Parede" );
		l.addLinha(Color.YELLOW,8,8,"Energia" );
		l.addLinha(Color.RED ,8,8,"Equipe 1" );
		l.addLinha(Color.WHITE,8,8,"Equipe 2" );
		
		panelLeste.add(l);
		//Adiciona componentes no toolBarControle
		toolBarControle.add(buttonPlay);
		toolBarControle.add(buttonPause);
		toolBarControle.add(buttonStop);
		toolBarControle.add(buttonCarregar);
		toolBarControle.add(buttonClose);
		
	}

	private void registraEventos() {
		//JButton
		buttonPlay.addActionListener(this); 
		buttonPause.addActionListener(this); 
		buttonStop.addActionListener(this);
		buttonCarregar.addActionListener(this);
		buttonClose.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == buttonPlay){
			controlador.play();
		}else if (source == buttonPause){
			controlador.pause();
		}else if (source == buttonStop){
			controlador.stop();
		}else if (source == buttonCarregar){
			String caminhoArquivo = abrirArquivo();
			if (!caminhoArquivo.equals("")){
				controlador.carregaSimulacao(caminhoArquivo);
			}
			
		}else if (source == buttonClose){
			System.exit(0);
		}
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
	
	public void carregaSimulacao(int [][] matrizSimulacao){
		painelLabirinto = new PainelLabirinto();
		painelLabirinto.setPixel(15);
		painelLabirinto.setGrid(matrizSimulacao);
		panelCentro.removeAll();
		panelCentro.add(painelLabirinto.returnPanel());
		SwingUtilities.updateComponentTreeUI(panelCentro);
		this.pack();
	}
	
	public void atualizaAmbiente(int [][] matrizSimulacao){
		
		painelLabirinto.setGrid(matrizSimulacao);
		panelCentro.removeAll();
		panelCentro.add(painelLabirinto.returnPanel());
		SwingUtilities.updateComponentTreeUI(panelCentro);
	}
	
	public void aumentarEnergia(int energia){
		int tempo = Integer.parseInt(this.labelTempoValor.getText());
		tempo +=energia;
		this.labelTempoValor.setText(Integer.toString(tempo));
	}
	
	public void reduzCronometro(int energia){
		int tempo = Integer.parseInt(this.labelTempoValor.getText());
		tempo -=energia;
		if (tempo<0){
			tempo = 0;
		}
		
		this.labelTempoValor.setText(Integer.toString(tempo));
	}

	public int getEnergiaRestante(){
		return Integer.parseInt(this.labelTempoValor.getText());
	}
	
	public void iniciarJogo(){
		this.labelTempoValor.setText(new Integer(Constantes.tempoInicialJogo).toString());
	}
	
	
	public void zeraEnergia(){
		this.labelTempoValor.setText(new String("0"));
	}
	
	public static void main(String[] args) {
		new FramePrincipal();
	}
}
