package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class PainelLabirinto extends JPanel {

	int[][] grid;

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

	public void desenhaGrade(Graphics g) {
		g.setColor(Color.BLACK);
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
						g.setColor(getCor(2));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
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
						g.setColor(getCor(5));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;

					default:
						g.setColor(getCor(10000));
						g.fillRect(i * pixel, j * pixel, pixel, pixel);
						break;
					}
				}

	}

	public Color getCor(int numGrid) {
		switch (numGrid) {
		case -1:
			return Color.WHITE;
		case 0:// livre
			return Color.BLACK;
		case 1:// parede
			return Color.BLUE;
		case 2:// pacman
			return Color.RED;
		case 3:// Saida
			return Color.GREEN;
		case 4:// moeda
			return Color.YELLOW;
		case 5:// Fantasma 1
			return Color.WHITE;//Color.MAGENTA;
		case 6:// Fantasma 2
			return Color.WHITE;//Color.DARK_GRAY;
		case 7:// Fantasma 3
			return Color.WHITE;//Color.CYAN;
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
