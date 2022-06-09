package info3.game.cavegenerator;

import java.util.ArrayList;
import java.util.List;

public class SpawnGenerator {

	int width;
	int height;
	List<Vec2> listSpawnPlayer = new ArrayList<Vec2>();

	public SpawnGenerator(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int[][] classicGen() {
		SimplexNoise3D noise = new SimplexNoise3D();
		int[][] values = noise.generation();
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
		int[][] values = classicGen();
		int depart = 0;
		boolean stop = true;
		int zoneLen = this.width / nbPlayers;
		for (int i = 0; i < nbPlayers; i++) {
			stop = true;
			while (stop) {
				int x = (int) (Math.random() * zoneLen + depart);
				int y = (int) (Math.random() * this.height);
				if (x > 15 && y < this.height - 15 && y > 15 && x < this.width - 15 && values[x - 1][y] == 1) {
					stop = false;
					depart += zoneLen;
					values[x][y] = 2;
					listSpawnPlayer.add(new Vec2(x, y));
				}
			}
		}
		return values;
	}

	public int[][] spawnZonePlayer(List<Vec2> listSpawnPlayer) {

		return null;
	}
}
