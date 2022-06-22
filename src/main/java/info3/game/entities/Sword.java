package info3.game.entities;

import info3.game.Inventory;
import info3.game.LocalController;
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
		Behaviour behav = owner.getBehaviour();
		if (behav.cell(owner, d, Category.ADVERSAIRE)) {
			if (behav.ret.pointsDeVie - mobDmg <= 0) {
				Inventory inv = owner.getInventory();
				inv.pick(inv.toolAt(3));
			}
			behav.ret.getBehaviour().protect(behav.ret, d, mobDmg);

		}
		return true;
		/*
		 * else if (behav.cell(owner, d, Category.JUMPABLE)) {
		 * behav.ret.getBehaviour().wizz(behav.ret, d);
		 */
		// non appelé car épée ne peut pas casser de blocs
		// (et plus pratique sinon changer wizz par protect...
	}

}
