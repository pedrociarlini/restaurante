package controle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class InterpretadorArquivo {

	public int[][] leArquivo(String caminhoArquivo) {

		int[][] matrizSimulacao = null;

		// Vector de Arrays (que é uma linha da matriz)
		Vector<String[]> vectorLinhaMatriz = new Vector<String[]>();

		try {
			// BufferedReader
			BufferedReader br = new BufferedReader(new FileReader(
					caminhoArquivo));

			String line = br.readLine();

			while (line != null) {
				String[] linhaMatriz = line.split(" ");

				vectorLinhaMatriz.add(linhaMatriz);

				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Monta a matriz da simulação;
/*		int totalLinhas = vectorLinhaMatriz.get(0).length;
		int totalColunas = vectorLinhaMatriz.size();
		matrizSimulacao = new int[totalLinhas][totalColunas];

		for (int i = 0; i < totalLinhas; i++) {
			for (int j = 0; j < totalColunas; j++) {
				matrizSimulacao[i][j] = Integer.parseInt(vectorLinhaMatriz
						.get(j)[i]);
			}
		}		
*/		
		// Monta a matriz da simulação;
		int totalLinhas = vectorLinhaMatriz.get(0).length;
		int totalColunas = vectorLinhaMatriz.size();
		matrizSimulacao = new int[totalLinhas][totalColunas];

		for (int i = 0; i < totalLinhas; i++) {
			for (int j = 0; j < totalColunas; j++) {
				matrizSimulacao[i][j] = Integer.parseInt(vectorLinhaMatriz
						.get(i)[j]);
			}
		}

		// Mostra a matriz formada;
		/*
		 * for (int i = 0 ; i < totalLinhas ; i++){ System.out.println();
		 * for(int j = 0 ; j < totalColunas; j++){
		 * System.out.print(matrizSimulacao[i][j] + " "); } }
		 */

		return matrizSimulacao;
	}
}
