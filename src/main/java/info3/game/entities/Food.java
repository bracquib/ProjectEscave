package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;
import info3.game.automata.Direction;

public class Food extends Consumable {

	private float feedValue;

	public Food(LocalController c, Player owner) {
		super(c, owner);
		this.setName("Food");
		this.feedValue = 10;
	}

	public Food(LocalController c, Vec2 pos, Player owner) {
		super(c, owner);
		this.setName("Food");
		this.position = pos;
		this.feedValue = 10;
	}

	public Food(LocalController c, Vec2 pos, Player owner, int feedVal) {
		super(c, owner);
		this.setName("Food");
		this.position = pos;
		this.feedValue = feedVal;
	}

	@Override
	public void useTool(Direction d) {
		owner.feed(feedValue);
	}

}
