package algoritmo;

import algoritmo.agentes.AgentesManager;


public class Equipe2 extends ProgramaEquipe2 {

	static {
		AgentesManager.registerClass("Equipe1", Equipe1.class);
	}

	static int cont = 0;
	
	public Equipe2(int nome) {
		super(nome);
	}

	public int acao() {
		
		int sorteio = 1;

		sorteio = selecionaIntervalo(0,4);
		cont++;
		
		return sorteio;
	}
	
	public int selecionaIntervalo(int intervalo1, int intervalo2) {
		return (int) (intervalo1+Math.random() * (intervalo2-intervalo1+1));
	}

	public double calculaDistancia(int pacmanX, int pacmanY,	int objeticoX, int objetivoY) {

		double distancia = 0.00;

		distancia = Math.sqrt((Math.pow(objeticoX - pacmanX, 2) + Math.pow(objetivoY	- pacmanY, 2)));

		return distancia;
	}
}
