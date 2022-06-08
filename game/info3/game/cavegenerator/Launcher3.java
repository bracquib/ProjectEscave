package info3.game.cavegenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Launcher3 {
	private static final int WIDTH = 512;
	private static final int HEIGHT = 512;
	private static final double FEATURE_SIZE = 24;

	public static void main(String[] args) throws IOException {

		OpenSimplexNoise noise = new OpenSimplexNoise();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < HEIGHT; y++) {
			for (int x = 0; x < WIDTH; x++) {
				double value = noise.eval(x / FEATURE_SIZE, y / FEATURE_SIZE);
				image.setRGB(x, y, ~((int) value * 0xFFFFFF));
			}
		}
		ImageIO.write(image, "png", new File("noisetest.png"));
	}
}