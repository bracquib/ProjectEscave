package info3.game.cavegenerator;

import java.util.HashMap;

public class DecorationGenerator {
	static int[] mineraux = { 100, 101, 102, 103, 104, 105 };
	static int[] champignons = { 250, 251, 252, 253, 254, 255, 256, 257 };
	static int[] cristaux_droit = { 500, 501, 502, 503, 504, 505 };
	static int[] cristaux_gauche = { 400, 401, 402, 403, 404, 405 };
	static int[] cristaux_sol = { 200, 201, 202, 203, 204, 205 };
	static int[] cristaux_plafond = { 300, 301, 302, 303, 304, 305 };
	static int[] arbres_droit = { 550, 551 };
	static int[] arbres_gauche = { 450, 451 };
	static int[] lanterne_courte = { 351 };

	public static Torus decorate(int[][] map) {
		Torus step1 = decorateMap(map, BlockIDs.PatternCouche1ToIDs);
		Torus step2 = decorateMap(step1.toArray(), BlockIDs.PatternCouche2ToIDs);
		Torus step3 = decorateMap(step2.toArray(), BlockIDs.PatternCouche3ToIDs);

		Torus extendDeco = extensionDecoration(step3.toArray(), 2, champignons, 40);
		extendDeco = extensionDecoration(extendDeco.toArray(), 1, mineraux, 0.5);
		Fillon.generateFilons(extendDeco, 1, mineraux);
		extendDeco = extensionDecoration(extendDeco.toArray(), 4, cristaux_droit, 15);
		extendDeco = extensionDecoration(extendDeco.toArray(), 8, cristaux_gauche, 15);
		extendDeco = extensionDecoration(extendDeco.toArray(), 2, cristaux_sol, 20);
		extendDeco = extensionDecoration(extendDeco.toArray(), 22, cristaux_sol, 20);
		extendDeco = extensionDecoration(extendDeco.toArray(), 3, cristaux_sol, 20);
		extendDeco = extensionDecoration(extendDeco.toArray(), 6, cristaux_plafond, 20);
		extendDeco = extensionDecoration(extendDeco.toArray(), 7, cristaux_plafond, 20);
		extendDeco = extensionDecoration(extendDeco.toArray(), 5, cristaux_plafond, 20);
		extendDeco = extensionDecoration(extendDeco.toArray(), 4, arbres_droit, 5);
		extendDeco = extensionDecoration(extendDeco.toArray(), 8, arbres_gauche, 5);
		extendDeco = extensionDecoration(extendDeco.toArray(), 6, lanterne_courte, 1);
		extendDeco = extensionStegDeco(extendDeco.toArray(), 0.01);
		extendDeco = extensionTRexDeco(extendDeco.toArray(), 0.01);
		extendDeco = extensionTricDeco(extendDeco.toArray(), 0.01);
		extendDeco = extensionLanterneLongueDeco(extendDeco.toArray(), 1);
		extendDeco = extensionLianesDeco(extendDeco.toArray(), 10);

		Fillon.generateFilons(extendDeco, 1, mineraux);

		return extendDeco;
	}

	public static Torus decorateMap(int[][] map, HashMap<Integer[][], Integer> hm) {
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

	private static Torus extensionDecoration(int[][] map, int idtoChange, int[] id, double percent) {
		Torus newmap = new Torus(map);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == idtoChange) {
					int rand = (int) Math.floor(Math.random() * 100);
					if (rand <= percent) {
						int randID = (int) Math.floor(Math.random() * id.length);
						newmap.set(i, j, id[randID]);
					}
				}
			}
		}
		return newmap;
	}

	private static Torus extensionStegDeco(int[][] map, double percent) {
		Torus newmap = new Torus(map);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 1) {
					int rand = (int) Math.floor(Math.random() * 100);
					if (rand <= percent) {
						if (checkZoneDino(newmap, i, j)) {
							placeSteg(newmap, i, j);
						}
					}
				}
			}
		}
		return newmap;
	}

	private static Torus extensionTricDeco(int[][] map, double percent) {
		Torus newmap = new Torus(map);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 1) {
					int rand = (int) Math.floor(Math.random() * 100);
					if (rand <= percent) {
						if (checkZoneDino(newmap, i, j)) {
							placeTric(newmap, i, j);
						}
					}
				}
			}
		}
		return newmap;
	}

	private static Torus extensionTRexDeco(int[][] map, double percent) {
		Torus newmap = new Torus(map);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 1) {
					int rand = (int) Math.floor(Math.random() * 100);
					if (rand <= percent) {
						if (checkZoneDino(newmap, i, j)) {
							placeTRex(newmap, i, j);
						}
					}
				}
			}
		}
		return newmap;
	}

	private static void placeTric(Torus tore, int x, int y) {
		tore.set(x - 2, y - 1, 170);
		tore.set(x - 1, y - 1, 171);
		tore.set(x, y - 1, 172);
		tore.set(x - 2, y, 173);
		tore.set(x - 1, y, 174);
		tore.set(x, y, 175);
	}

	private static void placeTRex(Torus tore, int x, int y) {
		tore.set(x - 1, y - 1, 150);
		tore.set(x, y - 1, 151);
		tore.set(x - 1, y, 152);
		tore.set(x, y, 153);
		tore.set(x + 1, y, 154);
		tore.set(x, y + 1, 155);
		tore.set(x + 1, y + 1, 156);

	}

	private static void placeSteg(Torus tore, int x, int y) {
		tore.set(x - 2, y - 1, 160);
		tore.set(x - 1, y - 1, 161);
		tore.set(x, y - 1, 162);
		tore.set(x + 1, y - 1, 163);
		tore.set(x - 2, y, 164);
		tore.set(x - 1, y, 165);
		tore.set(x, y, 166);
		tore.set(x + 1, y, 167);
	}

	private static boolean checkZoneDino(Torus tore, int x, int y) {
		int[] ligne1 = { tore.get(x - 2, y - 1), tore.get(x - 1, y - 1), tore.get(x, y - 1), tore.get(x + 1, y - 1) };
		int[] ligne2 = { tore.get(x - 2, y), tore.get(x - 1, y), tore.get(x, y), tore.get(x + 1, y) };
		int[][] res = { ligne1, ligne2 };
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 4; j++) {
				if (res[i][j] != 1)
					return false;
			}
		}
		return true;
	}

	private static boolean checkZoneLanterneLongue(Torus tore, int x, int y) {
		for (int i = 1; i < 3; i++) {
			if (tore.get(x, y + i) != 0)
				return false;
		}
		return true;
	}

	private static boolean checkZoneLianes(Torus tore, int x, int y) {
		for (int i = 1; i < 4; i++) {
			if (tore.get(x, y + i) != 0)
				return false;
		}
		return true;
	}

	private static Torus extensionLanterneLongueDeco(int[][] map, double percent) {
		Torus newmap = new Torus(map);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 5 || map[i][j] == 6 || map[i][j] == 7) {
					int rand = (int) Math.floor(Math.random() * 100);
					if (rand <= percent) {
						if (checkZoneLanterneLongue(newmap, i, j)) {
							newmap.set(i, j, 352);
						}
					}
				}
			}
		}
		return newmap;
	}

	private static Torus extensionLianesDeco(int[][] map, double percent) {
		Torus newmap = new Torus(map);
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				if (map[i][j] == 5 || map[i][j] == 6 || map[i][j] == 7) {
					int rand = (int) Math.floor(Math.random() * 100);
					if (rand <= percent) {
						if (checkZoneLianes(newmap, i, j)) {
							newmap.set(i, j, 350);
						}
					}
				}
			}
		}
		return newmap;
	}

}