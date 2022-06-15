package info3.game.automata;

import info3.game.entities.Entity;

public class BlockBehaviour implements Behaviour {

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
		/*
		 * if(degat_epee==0
		 * ||degat_pioche==1){m_points=m_points-degat_epee-degat_pioche;}return;
		 */

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
	public void protect(Entity e, Direction d) {
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
	public void egg(Entity e) {
		// pas besoin

	}

}
