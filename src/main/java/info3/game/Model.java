package info3.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import info3.game.automata.Automata;
import info3.game.automata.BotBuilder;
import info3.game.automata.ast.AST;
import info3.game.automata.parser.AutomataParser;
import info3.game.cavegenerator.DecorationGenerator;
import info3.game.cavegenerator.SpawnGenerator4D;
import info3.game.cavegenerator.Torus;
import info3.game.entities.Block;
import info3.game.entities.Entity;
import info3.game.entities.Player;
import info3.game.physics.PhysicsWorld;
import info3.game.physics.RigidBody;

/**
 * Représente l'ensemble des données du jeu.
 */
public class Model {
	/**
	 * Le controlleur associé à ce modèle.
	 */
	public static LocalController controller;

	/**
	 * La liste de toutes les entités dynamiques dans le monde.
	 */
	static ArrayList<RigidBody> entities;

	/**
	 * La liste des entités dynamiques à spawner au prochain tick
	 * 
	 * On ajoute pas directement dans entities pour éviter des accès concurrents
	 * par plusieurs threads #réseau #parallélisme
	 */
	static ArrayList<RigidBody> spawnQueue = new ArrayList<RigidBody>();

	/**
	 * La liste des blocs de la carte.
	 * 
	 * Les élements de ce tableau sont aussi dans le tableau `entities`. Cette
	 * duplication permet d'accéder précisément à un bloc à une position
	 * donnée. En réalité, il n'y a pas de duplication, juste de l'aliasing.
	 * 
	 * On peut voir la carte comme une matrice, dont on peut accéder à un
	 * élément précis avec la méthode getBlock(x, y) de cette classe.
	 */
	private static Block[][] map;

	static ArrayList<Vec2> spawnPoints;

	private static final int maxPlayers = 1;
	private static int playerCount = 0;

	static ArrayList<Automata> automatas;

	private static boolean started() {
		return Model.playerCount == Model.maxPlayers;
	}

	private static PhysicsWorld physics;

	public static void init(LocalController controller) {
		Model.controller = controller;
		Model.entities = new ArrayList<RigidBody>();
		Model.physics = new PhysicsWorld();
		Model.loadAutomatas();
	}

	/**
	 * Ajoute une entité dans le monde.
	 * 
	 * @param e L'entité à faire apparaître
	 */
	public static void spawn(RigidBody e) {
		Model.spawnQueue.add(e);
	}

	public static Player spawnPlayer() {
		// TODO: throw exception if there are more players than expected
		Model.generateMapIfNeeded();
		Player p = new Player(Model.controller, Player.colorFromInt(Model.playerCount),
				Model.spawnPoints.get(Model.playerCount).multiply(32), true, 10);
		Model.playerCount++;
		Model.spawn(p);

		return p;
	}

	public static void deleteBlock(int x, int y) {
		if (Model.map[x][y] == null) {
			return;
		}
		int avatarId = Model.map[x][y].getAvatar().getId();
		Model.map[x][y] = null;
		Model.controller.deleteAvatar(avatarId);
	}

	private static void generateMapIfNeeded() {
		if (Model.map == null) {
			// génération de la map
			SpawnGenerator4D generationMap = new SpawnGenerator4D();
			int[][] values = generationMap.spawnStatueTotal(Model.maxPlayers);
			Model.spawnPoints = generationMap.listSpawnPlayer;
			List<Vec2> blocs = generationMap.listSpawnBlocsStatues;
			List<Vec2> statues = generationMap.listSpawnStatues;

			Torus torus = DecorationGenerator.decorate(values);
			int[][] blocks = torus.toArray();

			Model.map = new Block[blocks.length][blocks[0].length]; // this.map
			for (int i = 0; i < blocks.length; i++) {
				for (int j = 0; j < blocks[i].length; j++) {
					if (blocks[i][j] != 0) {
						Model.map[i][j] = new Block(Model.controller, new Vec2(i * 32, j * 32), blocks[i][j], 1); // this.map
					}
				}
			}
		}
	}

