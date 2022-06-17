package info3.game.automata;

import java.util.ArrayList;

import info3.game.Model;
import info3.game.entities.Entity;
import info3.game.physics.RigidBody;

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
		switch (d) {
		case NORTH:
			ArrayList<Entity> nearEntities = Model.getNearEntities2((int) (e.getPosition().getX()),
					(int) (e.getPosition().getY()) + 32, 32, 32);
			for (Entity e1 : nearEntities) {
				if (e1.getCategory() == c) {
					return true;
				}
			}
			break;
		case SOUTH:
			ArrayList<Entity> nearEntities2 = Model.getNearEntities2((int) (e.getPosition().getX()),
					(int) (e.getPosition().getY()) - 32, 32, 32);
			for (Entity e1 : nearEntities2) {
				if (e1.getCategory() == c) {
					return true;
				}
			}
			break;
		case EST:
			ArrayList<Entity> nearEntities3 = Model.getNearEntities2((int) (e.getPosition().getX()) + 32,
					(int) (e.getPosition().getY()), 32, 32);
			for (Entity e1 : nearEntities3) {
				if (e1.getCategory() == c) {
					return true;
				}
			}
			break;
		case WEST:
			ArrayList<Entity> nearEntities4 = Model.getNearEntities2((int) (e.getPosition().getX()) - 32,
					(int) (e.getPosition().getY()) + 32, 32, 32);
			for (Entity e1 : nearEntities4) {
				if (e1.getCategory() == c) {
					return true;
				}
			}
			break;
		case NORTHWEST:
			break;
		case NORTHEST:
			break;
		}

		return false;
	}

	@Override
	public boolean closest(Entity e, Category c, Direction d) {
		// à faire
		// on peut utiliser getNearEntities pour le rayon_de_vision de l'entité et cela
		// détecte les entité présentes dedans mais faut voir pour les zones qu'on
		// détecte
		switch (d) {
		case NORTH:
			ArrayList<Entity> nearEntities = Model.getNearEntities2((int) (e.getPosition().getX()) - rayon_de_vision,
					(int) (e.getPosition().getY()) + rayon_de_vision, rayon_de_vision, rayon_de_vision);
			for (Entity e1 : nearEntities) {
				if (e1.getCategory() == c) {
					return true;
				}
			}
			break;
		case SOUTH:
			ArrayList<Entity> nearEntities2 = Model.getNearEntities2((int) (e.getPosition().getX()) - rayon_de_vision,
					(int) (e.getPosition().getY()) - rayon_de_vision, rayon_de_vision, rayon_de_vision);
			for (Entity e1 : nearEntities2) {
				if (e1.getCategory() == c) {
					return true;
				}
			}
			break;
		case EST:
			ArrayList<Entity> nearEntities3 = Model.getNearEntities2((int) (e.getPosition().getX()) - rayon_de_vision,
					(int) (e.getPosition().getY()) + rayon_de_vision, rayon_de_vision, rayon_de_vision);
			for (Entity e1 : nearEntities3) {
				if (e1.getCategory() == c) {
					return true;
				}
			}
			break;
		case WEST:
			ArrayList<Entity> nearEntities4 = Model.getNearEntities2((int) (e.getPosition().getX()) - rayon_de_vision,
					(int) (e.getPosition().getY()) + rayon_de_vision, rayon_de_vision, rayon_de_vision);
			for (Entity e1 : nearEntities4) {
				if (e1.getCategory() == c) {
					return true;
				}
			}
			break;
		case NORTHWEST:
			break;
		case NORTHEST:
			break;
		}

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

		switch (d) {
		case NORTH:
			break;
		case SOUTH:
			break;
		case EST:
			break;
		case WEST:
			break;

		case NORTHEST:
			RigidBody p = new RigidBody(e, 1, 5);
			p.getSpeed().setY(-120);
			p.getSpeed().setX(70);
			break;
		case NORTHWEST:
			RigidBody p1 = new RigidBody(e, 1, 5);
			p1.getSpeed().setY(-120);
			p1.getSpeed().setX(70);
			break;
		}

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

		RigidBody p = new RigidBody(e, 1, 5);
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
