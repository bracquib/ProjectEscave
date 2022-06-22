package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;
import info3.game.automata.Direction;

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
	public boolean useTool(Direction d) {
		System.out.println("satiété=" + owner.getThirstPoints());
		owner.water(waterValue);
		return true;
	}

}
