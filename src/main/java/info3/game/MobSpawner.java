package info3.game;

import java.util.ArrayList;

import info3.game.entities.Block;
import info3.game.entities.Mushroom;
import info3.game.entities.Player;

public class MobSpawner {
	private LocalController c;

	private final static int MAXENTITY = 30;
	private final static int BLOC_SIZE = 32;

	public final static int MIN_SPAWN_DISTANCE = 40;
	public final static int MAX_SPAWN_DISTANCE = 70;
	public final static float SPAWN_PROBA = 0.001f;

	MobSpawner(LocalController c) {
		this.c = c;
	}

	public void tick() {
		ArrayList<Player> players = Model.getPlayers();

		if (Model.allEntities().size() >= MAXENTITY)
			return;

		// Pour chaque joueur, essaye de spawn un mob autour
		for (Player player : players) {
			Block[][] inRangeBlocks = getInRangeBlocks(player.getPosition(), MIN_SPAWN_DISTANCE, MAX_SPAWN_DISTANCE);
			randomSpawn(inRangeBlocks);

		}

		// Nombre random pour décider de si ca spawn ou non
		// Trouve un bloc de sol dans la range
		// Check si ce bloc est à une distance
		// supérieur Ã  MIN_SPAWN_DISTANCE de tous les autres joueurs
		// Spawn du mob
	}

	public void randomSpawn(Block[][] blocks) {
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {
				Block bl = blocks[i][j];
				if (!isInPlayerRange(bl))
					blocks[i][j] = null;
				else {
					int rand = (int) Math.floor((float) Math.random() + SPAWN_PROBA);
					if (rand != 0) {
						spawn(blocks[i][j]);
						return;
					}
				}
			}
		}

	}

	private void spawn(Block block) {
		Mushroom mob = Mushroom.createMushroom(this.c, block.getPosition());
		Model.spawn(mob);
		return;
	}

	/*
	 * 
	 * 
	 * 
	 * 
	 */
	private Block[][] getInRangeBlocks(Vec2 center, int rangeMin, int rangeMax) {
		Vec2 coords = new Vec2(center.getX() / BLOC_SIZE, center.getY() / BLOC_SIZE).round();
		Block[][] mapZone = Model.getMapZone((int) coords.getX() - rangeMax, (int) coords.getY() - rangeMax, rangeMax,
				rangeMax);
		Block[][] blocks = mapZone.clone();

		for (int i = rangeMin; i < mapZone.length - rangeMin; i++) {
			for (int j = rangeMin; j < mapZone[0].length - rangeMin; j++) {
				blocks[i][j] = null;
			}
		}

		for (int i = 0; i < mapZone.length; i++) {
			for (int j = 0; j < mapZone[0].length; j++) {
				if (j < 2) {
					blocks[i][j] = null;
					continue;
				}
				if (j >= mapZone.length - 2) {
					blocks[i][j] = null;
					continue;
				}
				if (mapZone[i][j + 1] != null && mapZone[i][j + 2] != null) {
					blocks[i][j] = null;
				}
			}
		}
		return blocks;
	}

	private boolean isInPlayerRange(Block block) {
		for (Player player : Model.getPlayers()) {
			if (!(block.getPosition().sub(player.getPosition()).length() > MIN_SPAWN_DISTANCE * BLOC_SIZE)) {
				return false;
			}
		}
		return false;
	}

}
