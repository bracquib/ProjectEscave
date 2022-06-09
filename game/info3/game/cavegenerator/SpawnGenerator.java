package info3.game.cavegenerator;

import java.util.ArrayList;
import java.util.List;

/*Cette classe permet de générer entièrement la map
 * 
 * On va faire dans l'ordre:
 * - Génération Simplex Noise
 * - Spawn Player 
 * - Spawn Sortie
 * - Spawn statue
 * - Spawn murs
 * - Spawn salles
 * - Spawn mobs
 * 
 * 
 */

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

	public int[][] spawnZonePlayer(List<Vec2> listSpawnPlayer, int[][] values) {
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
		values = spawnZonePlayer(listSpawnPlayer, values);
		return values;
	}

}
