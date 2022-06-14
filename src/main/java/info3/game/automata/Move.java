package info3.game.automata;

import info3.game.entities.Entity;

public class Move implements IAction {

	@Override
	public boolean apply(Entity e, Direction d) {
		return e.behaviour.move(d);
	}

}
