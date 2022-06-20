package info3.game.automata;

import info3.game.entities.Entity;

public class Throw implements IAction {

	@Override
	public void apply(Entity e) {
		e.getBehaviour().throw_(e); // d?
	}

	public String toString() {
		return "Throw()";
	}

}
