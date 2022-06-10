package info3.game.cavegenerator;

import java.util.ArrayList;
import java.util.List;

/*Cette classe permet de générer entièrement la map
 * 
 * On va faire dans l'ordre:
 * 
 * - Génération Simplex Noise
 * - Spawn Player 
 * - Spawn Sortie
 * - Spawn statue
 * 
 * - Spawn salles
 * - Spawn mobs
 * 
 * - automate cellulaire
 * - Spawn murs
 * 
 */

public class SpawnGenerator {

	int width;
	int height;
	List<Vec2> listSpawnPlayer = new ArrayList<Vec2>();
	Vec2 exit = new Vec2(0);

	public int[][] classicGen(int nbPlayers) {
		long rand = (long) (Math.random() * System.currentTimeMillis());
		System.out.println(rand);
		SimplexNoise3D noise = new SimplexNoise3D(rand, nbPlayers);
		int[][] values = noise.generation(nbPlayers);
		this.width = values.length;
		this.height = values[0].length;
		return values;
	}

	/**
	 * Prend le nombre de joueurs dans la partie et modifie le tableau de 0 et de 1
	 * pour le mettre en 2, signifiant un spawn. Un spawn d'un joueur est dans une
	 * zone différente des autres, la map et divisé par le nombre de joueur
	 * 
	 * Le spawn respecte certaines conditions (le spawn doit être éloigné des bords)
	 * 
	 * @param nombre de joueur
	 * @return le tableau de valeurs modifié avec un 2 aux coordonnées de spawn
	 */
	public int[][] spawnPlayer(int nbPlayers) {
		int[][] values = classicGen(nbPlayers);
		int depart = 0;
		boolean stop = true;
		int zoneLen = this.width / nbPlayers;
		for (int i = 0; i < nbPlayers; i++) {
			stop = true;
			while (stop) {
				int x = (int) (Math.random() * zoneLen + depart);
				int y = (int) (Math.random() * this.height);

				if (x > 15 && y < this.height - 15 && y > 15 && x < this.width - 15 && values[x][y + 1] == 1
						&& values[x - 2][y + 1] == 1 && values[x - 1][y + 1] == 1 && values[x + 1][y + 1] == 1
						&& values[x + 2][y + 1] == 1 && values[x + 3][y + 1] == 1 && values[x - 3][y + 1] == 1) {
					stop = false;
					depart += zoneLen;
					values[x][y] = 2;
					listSpawnPlayer.add(new Vec2(x, y));
				}
			}
		}
		return values;
	}

	public int[][] zonePlayer(List<Vec2> listSpawnPlayer, int[][] values) {
		int len = listSpawnPlayer.size();
		for (int i = 0; i < len; i++) {
			Vec2 coords = listSpawnPlayer.get(i);
			values = zoneDestructionPlayer(values, coords);
		}
		return values;
	}

	public int[][] zoneDestructionPlayer(int[][] values, Vec2 coords) {
		int x = (int) coords.getX();
		int y = (int) coords.getY();
		// on creuse à droite et à gauche
		for (int i = -5; i < 6; i++) {
			values[x + i][y] = 4;
		}
		// ligne au dessus
		for (int i = -4; i < 6; i++) {
			values[x + i][y - 1] = 4;
		}
		// 2 lignes au dessus
		for (int i = -3; i < 5; i++) {
			values[x + i][y - 2] = 4;
		}
		// 3 lignes au dessus
		for (int i = -2; i < 4; i++) {
			values[x + i][y - 3] = 4;
		}
		// 4 lignes au dessus
		values[x][y - 4] = 4;
		values[x][y] = 3;
		return values;
	}

	public int[][] spawnPlayerTotal(int nbPlayers) {
		int[][] values = spawnPlayer(nbPlayers);
		values = zonePlayer(listSpawnPlayer, values);
		return values;
	}

	public int[][] spawnExit(int[][] values, int nbPlayers) {
		boolean stop = false;
		boolean find = true;
		int limit = 0;
		if (nbPlayers == 1 || nbPlayers == 2)
			limit = 50;
		if (nbPlayers == 3 || nbPlayers == 4)
			limit = 60;
		if (nbPlayers == 5 || nbPlayers == 6)
			limit = 50;
		if (nbPlayers == 7 || nbPlayers == 8)
			limit = 40;

		int cpt = 0;
		int len = listSpawnPlayer.size();
		while (!stop) {
			cpt++;
			stop = true;
			find = true;
			boolean full = true;
			int x = (int) (Math.random() * (this.width - 50) + 15);
			int y = (int) (Math.random() * (this.height - 50) + 15);
			for (int i = 0; i < len; i++) {
				Vec2 coords = listSpawnPlayer.get(i);
				System.out.println("Player " + i + ": " + coords.getX() + " " + coords.getY() + " distance: "
						+ Math.abs(coords.getX() - x) + " " + Math.abs(coords.getY() - y));

				for (int j = -nbPlayers - 2; j < nbPlayers + 2; j++) {
					if (values[x + j][y + 1] == 0)
						full = false;
				}
				if (full == false || Math.abs(coords.getX() - x) < limit || Math.abs(coords.getY() - y) < limit / 2
						|| x < 15 || y > this.height - 15 || y < 15 || x > this.width - 15 || values[x][y + 1] != 1)
					find = false;

			}
			if (find == false) {
				stop = false;
			} else {
				values[x][y] = 5;
				exit.setX(x);
				exit.setY(y);
				System.out.println(exit.getX());
				System.out.println(exit.getY());
			}
			System.out.println("cpt " + cpt);

		}
		return values;
	}

