package algoritmo;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import model.AgenteVO;
import controle.Controlador;

public class Ambiente {

	// Controlador
	private Controlador controlador;

	// Matriz da simulação
	int matrizSimulacao[][] = null;

	int matrizOlfatoEquipe1[][] = null;
	int matrizOlfatoEquipe2[][] = null;

	public List<Agentes> soldados = new ArrayList<Agentes>();

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

	@SuppressWarnings("unchecked")
	private void criaAgentes() {
		Agentes agenteTemp;

		ProgramaAbstract soldado;
		Constructor constr;
		int centenaSoldado = 0;
		try {
			for (AgenteVO agente : controlador.getAgentesEscolhidos()) {
				centenaSoldado++;
				constr = agente.getClasse().getConstructor(Integer.TYPE);
				for (int i = 0; i < 10; i++) {
					// soldado2 = new Equipe2(200 + (i * 10));
					soldado = (ProgramaAbstract) constr
							.newInstance(centenaSoldado * 100 + (i * 10));
					Arquitetura arq = new Arquitetura(matrizSimulacao,
							matrizOlfatoEquipe1, controlador,
							soldado.getNome(), soldado, this.soldados,
							matrizOlfatoEquipe2);
					agenteTemp = new Agentes(arq, soldado);
					arq.percebeEquipe1();
					this.soldados.add(agenteTemp);
				}
			}
		} catch (Exception e) {
			System.err.println("Erro durante a criação dos agentes;");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void executa() {
		for (Agentes soldado : soldados) {
			int acaoEscolhida = soldado.getPrograma().acao();
			if (acaoEscolhida != 0) {
				((ProgramaAbstract) soldado.getPrograma())
						.setUltimaAcao(acaoEscolhida);
			}
			Class equipe1 = controlador.getAgentesEscolhidos().get(0).getClasse();
			if (soldado.getPrograma().getClass().equals(equipe1)) {
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
