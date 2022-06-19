package info3.game.automata;

import info3.game.entities.Entity;
import info3.game.physics.RigidBody;

public class BlockBehaviour extends Behaviour {

	@Override
	public boolean true_(Entity e) {
		return true;
	}

	@Override
	public boolean key(Entity e, int keyCode) {
		// pas besoin
		return false;
	}

	@Override
	public boolean myDir(Entity e, Direction d) {
		// jamais utilisé
		return false;
	}

	@Override
	public boolean cell(Entity e, Direction d, Category c) {
		/*
		 * switch (d) { case NORTH: if (e.model.getNearEntities(e.getPosition().getX(),
		 * e.getPosition().getY() + 32, 32, 32) == c) { return true; } break; case
		 * SOUTH: if (e.model.getNearEntities(e.getPosition().getX(),
		 * e.getPosition().getY() - 32, 32, 32) == c) { return true; } break; case EST:
		 * if (e.model.getNearEntities(e.getPosition().getX()+32,
		 * e.getPosition().getY(), 32, 32) == c) { return true; } break; case WEST: if
		 * (e.model.getNearEntities(e.getPosition().getX()-32, e.getPosition().getY() +
		 * 32, 32, 32) == c) { return true; } break; }
		 */
		return false;
	}

	@Override
	public boolean closest(Entity e, Category c, Direction d, int diam_vision) {
		// pas besoin
		return false;
	}

	@Override
	public boolean gotPower(Entity e) {
		if (e.m_points > 0) {
			return true;
		}

		return false;

	}

	@Override
	public boolean gotStuff(Entity e) {
		// pas besoin
		return false;
	}

	@Override
	public void wizz(Entity e, Direction d) {
		// wizz=coup_reçu
		e.m_points -= 1; // à ajuster (dmg)
		RigidBody p = (RigidBody) e;
		switch (d) {
		case SOUTH:
			p.getSpeed().setY(-120);
			break;
		case EST:
			p.getSpeed().setX(70);
			break;
		case WEST:
			p.getSpeed().setX(-70);
			break;
		case NORTH:
			p.getSpeed().setY(120);
			break;
		default:
			break;
		}
		return;

	}

	@Override
	public void pop(Entity e, Direction d) {
		// TODO pas besoin

	}

	@Override
	public void move(Entity e, Direction d) {
		// pas besoin

	}

	@Override
	public void protect(Entity e, Direction d, int dmg) {
		// pas besoin

	}

	@Override
	public void move(Entity e) {
		// pas besoin

	}

	@Override
	public void jump(Entity e) {
		// pas besoin

	}

	@Override
	public void hit(Entity e) {
		// pas besoin

	}

	@Override
	public void pick(Entity e) {
		// pas besoin

	}

	@Override
	public void throw_(Entity e) {
		// pas besoin

	}

	@Override
	public void store(Entity e) {
		// pas besoin

	}

	@Override
	public void get(Entity e) {
		// pas besoin

	}

	@Override
	public void power(Entity e) {
		// pas besoin

	}

	@Override
	public void explode(Entity e) {
		// pas besoin

	}

	@Override
	public void egg(Entity e, Direction d) {
		// pas besoin

	}

}
