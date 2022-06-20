package info3.game.entities;

import info3.game.LocalController;
import info3.game.automata.Direction;

public abstract class Weapon extends Tool {

	public Weapon(LocalController c) {
		super(c);
		this.special = true;
	}

	public abstract void useTool(Direction d);

}
