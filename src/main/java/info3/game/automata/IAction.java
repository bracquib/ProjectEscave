package info3.game.automata;

import info3.game.entities.Entity;

public interface IAction {
	
	public boolean apply(Entity e, Direction d);
	
	//wait
	void drop(); //throw du parser
	
	void get();
	
	void protect(Direction d);
}
