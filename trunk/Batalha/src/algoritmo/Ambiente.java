package algoritmo;

import java.util.ArrayList;
import java.util.List;

import controle.Controlador;

public class Ambiente {

	// Controlador
	private Controlador controlador;

	// Matriz da simulação
	int matrizSimulacao[][] = null;

	int matrizOlfatoEquipe1[][] = null;
	int matrizOlfatoEquipe2[][] = null;

	public List<Agentes> equipes = new ArrayList<Agentes>();

	// Cria os agentes
	// Equipe 1
	Agentes equipe2_1;
	Agentes equipe2_2;
	Agentes equipe2_3;
	Agentes equipe2_4;
	Agentes equipe2_5;
	Agentes equipe2_6;
	Agentes equipe2_7;
	Agentes equipe2_8;
	Agentes equipe2_9;
	Agentes equipe2_10;

	Agentes equipe1_1;
	Agentes equipe1_2;
	Agentes equipe1_3;
	Agentes equipe1_4;
	Agentes equipe1_5;
	Agentes equipe1_6;
	Agentes equipe1_7;
	Agentes equipe1_8;
	Agentes equipe1_9;
	Agentes equipe1_10;

	/**
	 * Quantidade de linhas do cenário.
	 */
	int lin = 30;

	/**
	 * Quantidade de colunas do cenário do cenário.
	 */
	int col = 30;

	/**
	 * Contrutor que já prepara o ambiente
	 * 
	 * @param controlador
	 * @param matrizSimulacao
	 */
	public Ambiente(Controlador controlador, int matrizSimulacao[][]) {
		// Controlador
		this.controlador = controlador;
		// MatrizSimulacao
		this.matrizSimulacao = matrizSimulacao;

		// inicializa a a matriz olfato Equipe 1
		this.matrizOlfatoEquipe1 = new int[lin][col];
		for (int i = 0; i < lin; i++) {
			for (int j = 0; j < col; j++) {
				matrizOlfatoEquipe1[i][j] = 0;
			}
		}

		// inicializa a a matriz olfato Equipe 2
		this.matrizOlfatoEquipe2 = new int[lin][col];
		for (int i = 0; i < lin; i++) {
			for (int j = 0; j < col; j++) {
				matrizOlfatoEquipe2[i][j] = 0;
			}
		}

		criaAgentes();
	}