	public static void tick(long elapsed) {
		Model.entities.addAll(Model.spawnQueue);
		Model.spawnQueue.clear();
		if (!Model.started()) {
			return;
		}
		if (elapsed > 200) {
			System.out.println("[WARN] Tick ignored in model");
			return;
		}
		Model.physics.tick(elapsed);
		for (Entity e : Model.allEntities()) {
			e.tick(elapsed);
		}
	}

	/**
	 * Renvoie le bloc aux coordonnées (x, y) sur la carte, ou `null` si cet
	 * emplacement est vide.
	 * 
	 * Les indices peuvent être plus grands que la taille de la carte, le fait que
	 * le monde est circulaire est pris en compte.
	 * 
	 * @param x Coordonée x
	 * @param y Coordonée y
	 * @return Le bloc à cette position
	 */
	public static Block getBlock(int x, int y) {
		// TODO : on est sur un tore
		return Model.map[x][y];
	}

	/**
	 * 
	 * @param baseX  abscisse du coin hg de la zone de vision
	 * @param baseY  ordonnée (croissant vers le bas) du coin hg de la zone de
	 *               vision
	 * @param width  en px
	 * @param height en px
	 * @return
	 */
	public static ArrayList<Entity> getNearEntities(int baseX, int baseY, int width, int height) {
		ArrayList<Entity> nearEntities = new ArrayList<>();
		// On parcours d'abord la map pour avoir les blocs
		for (int i = 0; i < width / 32; i++) {
			for (int j = 0; j < height / 32; j++) {
				Block block = Model.getBlock(baseX / 32 + i, baseY / 32 + j);
				if (block != null) {
					nearEntities.add(block);
				}
			}
		}
		// Puis on parcours les entités "dynamiques"
		for (Entity e : Model.entities) {
			Vec2 pos = e.getPosition();
			if (pos.getX() > baseX && pos.getX() < baseX + width && pos.getY() > baseY && pos.getY() < baseY + height) {
				nearEntities.add(e);
			}
		}
		return nearEntities;
	}

	public static ArrayList<Entity> allEntities() {
		ArrayList<Entity> all;
		synchronized (Model.entities) {
			all = new ArrayList<Entity>(Model.entities);
		}
		if (Model.map != null) {
			synchronized (Model.map) {
				for (int i = 0; i < Model.map.length; i++) {
					for (int j = 0; j < Model.map[i].length; j++) {
						if (Model.map[i][j] != null) {
							all.add(Model.map[i][j]);
						}
					}
				}
			}
		}

		return all;
	}

	public static ArrayList<Entity> getNearBlocks(int x, int y, int width, int height) {
		ArrayList<Entity> nearBlocks = new ArrayList<>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				nearBlocks.add(Model.getBlock(x + i, y + j));
			}
		}
		return nearBlocks;
	}

	public static ArrayList<RigidBody> getEntities() {
		return Model.entities;
	}

	public static Block[][] getMap() {
		return Model.map;
	}

	private static void loadAutomatas() {
		AST ast = null;
		try {
			ast = new AutomataParser(new BufferedReader(new FileReader("src/main/resources/automatas.gal"))).Run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BotBuilder botBuilder = new BotBuilder();
		ast.accept(botBuilder);
		Model.automatas = botBuilder.getAutomatas();
		System.out.println("[DEBUG] Loaded " + Model.automatas.size() + " automatas");
	}

	public static Automata getAutomata(String name) {
		for (Automata automata : Model.automatas) {
			if (automata.getName().equals(name))
				return automata;
		}
		System.out.println("[WARNING] Automata " + name + " not found");
		return null;
	}

	public static Block[][] getMapZone(int x, int y, int width, int height) {
		Block[][] resMap = new Block[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				resMap[i][j] = map[x + i][y + j];
			}
		}
		return resMap;
	}

	public static ArrayList<Player> getPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (RigidBody rb : entities)
			if (rb instanceof Player)
				players.add((Player) rb);
		return players;
	}
}
