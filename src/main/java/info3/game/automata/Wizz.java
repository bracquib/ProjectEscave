package info3.game.automata;

import info3.game.entities.Entity;

public class Wizz implements IAction {
	
	Direction d; 
	public Wizz(Direction dir) {
		this.d = dir;
	}
	
	@Override
	public void apply(Entity e) { //d parfois inutile
		e.getBehaviour().wizz(e, d);
	}
}
