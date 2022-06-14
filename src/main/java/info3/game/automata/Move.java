package info3.game.automata;

import info3.game.entities.Entity;

public class Move implements IAction {

	@Override
	public void apply(Entity e, Direction d) {
		 e.getBehaviour().move(e, d);
	}

}
