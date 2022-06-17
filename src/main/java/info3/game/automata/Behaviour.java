package info3.game.automata;

import info3.game.entities.Entity;

public interface Behaviour {
	/**
	 * @return toujours true
	 */
	boolean true_(Entity e);

	/**
	 * @param keyCode la touche enfoncée
	 * @return true si la touche keyCode est enfoncée
	 */
	boolean key(Entity e, int keyCode);

	/**
	 * @param d la Direction
	 * @return true si l'entité est orientée dans la Direction d
	 */
	boolean myDir(Entity e, Direction d);

	/**
	 * @param d la Direction
	 * @param c la Category de l'Entité
	 * @return true si la cellule dans la Direction d contient une Entité de
	 *         Category c, et la met éventuellement dans Entity r
	 */
	boolean cell(Entity e, Direction d, Category c);

	/**
	 * @param c la Category de l'Entité
	 * @param d la Direction
	 * @return true si la plus proche Entité de Category c est dans la Direction d
	 */
	boolean closest(Entity e, Category c, Direction d);

	/**
	 * @return true s'il reste de l'énergie à l'entité
	 */
	boolean gotPower(Entity e);

	/**
	 * @return true s'il reste des choses dans le store
	 */
	boolean gotStuff(Entity e);

	void wizz(Entity e, Direction d);

	void pop(Entity e, Direction d);

	void move(Entity e, Direction d);

	void protect(Entity e, Direction d, Integer dmg);

	void move(Entity e);

	void jump(Entity e);

	void hit(Entity e);

	void pick(Entity e);

	void throw_(Entity e);

	void store(Entity e);

	void get(Entity e);

	void power(Entity e); // pas encore utilisé

	void explode(Entity e);

	void egg(Entity e);
}
