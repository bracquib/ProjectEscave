package info3.game.automata;

import info3.game.entities.Entity;

public class GotStuff implements ICondition {

	@Override
	public boolean eval(Entity e) {
		return e.getBehaviour().gotStuff(e);
	}

}