	private void criaAgentes() {

		// agente equipe2
		Equipe2 pm1 = new Equipe2(200);
		Equipe2 pm2 = new Equipe2(210);
		Equipe2 pm3 = new Equipe2(220);
		Equipe2 pm4 = new Equipe2(230);
		Equipe2 pm5 = new Equipe2(240);
		Equipe2 pm6 = new Equipe2(250);
		Equipe2 pm7 = new Equipe2(260);
		Equipe2 pm8 = new Equipe2(270);
		Equipe2 pm9 = new Equipe2(280);
		Equipe2 pm10 = new Equipe2(290);

		equipe2_1 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, pm1.getNome(), pm1,
				this.equipes, matrizOlfatoEquipe2), pm1);
		equipe2_2 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, pm2.getNome(), pm2,
				this.equipes, matrizOlfatoEquipe2), pm2);
		equipe2_3 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, pm3.getNome(), pm3,
				this.equipes, matrizOlfatoEquipe2), pm3);
		equipe2_4 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, pm4.getNome(), pm4,
				this.equipes, matrizOlfatoEquipe2), pm4);
		equipe2_5 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, pm5.getNome(), pm5,
				this.equipes, matrizOlfatoEquipe2), pm5);
		equipe2_6 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, pm6.getNome(), pm6,
				this.equipes, matrizOlfatoEquipe2), pm6);
		equipe2_7 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, pm7.getNome(), pm7,
				this.equipes, matrizOlfatoEquipe2), pm7);
		equipe2_8 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, pm8.getNome(), pm8,
				this.equipes, matrizOlfatoEquipe2), pm8);
		equipe2_9 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, pm9.getNome(), pm9,
				this.equipes, matrizOlfatoEquipe2), pm9);
		equipe2_10 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, pm10.getNome(), pm10,
				this.equipes, matrizOlfatoEquipe2), pm10);

		// agentes equipe1
		Equipe1 fm1 = new Equipe1(100);
		Equipe1 fm2 = new Equipe1(110);
		Equipe1 fm3 = new Equipe1(120);
		Equipe1 fm4 = new Equipe1(130);
		Equipe1 fm5 = new Equipe1(140);
		Equipe1 fm6 = new Equipe1(150);
		Equipe1 fm7 = new Equipe1(160);
		Equipe1 fm8 = new Equipe1(170);
		Equipe1 fm9 = new Equipe1(180);
		Equipe1 fm10 = new Equipe1(190);

		equipe1_1 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, fm1.getNome(), fm1,
				this.equipes, matrizOlfatoEquipe2), fm1);
		equipe1_2 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, fm2.getNome(), fm2,
				this.equipes, matrizOlfatoEquipe2), fm2);
		equipe1_3 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, fm3.getNome(), fm3,
				this.equipes, matrizOlfatoEquipe2), fm3);
		equipe1_4 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, fm4.getNome(), fm4,
				this.equipes, matrizOlfatoEquipe2), fm4);
		equipe1_5 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, fm5.getNome(), fm5,
				this.equipes, matrizOlfatoEquipe2), fm5);
		equipe1_6 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, fm6.getNome(), fm6,
				this.equipes, matrizOlfatoEquipe2), fm6);
		equipe1_7 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, fm7.getNome(), fm7,
				this.equipes, matrizOlfatoEquipe2), fm7);
		equipe1_8 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, fm8.getNome(), fm8,
				this.equipes, matrizOlfatoEquipe2), fm8);
		equipe1_9 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, fm9.getNome(), fm9,
				this.equipes, matrizOlfatoEquipe2), fm9);
		equipe1_10 = new Agentes(new Arquitetura(matrizSimulacao,
				matrizOlfatoEquipe1, controlador, fm10.getNome(), fm10,
				this.equipes, matrizOlfatoEquipe2), fm10);

		// ---------------------------
		// Lista de Equipes
		this.equipes.add(equipe1_1);
		this.equipes.add(equipe1_2);
		this.equipes.add(equipe1_3);
		this.equipes.add(equipe1_4);
		this.equipes.add(equipe1_5);
		this.equipes.add(equipe1_6);
		this.equipes.add(equipe1_7);
		this.equipes.add(equipe1_8);
		this.equipes.add(equipe1_9);
		this.equipes.add(equipe1_10);

		this.equipes.add(equipe2_1);
		this.equipes.add(equipe2_2);
		this.equipes.add(equipe2_3);
		this.equipes.add(equipe2_4);
		this.equipes.add(equipe2_5);
		this.equipes.add(equipe2_6);
		this.equipes.add(equipe2_7);
		this.equipes.add(equipe2_8);
		this.equipes.add(equipe2_9);
		this.equipes.add(equipe2_10);
		// ---------------------------
	}

	public void executa() {
		for (Agentes soldado : equipes) {
			int acaoEscolhida = soldado.getPrograma().acao();
			if (acaoEscolhida != 0) {
				((ProgramaAbstract) soldado.getPrograma())
						.setUltimaAcao(acaoEscolhida);
			}
			if (soldado.getPrograma() instanceof Equipe1) {
				if (soldado.getArquitetura().getEnergiaIndividual() > 0) {
					soldado.getArquitetura().percebeEquipe1();
					soldado.getArquitetura().moverEquipe1(acaoEscolhida);
				}
			} else {
				if (soldado.getArquitetura().getEnergiaIndividual() > 0) {
					soldado.getArquitetura().percebeEquipe2();
					soldado.getArquitetura().moverEquipe2(acaoEscolhida);
				}
			}
		}

		controlador.reduzTempo(1);

		/*
		 * if (posicaoMovimentoEquipe2 > 0) { //controlador.atualizaTempo(1); }
		 */
		this.decrementaFeromonio();
		controlador.isFimSimulacao();
		// this.imprimeFeromonio();
	}

	private void decrementaFeromonio() {

		// Equipe 1
		for (int i = 0; i < lin; i++) {
			for (int j = 0; j < col; j++) {

				if (matrizOlfatoEquipe1[i][j] != 0) {
					if (matrizOlfatoEquipe1[i][j] >= 5) {
						matrizOlfatoEquipe1[i][j] = 0;
					} else {
						matrizOlfatoEquipe1[i][j] += 1;
					}
				}
			}
		}

		// Equipe 2
		for (int i = 0; i < lin; i++) {
			for (int j = 0; j < col; j++) {

				if (matrizOlfatoEquipe2[i][j] != 0) {
					if (matrizOlfatoEquipe2[i][j] >= 5) {
						matrizOlfatoEquipe2[i][j] = 0;
					} else {
						matrizOlfatoEquipe2[i][j] += 1;
					}
				}
			}
		}

	}

	/**
	 * Escreve num log o que aconteceu com o agente.
	 */
	@SuppressWarnings(value = "unused")
	private void logFeromonio() {
		System.out
				.println("-----------------------------------------------------------------------");
		for (int i = 0; i < lin; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(matrizOlfatoEquipe1[i][j] + ";");
				// System.out.print(matrizSimulacao[j][i] + ";");
			}
			System.out.println("");
		}
		System.out.println("fim");
	}

}
