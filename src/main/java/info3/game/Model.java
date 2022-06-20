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
	public LocalController controller;

	/**
	 * La liste de toutes les entités dynamiques dans le monde.
	 */
	ArrayList<RigidBody> entities;
	static ArrayList<RigidBody> entities2;

	/**
	 * La liste des entités dynamiques à spawner au prochain tick
	 * 
	 * On ajoute pas directement dans entities pour éviter des accès concurrents par
	 * plusieurs threads #réseau #parallélisme
	 */
	static ArrayList<RigidBody> spawnQueue = new ArrayList<RigidBody>();

	/**
	 * La liste des blocs de la carte.
	 * 
	 * Les élements de ce tableau sont aussi dans le tableau `entities`. Cette
	 * duplication permet d'accéder précisément à un bloc à une position donnée. En
	 * réalité, il n'y a pas de duplication, juste de l'aliasing.
	 * 
	 * On peut voir la carte comme une matrice, dont on peut accéder à un élément
	 * précis avec la méthode getBlock(x, y) de cette classe.
	 */
	Block[][] map;
	static Block[][] map2;

	ArrayList<Vec2> spawnPoints;

	private final int maxPlayers = 1;
	private int playerCount = 0;

	ArrayList<Automata> automatas;

	private boolean started() {
		return this.playerCount == this.maxPlayers;
	}

	private PhysicsWorld physics;

	public Model(LocalController controller) {
		this.controller = controller;
		Model.entities2 = new ArrayList<RigidBody>();
		this.physics = new PhysicsWorld(this);
		this.loadAutomatas();
	}

	/**
	 * Ajoute une entité dans le monde.
	 * 
	 * @param e L'entité à faire apparaître
	 */
	public static void spawn(RigidBody e) {
		Model.spawnQueue.add(e);
	}

	public Player spawnPlayer() {
		// TODO: throw exception if there are more players than expected
		this.generateMapIfNeeded();
		Player p = new Player(this.controller, Player.colorFromInt(this.playerCount),
				this.spawnPoints.get(this.playerCount).multiply(32), true, 10);
		this.playerCount++;
		Model.spawn(p);

		return p;
	}

	private void generateMapIfNeeded() {
		if (Model.map2 == null) {
			// génération de la map
			SpawnGenerator4D generationMap = new SpawnGenerator4D();
			int[][] values = generationMap.spawnStatueTotal(this.maxPlayers);
			this.spawnPoints = generationMap.listSpawnPlayer;
			List<Vec2> blocs = generationMap.listSpawnBlocsStatues;
			List<Vec2> statues = generationMap.listSpawnStatues;

			Torus torus = DecorationGenerator.decorate(values);
			int[][] blocks = torus.toArray();

			Model.map2 = new Block[blocks.length][blocks[0].length]; // this.map
			for (int i = 0; i < blocks.length; i++) {
				for (int j = 0; j < blocks[i].length; j++) {
					if (blocks[i][j] != 0) {
						Model.map2[i][j] = new Block(this.controller, new Vec2(i * 32, j * 32), blocks[i][j], 1); // this.map

					}
				}
			}
		}
	}

	public void tick(long elapsed) {
		Model.entities2.addAll(this.spawnQueue);
		this.spawnQueue.clear();
		if (!this.started()) {
			return;
		}
		if (elapsed > 200) {
			System.out.println("[WARN] Tick ignored in model");
			return;
		}
		this.physics.tick(elapsed);
		for (Entity e : this.allEntities()) {
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
	public Entity getBlock(int x, int y) {
		// TODO : on est sur un tore
		return this.map[x][y];
	}

	/**
	 * Renvoie toutes les entités dans un rectangle donné
	 * 
	 * @param x      Abcisse du coin en haut à gauche du rectangle
	 * @param y      Ordonnée du coin en haut à gauche du rectangle
	 * @param width  La largeur du rectangle
	 * @param height La hauteur du rectangle
	 * @return Une liste de toutes les entités dans ce rectangle
	 */

	/*
	 * public ArrayList<Entity> getNearEntities(int x, int y, int width, int height)
	 * { ArrayList<Entity> nearEntities = new ArrayList<>(); // On parcours d'abord
	 * la map pour avoir les blocs for (int i = 0; i < width; i++) { for (int j = 0;
	 * j < height; j++) { nearEntities.add(this.getBlock(x, y)); } } // Puis on
	 * parcours les entités "dynamiques" for (Entity e : this.entities) { Vec2 pos =
	 * e.getPosition(); if (pos.getX() > x && pos.getX() < x + width && pos.getY() >
	 * y && pos.getY() < y + height) { nearEntities.add(e); } } return nearEntities;
	 * }
	 */

	/**
	 * 
	 * @param baseX  abscisse du coin hg de la zone de vision
	 * @param baseY  ordonnée (croissant vers le bas) du coin hg de la zone de
	 *               vision
	 * @param width  en px
	 * @param height en px
	 * @return
	 */
	public static ArrayList<Entity> getNearEntities2(int baseX, int baseY, int width, int height) {
		ArrayList<Entity> nearEntities = new ArrayList<>();
		// On parcours d'abord la map pour avoir les blocs
		/*
		 * int baseX = (x - width / 2) / 32; int baseY = (y - height / 2) / 32;
		 */
		for (int i = 0; i < width / 32; i++) {
			for (int j = 0; j < height / 32; j++) {
				Block block = getBlock2(baseX / 32 + i, baseY / 32 + j);
				if (block != null) {
					nearEntities.add(block);
				}
			}
		}
		// Puis on parcours les entités "dynamiques"
		for (Entity e : Model.entities2) {
			Vec2 pos = e.getPosition();
			if (pos.getX() > baseX && pos.getX() < baseX + width && pos.getY() > baseY && pos.getY() < baseY + height) {
				nearEntities.add(e);
			}
		}
		return nearEntities;
	}

	public static Block getBlock2(int x, int y) {
		// TODO : on est sur un tore
		return Model.map2[x][y];
	}

	public ArrayList<Entity> allEntities() {
		ArrayList<Entity> all;
		synchronized (Model.entities2) {
			all = new ArrayList<Entity>(Model.entities2);
		}
		if (Model.map2 != null) {
			synchronized (Model.map2) {
				for (int i = 0; i < Model.map2.length; i++) {
					for (int j = 0; j < Model.map2[i].length; j++) {
						if (Model.map2[i][j] != null) {
							all.add(Model.map2[i][j]);
						}
					}
				}
			}
		}

		return all;
	}

	public ArrayList<Entity> getNearBlocks(int x, int y, int width, int height) {
		ArrayList<Entity> nearBlocks = new ArrayList<>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				nearBlocks.add(Model.getBlock2(x + i, y + j));
			}
		}
		return nearBlocks;
	}

	public ArrayList<RigidBody> getEntities() {
		return Model.entities2;
	}

	public Block[][] getMap() {
		return Model.map2;
	}

	private void loadAutomatas() {
		AST ast = null;
		try {
			ast = new AutomataParser(new BufferedReader(new FileReader("src/main/resources/automatas.gal"))).Run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		BotBuilder botBuilder = new BotBuilder();
		ast.accept(botBuilder);
		this.automatas = botBuilder.getAutomatas();
		System.out.println("[DEBUG] Loaded " + this.automatas.size() + " automatas");
	}

	public Automata getAutomata(String name) {
		for (Automata automata : this.automatas) {
			if (automata.getName().equals(name))
				return automata;
		}
		System.out.println("[WARNING] Automata " + name + " not found");
		return null;
	}

}
