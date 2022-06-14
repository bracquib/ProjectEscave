package info3.game.automata;

import info3.game.entities.Entity;

public class Get implements IAction {

	@Override
	public void apply(Entity e, Direction d) {
		e.getBehaviour().get(e);
	}

}
