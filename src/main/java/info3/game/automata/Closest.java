package info3.game.automata;

import info3.game.entities.Entity;

public class Closest implements ICondition {

	@Override
	public boolean eval(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eval(Entity e, Direction d) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eval(Entity e, int touche) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eval(Entity e, Direction d, Category c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eval(Entity e, Category c, Direction d) {
		return e.getBehaviour().closest(e, c, d);
	}

}
