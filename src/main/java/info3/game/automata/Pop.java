package info3.game.automata;

import info3.game.entities.Entity;

public class Pop implements IAction {

	@Override
	public void apply(Entity e, Direction d) { //d  inutile
		e.getBehaviour().pop(e, d); //dinutilisé
	}

}
