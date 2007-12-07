package controle;

import gui.FramePrincipal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import model.AgenteVO;
import algoritmo.Agentes;
import algoritmo.Ambiente;
import algoritmo.ProgramaAbstract;

public class Controlador {
	
	static {
		try {
			Class.forName("algoritmo.Equipe1");
			Class.forName("algoritmo.Equipe2");
		} catch (Exception e) {
		}
	}
	
	public static final String ENERGIA_EQUIPE_1 = "EE1";
	public static final String ENERGIA_EQUIPE_2 = "EE2";

	// FramePrincipal
	private FramePrincipal framePrincipal;

	private int qtdeTurnosRestantes = 0;
	private int qtdeTurnos = 200;

	// *** Threads ***

	// ThreadSimulacao
	private ThreadSimulacao threadSimulacao;

	private SimulacaoStatus status = SimulacaoStatus.STOPED;

	// Matriz da Simula��o
	private int matrizSimulacao[][] = null;

	private int[][] matrizSimulacaoInicial;

	// AlgoritmoLabirinto
	private Ambiente algoritmoLabirinto;

	// Equipes que participar�o da simula��o.
	private List<AgenteVO> agentesEscolhidos = new ArrayList<AgenteVO>(2);

	private boolean isVisual;

	/**
	 * Usado para retornar o resultado para quem quiser avaliar.
	 */
	private Map<String, Double> resultado = new HashMap<String, Double>(2);

	/**
	 * Inicia o controlador no modo visual.
	 * @param framePrincipal Janela com todas as configura��es.
	 */
	public Controlador(FramePrincipal framePrincipal) {
		isVisual = true;
		// FramePrincipal
		this.framePrincipal = framePrincipal;
		isVisual = true;
	}
	
	@SuppressWarnings("unchecked")
	public Controlador(AgenteVO equipe1, AgenteVO equipe2, String caminhoArquivo) {
		isVisual = false;
		carregaSimulacao(caminhoArquivo);
		selecionaEquipes(equipe1, equipe2);
	}

	/**
	 * L� o arquivo com a configura��o do cen�rio e prepara o ambiente para
	 * excu��o.
	 * 
	 * @param caminhoArquivo
	 *            Caminho absoluto do arquivo.
	 */
	public void carregaSimulacao(String caminhoArquivo) {
		if (status == SimulacaoStatus.STOPED) {
			// Carrega a matriz do arquivo
			matrizSimulacaoInicial = InterpretadorArquivo.leArquivo(caminhoArquivo);
			matrizSimulacao = copiaArray(matrizSimulacaoInicial);

			// Carrega a simulacao passando a matriz
			if (isVisual)
				framePrincipal.carregaSimulacao(matrizSimulacao);
		} else {
			throw new RuntimeException(
					"N�o � poss�vel carregar o arquivo: \n"
							+ "Simula��o em execu��o!");
		}
	}

	/**
	 * Inicia a simula��o.
	 * @throws Exception Lan�ado caso n�o tenha sido carregado 
	 * nenhum cen�rio.
	 */
	public void play() throws Exception {
		if (matrizSimulacao == null) {
			throw new Exception("O ambiente n�o foi carregado!");
		} else if (agentesEscolhidos.size() != 2 || agentesEscolhidos.get(0) == null || agentesEscolhidos.get(1) == null) {
			throw new Exception("Os agentes n�o foram escolhidos!");			
		} else {
			if (status == SimulacaoStatus.STOPED) {
				// Inicia simula��o
				iniciaSimulacao();
			} else {
				// Simula��o j� foi iniciada e estava pausada
				voltaSimulacao();
			}
			status = SimulacaoStatus.PLAYED;
		}
	}

	public void pause() {
		if (status == SimulacaoStatus.PLAYED) {
			synchronized (threadSimulacao) {
				threadSimulacao.interrupt();
				threadSimulacao.pleaseWait = true;
				status = SimulacaoStatus.PAUSED;
			}
		}
	}

	/**
	 * Para a simula��o, considerando a partida como terminada.
	 * @throws Exception 
	 */
	public void stop() throws Exception {
		// Stop all threads
		// Pausa thread Simula��o
		if (status != SimulacaoStatus.STOPED) {
			threadSimulacao.interrupt();
			status = SimulacaoStatus.STOPED;

			finalizaSimulacao();

			// AlgoritmoLabirinto
			algoritmoLabirinto = null;
		}
		else {
			throw new Exception("A simula��o j� est� parada.");
		}
	}

