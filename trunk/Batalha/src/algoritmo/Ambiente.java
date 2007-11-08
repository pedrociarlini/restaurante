package algoritmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	// Equipe 2
	private Map<String, Agentes> equipe2 = new HashMap<String, Agentes>();

	// Equipe 1
	private Map<String, Agentes> equipe1 = new HashMap<String, Agentes>();

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
		Agentes agenteTemp;

		// TODO implementar Busca do agente escolhido pelo classloader.
		// Equipe2
		Equipe2 soldado2;
		for (int i = 0; i < 10; i++) {
			soldado2 = new Equipe2(200 + (i * 10));
			agenteTemp = new Agentes(new Arquitetura(matrizSimulacao,
					matrizOlfatoEquipe1, controlador, soldado2.getNome(),
					soldado2, this.equipes, matrizOlfatoEquipe2), soldado2);
			equipe2.put("equipe2_" + (i + 1), agenteTemp);
			this.equipes.add(agenteTemp);
		}

		// Equipe1
		Equipe1 soldado1;
		for (int i = 0; i < 10; i++) {
			soldado1 = new Equipe1(100 + (i * 10));
			agenteTemp = new Agentes(new Arquitetura(matrizSimulacao,
					matrizOlfatoEquipe1, controlador, soldado1.getNome(),
					soldado1, this.equipes, matrizOlfatoEquipe2), soldado1);
			equipe1.put("equipe1_" + (i + 1), agenteTemp);
			this.equipes.add(agenteTemp);
		}
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
		if (controlador.getTurnosRestantes() <= 0) {
			controlador.finalizaSimulacao();
		}
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
