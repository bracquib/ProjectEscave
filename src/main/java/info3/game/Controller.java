package info3.game;

import java.util.ArrayList;

import info3.game.entities.Entity;
import info3.game.network.KeyPress;

public abstract class Controller {
	static Controller controller;

	/**
	 * La largeur de la carte
	 */
	private static final int MAP_WIDTH = 1000;

	/**
	 * La liste de toutes les entités dans le monde.
	 */
	ArrayList<Entity> entities;

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
	ArrayList<Entity> map;

	/**
	 * Ajoute une entité dans le monde.
	 * 
	 * @param e L'entité à faire apparaître
	 */
	public void spawn(Entity e) {
		this.entities.add(e);
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
		// TODO: est ce que le monde est circulaire verticalement aussi ?
		return this.map.get((x * MAP_WIDTH) % MAP_WIDTH + y);
	}

	/**
	 * Renvoie toutes les entités dans un cercle donné
	 * 
	 * @param x      Abcisse du centre du cercle
	 * @param y      Ordonnée du centre du cercle
	 * @param radius Rayon du cercle
	 * @return Une liste de toutes les entités dans ce cercle
	 */
	public ArrayList<Entity> getNearEntities(int x, int y, float radius) {
		// TODO: ce code pourrait être optimisé en divisant la carte en chunks
		// Chaque chunk fait X par X blocs, et contient toutes les entités dans ce carré
		// Cet algorithme itèrerait alors d'abord sur les chunks pour trouver ceux dans
		// le rayon voulu (plus rapide car on a X^2 moins de chunks que de blocs)
		// puis à l'intérieur des chunks sélectionnés uniquement
		//
		// Seul difficulté : pour les entités qui se déplacent, il faut penser à les
		// changer de chunk si besoin
		ArrayList<Entity> nearEntities = new ArrayList<>();
		Vec2 center = new Vec2((float) x, (float) y);
		for (Entity e : this.entities) {
			if (center.distance(e.getPosition()) < radius) {
				nearEntities.add(e);
			}
		}
		return nearEntities;
	}

	protected Controller() {
		Controller.controller = this;
		this.entities = new ArrayList<Entity>();
	}

	public abstract void keyPressed(KeyPress e);

	public abstract void tick(long elapsed);

	public abstract void addView(View v);

	static int avatarID = 0;

	public abstract Avatar createAvatar(Vec2 pos, String string, int imageLen, int animationDelay);

	protected abstract void removeView(RemoteView view);
}
