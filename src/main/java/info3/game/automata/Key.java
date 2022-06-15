package info3.game.automata;

import info3.game.entities.Entity;

public class Key implements ICondition {

	Integer touche;

	public void key(Integer touche) {
		this.touche = touche;
	}

	@Override
	public boolean eval(Entity e) {
		return e.getBehaviour().key(e, touche);

	}

}
