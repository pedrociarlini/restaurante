package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import algoritmo.ProgramaAbstract;

@SuppressWarnings("serial")
public class PainelLabirinto extends JPanel {

	private int[][] grid;

	/**
	 * Tamanho da �rea que valer� por um pixel.
	 */
	int pixel = 10;

	public PainelLabirinto() {
		this.setBackground(Color.WHITE);
	}

	public void paint(Graphics g) {
		// this.setSize(240,240);
		super.paint(g);
		this.desenhaGrid(g);
		this.desenhaGrade(g);
	}

	/**
	 * Desenha uma grade que separa os elementos por uma linha.
	 * 
	 * @param g
	 *            <code>Graphis</code> que ser� desehado.
	 */
	public void desenhaGrade(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		if (grid != null) {
			for (int i = 0; i < this.grid[0].length; i++) {
				g
						.drawLine(i * pixel, 0, i * pixel, this.grid[0].length
								* pixel);
			}

			for (int i = 0; i < this.grid.length; i++) {
				g.drawLine(0, i * pixel, this.grid.length * pixel, i * pixel);
			}
		}
	}

	/**
	 * Desenha todos os elmentos, baseado na matriz da simula��o, representada
	 * nessa classe pela propriedade <code>grid</code>.
	 * 
	 * @param g
	 *            <code>Graphis</code> que ser� desehado.
	 */
	public void desenhaGrid(Graphics g) {
		if (grid != null)
			for (int i = 0; i < this.grid.length; i++)
				for (int j = 0; j < grid[0].length; j++) {
					switch (grid[i][j]) {
					case -1:
						g.setColor(getCor(-1));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 0:
						g.setColor(getCor(0));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 1:
						g.setColor(getCor(1));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 2:
						g.setColor(getCor(2));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 3:
						g.setColor(getCor(3));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 4:
						g.setColor(getCor(4));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 5:
						g.setColor(getCor(5));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 6:
						g.setColor(getCor(6));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 7:
						g.setColor(getCor(7));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 8:
						g.setColor(getCor(8));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 9:
						g.setColor(getCor(9));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 10:
						g.setColor(getCor(10));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					case 100:
					case 110:
					case 120:
					case 130:
					case 140:
					case 150:
					case 160:
					case 170:
					case 180:
					case 190:
						desenhaSoldadoEquipe1(g, grid[i][j], i, j);
						break;
					case 200:
					case 210:
					case 220:
					case 230:
					case 240:
					case 250:
					case 260:
					case 270:
					case 280:
					case 290:
						desenhaSoldadoEquipe2(g, grid[i][j], i, j);
						break;
					default:
						g.setColor(getCor(10000));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					}
				}

	}

	/**
	 * 
	 * @param g
	 * @param nome
	 */
	private void desenhaSoldadoEquipe1(Graphics g, int nome, int i, int j) {
		g.setColor(getCor(2));
		g.fillRect(i * pixel, j * pixel, pixel, pixel);
		int direcao = ProgramaAbstract.getByNome(nome).getUltimaAcao();
		desenharFrente(g, i, j, direcao);
	}

	/**
	 * 
	 * @param g
	 * @param nome
	 */
	private void desenhaSoldadoEquipe2(Graphics g, int nome, int i, int j) {
		g.setColor(getCor(5));
		g.fillRect(i * pixel, j * pixel, pixel, pixel);
		int direcao = ProgramaAbstract.getByNome(nome).getUltimaAcao();
		desenharFrente(g, i, j, direcao);
	}

	/**
	 * Os �ndices do array seguem a seguinte ordem: <br />
	 * DIRECAO_NORTE = 1; DIRECAO_SUL = 2; DIRECAO_LESTE = 3; DIRECAO_OESTE = 4;
	 * 
	 * @param g
	 * @param i
	 *            Linha
	 * @param j
	 *            Coluna
	 * @param k
	 *            Dire��o.
	 */
	private void desenharFrente(Graphics g, int i, int j, int direcao) {
		Color temp = g.getColor();
		g.setColor(Color.BLACK);
		if(direcao == 1) {
			g.drawRect(i * pixel + (pixel/2), j * pixel, 2, pixel/2);			
		}
		else if (direcao == 2) {
			g.drawRect(i * pixel + (pixel/2), j * pixel + (pixel/2), 2, pixel/2);			
		}
		else if (direcao == 3) {
			g.drawRect(i * pixel + (pixel/2), j * pixel + (pixel/2), pixel/2, 2);			
		}
		else if (direcao == 4) {
			g.drawRect(i * pixel, j * pixel + (pixel/2), pixel/2, 2);			
		}
		g.setColor(temp);
	}

	/**
	 * Retorna a cor relativa ao objeto que ser� desenhado na tela.
	 * 
	 * @param numGrid
	 * @return
	 */
	public static Color getCor(int numGrid) {
		switch (numGrid) {
		case -1:
			return Color.WHITE;
		case 0:// livre
			return Color.BLACK;
		case 1:// parede
			return Color.BLUE;
		case 2:// Equipe1
			return Color.RED;
		case 3:// Saida
			return Color.GREEN;
		case 4:// moeda
			return Color.YELLOW;
		case 5:// Equipe2
			return Color.WHITE;// Color.MAGENTA;
		case 6:// Fantasma 2
			return Color.WHITE;// Color.DARK_GRAY;
		case 7:// Fantasma 3
			return Color.WHITE;// Color.CYAN;
		case 8:
			return Color.RED;
		case 9:
			return Color.RED;
		default:
			return Color.CYAN;
		}
	}

	public int[][] getGrid() {
		return this.grid;
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
		this.setPreferredSize(new Dimension(grid.length * pixel, grid[0].length
				* pixel));
	}

	public int getPixel() {
		return pixel;
	}

	public void setPixel(int pixel) {
		this.pixel = pixel;
	}

	public JPanel returnPanel() {
		return this;
	}

}
