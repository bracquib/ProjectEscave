package info3.game.automata;

import info3.game.automata.ast.Behaviour;
import info3.game.entities.Entity;

public class Closest implements ICondition {
	Direction d;
	Category c;

	public void Closest(Category c, Direction d) {
		this.c = c;
		this.d = d;
	}

	public boolean eval(Entity e) {
		return Behaviour.Closest(c, d);
	}
}
