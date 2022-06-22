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

	public Block(LocalController c, Player owner) {
		super(c, owner);
		this.setCategory(Category.JUMPABLE);
		this.setName("Block");

	}

	@Override
	public boolean useTool(Direction d) {
		// Behaviour behav = owner.getBehaviour();

		Vec2 mousePos = owner.mousePos;
		int xBP = (int) (owner.getPosition().getX() / 32); // coord en block du joueur
		int yBP = (int) (owner.getPosition().getY() / 32);
		if (mousePos != null) {
			int i = ((int) mousePos.getX() / 32);
			int j = ((int) mousePos.getY() / 32);
			Block place = Model.getBlock(i, j);
			if (!(i == xBP & j == yBP) & i >= xBP - 2 & i <= xBP + 2 & j >= yBP - 2 & j <= yBP + 2) {
				if (place == null) {
					Model.getMap()[i][j] = new Block(Model.controller, new Vec2(i * 32, j * 32), 1, 1);
					return true;
				}
			}
			return false;

		} else {
			int decX, decY;
			switch (d) {
			case NORTH:
				decX = 0;
				decY = -1;
				break;
			case SOUTH:
				decX = 0;
				decY = 1;
				break;
			case EST:
				decX = 1;
				decY = 0;
				break;
			case WEST:
				decX = -1;
				decY = 0;
				break;
			default:
				decX = decY = 0;
			}
			Vec2 pos = owner.getPosition(); // pas owner car celui du bloc est null,
			// mais bien celui en paramètre, player possédant l'inventaire
			int i = ((int) pos.getX() / 32) + decX;
			int j = ((int) pos.getY() / 32) + decY;
			Block place = Model.getBlock(i, j);
			if (place == null) {
				Model.getMap()[i][j] = new Block(Model.controller, new Vec2(i * 32, j * 32), 1, 1);
				return true;
			}
			return false;
		}
	}
}
