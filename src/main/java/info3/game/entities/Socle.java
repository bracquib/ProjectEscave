package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;

public class Socle extends Entity {

	public Socle(Controller c, Vec2 position, int points) {
		super(c, points);
		this.position = position;
		this.avatar = this.controller.createAvatar(this.position, "socle.png", 1, 0);
	}
}
