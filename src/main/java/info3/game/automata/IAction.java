package info3.game.automata;

import info3.game.entities.Entity;

public interface IAction {
	
	public void apply(Entity e, Direction d); //boolean? return?
	//wait
}
