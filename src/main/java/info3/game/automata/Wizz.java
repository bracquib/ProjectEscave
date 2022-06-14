package info3.game.automata;

import info3.game.entities.Entity;

public class Wizz implements IAction {
	
	@Override
	public void apply(Entity e, Direction d) { //d parfois inutile
		e.getBehaviour().wizz(e, d);
	}
}
