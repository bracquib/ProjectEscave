package info3.game.automata;

import info3.game.entities.Entity;

public class Cell implements ICondition {
	Direction d;
	Category c;

	public void cell(Direction d, Category c) {
		this.d = d;
		this.c = c;
	}

	@Override
	public boolean eval(Entity e) {
		return e.getBehaviour().cell(e, d, c);

	}
}
