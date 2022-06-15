package info3.game;

import java.util.ArrayList;
import java.util.List;

import info3.game.cavegenerator.SpawnGenerator4D;
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

	/**
	 * La liste des entités dynamiques à spawner au prochain tick
	 * 
	 * On ajoute pas directement dans entities pour éviter des accès concurrents par
	 * plusieurs threads #réseau #parallélisme
	 */
	ArrayList<RigidBody> spawnQueue = new ArrayList<RigidBody>();

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

	ArrayList<Vec2> spawnPoints;

	private final int maxPlayers = 1;
	private int playerCount = 0;

	private boolean started() {
		return this.playerCount == this.maxPlayers;
	}

	private PhysicsWorld physics;

	public Model(LocalController controller) {
		this.controller = controller;
		this.entities = new ArrayList<RigidBody>();
		this.physics = new PhysicsWorld(this);
	}

	/**
	 * Ajoute une entité dans le monde.
	 * 
	 * @param e L'entité à faire apparaître
	 */
	public void spawn(RigidBody e) {
		this.spawnQueue.add(e);
	}

	public Player spawnPlayer() {
		// TODO: throw exception if there are more players than expected
		this.generateMapIfNeeded();
		Player p = new Player(this.controller, Player.colorFromInt(this.playerCount),
				this.spawnPoints.get(this.playerCount).multiply(32), true);
		this.playerCount++;
		this.spawn(p);
		return p;
	}

	private void generateMapIfNeeded() {
		if (this.map == null) {
			// génération de la map
			SpawnGenerator4D generationMap = new SpawnGenerator4D();
			int[][] blocks = generationMap.spawnStatueTotal(this.maxPlayers);
			this.spawnPoints = generationMap.listSpawnPlayer;
			List<Vec2> blocs = generationMap.listSpawnBlocsStatues;
			List<Vec2> statues = generationMap.listSpawnStatues;
			this.map = new Block[blocks.length][blocks[0].length];
			for (int i = 0; i < blocks.length; i++) {
				for (int j = 0; j < blocks[i].length; j++) {
					if (blocks[i][j] == 1) {
						this.map[i][j] = new Block(this.controller, new Vec2(i * 32, j * 32));
					}
				}
			}
		}
	}

	public void tick(long elapsed) {
		this.entities.addAll(this.spawnQueue);
		this.spawnQueue.clear();
		if (!this.started()) {
			return;
		}
		this.physics.tick(elapsed);
		for (Entity e : this.entities) {
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
	public ArrayList<Entity> getNearEntities(int x, int y, int width, int height) {
		ArrayList<Entity> nearEntities = new ArrayList<>();
		// On parcours d'abord la map pour avoir les blocs
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				nearEntities.add(this.getBlock(x, y));
			}
		}
		// Puis on parcours les entités "dynamiques"
		for (Entity e : this.entities) {
			Vec2 pos = e.getPosition();
			if (pos.getX() > x && pos.getX() < x + width && pos.getY() > y && pos.getY() < y + height) {
				nearEntities.add(e);
			}
		}
		return nearEntities;
	}

	public ArrayList<Entity> allEntities() {
		ArrayList<Entity> all;
		synchronized (this.entities) {
			all = new ArrayList<Entity>(this.entities);
		}
		if (this.map != null) {
			synchronized (this.map) {
				for (int i = 0; i < this.map.length; i++) {
					for (int j = 0; j < this.map[i].length; j++) {
						if (this.map[i][j] != null) {
							all.add(this.map[i][j]);
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
				nearBlocks.add(this.getBlock(x + i, y + j));
			}
		}
		return nearBlocks;
	}

	public ArrayList<RigidBody> getEntities() {
		return this.entities;
	}

	public Block[][] getMap() {
		return this.map;
	}
}
