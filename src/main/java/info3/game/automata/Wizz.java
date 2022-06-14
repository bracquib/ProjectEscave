package info3.game.automata;

import info3.game.entities.Entity;

public class Wizz implements IAction {
	
	@Override
	boolean apply(Entity e, Direction d) { //d parfois inutile
		return e.behaviour.wizz(d);
	}
}
