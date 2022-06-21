package info3.game.automata.behaviors;

import java.util.ArrayList;

import info3.game.Model;
import info3.game.Vec2;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.entities.Entity;
import info3.game.physics.RigidBody;

public class StalactiteBehaviour extends Behaviour {

	Entity ret;

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
		// pas besoin
		return false;
	}

	@Override
	public boolean closest(Entity e, Category c, Direction d, int diam_vision) {
		int diam = 480; // en pixel
		return super.closest(e, c, d, diam);

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
		// wizz=tomber
		RigidBody p = (RigidBody) e;
		p.addSpeed(new Vec2(0, 0));
	}

	@Override
	public void pop(Entity e, Direction d) {
		// pop=exploser

		ArrayList<Entity> nearEntities = Model.getNearEntities((int) (e.getPosition().getX()) - 32,
				(int) (e.getPosition().getY()) - 32, 96, 96);
		for (Entity e1 : nearEntities) {
			Category cat = e1.getCategory();
			if (cat == Category.PLAYER || cat == Category.ADVERSAIRE) {
				e1.getBehaviour().protect(e1, d, (int) ((RigidBody) e).getSpeed().getY() / 2);
			} else if (e1.getCategory() == Category.JUMPABLE) {
				e1.getBehaviour().wizz(ret, d);
			}
		}
		RigidBody p = (RigidBody) e;
		Model.deleteentities(p);
		return;
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
