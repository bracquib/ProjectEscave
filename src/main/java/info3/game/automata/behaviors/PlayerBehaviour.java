package info3.game.automata.behaviors;

import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.entities.Entity;
import info3.game.physics.RigidBody;

public class PlayerBehaviour extends Behaviour {

	Entity ret;

	@Override
	public boolean true_(Entity e) {
		return true;
	}

	@Override
	public boolean key(Entity e, int keyCode) {
		// à faire
		// System.out.println("[DEBUG] Checking key " + keyCode);
		return e.getController().isKeyPressed(keyCode);
	}

	@Override
	public boolean myDir(Entity e, Direction d) {
		// pas besoin pour player
		return false;
	}

	@Override
	public boolean closest(Entity e, Category c, Direction d, int diam_vision) {
		int diam = 320; // en pixel
		return super.closest(e, c, d, diam);
	}

	@Override
	public boolean gotPower(Entity e) {

		if (e.pointsDeVie > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean gotStuff(Entity e) {
		// pas besoin pour player
		return false;
	}

	@Override
	public void wizz(Entity e, Direction d) {
		// wizz=jump
		/*
		 * RigidBody p = new RigidBody(e, 1, 10); p.getSpeed().setY(-120);
		 */
		((RigidBody) e).getSpeed().setY(-270);
		return;
	}

	@Override
	public void pop(Entity e, Direction d) {
		// pop=hit à faire
		if (cell(e, d, Category.ADVERSAIRE)) {
			ret.getBehaviour().protect(ret, d, e.degatEpee);
		} else if (cell(e, d, Category.JUMPABLE)) {
			ret.getBehaviour().wizz(ret, d); // peut etre à ajuster
		}
		return;
		// e.degat_epee=1;voir comment décider si l'inventaire a une épée ou une pioche
	}

	@Override
	public void move(Entity e, Direction d) {

		RigidBody p = (RigidBody) e;
		// new RigidBody(e, 1, 10);
		switch (d) {
		case EST:
			p.getSpeed().setX(190);
			break;
		case WEST:
			p.getSpeed().setX(-190);
			break;
		case SOUTH:
			break;
		case NORTH:
			break;
		case NORTHWEST:
			break;
		case NORTHEST:
			break;

		}

		return;
	}

	@Override
	public void move(Entity e) {
		// pas besoin pour player

	}

	@Override
	public void jump(Entity e) {
		// jump=wizz donc fait dans wizz
	}

	@Override
	public void hit(Entity e) {
		// pop=hit donc fait dans pop
	}

	@Override
	public void pick(Entity e) {
		// pas besoin pour le player
	}

	@Override
	public void throw_(Entity e) {
		// pas besoin pour le player

	}

	@Override
	public void store(Entity e) {
		// pas besoin pour le player

	}

	@Override
	public void get(Entity e) {
		// pas besoin pour le player

	}

	@Override
	public void power(Entity e) {
		// jamais utilisé

	}

	@Override
	public void explode(Entity e) {
		// pas besoin pour le player

	}

	@Override
	public void egg(Entity e, Direction d) {
		// pas besoin pour le player

	}

	@Override
	public void protect(Entity e, Direction d, int dmg) {
		e.pointsDeVie -= dmg;
		RigidBody p = (RigidBody) e;
		switch (d) {
		case SOUTH:
			p.getSpeed().setY(-240);
			break;
		case EST:
			p.getSpeed().setX(140);
			break;
		case WEST:
			p.getSpeed().setX(-140);
			break;
		case NORTH:
			p.getSpeed().setY(240);
			break;
		default:
			break;
		}
		return;
	}

}