package controle;

import gui.FramePrincipal;
import algoritmo.Ambiente;

public class ThreadSimulacao extends Thread {

	// FramePrincipal
	private FramePrincipal framePrincipal;

	/**
	 * Indica se o ambiente est� pausado.
	 */
	public boolean pleaseWait = false;

	/**
	 * Indica se a simula��o terminou.
	 */
	public boolean allDone = false;

	private int tempoSimula��o;

	// Matriz da simula��o
	private int matrizSimulacao[][] = null;

	// AlgoritmoLabirinto
	private Ambiente ambiente;

	public ThreadSimulacao(Ambiente algoritmoLabirinto,
			FramePrincipal framePrincipal, int matrizSimulacao[][]) {
		setName(this.getClass().getSimpleName());
		this.ambiente = algoritmoLabirinto;
		this.framePrincipal = framePrincipal;
		this.matrizSimulacao = matrizSimulacao;
		tempoSimula��o = 100;
	}

	public void run() {

		while (true) {

			try {
				Thread.sleep(tempoSimula��o);

				
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

		System.out.println("Thread Simula��o Morreu");
	}

}
