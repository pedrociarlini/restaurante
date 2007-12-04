package algoritmo;

import model.AgenteVO;
import controle.Controlador;


public class GeneralTest {
	public static void main(String[] args) {
		try {
			Controlador c = new Controlador(new AgenteVO("Equipe treinada",
					Equipe1.class), new AgenteVO("Equipe fixa",
					Equipe1.class), "coliseu.txt");
			c.play();
			c.joinSimulacao();
			System.out.println("ENERGIA_EQUIPE_1:" + c.getResultado(Controlador.ENERGIA_EQUIPE_1));
			System.out.println("ENERGIA_EQUIPE_2:" + c.getResultado(Controlador.ENERGIA_EQUIPE_2));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
