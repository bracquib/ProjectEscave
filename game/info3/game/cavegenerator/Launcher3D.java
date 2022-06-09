package info3.game.cavegenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Launcher3D {
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 256;

	public static void main(String[] args) throws IOException {

		SpawnGenerator spawnPlayer = new SpawnGenerator(WIDTH, HEIGHT);
		int[][] values = spawnPlayer.spawnPlayer(3);
		// SimplexNoise3D noise = new SimplexNoise3D();
		// int[][] values = noise.generation();
		BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (values[x][y] == 2) {
					image.setRGB(x, y, 0xFF0000);
					if (x - 1 < WIDTH && x - 1 > 0 && y - 1 > 0 && y - 1 < HEIGHT) {
						values[x - 1][y] = 3;
						values[x - 1][y - 1] = 3;
						values[x - 1][y + 1] = 3;
						values[x][y - 1] = 3;
						values[x + 1][y - 1] = 3;
						values[x][y + 1] = 3;
						values[x + 1][y + 1] = 3;
						values[x + 1][y] = 3;
					}

				} else if (values[x][y] == 3) {
					image.setRGB(x, y, 0xFF0000);
				} else {
					image.setRGB(x, y, ~(values[x][y] * 0xFFFFFF));
				}
			}
		}
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {
				if (values[x][y] == 3) {
					image.setRGB(x, y, 0xFF0000);
				}
			}
		}
		ImageIO.write(image, "png", new File("playertest1.png"));
	}
}