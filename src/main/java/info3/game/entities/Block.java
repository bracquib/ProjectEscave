package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;
import info3.game.assets.Image;

public class Block extends Entity {
	public Block(LocalController c, Vec2 position) {
		super(c);
		this.position = position;
		this.avatar = this.controller.createAvatar(this.position, new Image("block.png"));
	}
}
