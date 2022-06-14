package info3.game.automata;

import info3.game.entities.Entity;

public class Jump implements IAction {

	@Override
	public void apply(Entity e, Direction d) {
		e.getBehaviour().jump(e);
	}
}
