package info3.game.entities;

import java.util.ArrayList;

import info3.game.Inventory;
import info3.game.LocalController;
import info3.game.Model;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.automata.behaviors.Behaviour;

public class Sword extends Weapon {
	int mobDmg = 50;
	int blockDmg = 0; // dmg à ajuster plus tard

	public Sword(LocalController c, Player owner) {
		super(c, owner);
		this.setName("Sword");
	}

	public boolean useTool(Direction d) {

		owner.mousePos.print();
		Behaviour behav = owner.getBehaviour();
		Direction orientation = owner.getPosition().orientation(owner.mousePos);

		int decX, decY, width, height;

		switch (orientation) {
		case NORTH:
			decX = -32;
			decY = -64;
			width = 96;
			height = 64;
			break;
		case SOUTH:
			decX = -32;
			decY = -32;
			width = 96;
			height = 64;
			break;
		case EST:
			decX = 32;
			decY = -32;
			width = 64;
			height = 96;
			break;
		case WEST:
			decX = -64;
			decY = -32;
			width = 64;
			height = 96;
			break;
		default:
			decX = 0;
			decY = 0;
			width = 32;
			height = 32;
			break;
		}

		System.out.println(orientation);
		ArrayList<Entity> nearEntities = Model.getNearEntities((int) (owner.getPosition().getX()) + decX,
				(int) (owner.getPosition().getY()) + decY, width, height);
		for (Entity e1 : nearEntities) {
			if (e1.getCategory() == Category.ADVERSAIRE) {
				if (e1.pointsDeVie - mobDmg <= 0) {
					Inventory inv = owner.getInventory();
					inv.pick(inv.toolAt(3));
				}
				e1.getBehaviour().protect(e1, orientation, mobDmg);
				return true;
			}
		}

		return false;
		/*
		 * else if (behav.cell(owner, d, Category.JUMPABLE)) {
		 * behav.ret.getBehaviour().wizz(behav.ret, d);
		 */
		// non appelé car épée ne peut pas casser de blocs
		// (et plus pratique sinon changer wizz par protect...
	}

}
