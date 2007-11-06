package controle;

import gui.FramePrincipal;

import javax.swing.JOptionPane;

import algoritmo.Agentes;
import algoritmo.Ambiente;

public class Controlador {

	// FramePrincipal
	private FramePrincipal framePrincipal;

	// *** Threads ***

	// ThreadSimulacao
	private ThreadSimulacao threadSimulacao;

	// Diz se a simula��o est� parada
	private boolean stop;

	// Matriz da Simula��o
	private int matrizSimulacao[][] = null;

	// Caminho do arquivo
	private String caminhoArquivo = null;

	// AlgoritmoLabirinto
	private Ambiente algoritmoLabirinto;

	public Controlador(FramePrincipal framePrincipal) {
		// FramePrincipal
		this.framePrincipal = framePrincipal;

		// *** Threads ***

		// ThreadSimulacao
		threadSimulacao = new ThreadSimulacao(algoritmoLabirinto,
				framePrincipal, matrizSimulacao);

		// Diz se a simula��o est� parada
		this.stop = true;

		// AlgoritmoLabirinto
		algoritmoLabirinto = new Ambiente(this, matrizSimulacao);
	}

	/**
	 * L� o arquivo com a configura��o do cen�rio e prepara o ambiente para excu��o.
	 * @param caminhoArquivo Caminho absoluto do arquivo.
	 */
	public void carregaSimulacao(String caminhoArquivo) {
		if (stop) {
			// Atualiza o caminho do arquivo
			this.caminhoArquivo = caminhoArquivo;

			// Carrega a matriz do arquivo
			matrizSimulacao = InterpretadorArquivo.leArquivo(caminhoArquivo);

			// Carrega a simulacao passando a matriz
			framePrincipal.carregaSimulacao(matrizSimulacao);
		} else {
			JOptionPane
					.showMessageDialog(
							null,
							"N�o foi poss�vel carregar o arquivo...\nSimula��o em execu��o!",
							"ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Inicia a simula��o.
	 */
	public void play() {
		if (matrizSimulacao == null) {
			JOptionPane.showMessageDialog(null,
					"O ambiente n�o foi carregado!", "ERRO",
					JOptionPane.ERROR_MESSAGE);
		} else {
			if (stop) {

				// inicia o Algoritmo Labirinto
				algoritmoLabirinto = new Ambiente(this, matrizSimulacao);

				// Inicia simula��o
				iniciaSimulacao();
				stop = false;
			} else {
				// Simula��o j� foi inicia e estava pausada
				voltaSimulacao();
			}
		}
	}

	public void pause() {
		// Pausa todas as threads

		// Pausa thread Simula��o
		synchronized (threadSimulacao) {
			threadSimulacao.interrupt();
			threadSimulacao.pleaseWait = true;
		}
	}

	public void stop() {
		// Stop all threads
		// Pausa thread Simula��o
		threadSimulacao.interrupt();
		threadSimulacao.allDone = true;

		stop = true;

		// Zera o cron�metro
		// framePrincipal.zeraEnergia();
		framePrincipal.iniciarJogo();

		// Volta ao ambiente inicial
		if (caminhoArquivo != null) {
			// Carrega a simulacao novamente
			carregaSimulacao(caminhoArquivo);
		}
		// AlgoritmoLabirinto
		algoritmoLabirinto = null;
	}

	/**
	 * Cria uma nova inst�ncia da Thread de simula��o do jogo e inicia a simula��o.
	 */
	private void iniciaSimulacao() {
		// Inicia thread Simula��o
		threadSimulacao = new ThreadSimulacao(algoritmoLabirinto,
				framePrincipal, matrizSimulacao);
		threadSimulacao.start();
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

	public void reduzTempo(int tempoSegundos) {
		framePrincipal.reduzCronometro(tempoSegundos);
	}

	public void atualizaTempo(int tempo) {
		framePrincipal.aumentarEnergia(tempo);
	}

	public int getEnergiaRestante() {
		// return (Constantes.tempoPacMan -
		// framePrincipal.getEnergiaRestante());
		return (framePrincipal.getEnergiaRestante());
	}

	public void isFimSimulacao() {

		int cE1 = 0;
		int cE2 = 0;
		int geral = 0;
		String ganhador = "";

		if (framePrincipal.getEnergiaRestante() <= 0) {
			this.pause();

			for (Agentes element : this.algoritmoLabirinto.equipes) {

				System.out.println("Agente: "
						+ element.getArquitetura().getNumeroAgente()
						+ " Energia: "
						+ element.getArquitetura().getEnergiaIndividual());
				geral++;
				if (geral <= 10)
					cE1 += element.getArquitetura().getEnergiaIndividual();
				else
					cE2 += element.getArquitetura().getEnergiaIndividual();
			}

			if (cE1 > cE2)
				ganhador = "Equipe 1 Ganhou!";
			else
				ganhador = "Equipe 2 Ganhou!";

			JOptionPane.showMessageDialog(null, "Fim do Tempo! Equipe1: " + cE1
					+ " Equipe2: " + cE2 + " \n" + ganhador);

		}
	}
}
