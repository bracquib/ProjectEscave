package info3.game.entities;

import info3.game.LocalController;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.automata.behaviors.Behaviour;

public class Sword extends Weapon {
	int mobDmg = 50;
	int blockDmg = 0; // dmg à ajuster plus tard

	public Sword(LocalController c) {
		super(c);
		this.setName("Sword");
	}

	public void useTool(Direction d) {
		Behaviour behav = owner.getBehaviour();
		if (behav.cell(owner, d, Category.ADVERSAIRE)) {
			behav.ret.getBehaviour().protect(behav.ret, d, mobDmg);
		}
		/*
		 * else if (behav.cell(owner, d, Category.JUMPABLE)) {
		 * behav.ret.getBehaviour().wizz(behav.ret, d);
		 */
		// non appelé car épée ne peut pas casser de blocs
		// (et plus pratique sinon changer wizz par protect...
	}

}
