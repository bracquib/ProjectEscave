package info3.game.automata;

import info3.game.entities.Entity;
import info3.game.physics.RigidBody;

public class PlayerBehaviour implements Behaviour {

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
		// pas besoin pour player
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
		// pas besoin pour player
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
		// pas besoin pour player
		return false;
	}

	@Override
	public void wizz(Entity e, Direction d) {
		// wizz=jump
		RigidBody p = new RigidBody(e, 1, 10);
		p.getSpeed().setY(-120);
		return;
	}

	@Override
	public void pop(Entity e, Direction d) {
		// pop=hit à faire
		// e.degat_epee=1;voir comment décider si l'inventaire a une épée ou une pioche
	}

	@Override
	public void move(Entity e, Direction d) {
		/*
		 * RigidBody p = new RigidBody(e, 1,10); switch (d) { case EAST:
		 * p.getSpeed().setX(70); break; case WEST: p.getSpeed().setX(-70); break; }
		 */
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
	public void egg(Entity e) {
		// pas besoin pour le player

	}

	@Override
	public void protect(Entity e, Direction d) {
		// comment accéder à degat_mob alors que c pour une autre entité
		/*
		 * if(degat_mob!=0){m_points=m_points-degat_mob; switch(d){case
		 * SOUTH:p.getSpeed().setY(-120);case EAST:p.getSpeed().setX(70);case
		 * WEST:p.getSpeed().setX(-70);}}return;
		 * 
		 * 
		 */
	}

}
