package algoritmo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import controle.Constantes;
import controle.Controlador;

/**
 * 
 * @author <Aluno do Andr�>
 * 
 */
public class Arquitetura {

	// Matriz da simula��o
	private int matrizSimulacao[][] = null;

	private int matrizOlfatoEquipe1[][] = null;
	private int matrizOlfatoEquipe2[][] = null;

	private Controlador controlador;

	private Programa programa;

	private int numeroAgente = -1;

	private int x = -1;

	private int y = -1;

	private String direcao;
	private int EnergiaIndividual;

	private List<Agentes> equipes = new ArrayList<Agentes>();

	public Arquitetura(int matrizSimulacao[][], int matrizOlfatoEquipe1[][],
			Controlador controlador, int numeroAgente, Programa programa,
			List<Agentes> equipes, int matrizOlfatoEquipe2[][]) {

		this.matrizSimulacao = matrizSimulacao;
		this.numeroAgente = numeroAgente;
		this.controlador = controlador;
		this.programa = programa;
		this.matrizOlfatoEquipe1 = matrizOlfatoEquipe1;
		this.matrizOlfatoEquipe2 = matrizOlfatoEquipe2;

		this.equipes = equipes;
		this.EnergiaIndividual = Constantes.energiaInicialEquipes;
		this.direcao = Constantes.NORTE;

		getPosicaoAgente();
	}

