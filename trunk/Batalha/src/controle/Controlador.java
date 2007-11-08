package controle;

import java.util.Arrays;

import gui.FramePrincipal;

import javax.swing.JOptionPane;

import algoritmo.Agentes;
import algoritmo.Ambiente;

public class Controlador {

	// FramePrincipal
	private FramePrincipal framePrincipal;

	private int qtdeTurnosRestantes = 0;
	private int qtdeTurnos = 200;

	// *** Threads ***

	// ThreadSimulacao
	private ThreadSimulacao threadSimulacao;

	// Diz se a simula��o est� parada
	private boolean stop = true;

	// Matriz da Simula��o
	private int matrizSimulacao[][] = null;

	private int[][] matrizSimulacaoInicial;

	// Caminho do arquivo
	private String caminhoArquivo = null;

	// AlgoritmoLabirinto
	private Ambiente algoritmoLabirinto;

	public Controlador(FramePrincipal framePrincipal) {
		// FramePrincipal
		this.framePrincipal = framePrincipal;

		// AlgoritmoLabirinto
		algoritmoLabirinto = new Ambiente(this, matrizSimulacao);
	}

	/**
	 * L� o arquivo com a configura��o do cen�rio e prepara o ambiente para
	 * excu��o.
	 * 
	 * @param caminhoArquivo
	 *            Caminho absoluto do arquivo.
	 */
	public void carregaSimulacao(String caminhoArquivo) {
		if (stop) {
			// Atualiza o caminho do arquivo
			this.caminhoArquivo = caminhoArquivo;

			// Carrega a matriz do arquivo
			matrizSimulacaoInicial = InterpretadorArquivo.leArquivo(caminhoArquivo);
			matrizSimulacao = copiaArray(matrizSimulacaoInicial);

			// Carrega a simulacao passando a matriz
			framePrincipal.carregaSimulacao(matrizSimulacao);
		} else {
			throw new RuntimeException(
					"N�o foi poss�vel carregar o arquivo...\n"
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
		} else {
			if (stop) {
				// Inicia simula��o
				iniciaSimulacao();
				stop = false;
			} else {
				// Simula��o j� foi iniciada e estava pausada
				voltaSimulacao();
			}
		}
	}

	public void pause() {
		// Pausa thread Simula��o
		synchronized (threadSimulacao) {
			threadSimulacao.interrupt();
			threadSimulacao.pleaseWait = true;
		}
	}

	/**
	 * Para a simula��o, considerando a partida como terminada.
	 * @throws Exception 
	 */
	public void stop() throws Exception {
		// Stop all threads
		// Pausa thread Simula��o
		if (!stop) {
		threadSimulacao.interrupt();
		finalizaSimulacao();
		stop = true;

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
				framePrincipal, matrizSimulacao);
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
	public void finalizaSimulacao() {

		int energiaEquipe1 = 0;
		int energiaEquipe2 = 0;
		int geral = 0;
		String ganhador = "";

		threadSimulacao.allDone = true;
		framePrincipal
				.setLabelTempoValorText(Integer.toString(this.qtdeTurnosRestantes));

		for (Agentes agente : this.algoritmoLabirinto.equipes) {

			System.out.println("Agente: ["
					+ agente.getArquitetura().getNumeroAgente() + "] Energia: "
					+ agente.getArquitetura().getEnergiaIndividual());
			geral++;
			if (geral <= 10)
				energiaEquipe2 += agente.getArquitetura()
						.getEnergiaIndividual();
			else
				energiaEquipe1 += agente.getArquitetura()
						.getEnergiaIndividual();
		}

		if (energiaEquipe1 > energiaEquipe2)
			ganhador = "Equipe 1 Ganhou!";
		else
			ganhador = "Equipe 2 Ganhou!";

		JOptionPane.showMessageDialog(null, "Fim do Tempo! Equipe1: "
				+ energiaEquipe1 + " Equipe2: " + energiaEquipe2 + " \n"
				+ ganhador);

	}
}
