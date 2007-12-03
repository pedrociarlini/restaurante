package controle;

import gui.FramePrincipal;
import algoritmo.Ambiente;

public class ThreadSimulacao extends Thread {

	// FramePrincipal
	private FramePrincipal framePrincipal;

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

	public ThreadSimulacao(Ambiente algoritmoLabirinto,
			FramePrincipal framePrincipal, int matrizSimulacao[][]) {
		setName(this.getClass().getSimpleName());
		this.ambiente = algoritmoLabirinto;
		this.framePrincipal = framePrincipal;
		this.matrizSimulacao = matrizSimulacao;
		tempoSimulação = 100;
	}

	public void run() {

		while (true) {

			try {
				Thread.sleep(tempoSimulação);

				
				ambiente.executa();
				
				framePrincipal.atualizaGrid(ambiente.soldados);
				framePrincipal.atualizaAmbiente(matrizSimulacao);
				
			} catch (InterruptedException e1) {
				// e1.printStackTrace();
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

		System.out.println("Thread Simulação Morreu");
	}

}
