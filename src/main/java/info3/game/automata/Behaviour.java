package info3.game.automata;

import java.util.ArrayList;

import info3.game.Model;
import info3.game.entities.Entity;

public abstract class Behaviour {
	Entity ret;

	/**
	 * @return toujours true
	 */
	abstract boolean true_(Entity e);

	/**
	 * @param keyCode la touche enfoncée
	 * @return true si la touche keyCode est enfoncée
	 */
	abstract boolean key(Entity e, int keyCode);

	/**
	 * @param d la Direction
	 * @return true si l'entité est orientée dans la Direction d
	 */
	abstract boolean myDir(Entity e, Direction d);

	/**
	 * @param d la Direction
	 * @param c la Category de l'Entité
	 * @return true si la cellule dans la Direction d contient une Entité de
	 *         Category c, et la met éventuellement dans Entity r
	 */
	boolean cell(Entity e, Direction d, Category c) {
		// 32 =une case
		// donner les coord en haut a gacuhe de la zone de vision
		int decX, decY;
		switch (d) {
		case NORTH:
			decX = 0;
			decY = -32;
			break;
		case SOUTH:
			decX = 0;
			decY = 32;
			break;
		case EST:
			decX = 32;
			decY = 0;
			break;
		case WEST:
			decX = -32;
			decY = 0;

			break;
		case NORTHWEST:
			decX = -32;
			decY = -32;
			break;
		case NORTHEST:
			decX = 32;
			decY = -32;
			break;
		default: // notamment HERE
			decX = decY = 0;
			break;
		}
		ArrayList<Entity> nearEntities = Model.getNearEntities2((int) (e.getPosition().getX()) + decX,
				(int) (e.getPosition().getY()) + decY, 32, 32);
		for (Entity e1 : nearEntities) {
			if (e1.getCategory() == c) {
				ret = e1;
				return true;
			}
		}
		return false;
	}

	/**
	 * @param c la Category de l'Entité
	 * @param d la Direction
	 * @return true si la plus proche Entité de Category c est dans la Direction d
	 */
	boolean closest(Entity e, Category c, Direction d, int diamVision) {
		int decX, decY;// test rayon vision
		switch (d) {
		case NORTH:
			decX = -diamVision / 2;
			decY = -diamVision - 32;
			break;

		case SOUTH:
			decX = -diamVision / 2;
			decY = 32;
			break;

		case EST:
			decX = 32;
			decY = -diamVision / 2;
			break;

		case WEST:
			decX = -diamVision - 32;
			decY = -diamVision / 2;
			break;

		default:
			decX = decY = 0;
			break;
		}

		ArrayList<Entity> nearEntities4 = Model.getNearEntities2((int) (e.getPosition().getX()) + decX,
				(int) (e.getPosition().getY()) + decY, diamVision, diamVision);
		for (Entity e1 : nearEntities4) {
			if (e1.getCategory() == c) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return true s'il reste de l'énergie à l'entité
	 */
	abstract boolean gotPower(Entity e);

	/**
	 * @return true s'il reste des choses dans le store
	 */
	abstract boolean gotStuff(Entity e);

	abstract void wizz(Entity e, Direction d);

	abstract void pop(Entity e, Direction d);

	abstract void move(Entity e, Direction d);

	abstract void protect(Entity e, Direction d, int dmg);

	abstract void move(Entity e);

	abstract void jump(Entity e);

	abstract void hit(Entity e);

	abstract void pick(Entity e);

	abstract void throw_(Entity e);

	abstract void store(Entity e);

	abstract void get(Entity e);

	abstract void power(Entity e); // pas encore utilisé

	abstract void explode(Entity e);

	abstract void egg(Entity e, Direction d);
}