	public int[][] zoneExit(Vec2 exit, int[][] values, int nbPlayers) {
		values = zoneDestructionExit(values, exit, nbPlayers);
		return values;
	}

	public int[][] zoneDestructionExit(int[][] values, Vec2 coords, int nbPlayers) {
		int x = (int) coords.getX();
		int y = (int) coords.getY();
		if (nbPlayers == 1 || nbPlayers == 2)
			values = zoneDestructionExit12(values, x, y);
		if (nbPlayers == 3 || nbPlayers == 4)
			values = zoneDestructionExit34(values, x, y);
		if (nbPlayers == 5 || nbPlayers == 6)
			values = zoneDestructionExit56(values, x, y);
		if (nbPlayers == 7 || nbPlayers == 8)
			values = zoneDestructionExit78(values, x, y);
		return values;
	}

	public int[][] zoneDestructionExit12(int[][] values, int x, int y) {
		for (int i = -7; i < 9; i++) {
			values[x + i][y] = 6;
		}
		for (int i = -7; i < 9; i++) {
			values[x + i][y - 1] = 6;
		}
		for (int i = -6; i < 7; i++) {
			values[x + i][y - 2] = 6;
		}
		for (int i = -4; i < 5; i++) {
			values[x + i][y - 3] = 6;
		}
		for (int i = -1; i < 3; i++) {
			values[x + i][y - 4] = 6;
		}
		values[x][y] = 5;
		return values;
	}

	public int[][] zoneDestructionExit34(int[][] values, int x, int y) {
		for (int i = -6; i < 8; i++) {
			values[x + i][y] = 6;
		}
		for (int i = -6; i < 7; i++) {
			values[x + i][y - 1] = 6;
		}
		for (int i = -5; i < 7; i++) {
			values[x + i][y - 2] = 6;
		}
		for (int i = -3; i < 4; i++) {
			values[x + i][y - 3] = 6;
		}
		for (int i = -3; i < 4; i++) {
			values[x + i][y - 4] = 6;
		}
		for (int i = 0; i < 3; i++) {
			values[x + i][y - 5] = 6;
		}
		values[x][y] = 5;
		return values;
	}

	public int[][] zoneDestructionExit56(int[][] values, int x, int y) {
		for (int i = -8; i < 9; i++) {
			values[x + i][y] = 6;
		}
		for (int i = -8; i < 9; i++) {
			values[x + i][y - 1] = 6;
		}
		for (int i = -7; i < 6; i++) {
			values[x + i][y - 2] = 6;
		}
		for (int i = -6; i < 6; i++) {
			values[x + i][y - 3] = 6;
		}
		for (int i = -5; i < 6; i++) {
			values[x + i][y - 4] = 6;
		}
		for (int i = -3; i < 4; i++) {
			values[x + i][y - 5] = 6;
		}
		for (int i = -2; i < 1; i++) {
			values[x + i][y - 6] = 6;
		}
		values[x][y] = 5;
		return values;
	}

	public int[][] zoneDestructionExit78(int[][] values, int x, int y) {
		for (int i = -10; i < 11; i++) {
			values[x + i][y] = 6;
		}
		for (int i = -10; i < 11; i++) {
			values[x + i][y - 1] = 6;
		}
		for (int i = -9; i < 10; i++) {
			values[x + i][y - 2] = 6;
		}
		for (int i = -9; i < 10; i++) {
			values[x + i][y - 3] = 6;
		}
		for (int i = -7; i < 8; i++) {
			values[x + i][y - 4] = 6;
		}
		for (int i = -4; i < 4; i++) {
			values[x + i][y - 5] = 6;
		}
		for (int i = -2; i < 2; i++) {
			values[x + i][y - 6] = 6;
		}
		values[x][y] = 5;
		return values;
	}

	public int[][] spawnExitTotal(int nbPlayers) {
		int[][] values = spawnPlayerTotal(nbPlayers);
		values = spawnExit(values, nbPlayers);
		values = zoneExit(this.exit, values, nbPlayers);
		return values;
	}
}
