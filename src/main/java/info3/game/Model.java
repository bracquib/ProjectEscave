package info3.game;

import java.util.ArrayList;

import info3.game.entities.Entity;

/**
 * Représente l'ensemble des données du jeu.
 */
public class Model {
	/**
	 * Le controlleur associé à ce modèle.
	 */
	public Controller controller;

	/**
	 * La liste de toutes les entités dynamiques dans le monde.
	 */
	ArrayList<Entity> entities;

	/**
	 * La liste des entités dynamiques à spawner au prochain tick
	 * 
	 * On ajoute pas directement dans entities pour éviter des accès concurrents par
	 * plusieurs threads #réseau #parallélisme
	 */
	ArrayList<Entity> spawnQueue = new ArrayList<Entity>();

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
	Entity[][] map;

	public Model(Controller controller) {
		this.controller = controller;
		this.entities = new ArrayList<Entity>();
		this.map = new Entity[0][0];
	}

	/**
	 * Ajoute une entité dans le monde.
	 * 
	 * @param e L'entité à faire apparaître
	 */
	public void spawn(Entity e) {
		this.spawnQueue.add(e);
	}

	public void tick(long elapsed) {
		this.entities.addAll(this.spawnQueue);
		this.spawnQueue.clear();
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
}
