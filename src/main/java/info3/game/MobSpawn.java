package info3.game;

import java.util.ArrayList;

import info3.game.entities.Block;
import info3.game.entities.Player;

public class MobSpawn {
	private final static int MAXENTITY = 30;

	private final static int MIN_SPAWN_DISTANCE = 40;
	private final static int MAX_SPAWN_DISTANCE = 70;

	public static void tick() {
		ArrayList<Player> players = Model.getPlayers();

		if (Model.allEntities().size() >= MAXENTITY)
			return;

		// Pour chaque joueur, essaye de spawn un mob autour
		for (Player player : players) {

		}

		// Nombre random pour décider de si ca spawn ou non
		// Trouve un bloc de sol dans la range
		// Check si ce bloc est à une distance
		// supérieur à MIN_SPAWN_DISTANCE de tous les autres joueurs
		// Spawn du mob
	}

	private static void spawn() {
		return;
	}

	private static ArrayList<Block> getInRangeBlocks(Vec2 center, float rangeMin, float rangeMax) {
		ArrayList<Block> blocks = new ArrayList<Block>();
		Block[][] mapZone = Model.getMapZone((int) center.getX() - MAX_SPAWN_DISTANCE,
				(int) center.getY() - MAX_SPAWN_DISTANCE, MAX_SPAWN_DISTANCE, MAX_SPAWN_DISTANCE);

	}
}
