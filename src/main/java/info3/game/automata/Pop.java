package info3.game.automata;

import info3.game.entities.Entity;

public class Pop implements IAction {

	@Override
	public boolean apply(Entity e, Direction d) { //d  inutile
		return e.behaviour.pop());
	}

}
