package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;
import info3.game.assets.Image;

public class Statue extends Entity {

	public Statue(LocalController c, Vec2 position, int points) {
		super(c, points);
		this.position = position;
		this.avatar = this.controller.createAvatar(this.position, new Image("statue.png"));
	}
}