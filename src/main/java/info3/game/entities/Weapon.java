package info3.game.entities;

import info3.game.Controller;

public abstract class Weapon extends Tool {

	public Weapon(Controller c) {
		super(c);
		this.special = true;
	}

	public void useTool() {

	}

}
