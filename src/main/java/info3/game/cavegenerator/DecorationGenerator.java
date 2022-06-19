package info3.game.cavegenerator;

import java.util.HashMap;

public class DecorationGenerator {
	static int[] mineraux = { 100, 101, 102, 103, 104, 105 };

	public static Torus decorate(int[][] map) {
		Torus step1 = decorateMap(map, BlockIDs.PatternCouche1ToIDs);
		Torus step2 = decorateMap(step1.toArray(), BlockIDs.PatternCouche2ToIDs);
		Torus step3 = decorateMap(step2.toArray(), BlockIDs.PatternCouche3ToIDs);
		Torus minerauxDeco = extensionDecoration(step3.toArray(), 1, mineraux, 2);
		return minerauxDeco;

	}

	private static Torus decorateMap(int[][] map, HashMap<Integer[][], Integer> hm) {
		Torus newmap = new Torus(map);
		Torus map2 = new Torus(map);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				int[][] paternToCompare = DecorationGenerator.getPattern(i, j, map2);
				int id = DecorationPaterns.comparePatterns(paternToCompare, hm);
				newmap.set(i, j, id);
			}
		}
		return newmap;
	}

	private static int[][] getPattern(int x, int y, Torus tore) {
		int[] ligne1 = { tore.get(x - 1, y - 1), tore.get(x, y - 1), tore.get(x + 1, y - 1) };
		int[] ligne2 = { tore.get(x - 1, y), tore.get(x, y), tore.get(x + 1, y) };
		int[] ligne3 = { tore.get(x - 1, y + 1), tore.get(x, y + 1), tore.get(x + 1, y + 1) };
		int[][] res = { ligne1, ligne2, ligne3 };
		return res;
	}

	private static Torus extensionDecoration(int[][] map, int idtoChange, int[] id, int purcent) {
		Torus newmap = new Torus(map);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == idtoChange) {
					int rand = (int) Math.floor(Math.random() * 100);
					if (rand <= purcent) {
						int randID = (int) Math.floor(Math.random() * id.length);
						newmap.set(i, j, id[randID]);
					}
				}
			}
		}
		return newmap;
	}
}