	private void getPosicaoAgente() {
		boolean achouAgente = false;

		if (matrizSimulacao != null) {
			// Procura o agente
			forMaior: {
				for (int i = 0; i < matrizSimulacao.length; i++) {
					for (int j = 0; j < matrizSimulacao[0].length; j++) {
						if (matrizSimulacao[i][j] == numeroAgente) {

							// Atualiza a posi��o do agente
							this.x = i;
							this.y = j;

							achouAgente = true;

							break forMaior;
						}
					}
				}
			}

			if (!achouAgente) {
				JOptionPane.showMessageDialog(null,
						"N�o foi poss�vel achar o agente de n�mero /'"
								+ numeroAgente + "/'!", "Agente Inv�lido",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public Point getPosicao() {
		return new Point(this.getX(), this.getY());
	}

	public int getEnergiaRestante() {
		return controlador.getEnergiaRestante();
	}

	public void percebeEquipe2() {

		SensoresEquipe2 sensor = new SensoresEquipe2();

		// 1 - Percepcao do Ambiente de Acordo com sua Visao.
		sensor.setVisaoIdentificacao(this.percebeAmbienteEquipe2());

		// Posicao Atuao do Agente.
		sensor.setPosicao(this.getPosicao());

		// Nivel de Energia do Agente.
		sensor.setNivelEnergia(this.getEnergiaIndividual());

		// 2 - Direcao do Oponente ok
		sensor.setDirecaoOponente(this.percebeDirecaoOponenteEquipe2());
		sensor.setDirecaoEquipe(this.percebeDirecaoOponenteEquipe1());

		// 3 - Energia do Oponente ok
		sensor.setEnergiaOponente(this.percebeEnergiaOponenteEquipe2());
		sensor.setEnergiaEquipe(this.percebeEnergiaOponenteEquipe1());

		// 4 - Olfato Equipe 2 ok
		sensor.setAmbienteOlfatoEquipe(this.percebeAmbienteOlfatoEquipe2());
		sensor.setAmbienteOlfatoOponente(this.percebeAmbienteOlfatoEquipe1());

		((ProgramaEquipe2) programa).sensor = sensor;

	}

	public void percebeEquipe1() {

		SensoresEquipe1 sensor = new SensoresEquipe1();

		// 1 - Percepcao do Ambiente de Acordo com sua Visao.OK
		sensor.setVisaoIdentificacao(this.percebeAmbienteEquipe2());

		// Posicao Atuao do Agente.
		sensor.setPosicao(this.getPosicao());

		// Nivel de Energia do Agente.
		sensor.setNivelEnergia(this.getEnergiaIndividual());

		// 2 - Direcao do Oponente ok
		sensor.setDirecaoOponente(this.percebeDirecaoOponenteEquipe1());
		sensor.setDirecaoEquipe(this.percebeDirecaoOponenteEquipe2());

		// 3 - Energia do Oponente ok
		sensor.setEnergiaOponente(this.percebeEnergiaOponenteEquipe1());
		sensor.setEnergiaEquipe(this.percebeEnergiaOponenteEquipe2());

		// 4 - Olfato Equipe 1
		sensor.setAmbienteOlfatoEquipe(this.percebeAmbienteOlfatoEquipe1());
		sensor.setAmbienteOlfatoOponente(this.percebeAmbienteOlfatoEquipe2());

		((ProgramaEquipe1) programa).sensor = sensor;
	}

	public int[] percebeAmbienteEquipe2() {

		int[] ambiente = new int[24];

		if ((x == 0) || (y == 0))
			ambiente[6] = Constantes.foraAmbiene;
		else
			ambiente[6] = matrizSimulacao[x - 1][y - 1];

		if ((y == 0))
			ambiente[7] = Constantes.foraAmbiene;
		else
			ambiente[7] = matrizSimulacao[x][y - 1];

		if ((x == 29) || (y == 0))
			ambiente[8] = Constantes.foraAmbiene;
		else
			ambiente[8] = matrizSimulacao[x + 1][y - 1];

		if ((x == 0))
			ambiente[11] = Constantes.foraAmbiene;
		else
			ambiente[11] = matrizSimulacao[x - 1][y];

		if ((x == 29))
			ambiente[12] = Constantes.foraAmbiene;
		else
			ambiente[12] = matrizSimulacao[x + 1][y];

		if ((x == 0) || (y == 29))
			ambiente[15] = Constantes.foraAmbiene;
		else
			ambiente[15] = matrizSimulacao[x - 1][y + 1];

		if ((y == 29))
			ambiente[16] = Constantes.foraAmbiene;
		else
			ambiente[16] = matrizSimulacao[x][y + 1];

		if ((x == 29) || (y == 29))
			ambiente[17] = Constantes.foraAmbiene;
		else
			ambiente[17] = matrizSimulacao[x + 1][y + 1];

		if (ambiente[6] == Constantes.numeroParede)
			ambiente[0] = Constantes.semVisao;
		else {
			if ((x <= 1) || (y <= 1))
				ambiente[0] = Constantes.foraAmbiene;
			else
				ambiente[0] = matrizSimulacao[x - 2][y - 2];
		}

		if (ambiente[6] == Constantes.numeroParede
				&& ambiente[7] == Constantes.numeroParede)
			ambiente[1] = Constantes.semVisao;
		else {
			if ((x == 0) || (y <= 1))
				ambiente[1] = Constantes.foraAmbiene;
			else
				ambiente[1] = matrizSimulacao[x - 1][y - 2];
		}

		if (ambiente[7] == Constantes.numeroParede)
			ambiente[2] = Constantes.semVisao;
		else {
			if ((y <= 1))
				ambiente[2] = Constantes.foraAmbiene;
			else
				ambiente[2] = matrizSimulacao[x][y - 2];
		}

		if (ambiente[7] == Constantes.numeroParede
				&& ambiente[8] == Constantes.numeroParede)
			ambiente[3] = Constantes.semVisao;
		else {
			if ((x == 29) || (y <= 1))
				ambiente[3] = Constantes.foraAmbiene;
			else
				ambiente[3] = matrizSimulacao[x + 1][y - 2];
		}

		if (ambiente[8] == Constantes.numeroParede)
			ambiente[4] = Constantes.semVisao;
		else {
			if ((x >= 28) || (y <= 1))
				ambiente[4] = Constantes.foraAmbiene;
			else
				ambiente[4] = matrizSimulacao[x + 2][y - 2];
		}

		if (ambiente[6] == Constantes.numeroParede
				&& ambiente[11] == Constantes.numeroParede)
			ambiente[5] = Constantes.semVisao;
		else {
			if ((x <= 1) || (y == 0))
				ambiente[5] = Constantes.foraAmbiene;
			else
				ambiente[5] = matrizSimulacao[x - 2][y - 1];
		}

		if (ambiente[8] == Constantes.numeroParede
				&& ambiente[12] == Constantes.numeroParede)
			ambiente[9] = Constantes.semVisao;
		else {
			if ((x >= 28) || (y == 0))
				ambiente[9] = Constantes.foraAmbiene;
			else
				ambiente[9] = matrizSimulacao[x + 2][y - 1];
		}

		if (ambiente[11] == Constantes.numeroParede)
			ambiente[10] = Constantes.semVisao;
		else {
			if ((x <= 1))
				ambiente[10] = Constantes.foraAmbiene;
			else
				ambiente[10] = matrizSimulacao[x - 2][y];
		}

		if (ambiente[12] == Constantes.numeroParede)
			ambiente[13] = Constantes.semVisao;
		else {
			if ((x >= 28))
				ambiente[13] = Constantes.foraAmbiene;
			else
				ambiente[13] = matrizSimulacao[x + 2][y];
		}

		if (ambiente[11] == Constantes.numeroParede
				&& ambiente[15] == Constantes.numeroParede)
			ambiente[14] = Constantes.semVisao;
		else {
			if ((x <= 1) || (y == 29))
				ambiente[14] = Constantes.foraAmbiene;
			else
				ambiente[14] = matrizSimulacao[x - 2][y + 1];
		}

		if (ambiente[12] == Constantes.numeroParede
				&& ambiente[17] == Constantes.numeroParede)
			ambiente[18] = Constantes.semVisao;
		else {
			if ((x >= 28) || (y == 29))
				ambiente[18] = Constantes.foraAmbiene;
			else
				ambiente[18] = matrizSimulacao[x + 2][y + 1];
		}

		if (ambiente[15] == Constantes.numeroParede)
			ambiente[19] = Constantes.semVisao;
		else {
			if ((x <= 1) || (y >= 28))
				ambiente[19] = Constantes.foraAmbiene;
			else
				ambiente[19] = matrizSimulacao[x - 2][y + 2];
		}

		if (ambiente[15] == Constantes.numeroParede
				&& ambiente[16] == Constantes.numeroParede)
			ambiente[20] = Constantes.semVisao;
		else {
			if ((x == 0) || (y >= 28))
				ambiente[20] = Constantes.foraAmbiene;
			else
				ambiente[20] = matrizSimulacao[x - 1][y + 2];
		}

		if (ambiente[16] == Constantes.numeroParede)
			ambiente[21] = Constantes.semVisao;
		else {
			if ((y >= 28))
				ambiente[21] = Constantes.foraAmbiene;
			else
				ambiente[21] = matrizSimulacao[x][y + 2];
		}

		if (ambiente[16] == Constantes.numeroParede
				&& ambiente[17] == Constantes.numeroParede)
			ambiente[22] = Constantes.semVisao;
		else {
			if ((x == 29) || (y >= 28))
				ambiente[22] = Constantes.foraAmbiene;
			else
				ambiente[22] = matrizSimulacao[x + 1][y + 2];
		}

		if (ambiente[17] == Constantes.numeroParede)
			ambiente[23] = Constantes.semVisao;
		else {
			if ((x >= 28) || (y >= 28))
				ambiente[23] = Constantes.foraAmbiene;
			else
				ambiente[23] = matrizSimulacao[x + 2][y + 2];
		}

		return ambiente;
	}

	public int[] percebeAmbienteEquipe1() {
		int[] ambiente = new int[8];

		if ((x == 0) || (y == 0))
			ambiente[0] = Constantes.foraAmbiene;
		else
			ambiente[0] = matrizSimulacao[x - 1][y - 1];

		if ((y == 0))
			ambiente[1] = Constantes.foraAmbiene;
		else
			ambiente[1] = matrizSimulacao[x][y - 1];

		if ((x == 29) || (y == 0))
			ambiente[2] = Constantes.foraAmbiene;
		else
			ambiente[2] = matrizSimulacao[x + 1][y - 1];

		if ((x == 0))
			ambiente[3] = Constantes.foraAmbiene;
		else
			ambiente[3] = matrizSimulacao[x - 1][y];

		if ((x == 29))
			ambiente[4] = Constantes.foraAmbiene;
		else
			ambiente[4] = matrizSimulacao[x + 1][y];

		if ((x == 0) || (y == 29))
			ambiente[5] = Constantes.foraAmbiene;
		else
			ambiente[5] = matrizSimulacao[x - 1][y + 1];

		if ((y == 29))
			ambiente[6] = Constantes.foraAmbiene;
		else
			ambiente[6] = matrizSimulacao[x][y + 1];

		if ((x == 29) || (y == 29))
			ambiente[7] = Constantes.foraAmbiene;
		else
			ambiente[7] = matrizSimulacao[x + 1][y + 1];

		return ambiente;
	}

	public int[] percebeAmbienteOlfatoEquipe1() {
		int[] ambienteOlfato = new int[8];

		if ((x == 0) || (y == 0))
			ambienteOlfato[0] = Constantes.foraAmbiene;
		else
			ambienteOlfato[0] = matrizOlfatoEquipe1[x - 1][y - 1];

		if ((y == 0))
			ambienteOlfato[1] = Constantes.foraAmbiene;
		else
			ambienteOlfato[1] = matrizOlfatoEquipe1[x][y - 1];

		if ((x == 29) || (y == 0))
			ambienteOlfato[2] = Constantes.foraAmbiene;
		else
			ambienteOlfato[2] = matrizOlfatoEquipe1[x + 1][y - 1];

		if ((x == 0))
			ambienteOlfato[3] = Constantes.foraAmbiene;
		else
			ambienteOlfato[3] = matrizOlfatoEquipe1[x - 1][y];

		if ((x == 29))
			ambienteOlfato[4] = Constantes.foraAmbiene;
		else
			ambienteOlfato[4] = matrizOlfatoEquipe1[x + 1][y];

		if ((x == 0) || (y == 29))
			ambienteOlfato[5] = Constantes.foraAmbiene;
		else
			ambienteOlfato[5] = matrizOlfatoEquipe1[x - 1][y + 1];

		if ((y == 29))
			ambienteOlfato[6] = Constantes.foraAmbiene;
		else
			ambienteOlfato[6] = matrizOlfatoEquipe1[x][y + 1];

		if ((x == 29) || (y == 29))
			ambienteOlfato[7] = Constantes.foraAmbiene;
		else
			ambienteOlfato[7] = matrizOlfatoEquipe1[x + 1][y + 1];

		return ambienteOlfato;
	}

	public int[] percebeAmbienteOlfatoEquipe2() {

		int[] ambienteOlfato = new int[8];

		if ((x == 0) || (y == 0))
			ambienteOlfato[0] = Constantes.foraAmbiene;
		else
			ambienteOlfato[0] = matrizOlfatoEquipe2[x - 1][y - 1];

		if ((y == 0))
			ambienteOlfato[1] = Constantes.foraAmbiene;
		else
			ambienteOlfato[1] = matrizOlfatoEquipe2[x][y - 1];

		if ((x == 29) || (y == 0))
			ambienteOlfato[2] = Constantes.foraAmbiene;
		else
			ambienteOlfato[2] = matrizOlfatoEquipe2[x + 1][y - 1];

		if ((x == 0))
			ambienteOlfato[3] = Constantes.foraAmbiene;
		else
			ambienteOlfato[3] = matrizOlfatoEquipe2[x - 1][y];

		if ((x == 29))
			ambienteOlfato[4] = Constantes.foraAmbiene;
		else
			ambienteOlfato[4] = matrizOlfatoEquipe2[x + 1][y];

		if ((x == 0) || (y == 29))
			ambienteOlfato[5] = Constantes.foraAmbiene;
		else
			ambienteOlfato[5] = matrizOlfatoEquipe2[x - 1][y + 1];

		if ((y == 29))
			ambienteOlfato[6] = Constantes.foraAmbiene;
		else
			ambienteOlfato[6] = matrizOlfatoEquipe2[x][y + 1];

		if ((x == 29) || (y == 29))
			ambienteOlfato[7] = Constantes.foraAmbiene;
		else
			ambienteOlfato[7] = matrizOlfatoEquipe2[x + 1][y + 1];

		return ambienteOlfato;
	}

	private void isMoeda(int xNovo, int yNovo) {

		if (isCelulaValida(xNovo, yNovo)) {

			if (matrizSimulacao[xNovo][yNovo] == Constantes.numeroMoeda) {

				this.EnergiaIndividual = this.EnergiaIndividual
						+ Constantes.energiaGanhoMoedas;

				// Volta a posi��o do agente para preto
				matrizSimulacao[this.x][this.y] = 0;
				// Move o agente
				matrizSimulacao[xNovo][yNovo] = numeroAgente;
				// Atualiza a posi��o do agente
				this.x = xNovo;
				this.y = yNovo;
			}
		}
	}

	public void moveCimaEquipe2() {
		int xNovo = x;
		int yNovo = y - 1;

		// Caso tenha um obstaculo.
		if (!isPosicaoValida(xNovo, yNovo)) {

			isEquipe1(xNovo, yNovo);
			// isObjetivo(xNovo, yNovo);
			isMoeda(xNovo, yNovo);

		} else {
			// Volta a posi��o do agente para preto
			matrizSimulacao[this.x][this.y] = 0;

			// Move o agente
			matrizSimulacao[xNovo][yNovo] = numeroAgente;

			// Atualiza MatrizOlfato
			matrizOlfatoEquipe2[xNovo][yNovo] = 1;

			// Atualiza a posi��o do agente
			this.x = xNovo;
			this.y = yNovo;
		}
	}

	public void ficarParadoEquipe2() {

	}

	public void ficarParadoEquipe1() {

	}

	public void moveBaixoEquipe2() {
		int xNovo = x;
		int yNovo = y + 1;

		if (!isPosicaoValida(xNovo, yNovo)) {

			isEquipe1(xNovo, yNovo);
			// isObjetivo(xNovo, yNovo);
			isMoeda(xNovo, yNovo);

		} else {
			// Volta a posi��o do agente para preto
			matrizSimulacao[this.x][this.y] = 0;

			// Move o agente
			matrizSimulacao[xNovo][yNovo] = numeroAgente;

			// Atualiza MatrizOlfato
			matrizOlfatoEquipe2[xNovo][yNovo] = 1;

			// Atualiza a posi��o do agente
			this.x = xNovo;
			this.y = yNovo;
		}
	}

	public void moveEsquerdaEquipe2() {
		int xNovo = x - 1;
		int yNovo = y;

		if (!isPosicaoValida(xNovo, yNovo)) {

			isEquipe1(xNovo, yNovo);
			// isObjetivo(xNovo, yNovo);
			isMoeda(xNovo, yNovo);

		} else {
			// Volta a posi��o do agente para preto
			matrizSimulacao[this.x][this.y] = 0;

			// Move o agente
			matrizSimulacao[xNovo][yNovo] = numeroAgente;

			// Atualiza MatrizOlfato
			matrizOlfatoEquipe2[xNovo][yNovo] = 1;

			// Atualiza a posi��o do agente
			this.x = xNovo;
			this.y = yNovo;
		}
	}

	public void moveDireitaEquipe2() {
		int xNovo = x + 1;
		int yNovo = y;

		if (!isPosicaoValida(xNovo, yNovo)) {

			isEquipe1(xNovo, yNovo);
			// isObjetivo(xNovo, yNovo);
			isMoeda(xNovo, yNovo);

		} else {
			// Volta a posi��o do agente para preto
			matrizSimulacao[this.x][this.y] = 0;

			// Move o agente
			matrizSimulacao[xNovo][yNovo] = numeroAgente;

			// Atualiza MatrizOlfato
			matrizOlfatoEquipe2[xNovo][yNovo] = 1;

			// Atualiza a posi��o do agente
			this.x = xNovo;
			this.y = yNovo;
		}
	}

	public void moveCimaEquipe1() {
		int xNovo = x;
		int yNovo = y - 1;

		if (!isPosicaoValida(xNovo, yNovo)) {
			isEquipe2(xNovo, yNovo);
			isMoeda(xNovo, yNovo);
		} else {
			// Volta a posi��o do agente para preto
			matrizSimulacao[this.x][this.y] = 0;

			// Move o agente
			matrizSimulacao[xNovo][yNovo] = numeroAgente;

			// Atualiza MatrizOlfato
			matrizOlfatoEquipe1[xNovo][yNovo] = 1;

			// Atualiza a posi��o do agente
			this.x = xNovo;
			this.y = yNovo;
		}
	}

	public void moveBaixoEquipe1() {
		int xNovo = x;
		int yNovo = y + 1;

		if (!isPosicaoValida(xNovo, yNovo)) {
			isEquipe2(xNovo, yNovo);
			isMoeda(xNovo, yNovo);
		} else {
			// Volta a posi��o do agente para preto
			matrizSimulacao[this.x][this.y] = 0;

			// Move o agente
			matrizSimulacao[xNovo][yNovo] = numeroAgente;

			// Atualiza MatrizOlfato
			matrizOlfatoEquipe1[xNovo][yNovo] = 1;

			// Atualiza a posi��o do agente
			this.x = xNovo;
			this.y = yNovo;
		}
	}

	public void moveEsquerdaEquipe1() {
		int xNovo = x - 1;
		int yNovo = y;

		if (!isPosicaoValida(xNovo, yNovo)) {
			isEquipe2(xNovo, yNovo);
			isMoeda(xNovo, yNovo);

		} else {
			// Volta a posi��o do agente para preto
			matrizSimulacao[this.x][this.y] = 0;

			// Move o agente
			matrizSimulacao[xNovo][yNovo] = numeroAgente;

			// Atualiza MatrizOlfato
			matrizOlfatoEquipe1[xNovo][yNovo] = 1;

			// Atualiza a posi��o do agente
			this.x = xNovo;
			this.y = yNovo;
		}
	}

	public void moveDireitaEquipe1() {
		int xNovo = x + 1;
		int yNovo = y;

		if (!isPosicaoValida(xNovo, yNovo)) {
			isEquipe2(xNovo, yNovo);
			isMoeda(xNovo, yNovo);
		} else {
			// Volta a posi��o do agente para preto
			matrizSimulacao[this.x][this.y] = 0;

			// Move o agente
			matrizSimulacao[xNovo][yNovo] = numeroAgente;

			// Atualiza MatrizOlfato
			matrizOlfatoEquipe1[xNovo][yNovo] = 1;

			// Atualiza a posi��o do agente
			this.x = xNovo;
			this.y = yNovo;
		}
	}

	/*
	 * private void isObjetivo(int xNovo, int yNovo) { if (isCelulaValida(xNovo,
	 * yNovo)) { if (matrizSimulacao[xNovo][yNovo] == Constantes.objetivo) {
	 * controlador.pause(); JOptionPane.showMessageDialog(null, "Voc� Ganhou!"); } } }
	 */

	private boolean isCelulaValida(int xNovo, int yNovo) {
		if ((xNovo > 29) || (xNovo < 0) || (yNovo > 29) || (yNovo < 0))
			return false;
		else
			return true;
	}

	public void moverEquipe2(int posicao) {

		switch (posicao) {
		case 0:
			this.ficarParadoEquipe2();
			break;
		case 1:
			this.direcao = Constantes.NORTE;
			this.moveCimaEquipe2();
			break;
		case 2:
			this.direcao = Constantes.SUL;
			this.moveBaixoEquipe2();
			break;
		case 3:
			this.direcao = Constantes.OESTE;
			this.moveDireitaEquipe2();
			break;
		case 4:
			this.direcao = Constantes.LESTE;
			this.moveEsquerdaEquipe2();
			break;
		}

	}

	public void moverEquipe1(int posicao) {

		switch (posicao) {
		case 0:
			this.ficarParadoEquipe1();
			break;
		case 1:
			this.direcao = Constantes.NORTE;
			this.moveCimaEquipe1();
			break;
		case 2:
			this.direcao = Constantes.SUL;
			this.moveBaixoEquipe1();
			break;
		case 3:
			this.direcao = Constantes.OESTE;
			this.moveDireitaEquipe1();
			break;
		case 4:
			this.direcao = Constantes.LESTE;
			this.moveEsquerdaEquipe1();
			break;
		}
	}

	private boolean isPosicaoValida(int xNovo, int yNovo) {
		if (isCelulaValida(xNovo, yNovo)) {
			if (matrizSimulacao[xNovo][yNovo] == Constantes.posicaoLivre)
				return true;
			else
				return false;
		} else
			return false;
	}

	private void isEquipe1(int xNovo, int yNovo) {

		if (isCelulaValida(xNovo, yNovo)) {
			// se achar fantasma morre
			if ((matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_1)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_2)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_3)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_4)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_5)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_6)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_7)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_8)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_9)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_10)) {

				for (Agentes element : this.equipes) {

					// Caso seja um individuo da equipe oponente
					if ((element.getArquitetura().getPosicao().x == xNovo)
							& (element.getArquitetura().getPosicao().y == yNovo)) {

						// encontrou pelas costas - mata o agente oponente
						if (this.direcao
								.equals(element.getArquitetura().direcao)) {
							if (element.getArquitetura().EnergiaIndividual > 0) {
								element.getArquitetura().EnergiaIndividual = 0;
								// element.getArquitetura().numeroAgente =1000;
							}
						} else {// leste ou oesta tira uma unidade de energia

							// condicoes para eles baterem de frente - nao
							// perdem ponto algum
							if ((element.getArquitetura().direcao
									.equals(Constantes.LESTE))
									& (this.direcao.equals(Constantes.OESTE))) {
							} else if ((element.getArquitetura().direcao
									.equals(Constantes.OESTE))
									& (this.direcao.equals(Constantes.LESTE))) {
							} else if ((element.getArquitetura().direcao
									.equals(Constantes.NORTE))
									& (this.direcao.equals(Constantes.SUL))) {
							} else if ((element.getArquitetura().direcao
									.equals(Constantes.SUL))
									& (this.direcao.equals(Constantes.NORTE))) {
							} else {
								// encontrou pelo lado - perde um ponto.
								if (element.getArquitetura().EnergiaIndividual > 0) {
									element.getArquitetura().EnergiaIndividual = (element
											.getArquitetura().EnergiaIndividual - 1);
								}
							}
						}
					}
				}
			}
		}
	}

	private Boolean validaEquipe1(int xNovo, int yNovo) {
		Boolean resultado = false;

		if (isCelulaValida(xNovo, yNovo)) {
			// se achar fantasma morre
			if ((matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_1)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_2)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_3)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_4)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_5)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_6)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_7)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_8)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_9)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe1_10)) {
				resultado = true;

			}
		}

		return resultado;
	}

	private Boolean validaEquipe2(int xNovo, int yNovo) {
		Boolean resultado = false;

		if (isCelulaValida(xNovo, yNovo)) {
			if ((matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_1)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_2)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_3)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_4)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_5)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_6)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_7)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_8)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_9)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_10)) {
				resultado = true;
			}
		}
		return resultado;
	}

	private void isEquipe2(int xNovo, int yNovo) {
		if (isCelulaValida(xNovo, yNovo)) {
			// se achar fantasma morre
			if ((matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_1)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_2)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_3)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_4)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_5)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_6)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_7)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_8)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_9)
					|| (matrizSimulacao[xNovo][yNovo] == Constantes.numeroEquipe2_10)) {

				for (Agentes element : this.equipes) {

					// Caso seja um individuo da equipe oponente
					if ((element.getArquitetura().getPosicao().x == xNovo)
							& (element.getArquitetura().getPosicao().y == yNovo)) {

						// encontrou pelas costas - mata o agente oponente
						if (this.direcao
								.equals(element.getArquitetura().direcao)) {
							if (element.getArquitetura().EnergiaIndividual > 0) {
								element.getArquitetura().EnergiaIndividual = 0;
							}
						} else {// leste ou oesta tira uma unidade de energia

							// condicoes para eles baterem de frente - nao
							// perdem ponto algum
							if ((element.getArquitetura().direcao
									.equals(Constantes.LESTE))
									& (this.direcao.equals(Constantes.OESTE))) {
							} else if ((element.getArquitetura().direcao
									.equals(Constantes.OESTE))
									& (this.direcao.equals(Constantes.LESTE))) {
							} else if ((element.getArquitetura().direcao
									.equals(Constantes.NORTE))
									& (this.direcao.equals(Constantes.SUL))) {
							} else if ((element.getArquitetura().direcao
									.equals(Constantes.SUL))
									& (this.direcao.equals(Constantes.NORTE))) {
							} else {
								// encontrou pelo lado - perde um ponto.
								if (element.getArquitetura().EnergiaIndividual > 0) {
									element.getArquitetura().EnergiaIndividual = (element
											.getArquitetura().EnergiaIndividual - 1);
								}
							}
						}
					}
				}

			}
		}
	}

	/*
	 * private void perdeu() { controlador.pause();
	 * JOptionPane.showMessageDialog(null, "Voc� Perdeu!"); }
	 */
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getNumeroAgente() {
		return numeroAgente;
	}

	public String getDirecao() {
		return direcao;
	}

	public void setDirecao(String direcao) {
		this.direcao = direcao;
	}

	public int getEnergiaIndividual() {
		return EnergiaIndividual;
	}

	public void setEnergiaIndividual(int energiaIndividual) {
		EnergiaIndividual = energiaIndividual;
	}

	public int retornaEnergiaAgente(int xAgente, int yAgente) {

		int resultado = 0;

		for (Agentes element : this.equipes) {

			if ((element.getArquitetura().getPosicao().x == xAgente)
					& (element.getArquitetura().getPosicao().y == yAgente)) {

				resultado = element.getArquitetura().EnergiaIndividual;
			}
		}

		return resultado;
	}

	public int retornaDirecaoAgente(int xAgente, int yAgente) {

		int resultado = 0;

		for (Agentes element : this.equipes) {

			if ((element.getArquitetura().getPosicao().x == xAgente)
					& (element.getArquitetura().getPosicao().y == yAgente)) {

				if (element.getArquitetura().direcao.equals(Constantes.NORTE)) {
					resultado = 1;
				} else if (element.getArquitetura().direcao
						.equals(Constantes.SUL)) {
					resultado = 2;
				} else if (element.getArquitetura().direcao
						.equals(Constantes.LESTE)) {
					resultado = 3;
				} else if (element.getArquitetura().direcao
						.equals(Constantes.OESTE)) {
					resultado = 4;
				}
			}
		}

		return resultado;
	}

	public int[] percebeEnergiaOponenteEquipe2() {

		int[] ambiente = new int[24];

		for (int j = 0; j < 24; j++) {
			ambiente[j] = 0;
		}

		if ((x == 0) || (y == 0))
			ambiente[6] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x - 1, y - 1)) {
				ambiente[6] = retornaEnergiaAgente(x - 1, y - 1);
			}
		}

		if ((y == 0))
			ambiente[7] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x, y - 1)) {
				ambiente[7] = retornaEnergiaAgente(x, y - 1);
			}
		}

		if ((x == 29) || (y == 0))
			ambiente[8] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x + 1, y - 1)) {
				ambiente[8] = retornaEnergiaAgente(x + 1, y - 1);
			}
		}

		if ((x == 0))
			ambiente[11] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x - 1, y)) {
				ambiente[11] = retornaEnergiaAgente(x - 1, y);
			}
		}

		if ((x == 29))
			ambiente[12] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x + 1, y)) {
				ambiente[12] = retornaEnergiaAgente(x + 1, y);
			}
		}

		if ((x == 0) || (y == 29))
			ambiente[15] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x - 1, y + 1)) {
				ambiente[15] = retornaEnergiaAgente(x - 1, y + 1);
			}
		}

		if ((y == 29))
			ambiente[16] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x, y + 1)) {
				ambiente[16] = retornaEnergiaAgente(x, y + 1);
			}
		}

		if ((x == 29) || (y == 29))
			ambiente[17] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x + 1, y + 1)) {
				ambiente[17] = retornaEnergiaAgente(x + 1, y + 1);
			}
		}

		if (ambiente[6] == Constantes.numeroParede)
			ambiente[0] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y <= 1))
				ambiente[0] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 2, y - 2)) {
					ambiente[0] = retornaEnergiaAgente(x - 2, y - 2);
				}
			}
		}

		if (ambiente[6] == Constantes.numeroParede
				&& ambiente[7] == Constantes.numeroParede)
			ambiente[1] = 0;// Constantes.semVisao;
		else {
			if ((x == 0) || (y <= 1))
				ambiente[1] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 1, y - 2)) {
					ambiente[1] = retornaEnergiaAgente(x - 1, y - 2);
				}
			}
		}

		if (ambiente[7] == Constantes.numeroParede)
			ambiente[2] = 0;// Constantes.semVisao;
		else {
			if ((y <= 1))
				ambiente[2] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x, y - 2)) {
					ambiente[2] = retornaEnergiaAgente(x, y - 2);
				}
			}
		}

		if (ambiente[7] == Constantes.numeroParede
				&& ambiente[8] == Constantes.numeroParede)
			ambiente[3] = 0;// Constantes.semVisao;
		else {
			if ((x == 29) || (y <= 1))
				ambiente[3] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 1, y - 2)) {
					ambiente[3] = retornaEnergiaAgente(x + 1, y - 2);
				}
			}
		}

		if (ambiente[8] == Constantes.numeroParede)
			ambiente[4] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y <= 1))
				ambiente[4] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 2, y - 2)) {
					ambiente[4] = retornaEnergiaAgente(x + 2, y - 2);
				}
			}
		}

		if (ambiente[6] == Constantes.numeroParede
				&& ambiente[11] == Constantes.numeroParede)
			ambiente[5] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y == 0))
				ambiente[5] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 2, y - 1)) {
					ambiente[5] = retornaEnergiaAgente(x - 2, y - 1);
				}
			}

		}

		if (ambiente[8] == Constantes.numeroParede
				&& ambiente[12] == Constantes.numeroParede)
			ambiente[9] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y == 0))
				ambiente[9] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 2, y - 1)) {
					ambiente[9] = retornaEnergiaAgente(x + 2, y - 1);
				}
			}
		}

		if (ambiente[11] == Constantes.numeroParede)
			ambiente[10] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1))
				ambiente[10] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 2, y)) {
					ambiente[10] = retornaEnergiaAgente(x - 2, y);
				}
			}

		}

		if (ambiente[12] == Constantes.numeroParede)
			ambiente[13] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28))
				ambiente[13] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 2, y)) {
					ambiente[13] = retornaEnergiaAgente(x + 2, y);
				}
			}
		}

		if (ambiente[11] == Constantes.numeroParede
				&& ambiente[15] == Constantes.numeroParede)
			ambiente[14] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y == 29))
				ambiente[14] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 2, y + 1)) {
					ambiente[14] = retornaEnergiaAgente(x - 2, y + 1);
				}
			}

		}

		if (ambiente[12] == Constantes.numeroParede
				&& ambiente[17] == Constantes.numeroParede)
			ambiente[18] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y == 29))
				ambiente[18] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 2, y + 1)) {
					ambiente[18] = retornaEnergiaAgente(x + 2, y + 1);
				}
			}
		}

		if (ambiente[15] == Constantes.numeroParede)
			ambiente[19] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y >= 28))
				ambiente[19] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 2, y + 2)) {
					ambiente[19] = retornaEnergiaAgente(x - 2, y + 2);
				}
			}
		}

		if (ambiente[15] == Constantes.numeroParede
				&& ambiente[16] == Constantes.numeroParede)
			ambiente[20] = 0;// Constantes.semVisao;
		else {
			if ((x == 0) || (y >= 28))
				ambiente[20] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 1, y + 2)) {
					ambiente[20] = retornaEnergiaAgente(x - 1, y + 2);
				}
			}

		}

		if (ambiente[16] == Constantes.numeroParede)
			ambiente[21] = 0;// Constantes.semVisao;
		else {
			if ((y >= 28))
				ambiente[21] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x, y + 2)) {
					ambiente[21] = retornaEnergiaAgente(x, y + 2);
				}
			}
		}

		if (ambiente[16] == Constantes.numeroParede
				&& ambiente[17] == Constantes.numeroParede)
			ambiente[22] = 0;// Constantes.semVisao;
		else {
			if ((x == 29) || (y >= 28))
				ambiente[22] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 1, y + 2)) {
					ambiente[22] = retornaEnergiaAgente(x + 1, y + 2);
				}
			}

		}

		if (ambiente[17] == Constantes.numeroParede)
			ambiente[23] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y >= 28))
				ambiente[23] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 2, y + 2)) {
					ambiente[23] = retornaEnergiaAgente(x + 2, y + 2);
				}
			}
		}

		return ambiente;
	}

	public int[] percebeEnergiaOponenteEquipe1() {

		int[] ambiente = new int[24];

		for (int j = 0; j < 24; j++) {
			ambiente[j] = 0;
		}

		if ((x == 0) || (y == 0))
			ambiente[6] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x - 1, y - 1)) {
				ambiente[6] = retornaEnergiaAgente(x - 1, y - 1);
			}
		}

		if ((y == 0))
			ambiente[7] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x, y - 1)) {
				ambiente[7] = retornaEnergiaAgente(x, y - 1);
			}
		}

		if ((x == 29) || (y == 0))
			ambiente[8] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x + 1, y - 1)) {
				ambiente[8] = retornaEnergiaAgente(x + 1, y - 1);
			}
		}

		if ((x == 0))
			ambiente[11] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x - 1, y)) {
				ambiente[11] = retornaEnergiaAgente(x - 1, y);
			}
		}

		if ((x == 29))
			ambiente[12] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x + 1, y)) {
				ambiente[12] = retornaEnergiaAgente(x + 1, y);
			}
		}

		if ((x == 0) || (y == 29))
			ambiente[15] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x - 1, y + 1)) {
				ambiente[15] = retornaEnergiaAgente(x - 1, y + 1);
			}
		}

		if ((y == 29))
			ambiente[16] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x, y + 1)) {
				ambiente[16] = retornaEnergiaAgente(x, y + 1);
			}
		}

		if ((x == 29) || (y == 29))
			ambiente[17] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x + 1, y + 1)) {
				ambiente[17] = retornaEnergiaAgente(x + 1, y + 1);
			}
		}

		if (ambiente[6] == Constantes.numeroParede)
			ambiente[0] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y <= 1))
				ambiente[0] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 2, y - 2)) {
					ambiente[0] = retornaEnergiaAgente(x - 2, y - 2);
				}
			}
		}

		if (ambiente[6] == Constantes.numeroParede
				&& ambiente[7] == Constantes.numeroParede)
			ambiente[1] = 0;// Constantes.semVisao;
		else {
			if ((x == 0) || (y <= 1))
				ambiente[1] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 1, y - 2)) {
					ambiente[1] = retornaEnergiaAgente(x - 1, y - 2);
				}
			}
		}

		if (ambiente[7] == Constantes.numeroParede)
			ambiente[2] = 0;// Constantes.semVisao;
		else {
			if ((y <= 1))
				ambiente[2] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x, y - 2)) {
					ambiente[2] = retornaEnergiaAgente(x, y - 2);
				}
			}
		}

		if (ambiente[7] == Constantes.numeroParede
				&& ambiente[8] == Constantes.numeroParede)
			ambiente[3] = 0;// Constantes.semVisao;
		else {
			if ((x == 29) || (y <= 1))
				ambiente[3] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 1, y - 2)) {
					ambiente[3] = retornaEnergiaAgente(x + 1, y - 2);
				}
			}
		}

		if (ambiente[8] == Constantes.numeroParede)
			ambiente[4] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y <= 1))
				ambiente[4] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 2, y - 2)) {
					ambiente[4] = retornaEnergiaAgente(x + 2, y - 2);
				}
			}
		}

		if (ambiente[6] == Constantes.numeroParede
				&& ambiente[11] == Constantes.numeroParede)
			ambiente[5] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y == 0))
				ambiente[5] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 2, y - 1)) {
					ambiente[5] = retornaEnergiaAgente(x - 2, y - 1);
				}
			}

		}

		if (ambiente[8] == Constantes.numeroParede
				&& ambiente[12] == Constantes.numeroParede)
			ambiente[9] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y == 0))
				ambiente[9] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 2, y - 1)) {
					ambiente[9] = retornaEnergiaAgente(x + 2, y - 1);
				}
			}
		}

		if (ambiente[11] == Constantes.numeroParede)
			ambiente[10] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1))
				ambiente[10] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 2, y)) {
					ambiente[10] = retornaEnergiaAgente(x - 2, y);
				}
			}

		}

		if (ambiente[12] == Constantes.numeroParede)
			ambiente[13] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28))
				ambiente[13] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 2, y)) {
					ambiente[13] = retornaEnergiaAgente(x + 2, y);
				}
			}
		}

		if (ambiente[11] == Constantes.numeroParede
				&& ambiente[15] == Constantes.numeroParede)
			ambiente[14] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y == 29))
				ambiente[14] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 2, y + 1)) {
					ambiente[14] = retornaEnergiaAgente(x - 2, y + 1);
				}
			}

		}

		if (ambiente[12] == Constantes.numeroParede
				&& ambiente[17] == Constantes.numeroParede)
			ambiente[18] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y == 29))
				ambiente[18] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 2, y + 1)) {
					ambiente[18] = retornaEnergiaAgente(x + 2, y + 1);
				}
			}
		}

		if (ambiente[15] == Constantes.numeroParede)
			ambiente[19] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y >= 28))
				ambiente[19] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 2, y + 2)) {
					ambiente[19] = retornaEnergiaAgente(x - 2, y + 2);
				}
			}
		}

		if (ambiente[15] == Constantes.numeroParede
				&& ambiente[16] == Constantes.numeroParede)
			ambiente[20] = 0;// Constantes.semVisao;
		else {
			if ((x == 0) || (y >= 28))
				ambiente[20] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 1, y + 2)) {
					ambiente[20] = retornaEnergiaAgente(x - 1, y + 2);
				}
			}

		}

		if (ambiente[16] == Constantes.numeroParede)
			ambiente[21] = 0;// Constantes.semVisao;
		else {
			if ((y >= 28))
				ambiente[21] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x, y + 2)) {
					ambiente[21] = retornaEnergiaAgente(x, y + 2);
				}
			}
		}

		if (ambiente[16] == Constantes.numeroParede
				&& ambiente[17] == Constantes.numeroParede)
			ambiente[22] = 0;// Constantes.semVisao;
		else {
			if ((x == 29) || (y >= 28))
				ambiente[22] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 1, y + 2)) {
					ambiente[22] = retornaEnergiaAgente(x + 1, y + 2);
				}
			}

		}

		if (ambiente[17] == Constantes.numeroParede)
			ambiente[23] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y >= 28))
				ambiente[23] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 2, y + 2)) {
					ambiente[23] = retornaEnergiaAgente(x + 2, y + 2);
				}
			}
		}

		return ambiente;
	}

	public int[] percebeDirecaoOponenteEquipe1() {

		int[] ambiente = new int[24];

		for (int j = 0; j < 24; j++) {
			ambiente[j] = 0;
		}

		if ((x == 0) || (y == 0))
			ambiente[6] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x - 1, y - 1)) {
				ambiente[6] = retornaDirecaoAgente(x - 1, y - 1);
			}
		}

		if ((y == 0))
			ambiente[7] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x, y - 1)) {
				ambiente[7] = retornaDirecaoAgente(x, y - 1);
			}
		}

		if ((x == 29) || (y == 0))
			ambiente[8] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x + 1, y - 1)) {
				ambiente[8] = retornaDirecaoAgente(x + 1, y - 1);
			}
		}

		if ((x == 0))
			ambiente[11] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x - 1, y)) {
				ambiente[11] = retornaDirecaoAgente(x - 1, y);
			}
		}

		if ((x == 29))
			ambiente[12] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x + 1, y)) {
				ambiente[12] = retornaDirecaoAgente(x + 1, y);
			}
		}

		if ((x == 0) || (y == 29))
			ambiente[15] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x - 1, y + 1)) {
				ambiente[15] = retornaDirecaoAgente(x - 1, y + 1);
			}
		}

		if ((y == 29))
			ambiente[16] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x, y + 1)) {
				ambiente[16] = retornaDirecaoAgente(x, y + 1);
			}
		}

		if ((x == 29) || (y == 29))
			ambiente[17] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe2(x + 1, y + 1)) {
				ambiente[17] = retornaDirecaoAgente(x + 1, y + 1);
			}
		}

		if (ambiente[6] == Constantes.numeroParede)
			ambiente[0] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y <= 1))
				ambiente[0] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 2, y - 2)) {
					ambiente[0] = retornaDirecaoAgente(x - 2, y - 2);
				}
			}
		}

		if (ambiente[6] == Constantes.numeroParede
				&& ambiente[7] == Constantes.numeroParede)
			ambiente[1] = 0;// Constantes.semVisao;
		else {
			if ((x == 0) || (y <= 1))
				ambiente[1] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 1, y - 2)) {
					ambiente[1] = retornaDirecaoAgente(x - 1, y - 2);
				}
			}
		}

		if (ambiente[7] == Constantes.numeroParede)
			ambiente[2] = 0;// Constantes.semVisao;
		else {
			if ((y <= 1))
				ambiente[2] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x, y - 2)) {
					ambiente[2] = retornaDirecaoAgente(x, y - 2);
				}
			}
		}

		if (ambiente[7] == Constantes.numeroParede
				&& ambiente[8] == Constantes.numeroParede)
			ambiente[3] = 0;// Constantes.semVisao;
		else {
			if ((x == 29) || (y <= 1))
				ambiente[3] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 1, y - 2)) {
					ambiente[3] = retornaDirecaoAgente(x + 1, y - 2);
				}
			}
		}

		if (ambiente[8] == Constantes.numeroParede)
			ambiente[4] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y <= 1))
				ambiente[4] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 2, y - 2)) {
					ambiente[4] = retornaDirecaoAgente(x + 2, y - 2);
				}
			}
		}

		if (ambiente[6] == Constantes.numeroParede
				&& ambiente[11] == Constantes.numeroParede)
			ambiente[5] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y == 0))
				ambiente[5] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 2, y - 1)) {
					ambiente[5] = retornaDirecaoAgente(x - 2, y - 1);
				}
			}

		}

		if (ambiente[8] == Constantes.numeroParede
				&& ambiente[12] == Constantes.numeroParede)
			ambiente[9] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y == 0))
				ambiente[9] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 2, y - 1)) {
					ambiente[9] = retornaDirecaoAgente(x + 2, y - 1);
				}
			}
		}

		if (ambiente[11] == Constantes.numeroParede)
			ambiente[10] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1))
				ambiente[10] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 2, y)) {
					ambiente[10] = retornaDirecaoAgente(x - 2, y);
				}
			}

		}

		if (ambiente[12] == Constantes.numeroParede)
			ambiente[13] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28))
				ambiente[13] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 2, y)) {
					ambiente[13] = retornaDirecaoAgente(x + 2, y);
				}
			}
		}

		if (ambiente[11] == Constantes.numeroParede
				&& ambiente[15] == Constantes.numeroParede)
			ambiente[14] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y == 29))
				ambiente[14] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 2, y + 1)) {
					ambiente[14] = retornaDirecaoAgente(x - 2, y + 1);
				}
			}

		}

		if (ambiente[12] == Constantes.numeroParede
				&& ambiente[17] == Constantes.numeroParede)
			ambiente[18] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y == 29))
				ambiente[18] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 2, y + 1)) {
					ambiente[18] = retornaDirecaoAgente(x + 2, y + 1);
				}
			}
		}

		if (ambiente[15] == Constantes.numeroParede)
			ambiente[19] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y >= 28))
				ambiente[19] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 2, y + 2)) {
					ambiente[19] = retornaDirecaoAgente(x - 2, y + 2);
				}
			}
		}

		if (ambiente[15] == Constantes.numeroParede
				&& ambiente[16] == Constantes.numeroParede)
			ambiente[20] = 0;// Constantes.semVisao;
		else {
			if ((x == 0) || (y >= 28))
				ambiente[20] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x - 1, y + 2)) {
					ambiente[20] = retornaDirecaoAgente(x - 1, y + 2);
				}
			}

		}

		if (ambiente[16] == Constantes.numeroParede)
			ambiente[21] = 0;// Constantes.semVisao;
		else {
			if ((y >= 28))
				ambiente[21] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x, y + 2)) {
					ambiente[21] = retornaDirecaoAgente(x, y + 2);
				}
			}
		}

		if (ambiente[16] == Constantes.numeroParede
				&& ambiente[17] == Constantes.numeroParede)
			ambiente[22] = 0;// Constantes.semVisao;
		else {
			if ((x == 29) || (y >= 28))
				ambiente[22] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 1, y + 2)) {
					ambiente[22] = retornaDirecaoAgente(x + 1, y + 2);
				}
			}

		}

		if (ambiente[17] == Constantes.numeroParede)
			ambiente[23] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y >= 28))
				ambiente[23] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe2(x + 2, y + 2)) {
					ambiente[23] = retornaDirecaoAgente(x + 2, y + 2);
				}
			}
		}

		return ambiente;
	}

	public int[] percebeDirecaoOponenteEquipe2() {

		int[] ambiente = new int[24];

		for (int j = 0; j < 24; j++) {
			ambiente[j] = 0;
		}

		if ((x == 0) || (y == 0))
			ambiente[6] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x - 1, y - 1)) {
				ambiente[6] = retornaDirecaoAgente(x - 1, y - 1);
			}
		}

		if ((y == 0))
			ambiente[7] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x, y - 1)) {
				ambiente[7] = retornaDirecaoAgente(x, y - 1);
			}
		}

		if ((x == 29) || (y == 0))
			ambiente[8] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x + 1, y - 1)) {
				ambiente[8] = retornaDirecaoAgente(x + 1, y - 1);
			}
		}

		if ((x == 0))
			ambiente[11] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x - 1, y)) {
				ambiente[11] = retornaDirecaoAgente(x - 1, y);
			}
		}

		if ((x == 29))
			ambiente[12] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x + 1, y)) {
				ambiente[12] = retornaDirecaoAgente(x + 1, y);
			}
		}

		if ((x == 0) || (y == 29))
			ambiente[15] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x - 1, y + 1)) {
				ambiente[15] = retornaDirecaoAgente(x - 1, y + 1);
			}
		}

		if ((y == 29))
			ambiente[16] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x, y + 1)) {
				ambiente[16] = retornaDirecaoAgente(x, y + 1);
			}
		}

		if ((x == 29) || (y == 29))
			ambiente[17] = 0;// Constantes.foraAmbiene;
		else {
			if (validaEquipe1(x + 1, y + 1)) {
				ambiente[17] = retornaDirecaoAgente(x + 1, y + 1);
			}
		}

		if (ambiente[6] == Constantes.numeroParede)
			ambiente[0] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y <= 1))
				ambiente[0] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 2, y - 2)) {
					ambiente[0] = retornaDirecaoAgente(x - 2, y - 2);
				}
			}
		}

		if (ambiente[6] == Constantes.numeroParede
				&& ambiente[7] == Constantes.numeroParede)
			ambiente[1] = 0;// Constantes.semVisao;
		else {
			if ((x == 0) || (y <= 1))
				ambiente[1] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 1, y - 2)) {
					ambiente[1] = retornaDirecaoAgente(x - 1, y - 2);
				}
			}
		}

		if (ambiente[7] == Constantes.numeroParede)
			ambiente[2] = 0;// Constantes.semVisao;
		else {
			if ((y <= 1))
				ambiente[2] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x, y - 2)) {
					ambiente[2] = retornaDirecaoAgente(x, y - 2);
				}
			}
		}

		if (ambiente[7] == Constantes.numeroParede
				&& ambiente[8] == Constantes.numeroParede)
			ambiente[3] = 0;// Constantes.semVisao;
		else {
			if ((x == 29) || (y <= 1))
				ambiente[3] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 1, y - 2)) {
					ambiente[3] = retornaDirecaoAgente(x + 1, y - 2);
				}
			}
		}

		if (ambiente[8] == Constantes.numeroParede)
			ambiente[4] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y <= 1))
				ambiente[4] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 2, y - 2)) {
					ambiente[4] = retornaDirecaoAgente(x + 2, y - 2);
				}
			}
		}

		if (ambiente[6] == Constantes.numeroParede
				&& ambiente[11] == Constantes.numeroParede)
			ambiente[5] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y == 0))
				ambiente[5] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 2, y - 1)) {
					ambiente[5] = retornaDirecaoAgente(x - 2, y - 1);
				}
			}

		}

		if (ambiente[8] == Constantes.numeroParede
				&& ambiente[12] == Constantes.numeroParede)
			ambiente[9] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y == 0))
				ambiente[9] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 2, y - 1)) {
					ambiente[9] = retornaDirecaoAgente(x + 2, y - 1);
				}
			}
		}

		if (ambiente[11] == Constantes.numeroParede)
			ambiente[10] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1))
				ambiente[10] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 2, y)) {
					ambiente[10] = retornaDirecaoAgente(x - 2, y);
				}
			}

		}

		if (ambiente[12] == Constantes.numeroParede)
			ambiente[13] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28))
				ambiente[13] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 2, y)) {
					ambiente[13] = retornaDirecaoAgente(x + 2, y);
				}
			}
		}

		if (ambiente[11] == Constantes.numeroParede
				&& ambiente[15] == Constantes.numeroParede)
			ambiente[14] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y == 29))
				ambiente[14] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 2, y + 1)) {
					ambiente[14] = retornaDirecaoAgente(x - 2, y + 1);
				}
			}

		}

		if (ambiente[12] == Constantes.numeroParede
				&& ambiente[17] == Constantes.numeroParede)
			ambiente[18] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y == 29))
				ambiente[18] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 2, y + 1)) {
					ambiente[18] = retornaDirecaoAgente(x + 2, y + 1);
				}
			}
		}

		if (ambiente[15] == Constantes.numeroParede)
			ambiente[19] = 0;// Constantes.semVisao;
		else {
			if ((x <= 1) || (y >= 28))
				ambiente[19] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 2, y + 2)) {
					ambiente[19] = retornaDirecaoAgente(x - 2, y + 2);
				}
			}
		}

		if (ambiente[15] == Constantes.numeroParede
				&& ambiente[16] == Constantes.numeroParede)
			ambiente[20] = 0;// Constantes.semVisao;
		else {
			if ((x == 0) || (y >= 28))
				ambiente[20] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x - 1, y + 2)) {
					ambiente[20] = retornaDirecaoAgente(x - 1, y + 2);
				}
			}

		}

		if (ambiente[16] == Constantes.numeroParede)
			ambiente[21] = 0;// Constantes.semVisao;
		else {
			if ((y >= 28))
				ambiente[21] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x, y + 2)) {
					ambiente[21] = retornaDirecaoAgente(x, y + 2);
				}
			}
		}

		if (ambiente[16] == Constantes.numeroParede
				&& ambiente[17] == Constantes.numeroParede)
			ambiente[22] = 0;// Constantes.semVisao;
		else {
			if ((x == 29) || (y >= 28))
				ambiente[22] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 1, y + 2)) {
					ambiente[22] = retornaDirecaoAgente(x + 1, y + 2);
				}
			}

		}

		if (ambiente[17] == Constantes.numeroParede)
			ambiente[23] = 0;// Constantes.semVisao;
		else {
			if ((x >= 28) || (y >= 28))
				ambiente[23] = 0;// Constantes.foraAmbiene;
			else {
				if (validaEquipe1(x + 2, y + 2)) {
					ambiente[23] = retornaDirecaoAgente(x + 2, y + 2);
				}
			}
		}

		return ambiente;
	}

}