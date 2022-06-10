package info3.game.cavegenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Launcher3D {
	private static final int nbPlayers = 6;
	static int width;
	static int height;

	public static void coloration(int[][] values, int a, int color, BufferedImage image) {

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (values[x][y] == a) {
					image.setRGB(x, y, color);
				}
			}
		}
	}

	public static void colorationBlocs(List<Vec2> coords, BufferedImage image) {
		for (Vec2 coordsBloc : coords) {
			int x = (int) coordsBloc.getX();
			int y = (int) coordsBloc.getY();
			image.setRGB(x, y, 0xFFA500);
		}
	}

	public static void colorationStatue(List<Vec2> coords, BufferedImage image) {
		for (Vec2 coordsBloc : coords) {
			int x = (int) coordsBloc.getX();
			int y = (int) coordsBloc.getY();
			image.setRGB(x, y, 0xFFFF00);
		}
	}

	public static void main(String[] args) throws IOException {
		SpawnGenerator generationMap = new SpawnGenerator();
		int[][] values = generationMap.spawnStatueTotal(nbPlayers);
		List<Vec2> blocs = generationMap.listSpawnBlocsStatues;
		List<Vec2> statues = generationMap.listSpawnStatues;

		width = values.length;
		height = values[0].length;

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, ~(values[x][y] * 0xFFFFFF));
			}
		}
		coloration(values, 3, 0xFF0000, image);
		coloration(values, 4, 0x00FF00, image);
		coloration(values, 5, 0x0000FF, image);
		coloration(values, 6, 0x00FFFF, image);
		colorationBlocs(blocs, image);
		coloration(values, 7, 0xE389B9, image);
		colorationStatue(statues, image);

		ImageIO.write(image, "png", new File("Amaury.png"));
	}
}