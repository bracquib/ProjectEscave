package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;

public class Water extends Consumable {

	private float waterValue;

	public Water(LocalController c, Player owner) {
		super(c, owner);
		this.setName("Water");
		this.waterValue = 10;
	}

	public Water(LocalController c, Vec2 pos, Player owner) {
		super(c, owner);
		this.setName("Water");
		this.position = pos;
		this.waterValue = 10;
	}

	public Water(LocalController c, Vec2 pos, Player owner, float waterVal) {
		super(c, owner);
		this.setName("Water");
		this.position = pos;
		this.waterValue = waterVal;
	}

	@Override
	public void useTool() {
		owner.water(waterValue);

	}

}
