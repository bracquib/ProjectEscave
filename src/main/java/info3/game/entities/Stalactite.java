package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;

public class Stalactite extends Entity {

	public Stalactite(Controller c, Vec2 position, int points) {
		super(c, points);
		this.position = position;
		this.avatar = this.controller.createAvatar(this.position, "stalactite.png", 1, 0);
	}
}