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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean myDir(Entity e, Direction d) {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		// pas besoin pour player
		return false;
	}

	@Override
	public boolean gotPower(Entity e) {
		/*
		 * if (e.m_points > 0) { return true; }
		 */
		return false;
	}

	@Override
	public boolean gotStuff(Entity e) {
		// TODO Auto-generated method stub
		// pas besoin pour player
		return false;
	}

	@Override
	public void wizz(Entity e, Direction d) {
		// TODO Auto-generated method stub
		// wizz=jump
		RigidBody p = new RigidBody(e, 1);
		p.getSpeed().setY(-120);
		return;
	}

	@Override
	public void pop(Entity e, Direction d) {
		// TODO Auto-generated method stub
		// pop=hit

	}

	@Override
	public void move(Entity e, Direction d) {
		// TODO Auto-generated method stub
		/*
		 * RigidBody p = new RigidBody(e, 1); switch (d) { case EAST:
		 * p.getSpeed().setX(70); break; case WEST: p.getSpeed().setX(-70); break; }
		 */
		return;
	}

	@Override
	public void move(Entity e) {
		// TODO Auto-generated method stub
		// pas besoin pour player

	}

	@Override
	public void jump(Entity e) {
		// TODO Auto-generated method stub
		// jump=wizz
	}

	@Override
	public void hit(Entity e) {
		// TODO Auto-generated method stub
		// pop=hit
	}

	@Override
	public void pick(Entity e) {
		// TODO Auto-generated method stub
		// pas besoin pour le player
	}

	@Override
	public void throw_(Entity e) {
		// TODO Auto-generated method stub
		// pas besoin pour le player

	}

	@Override
	public void store(Entity e) {
		// TODO Auto-generated method stub
		// pas besoin pour le player

	}

	@Override
	public void get(Entity e) {
		// TODO Auto-generated method stub
		// pas besoin pour le player

	}

	@Override
	public void power(Entity e) {
		// TODO Auto-generated method stub
		// pas besoin pour le player

	}

	@Override
	public void explode(Entity e) {
		// TODO Auto-generated method stub
		// pas besoin pour le player

	}

	@Override
	public void egg(Entity e) {
		// TODO Auto-generated method stub
		// pas besoin pour le player

	}

	@Override
	public void protect(Entity e, Direction d) {
		// TODO Auto-generated method stub

	}

}
