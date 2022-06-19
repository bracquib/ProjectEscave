package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;
import info3.game.assets.Image;
import info3.game.automata.Category;

public class Stalactite extends Entity {

	public Stalactite(LocalController c, Vec2 position, int points) {
		super(c, points);
		this.position = position;
		this.setCategory(Category.SOMETHING);
		this.avatar = this.controller.createAvatar(this.position, new Image("stalactite.png"));
	}
}