package info3.game.automata.behaviors;

import info3.game.Model;
import info3.game.Vec2;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.entities.Entity;
import info3.game.entities.Mushroom;
import info3.game.physics.RigidBody;

public class MushroomBehaviour extends Behaviour {

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
		// pas besoin
		return false;
	}

	@Override
	public void wizz(Entity e, Direction d) {
		// wizz=jump

		switch (d) {
		case NORTHEST:
			RigidBody p = (RigidBody) e;
			// new RigidBody(e, 1, 5);
			p.getSpeed().setY(-270);
			p.getSpeed().setX(150);
			break;
		case NORTHWEST:
			RigidBody p1 = (RigidBody) e;
			// new RigidBody(e, 1, 5);
			p1.getSpeed().setY(-270);
			p1.getSpeed().setX(150);
			break;
		default:
			break;
		}

		return;
	}

	@Override
	public void pop(Entity e, Direction d) {
		// pop = hit
		if (cell(e, Direction.HERE, Category.PLAYER)) {
			System.out.println("Mushroom hit player?");
			super.ret.getBehaviour().protect(ret, d, e.degatMob);
		}
		return;

	}

	@Override
	public void move(Entity e, Direction d) {

		RigidBody p = (RigidBody) e;
		// new RigidBody(e, 1, 5);
		switch (d) {
		case EST:
			p.getSpeed().setX(50);
			break;
		case WEST:
			p.getSpeed().setX(-50);
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
	public void egg(Entity e, Direction d) {
		Mushroom pere = (Mushroom) e;
		if (pere.childRemain > 0) {
			int decX;
			switch (d) {
			case EST:
				decX = 48;
				break;
			case WEST:
				decX = -48;
				break;
			default:
				decX = 0;
				break;
			}
			Vec2 childPos = new Vec2(pere.getPosition().getX() + decX, pere.getPosition().getY());
			Mushroom child = new Mushroom(pere.getController(), childPos, 100, pere.childRemain - 1);
			// pts de vie?
			Model.spawn(child);
		}
	}

}
