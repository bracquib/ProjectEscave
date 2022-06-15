package info3.game.automata;

import info3.game.entities.Entity;

public class MyDir implements ICondition {

	Direction d;

	public void myDir(Direction d) {
		this.d = d;
	}

	@Override
	public boolean eval(Entity e) {
		return e.getBehaviour().myDir(e, d);

	}

}
