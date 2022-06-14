package info3.game.automata;

import info3.game.entities.Entity;

public class True implements ICondition {

	@Override
	public boolean eval(Entity e) {
		return e.getBehaviour().true_(e);
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
		// TODO Auto-generated method stub
		return false;
	}

}
