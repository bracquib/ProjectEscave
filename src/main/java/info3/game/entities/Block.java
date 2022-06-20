package info3.game.entities;

import info3.game.LocalController;
import info3.game.Model;
import info3.game.Vec2;
import info3.game.assets.Image;
import info3.game.automata.Category;
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
	public void useTool() {
		// TODO placer un bloc sur la carte

	}
}
