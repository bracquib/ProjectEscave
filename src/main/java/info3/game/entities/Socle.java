package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;
import info3.game.automata.Category;

public class Socle extends Entity {

	public Socle(Controller c, Vec2 position, int points) {
		super(c, points);
		this.setCategory(Category.JUMPABLE);
		this.position = position;
		this.avatar = this.controller.createAvatar(this.position, "socle.png", 1, 0);
	}
}
