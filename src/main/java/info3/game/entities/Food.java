package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;

public class Food extends Consumable {

	private float feedValue;

	public Food(LocalController c, Vec2 pos, Player owner) {
		super(c, pos, owner);
		this.setName("Food");
		this.feedValue = 10;
	}

	public Food(LocalController c, Vec2 pos, Player owner, int feedVal) {
		super(c, pos, owner);
		this.setName("Food");
		this.feedValue = feedVal;
	}

	@Override
	public void useTool() {
		owner.feed(feedValue);
	}

}
