package info3.game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import info3.game.entities.Block;
import info3.game.entities.Mushroom;
import info3.game.entities.Player;
import info3.game.physics.RigidBody;

/*
 * 2 3 22 
 */

public class MobSpawner {
	private static int maxEntity;
	public static float spawnProba;
	private static ArrayList<Player> players;
	private static boolean initialized = false;
	public static ArrayList<Block> spawnable;

	public final static int MIN_SPAWN_DISTANCE = 5;
	public final static int MAX_SPAWN_DISTANCE = 10;

	public static void init(int maxEntityPerPlayer, float probaSpawnPerPlayer) {
		maxEntity = maxEntityPerPlayer * Model.getPlayers().size();
		spawnProba = probaSpawnPerPlayer * Model.getPlayers().size();
		initialized = true;
	}

	public static void tick() throws Exception {
		if (!isInitialized()) {
			throw new Exception("Class not initialized");
		}

		players = new ArrayList<>(Model.getPlayers());
		int minD = MIN_SPAWN_DISTANCE;
		int maxD = MAX_SPAWN_DISTANCE;
		if (minD >= maxD) {
			minD = MAX_SPAWN_DISTANCE;
			maxD = MIN_SPAWN_DISTANCE;
		}

		int mobCount = 0;
		for (RigidBody rb : Model.getEntities()) {
			if (rb instanceof Mushroom) {
				mobCount++;
			}
		}

		if (mobCount >= maxEntity) {
			return;
		}

		for (Player player : players) {
			// Spawn By ID
			ArrayList<Block> spawnableID = getSpawnableByID(player.getPosition().add(new Vec2(0, -Block.SIZE)), minD,
					maxD);
			randomSpawnByID(spawnableID);

			// * Spawn by checking blocks
			// Block[][] spawnable = getSpawnableBlocks(player.getPosition().add(new Vec2(0,
			// -Block.SIZE)), minD, maxD);
			// randomSpawn(spawnable);
		}
	}

	private static void randomSpawn(Block[][] blocks) {
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {
				Block bl = blocks[i][j];
				if (bl == null)
					continue;
				if (isInPlayersRange(bl))
					blocks[i][j] = null;
				else {
					int rand = (int) Math.floor((float) Math.random() + spawnProba);
					if (rand != 0) {
						spawn(bl);
						return;
					}
				}
			}
		}
	}

	private static void randomSpawnByID(ArrayList<Block> blocks) {
		for (Block bl : blocks) {
			if (bl == null)
				continue;
			if (isInPlayersRange(bl))
				blocks.remove(bl);
			else {
				int rand = (int) Math.floor((float) Math.random() + spawnProba);
				if (rand != 0) {
					spawn(bl);
					return;
				}
			}
		}
	}

	private static void spawn(Block block) {
		Mushroom mob = new Mushroom(Model.controller, block.getPosition(), 1);
		Model.spawn(mob);
	}

	private static Block[][] getSpawnableBlocks(Vec2 center, int rangeMin, int rangeMax) {
		Vec2 coords = new Vec2(center).divide(Block.SIZE).round();
		Block[][] mapZone = Model.getMapZone((int) coords.getX() - rangeMax, (int) coords.getY() - rangeMax,
				2 * rangeMax, 2 * rangeMax);
		Block[][] blocks = mapZone.clone();

		for (int i = rangeMin; i < mapZone.length - rangeMin; i++) {
			for (int j = rangeMin; j < mapZone[0].length - rangeMin; j++) {
				blocks[i][j] = null;
			}
		}

		for (int i = 1; i < mapZone.length - 1; i++) {
			for (int j = 0; j < mapZone[0].length; j++) {
				if (mapZone[i][j] == null)
					continue;
				if (j < 2) {
					blocks[i][j] = null;
					continue;
				}
				if (j >= mapZone.length - 2) {
					blocks[i][j] = null;
					continue;
				}
				if (mapZone[i][j].id == 0)
					blocks[i][j] = null;
				if (!(mapZone[i][j - 1] == null && mapZone[i][j - 2] == null)) {
					blocks[i][j] = null;
				}
				if (!(mapZone[i + 1][j] == null && mapZone[i - 1][j] == null)) {
					blocks[i][j] = null;
				}
			}
		}
		return blocks;
	}

	private static boolean isInPlayersRange(Block block) {
		if (block == null)
			return true;
		for (Player player : MobSpawner.players) {
			if (block.getPosition().sub(player.getPosition()).length() <= MIN_SPAWN_DISTANCE * Block.SIZE) {
				return true;
			}
		}
		return false;
	}

	private static ArrayList<Block> getSpawnableByID(Vec2 center, int rangeMin, int rangeMax) {
		Vec2 coords = new Vec2(center).divide(Block.SIZE).round();
		Block[][] mapZone = Model.getMapZone((int) coords.getX() - rangeMax, (int) coords.getY() - rangeMax,
				2 * rangeMax, 2 * rangeMax);
		ArrayList<Block> spawnable = new ArrayList<Block>();
		for (int i = 0; i < mapZone.length; i++) {
			for (int j = 0; j < mapZone[0].length; j++) {
				Block block = mapZone[i][j];
				if (block == null)
					continue;
				if (block.id == 2 || block.id == 3 || block.id == 22) {
					spawnable.add(block);
				}
			}
		}
		MobSpawner.spawnable = spawnable;
		return spawnable;
	}

	public static boolean isInitialized() {
		return initialized;
	}

	public static void drawBlocks(Vec2 camPos, Graphics g) {
		if (initialized == false)
			return;
		if (spawnable == null)
			return;

		for (Block bl : MobSpawner.spawnable) {
			if (bl == null)
				continue;
			g.setColor(Color.green);
			Vec2 blockPos = bl.getPosition().globalToScreen(camPos);
			g.drawRect((int) blockPos.x, (int) blockPos.y, 64, 64);

		}
	}
}
