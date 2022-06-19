package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;
import info3.game.assets.Image;
import info3.game.automata.Category;
import info3.game.physics.RigidBody;

public class Mushroom extends RigidBody {

	public Mushroom(LocalController c, Vec2 pos, boolean local, int points) {
		super(1, c, points);
		this.setPosition(pos);
		this.setCategory(Category.ADVERSAIRE);
		if (local) {
			this.avatar = this.controller.createAvatar(new Vec2(this.getPosition()), new Image(this.avatarPath()));
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
