package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Legenda extends JPanel {
	private JPanel panelEsq = new JPanel(new GridLayout(0, 1, 0, 0));

	private JPanel panelDir = new JPanel(new GridLayout(0, 1, 0, 0));

	public Legenda(String nomeLegenda) {
		this.setBorder(new TitledBorder(nomeLegenda));
		this.setLayout(new FlowLayout());

		this.add(panelEsq);
		this.add(panelDir);
	}

	public void addLinha(Color colorEsq, int height, int width, String descricao) {
		panelEsq.setLayout(new GridLayout(0, 1, 0, 7));
		panelDir.setLayout(new GridLayout(0, 1, 0, 0));

		JPanel linhaEsq = new JPanel();
		JLabel linhaDireita = new JLabel(descricao);
		JPanel linha = new JPanel();
		linhaEsq.setBackground(colorEsq);
		linhaEsq.setPreferredSize(new Dimension(width, height));
		linhaEsq.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		linha.add(linhaEsq);
		panelEsq.add(linha);
		linha = new JPanel();
		linha.add(linhaDireita);
		panelDir.add(linha);
	}

	public void addLinha(ImageIcon imageIcon, double sx, double sy,
			String descricao) {
		panelEsq.setLayout(new GridLayout(0, 1, 0, 0));
		panelDir.setLayout(new GridLayout(0, 1, 0, 5));

		BufferedImage bufferedImage = toBufferedImage(imageIcon.getImage());
		AffineTransform tx = new AffineTransform();
		tx.scale(sx, sy);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		bufferedImage = op.filter(bufferedImage, null);

		JLabel linhaEsq = new JLabel(new ImageIcon(bufferedImage));
		JLabel linhaDireita = new JLabel(descricao);
		JPanel linha = new JPanel();

		linhaEsq.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		linha.add(linhaEsq);
		panelEsq.add(linha);
		linha = new JPanel();
		linha.add(linhaDireita);
		panelDir.add(linha);
	}

	// This method returns a buffered image with the contents of an image
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent
		// Pixels
		boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the
		// screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			if (hasAlpha) {
				transparency = Transparency.BITMASK;
			}

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image
					.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			if (hasAlpha) {
				type = BufferedImage.TYPE_INT_ARGB;
			}
			bimage = new BufferedImage(image.getWidth(null), image
					.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	// This method returns true if the specified image has transparent pixels
	public static boolean hasAlpha(Image image) {
		// If buffered image, the color model is readily available
		if (image instanceof BufferedImage) {
			BufferedImage bimage = (BufferedImage) image;
			return bimage.getColorModel().hasAlpha();
		}

		// Use a pixel grabber to retrieve the image's color model;
		// grabbing a single pixel is usually sufficient
		PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
		try {
			pg.grabPixels();
		} catch (InterruptedException e) {
		}

		// Get the image's color model
		ColorModel cm = pg.getColorModel();
		return cm.hasAlpha();
	}
}
