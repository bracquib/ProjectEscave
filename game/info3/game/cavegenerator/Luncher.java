package info3.game.cavegenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The main class for launching the application.
 */
public final class Luncher {

	/**
	 * Hidden constructor.
	 */
	private Luncher() {
	}

	/**
	 * The main method.
	 * 
	 * @param args command line arguments
	 */
	public static void main(final String[] args) {
		/* Create an image. */
		int size = 200;
		float[][] pn = PerlinNoise2.genPerlinNoiseGrid(size, size, 0.1f);

		var im = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				im.setRGB(i, j, (int) (pn[i][j]));
			}
		}

		/* Save the image. */
		try {
			ImageIO.write(im, "PNG", new File("out3.png"));
		} catch (IOException e) {
			System.out.println("Failed to write image.");
			System.exit(-1);
		}
	}
}