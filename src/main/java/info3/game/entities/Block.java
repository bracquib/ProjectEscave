package info3.game.entities;

import info3.game.LocalController;
import info3.game.Model;
import info3.game.Vec2;
import info3.game.assets.Image;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.automata.behaviors.BlockBehaviour;
import info3.game.cavegenerator.BlockIDs;

//public class Block extends Consumable {
//	public Block(LocalController c, Vec2 position, int id) {
//		super(c, position, null);

public class Block extends Consumable {
	public Block(LocalController c, Vec2 position, int id, int points) {
		super(c, null);
		this.pointsDeVie = points;
		this.position = position;
		this.setCategory(Category.JUMPABLE);
		this.setAutomata(Model.getAutomata("Block"));
		this.setBehaviour(new BlockBehaviour());

		Vec2 offset = BlockIDs.IDsToVec2.getOrDefault(id, new Vec2(0, 0)).multiply(-32);
		this.avatar = this.controller.createAvatar(this.position.add(offset),
				new Image("classic_block/" + BlockIDs.IDs.get(id) + ".png"));
		this.setName("Block");
	}

	public Block(LocalController c) {
		super(c, null);
		this.setCategory(Category.JUMPABLE);
		this.setName("Block");
	}

	@Override
	public void useTool(Direction d) {
		// Behaviour behav = owner.getBehaviour();
		int decX, decY;
		switch (d) {
		case NORTH:
			decX = 0;
			decY = -32;
			break;
		case SOUTH:
			decX = 0;
			decY = 32;
			break;
		case EST:
			decX = 32;
			decY = 0;
			break;
		case WEST:
			decX = -32;
			decY = 0;
			break;
		default:
			decX = decY = 0;
		}
		Vec2 pos = owner.getPosition();
		int i = ((int) pos.getX() / 32) + decX;
		int j = ((int) pos.getY() / 32) + decY;
		Block place = Model.getBlock(i, j);
		if (place == null) {
			Model.getMap()[i][j] = new Block(Model.controller, new Vec2(i * 32, j * 32), 1, 1);
			// TODO placer un bloc sur la carte
		}
	}
}
