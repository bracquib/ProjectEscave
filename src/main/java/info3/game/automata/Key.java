package info3.game.automata;

import info3.game.entities.Entity;

public class Key implements ICondition {

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
		return e.getBehaviour().key(e, touche);
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
