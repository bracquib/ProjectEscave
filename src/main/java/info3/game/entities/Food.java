package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;

public class Food extends Consumable {

	private float feedValue;

	public Food(Controller c, Vec2 pos, Player owner) {
		super(c, pos, owner);
		this.setName("Food");
		this.feedValue = 10;
	}

	public Food(Controller c, Vec2 pos, Player owner, int feedVal) {
		super(c, pos, owner);
		this.setName("Food");
		this.feedValue = feedVal;
	}

	@Override
	public void useTool() {
		owner.feed(feedValue);
	}

}
