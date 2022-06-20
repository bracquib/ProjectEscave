package info3.game.automata;

import info3.game.entities.Entity;
import info3.game.physics.RigidBody;

public class StatueBehaviour extends Behaviour {

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

	/*
	 * @Override public boolean cell(Entity e, Direction d, Category c) { switch (d)
	 * { case NORTH: ArrayList<Entity> nearEntities = Model.getNearEntities2((int)
	 * (e.getPosition().getX()), (int) (e.getPosition().getY()) + 32, 32, 32); for
	 * (Entity e1 : nearEntities) { if (e1.getCategory() == c) { return true; } }
	 * break; case SOUTH: ArrayList<Entity> nearEntities2 =
	 * Model.getNearEntities2((int) (e.getPosition().getX()), (int)
	 * (e.getPosition().getY()) - 32, 32, 32); for (Entity e1 : nearEntities2) { if
	 * (e1.getCategory() == c) { return true; } } break; case EST: ArrayList<Entity>
	 * nearEntities3 = Model.getNearEntities2((int) (e.getPosition().getX()) + 32,
	 * (int) (e.getPosition().getY()), 32, 32); for (Entity e1 : nearEntities3) { if
	 * (e1.getCategory() == c) { return true; } } break; case WEST:
	 * ArrayList<Entity> nearEntities4 = Model.getNearEntities2((int)
	 * (e.getPosition().getX()) - 32, (int) (e.getPosition().getY()) + 32, 32, 32);
	 * for (Entity e1 : nearEntities4) { if (e1.getCategory() == c) { return true; }
	 * } break; case NORTHWEST: break; case NORTHEST: break; }
	 * 
	 * return false; }
	 */

	@Override
	public boolean closest(Entity e, Category c, Direction d, int diam_vision) {
		// pas besoin
		return false;
	}

	@Override
	public boolean gotPower(Entity e) {
		// pas besoin
		return false;
	}

	@Override
	public boolean gotStuff(Entity e) {
		// pas besoin
		return false;
	}

	@Override
	public void wizz(Entity e, Direction d) {
		// activer la statue et le il y a un transfert d'automate

		e.setAutomata(e.getController().model.getAutomata("Player"));
	}

	@Override
	public void pop(Entity e, Direction d) {
		// pas besoin

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
	public void protect(Entity e, Direction d, int dmg) {

		e.setAutomata(e.getController().model.getAutomata("Statue"));
		return;

	}

	@Override
	public void move(Entity e) {
		// pas besoin

	}

	@Override
	public void jump(Entity e) {
		((RigidBody) e).getSpeed().setY(-250);
		return;
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
		// TODO Auto-generated method stub

	}

}
