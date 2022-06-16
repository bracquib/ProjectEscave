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
	public boolean closest(Entity e, Category c, Direction d) {
		// à faire
		// on peut utiliser getNearEntities pour le rayon_de_vision de l'entité et cela
		// détecte les entité présentes dedans mais faut voir pour les zones qu'on
		// détecte

		/*
		 * switch (d) { case NORTH: if
		 * (e.model.getNearEntities(e.getPosition().getX()-rayon_de_vision,
		 * e.getPosition().getY() + rayon_de_vision, rayon_de_vision, rayon_de_vision)
		 * == c) { return true; } break; case SOUTH: if
		 * (e.Model.getNearEntities(e.getPosition().getX()-rayon_de_vision,
		 * e.getPosition().getY() - rayon_de_vision, rayon_de_vision, rayon_de_vision)
		 * == c) { return true; } break; case EST: if
		 * ((e.model.getNearEntities(e.getPosition().getX()-rayon_de_vision,
		 * e.getPosition().getY() + rayon_de_vision, rayon_de_vision, rayon_de_vision)
		 * == c) { return true; } break; case WEST: if
		 * ((e.model.getNearEntities(e.getPosition().getX()-rayon_de_vision,
		 * e.getPosition().getY() + rayon_de_vision, rayon_de_vision, rayon_de_vision)
		 * == c) { return true; } break; }
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
		 * WEST:p.getSpeed().setX(-70);}} degat_epee=0;degat_pioche=0;return;
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
