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
	
	public List equipes = new ArrayList(); 

	// Cria os agentes
	//Equipe 1
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
	
	int lin = 30;
	int col = 30;

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
		Equipe2 pm1 = new Equipe2();
		Equipe2 pm2 = new Equipe2();
		Equipe2 pm3 = new Equipe2();
		Equipe2 pm4 = new Equipe2();
		Equipe2 pm5 = new Equipe2();
		Equipe2 pm6 = new Equipe2();
		Equipe2 pm7 = new Equipe2();
		Equipe2 pm8 = new Equipe2();
		Equipe2 pm9 = new Equipe2();
		Equipe2 pm10 = new Equipe2();

		equipe2_1 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 200, pm1,this.equipes, matrizOlfatoEquipe2), pm1);
		equipe2_2 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 210, pm2,this.equipes, matrizOlfatoEquipe2), pm2);
		equipe2_3 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 220, pm3,this.equipes, matrizOlfatoEquipe2), pm3);
		equipe2_4 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 230, pm4,this.equipes, matrizOlfatoEquipe2), pm4);
		equipe2_5 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 240, pm5,this.equipes, matrizOlfatoEquipe2), pm5);
		equipe2_6 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 250, pm6,this.equipes, matrizOlfatoEquipe2), pm6);
		equipe2_7 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 260, pm7,this.equipes, matrizOlfatoEquipe2), pm7);
		equipe2_8 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 270, pm8,this.equipes, matrizOlfatoEquipe2), pm8);
		equipe2_9 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 280, pm9,this.equipes, matrizOlfatoEquipe2), pm9);
		equipe2_10 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 290, pm10,this.equipes, matrizOlfatoEquipe2), pm10);
		
		
		// agentes equipe1
		Equipe1 fm1 = new Equipe1();
		Equipe1 fm2 = new Equipe1();
		Equipe1 fm3 = new Equipe1();
		Equipe1 fm4 = new Equipe1();
		Equipe1 fm5 = new Equipe1();
		Equipe1 fm6 = new Equipe1();
		Equipe1 fm7 = new Equipe1();
		Equipe1 fm8 = new Equipe1();
		Equipe1 fm9 = new Equipe1();
		Equipe1 fm10 = new Equipe1();
		
		equipe1_1 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 100, fm1,this.equipes, matrizOlfatoEquipe2), fm1);
		equipe1_2 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 110, fm2,this.equipes, matrizOlfatoEquipe2), fm2);
		equipe1_3 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 120, fm3,this.equipes, matrizOlfatoEquipe2), fm3);
		equipe1_4 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 130, fm4,this.equipes, matrizOlfatoEquipe2), fm4);
		equipe1_5 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 140, fm5,this.equipes, matrizOlfatoEquipe2), fm5);
		equipe1_6 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 150, fm6,this.equipes, matrizOlfatoEquipe2), fm6);
		equipe1_7 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 160, fm7,this.equipes, matrizOlfatoEquipe2), fm7);
		equipe1_8 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 170, fm8,this.equipes, matrizOlfatoEquipe2), fm8);
		equipe1_9 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 180, fm9,this.equipes, matrizOlfatoEquipe2), fm9);
		equipe1_10 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoEquipe1,
				controlador, 190, fm10,this.equipes, matrizOlfatoEquipe2), fm10);
		
		//---------------------------
		//Lista de Equipes
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
		//---------------------------
	
	}

	public void executa() {

		//Equipe 2 ---------------------------------------------------------
		if (equipe2_1.getArquitetura().getEnergiaIndividual()>0){
			equipe2_1.getArquitetura().percebeEquipe2();
			equipe2_1.getArquitetura().moverEquipe2(equipe2_1.getPrograma().acao());
		}
		
		if (equipe2_2.getArquitetura().getEnergiaIndividual()>0){
			equipe2_2.getArquitetura().percebeEquipe2();
			equipe2_2.getArquitetura().moverEquipe2(equipe2_2.getPrograma().acao());
		}

		if (equipe2_3.getArquitetura().getEnergiaIndividual()>0){
			equipe2_3.getArquitetura().percebeEquipe2();
			equipe2_3.getArquitetura().moverEquipe2(equipe2_3.getPrograma().acao());
		}
		if (equipe2_4.getArquitetura().getEnergiaIndividual()>0){
			equipe2_4.getArquitetura().percebeEquipe2();
			equipe2_4.getArquitetura().moverEquipe2(equipe2_4.getPrograma().acao());
		}
		
		if (equipe2_5.getArquitetura().getEnergiaIndividual()>0){
			equipe2_5.getArquitetura().percebeEquipe2();
			equipe2_5.getArquitetura().moverEquipe2(equipe2_5.getPrograma().acao());
		}

		if (equipe2_6.getArquitetura().getEnergiaIndividual()>0){
			equipe2_6.getArquitetura().percebeEquipe2();
			equipe2_6.getArquitetura().moverEquipe2(equipe2_6.getPrograma().acao());
		}
		if (equipe2_7.getArquitetura().getEnergiaIndividual()>0){
			equipe2_7.getArquitetura().percebeEquipe2();
			equipe2_7.getArquitetura().moverEquipe2(equipe2_7.getPrograma().acao());
		}
		
		if (equipe2_8.getArquitetura().getEnergiaIndividual()>0){
			equipe2_8.getArquitetura().percebeEquipe2();
			equipe2_8.getArquitetura().moverEquipe2(equipe2_8.getPrograma().acao());
		}

		if (equipe2_9.getArquitetura().getEnergiaIndividual()>0){
			equipe2_9.getArquitetura().percebeEquipe2();
			equipe2_9.getArquitetura().moverEquipe2(equipe2_9.getPrograma().acao());
		}

		if (equipe2_10.getArquitetura().getEnergiaIndividual()>0){
			equipe2_10.getArquitetura().percebeEquipe2();
			equipe2_10.getArquitetura().moverEquipe2(equipe2_10.getPrograma().acao());
		}
		//Equipe 2 ---------------------------------------------------------
		
		//Equipe 1 ---------------------------------------------------------
		if (equipe1_1.getArquitetura().getEnergiaIndividual()>0){
			equipe1_1.getArquitetura().percebeEquipe1();
			equipe1_1.getArquitetura().moverEquipe1(equipe1_1.getPrograma().acao());	
		}

		if (equipe1_2.getArquitetura().getEnergiaIndividual()>0){
			equipe1_2.getArquitetura().percebeEquipe1();
			equipe1_2.getArquitetura().moverEquipe1(equipe1_2.getPrograma().acao());	
		}

		if (equipe1_3.getArquitetura().getEnergiaIndividual()>0){
			equipe1_3.getArquitetura().percebeEquipe1();
			equipe1_3.getArquitetura().moverEquipe1(equipe1_3.getPrograma().acao());
		}
		if (equipe1_4.getArquitetura().getEnergiaIndividual()>0){
			equipe1_4.getArquitetura().percebeEquipe1();
			equipe1_4.getArquitetura().moverEquipe1(equipe1_4.getPrograma().acao());	
		}

		if (equipe1_5.getArquitetura().getEnergiaIndividual()>0){
			equipe1_5.getArquitetura().percebeEquipe1();
			equipe1_5.getArquitetura().moverEquipe1(equipe1_5.getPrograma().acao());	
		}

		if (equipe1_6.getArquitetura().getEnergiaIndividual()>0){
			equipe1_6.getArquitetura().percebeEquipe1();
			equipe1_6.getArquitetura().moverEquipe1(equipe1_6.getPrograma().acao());
		}
		if (equipe1_7.getArquitetura().getEnergiaIndividual()>0){
			equipe1_7.getArquitetura().percebeEquipe1();
			equipe1_7.getArquitetura().moverEquipe1(equipe1_7.getPrograma().acao());	
		}

		if (equipe1_8.getArquitetura().getEnergiaIndividual()>0){
			equipe1_8.getArquitetura().percebeEquipe1();
			equipe1_8.getArquitetura().moverEquipe1(equipe1_8.getPrograma().acao());	
		}

		if (equipe1_9.getArquitetura().getEnergiaIndividual()>0){
			equipe1_9.getArquitetura().percebeEquipe1();
			equipe1_9.getArquitetura().moverEquipe1(equipe1_9.getPrograma().acao());
		}
		
		if (equipe1_10.getArquitetura().getEnergiaIndividual()>0){
			equipe1_10.getArquitetura().percebeEquipe1();
			equipe1_10.getArquitetura().moverEquipe1(equipe1_10.getPrograma().acao());
		}

		// Equipe 1 ---------------------------------------------------------
		
		controlador.reduzTempo(1);
		
/*		if (posicaoMovimentoEquipe2 > 0) {
			//controlador.atualizaTempo(1);
		}
*/		
		this.decrementaFeromonio();
		controlador.isFimSimulacao();
		//this.imprimeFeromonio();
	}

	private void decrementaFeromonio() {

		//Equipe 1
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

		//Equipe 2
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

	private void imprimeFeromonio() {
		System.out.println("-----------------------------------------------------------------------");
		for (int i = 0; i < lin; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(matrizOlfatoEquipe1[i][j] + ";");
				//System.out.print(matrizSimulacao[j][i] + ";");
			}
			System.out.println("");
		}
		System.out.println("fim");
	}


}
