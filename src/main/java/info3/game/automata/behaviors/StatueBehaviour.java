package info3.game.automata.behaviors;

import info3.game.Model;
import info3.game.automata.Category;
import info3.game.automata.Direction;
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
		e.setCategory(Category.PLAYER);
		e.setAutomata(Model.getAutomata("Player"));
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

		e.setAutomata(Model.getAutomata("Statue"));
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
