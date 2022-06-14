package info3.game.automata;

import info3.game.automata.ast.Behaviour;
import info3.game.entities.Entity;

public class Cell implements ICondition {
	Direction d;
	Category c;

	public void Cell(Direction d, Category c) {
		this.c = c;
		this.d = d;
	}

	public boolean eval(Entity e) {
		return Behaviour.Cell(d, c);
	}
}
