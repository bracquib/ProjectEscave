package info3.game.automata;

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
		// FU: 785
		// FD: 768
		// FL: 776
		// FR: 782
		System.out.println("[DEBUG] Checking key " + keyCode);
		return e.isKeyPressed(keyCode);
	}

	@Override
	public boolean myDir(Entity e, Direction d) {
		// pas besoin pour player
		return false;
	}

	/*
	 * @Override public boolean cell(Entity e, Direction d, Category c) { // 32 =une
	 * case
	 * 
	 * switch (d) { case NORTH: ArrayList<Entity> nearEntities =
	 * Model.getNearEntities2((int) (e.getPosition().getX())+16, (int)
	 * (e.getPosition().getY()) - 16, 32, 32); for (Entity e1 : nearEntities) { if
	 * (e1.getCategory() == c) { ret = e1; return true; } } break; case SOUTH:
	 * ArrayList<Entity> nearEntities2 = Model.getNearEntities2((int)
	 * (e.getPosition().getX()) + 16, (int) (e.getPosition().getY()) + 48, 32, 32);
	 * for (Entity e1 : nearEntities2) { if (e1.getCategory() == c) { ret = e1;
	 * return true; } } break; case EST: ArrayList<Entity> nearEntities3 =
	 * Model.getNearEntities2((int) (e.getPosition().getX()) + 48, (int)
	 * (e.getPosition().getY())+16, 32, 32); for (Entity e1 : nearEntities3) { if
	 * (e1.getCategory() == c) { ret = e1; return true; } } break; case WEST:
	 * ArrayList<Entity> nearEntities4 = Model.getNearEntities2((int)
	 * (e.getPosition().getX()) - 16, (int) (e.getPosition().getY()) + 16, 32, 32);
	 * for (Entity e1 : nearEntities4) { if (e1.getCategory() == c) { ret = e1;
	 * return true; } } break; case NORTHWEST: break; case NORTHEST: break; }
	 * 
	 * return false; }
	 */

	@Override
	public boolean closest(Entity e, Category c, Direction d, int diam_vision) {
		int diam = 320; // en pixel
		return super.closest(e, c, d, diam);
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
		// pas besoin pour player
		return false;
	}

	@Override
	public void wizz(Entity e, Direction d) {
		// wizz=jump
		/*
		 * RigidBody p = new RigidBody(e, 1, 10); p.getSpeed().setY(-120);
		 */
		((RigidBody) e).getSpeed().setY(-250);
		return;
	}

	@Override
	public void pop(Entity e, Direction d) {
		// pop=hit à faire
		if (cell(e, d, Category.ADVERSAIRE)) {
			ret.getBehaviour().protect(ret, d, e.degat_epee);
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
			p.getSpeed().setX(70);
			break;
		case WEST:
			p.getSpeed().setX(-70);
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
		e.m_points -= dmg;
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

}
