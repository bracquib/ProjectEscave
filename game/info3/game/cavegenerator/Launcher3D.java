package info3.game.cavegenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Launcher3D {
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 256;

	public static void main(String[] args) throws IOException {

		SimplexNoise3D noise = new SimplexNoise3D();
		int[][] values = noise.generation();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {

				image.setRGB(x, y, ~(values[x][y] * 0xFFFFFF));
			}
		}
		ImageIO.write(image, "png", new File("NOICCEE.png"));
	}
}