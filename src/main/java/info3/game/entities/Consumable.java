package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;

public abstract class Consumable extends Tool {

	public Consumable(LocalController c, Vec2 pos, Player owner) {
		super(c);
		this.position = pos;
		this.owner = owner;
		this.special = false;

	}

	public abstract void useTool();

}
