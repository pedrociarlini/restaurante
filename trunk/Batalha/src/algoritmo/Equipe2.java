package algoritmo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import controle.Constantes;

public class Equipe2 extends ProgramaEquipe2 {
	static int cont = 0;
	
	public int acao() {
		
		//Lab1
		//int[] movimento = {4,4,4,4,4,4,1,1,1,1,1,1,4,4,1,4,4,1,1,1,1,1,3};
		//Lab2
		//int[] movimento = {3,1,3,3,3,3,3,1,1,1,3,1,1,1,3,1,3,1,1,1,1};
		//Lab3
		int[] movimento = {4,4,4,4,1,1,1,4,4,4,1,4,1,1,1,1,3,1,3,1,1,1,1,1,1,1,1,1,1,1,4,4,1,1,4,4,4,4,4,1,1,3,3};
		
		int sorteio = 1;
		List posicao = new ArrayList();
		List pontos = new ArrayList();
		double menorDistancia = Double.MAX_VALUE;
		int indice = 1;
		int ObjX = 24;//4;//2;//
		int ObjY = 15;//2;//5;//
		
		/*posicao.add(0, 7);
		posicao.add(1, 16);
		posicao.add(2, 12);
		posicao.add(3, 11);
		
		pontos.add(0, new Point(sensor.getPosicao().x,sensor.getPosicao().y-1));
		pontos.add(1, new Point(sensor.getPosicao().x,sensor.getPosicao().y+1));
		pontos.add(2, new Point(sensor.getPosicao().x+1,sensor.getPosicao().y));
		pontos.add(3, new Point(sensor.getPosicao().x-1,sensor.getPosicao().y));
		
		for (int i = 0; i < posicao.size() ; i++) {
		
			if ((sensor.getAmbiente()[Integer.parseInt(posicao.get(i)+"")] == Constantes.posicaoLivre) || (sensor.getAmbiente()[Integer.parseInt(posicao.get(i)+"")] == Constantes.objetivo) || (sensor.getAmbiente()[Integer.parseInt(posicao.get(i)+"")] == Constantes.numeroMoeda )){
				if ((calculaDistancia(((Point)pontos.get(i)).x , ((Point)pontos.get(i)).y,ObjX,ObjY)<=menorDistancia)) {
					menorDistancia = calculaDistancia(((Point)pontos.get(i)).x , ((Point)pontos.get(i)).y,ObjX,ObjY);
					indice = i;
				}
			}
		}
		 
		
		sorteio = indice+1;
		*/
/*		for (int i = 0; i < sensor.getAmbiente().length; i++){
			if (i == 4 || i == 9  || i == 13 || i == 18 || i == 23){
				System.out.println(sensor.getAmbiente()[i] + " - ");	
			}else if (i == 12){
						System.out.print("  - "+ sensor.getAmbiente()[i] + " - " );	
					}else{
						System.out.print(sensor.getAmbiente()[i] + " - ");
					}
		}	

		System.out.println("Energia : "+sensor.getNivelEnergia());
		System.out.println("Posicao : "+ sensor.getPosicao().x + "," + sensor.getPosicao().y  );
*/		
		
		/*
		 * 0 - Ficar Parado
		 * 1 - Mover Cima
		 * 2 - Mover Baixo
		 * 3 - Mover Direita
		 * 4 - Mover Esquerda
		 */
		//System.out.print(sorteio + ",");
		//sorteio = movimento[cont];

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
