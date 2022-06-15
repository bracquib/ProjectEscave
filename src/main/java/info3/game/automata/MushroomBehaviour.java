package info3.game.automata;

import info3.game.entities.Entity;

public class MushroomBehaviour implements Behaviour {

	@Override
	public boolean true_(Entity e) {
		return true;
	}

	@Override
	public boolean key(Entity e, int keyCode) {
		// à faire
		return false;
	}

	@Override
	public boolean myDir(Entity e, Direction d) {
		// pas besoin
		return false;
	}

	@Override
	public boolean cell(Entity e, Direction d, Category c) {
		/*
		 * switch (d) { case NORTH: if (e.getPosition().getY() + 32 == c) { return true;
		 * } break; case SOUTH: if (e.getPosition().getY() - 32 == c) { return true; }
		 * break; case EAST: if (e.getPosition().getX() + 32 == c) { return true; }
		 * break; case WEST: if (e.getPosition().getX() - 32 == c) { return true; }
		 * break; }
		 */
		return false;
	}

	@Override
	public boolean closest(Entity e, Category c, Direction d) {
		// à faire

		/*
		 * if(e.getPosition.distance(c.getPosition)<rayon_de_vision) {
		 *
		 * }
		 */
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
		// wizz=jump
		/*
		 * switch (d) { case NE: RigidBody p = new RigidBody(e, 1,5);
		 * p.getSpeed().setY(-120); p.getSpeed().setX(70); break; case NW: RigidBody p =
		 * new RigidBody(e, 1,5); p.getSpeed().setY(-120); p.getSpeed().setX(70); break;
		 * }
		 */
		return;
	}

	@Override
	public void pop(Entity e, Direction d) {
		// pop=hit
		// e.degat_mob = 1;
		return;

	}

	@Override
	public void move(Entity e, Direction d) {
		/*
		 * RigidBody p = new RigidBody(e, 1,5); switch (d) { case EAST:
		 * p.getSpeed().setX(70); break; case WEST: p.getSpeed().setX(-70); break; case
		 * SOUTH: }
		 */

	}

	@Override
	public void protect(Entity e, Direction d) {
		/*
		 * if(degat_epee!=0
		 * ||degat_pioche!=0){m_points=m_points-degat_epee-degat_pioche; switch(d){case
		 * SOUTH:p.getSpeed().setY(-120);case EAST:p.getSpeed().setX(70);case
		 * WEST:p.getSpeed().setX(-70);}}return;
		 * 
		 * 
		 */
	}

	@Override
	public void move(Entity e) {
		// pas besoin
	}

	@Override
	public void jump(Entity e) {
		// wizz=jump donc fait dans wizz
	}

	@Override
	public void hit(Entity e) {
		// pop=hit donc fait dans pop

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
	public void egg(Entity e) {
		// RigidBody p = new RigidBody(e, 1,5);
		// Model.spawn(p)=
	}

}
