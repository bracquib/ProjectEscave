package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;

public class Food extends Consumable {

	public Food(Controller c, Vec2 pos) {
		super(c, pos);
		this.setName("Food");
	}

	@Override
	public void useTool() {
		// TODO Auto-generated method stub

	}

}
