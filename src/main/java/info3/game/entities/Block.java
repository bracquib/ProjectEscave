package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;
import info3.game.assets.Image;
import info3.game.cavegenerator.BlockIDs;

public class Block extends Entity {
	public Block(LocalController c, Vec2 position, int id) {
		super(c);
		this.position = position;
		Vec2 offset = BlockIDs.IDsToVec2.getOrDefault(id, new Vec2(0, 0)).multiply(-32);
		this.avatar = this.controller.createAvatar(this.position.add(offset),
				new Image("classic_block/" + BlockIDs.IDs.get(id) + ".png"));
	}
}
