package controle;

import algoritmo.Ambiente;

public class ThreadSimulacao extends Thread {

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

	private Controlador controlador;

	public ThreadSimulacao(Ambiente algoritmoLabirinto,
			Controlador controlador, int matrizSimulacao[][]) {
		setName(this.getClass().getSimpleName());
		this.ambiente = algoritmoLabirinto;
		this.controlador = controlador;
		this.matrizSimulacao = matrizSimulacao;
		tempoSimula��o = 100;
	}

	public void run() {

		while (true) {

			try {
				if (controlador.isVisual())
					Thread.sleep(tempoSimula��o);
				
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

		System.out.println("Thread simula��o finalizou");
	}
}
