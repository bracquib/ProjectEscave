package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;

public class Water extends Consumable {

	public Water(Controller c, Vec2 pos) {
		super(c, pos);
		this.setName("Water");
	}

	@Override
	public void useTool() {
		// TODO Auto-generated method stub

	}

}
