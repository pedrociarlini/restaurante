package algoritmo;

import algoritmo.agentes.AgentesManager;

public class Equipe1 extends ProgramaEquipe1 {
	
	static {
		AgentesManager.registerClass("Equipe1", Equipe1.class);
	}
	
	public Equipe1(int nome) {
		super(nome);
	}

	public int acao() {
		return this.selecionaIntervalo(0,4);
	}

	public int selecionaIntervalo(int intervalo1, int intervalo2) {
		return (int) (intervalo1+Math.random() * (intervalo2-intervalo1+1));
	}

}
