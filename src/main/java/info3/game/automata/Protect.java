package info3.game.automata;

import info3.game.entities.Entity;

public class Protect implements IAction {

	Direction d;

	public Protect(Direction dir) {
		this.d = dir;
	}

	@Override
	public void apply(Entity e) {
		e.getBehaviour().protect(e, d);
	}

	public String toString() {
		return "Protect(" + d.name() + ")";
	}

}
