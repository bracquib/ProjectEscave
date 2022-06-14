package info3.game.automata;

import info3.game.entities.Entity;

public class GotPower implements ICondition {

	@Override
	public boolean eval(Entity e) {
		return e.getBehaviour().gotPower(e);
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
