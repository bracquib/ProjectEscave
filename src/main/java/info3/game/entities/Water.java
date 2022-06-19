package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;

public class Water extends Consumable {

	private float waterValue;

	public Water(Controller c, Vec2 pos, Player owner) {
		super(c, pos, owner);
		this.setName("Water");
		this.waterValue = 10;
	}

	public Water(Controller c, Vec2 pos, Player owner, float waterVal) {
		super(c, pos, owner);
		this.setName("Water");
		this.waterValue = waterVal;
	}

	@Override
	public void useTool() {
		owner.water(waterValue);

	}

}
