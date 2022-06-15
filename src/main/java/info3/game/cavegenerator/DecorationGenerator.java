package info3.game.cavegenerator;

public class DecorationGenerator {
	private static final int PATERNSIZE = 5;

	public DecorationGenerator() {
	}

	public int[][] decorateMap(int[][] map) {
		int[][] newmap = map.clone();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				int[][] paternToCompare = this.getPattern(i, j, PATERNSIZE);
				int id = DecorationPaterns.comparePatterns(paternToCompare);
				newmap[i][j] = id;
			}
		}
		return newmap;
	}

	private int[][] getPattern(int x, int y, int size) {
		int[][] test = { { 0, 1 }, { 1, 1 } };
		return test;

	}
}