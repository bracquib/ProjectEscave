package info3.game.cavegenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Launcher3D {
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 256;
	private static final double FEATURE_SIZE = 22;

	public static void main(String[] args) throws IOException {

		SimplexNoise3D noise = new SimplexNoise3D();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				double angle = (2 * Math.PI * x) / WIDTH;
				double value = noise.eval(256 * Math.cos(angle) / FEATURE_SIZE, y / FEATURE_SIZE,
						256 * Math.sin(angle) / FEATURE_SIZE);
				image.setRGB(x, y, ~((int) value * 0xFFFFFF));
			}
		}
		ImageIO.write(image, "png", new File("noise3D1.png"));
	}
}