	/**
	 * Cria uma nova inst�ncia da Thread de simula��o do jogo e inicia a
	 * simula��o.
	 */
	private void iniciaSimulacao() {
		// Volta ao ambiente inicial
		if (matrizSimulacaoInicial != null) {
			// Reinicia a simula��o
			matrizSimulacao = copiaArray(matrizSimulacaoInicial);
		}
		// inicia o Algoritmo Labirinto
		algoritmoLabirinto = new Ambiente(this, matrizSimulacao);
		this.qtdeTurnosRestantes = this.qtdeTurnos;
		// Inicia thread Simula��o
		threadSimulacao = new ThreadSimulacao(algoritmoLabirinto,
				this, matrizSimulacao);
		threadSimulacao.start();
	}
	
	/**
	 * Cria uma c�pia inteira dos elelmentos da matriz. 
	 * @param source
	 * @return
	 */
	private static int[][] copiaArray(int[][] source) {
		int[][] result = new int[source.length][];
		for (int i = 0; i < source.length; i++) {
			result[i] = Arrays.copyOf(source[i], source[i].length);
		}
		return result;
	}

	/**
	 * Retorna a execu��o da thread de simula��o.
	 */
	private void voltaSimulacao() {
		synchronized (threadSimulacao) {
			threadSimulacao.pleaseWait = false;
			threadSimulacao.notify();
		}
	}

	public boolean isPosicaoValida(int x, int y) {
		int linhas = matrizSimulacao.length;
		int colunas = matrizSimulacao[0].length;

		if (x < linhas && y < colunas && x >= 0 && y >= 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Reduz a quantidade de turnos indicada pelo par�metro.
	 * 
	 * @param turnos
	 *            N�mero de turnos a serem decrementados.
	 */
	public void reduzTempo(int turnos) {
		this.qtdeTurnosRestantes--;
		if (isVisual)
			framePrincipal.setLabelTempoValorText(Integer.toString(qtdeTurnosRestantes));
	}

	/**
	 * Indica a quantidade de turnos para o fim da simula��o.
	 * 
	 * @return a quantidade de turnos para o fim da simula��o.
	 */
	public int getTurnosRestantes() {
		return this.qtdeTurnosRestantes;
	}

	/**
	 * Finaliza a simula��o.
	 */
	private void finalizaSimulacao() {

		int[] energiaEquipes = new int[agentesEscolhidos.size()];
		int ganhador = -1;

		threadSimulacao.allDone = true;
		if (isVisual)
			framePrincipal.setLabelTempoValorText(
					Integer.toString(this.qtdeTurnosRestantes));

		for (Agentes agente : this.algoritmoLabirinto.soldados) {

			if (isVisual)
				System.out.println("Agente: ["
						+ agente.getArquitetura().getNumeroAgente() + "] Energia: "
						+ agente.getArquitetura().getEnergiaIndividual());
			if (((ProgramaAbstract)agente.getPrograma()).getNome() >= 200)
				energiaEquipes[1] += agente.getArquitetura()
						.getEnergiaIndividual();
			else
				energiaEquipes[0] += agente.getArquitetura()
						.getEnergiaIndividual();
		}

		if (energiaEquipes[0] > energiaEquipes[1])
			ganhador = 0;
		else
			ganhador = 1;
		
		// Usado para caso de treinamento do agente
		resultado.put(ENERGIA_EQUIPE_1, Double.valueOf(energiaEquipes[0]));
		resultado.put(ENERGIA_EQUIPE_2, Double.valueOf(energiaEquipes[1]));

		if (isVisual)
			JOptionPane.showMessageDialog(null, "Fim do Tempo!\n Equipe1: "
					+ energiaEquipes[0] + "\nEquipe2: " + energiaEquipes[1]
					+ " \n\n" + agentesEscolhidos.get(ganhador) + " ganhou!");
	}
	
	public SimulacaoStatus getStatus() {
		return status;
	}

	/**
	 * Especifica quais os agentes que far�o parte da simula��o.
	 * @param agenteEquipe1 Agente 1.
	 * @param agenteEquipe2 Agente 2.
	 */
	public void selecionaEquipes(AgenteVO agenteEquipe1, AgenteVO agenteEquipe2) {
		agentesEscolhidos.clear();
		agentesEscolhidos.add(agenteEquipe1);
		agentesEscolhidos.add(agenteEquipe2);
	}
	
	public List<AgenteVO> getAgentesEscolhidos() {
		return agentesEscolhidos;
	}

	public boolean isVisual() {
		return isVisual;
	}

	public void atualizaVisual(List<Agentes> soldados, int[][] matrizSimulacao2) {
		framePrincipal.atualizaGrid(soldados);
		framePrincipal.atualizaAmbiente(matrizSimulacao2);	
	}
	
	public void joinSimulacao() throws InterruptedException {
		threadSimulacao.join();
	}

	/**
	 * Retorna o valor do resultado passado por par�metro.<br>
	 * Pode ser uma das constantes:<br>
	 * ENERGIA_EQUIPE_1, ENERGIA_EQUIPE_2
	 * @param nomeDado
	 * @return
	 */
	public Double getResultado(String nomeDado) {
		return resultado.get(nomeDado);
	}
}
