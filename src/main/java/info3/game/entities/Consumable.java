package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;

public abstract class Consumable extends Tool {

	public Consumable(Controller c, Vec2 pos) {
		super(c);
		this.position = pos;
		this.special = false;
		// TODO Auto-generated constructor stub
	}

	public abstract void useTool();

}
