package info3.game.automata;

import info3.game.automata.ast.Behaviour;
import info3.game.entities.Entity;

public class MyDir implements ICondition {
	Direction d;

	public void MyDir(Direction d) {
		this.d = d;
	}

	public boolean eval(Entity e) {
		return Behaviour.MyDir(d);
	}
}
