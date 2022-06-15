package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;
import info3.game.physics.RigidBody;

public class Mushroom extends RigidBody {

	public Mushroom(Controller c, Vec2 pos, boolean local, int points) {
		super(1, c, points);
		this.setPosition(pos);
		if (local) {
			this.avatar = this.controller.createAvatar(new Vec2(this.getPosition()), this.avatarPath(), 1, 0);
		}
	}

	@Override
	public void tick(long el) {
		super.tick(el);
	}

	private String avatarPath() {
		return "mushroom" + ".png";
	}
}
