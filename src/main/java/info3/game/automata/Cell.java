package info3.game.automata;

import info3.game.entities.Entity;

public class Cell implements ICondition {
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

		return e.getBehaviour().cell(e, d, c);
	}

	@Override
	public boolean eval(Entity e, Category c, Direction d) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean eval(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}
}
