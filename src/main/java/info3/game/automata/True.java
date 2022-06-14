package info3.game.automata;

import info3.game.automata.ast.Behaviour;
import info3.game.entities.Entity;

public class True implements ICondition {

	public boolean eval(Entity e) {
		return Behaviour.True();
	}
}
