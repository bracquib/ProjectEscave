package info3.game.automata;

import info3.game.entities.Entity;

public class Egg implements IAction {

	@Override
	public void apply(Entity e) {
		e.getBehaviour().egg(e);
	}

}
