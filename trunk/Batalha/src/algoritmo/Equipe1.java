package algoritmo;

public class Equipe1 extends ProgramaEquipe1 {
	
	public int acao() {
		
		//visualiza energia
/*		for (int i = 0; i < sensor.getEnergiaOponente().length; i++){
			if (i == 2 || i == 7){
				System.out.println(sensor.getEnergiaOponente()[i] );	
			}else if (i == 4){
					System.out.println("  - "+ sensor.getEnergiaOponente()[i] );	
					}
					else{
						System.out.print(sensor.getEnergiaOponente()[i] + " - ");
						}	
		}
*/		

		//visualiza direcao
/*		for (int i = 0; i < sensor.getDirecaoOponente().length; i++){
			if (i == 2 || i == 7){
				System.out.println(sensor.getDirecaoOponente()[i] );	
			}else if (i == 4){
					System.out.println("  - "+ sensor.getDirecaoOponente()[i] );	
					}
					else{
						System.out.print(sensor.getDirecaoOponente()[i] + " - ");
						}	
		}*/
				
		
		//visualiza ambiente olfato
/*		for (int i = 0; i < sensor.getAmbienteOlfato().length; i++){
			if (i == 2 || i == 7){
				System.out.println(sensor.getAmbienteOlfato()[i] );	
			}else if (i == 4){
					System.out.println("  - "+ sensor.getAmbienteOlfato()[i] );	
					}
					else{
						System.out.print(sensor.getAmbienteOlfato()[i] + " - ");
						}	
		}
*/		
		return this.selecionaIntervalo(0,4);
	}

	public int selecionaIntervalo(int intervalo1, int intervalo2) {
		return (int) (intervalo1+Math.random() * (intervalo2-intervalo1+1));
	}

}
