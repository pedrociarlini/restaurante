package controle;

import algoritmo.Ambiente;

public class ThreadSimulacao extends Thread {

	/**
	 * Indica se o ambiente está pausado.
	 */
	public boolean pleaseWait = false;

	/**
	 * Indica se a simulação terminou.
	 */
	public boolean allDone = false;

	private int tempoSimulação;

	// Matriz da simulação
	private int matrizSimulacao[][] = null;

	// AlgoritmoLabirinto
	private Ambiente ambiente;

	private Controlador controlador;

	public ThreadSimulacao(Ambiente algoritmoLabirinto,
			Controlador controlador, int matrizSimulacao[][]) {
		setName(this.getClass().getSimpleName());
		this.ambiente = algoritmoLabirinto;
		this.controlador = controlador;
		this.matrizSimulacao = matrizSimulacao;
		tempoSimulação = 100;
	}

	public void run() {

		while (true) {

			try {
				if (controlador.isVisual())
					Thread.sleep(tempoSimulação);
				
				ambiente.executa();
				
				if (controlador.isVisual())
					controlador.atualizaVisual(ambiente.soldados, matrizSimulacao);
				
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			// Check if should wait
			synchronized (this) {
				while (pleaseWait) {
					try {
						wait();
					} catch (Exception e) {
						// e.printStackTrace();
					}

					// Mata a thread
					if (allDone) {
						// return;
						break;
					}
				}
			}

			// Mata a thread
			if (allDone) {
				// return;
				break;
			}
		}

		System.out.println("Thread simulação finalizou");
	}
}
