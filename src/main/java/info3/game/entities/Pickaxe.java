package info3.game.entities;

import info3.game.LocalController;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.automata.behaviors.Behaviour;

public class Pickaxe extends Weapon {
	int mobDmg = 25;
	int blockdmg = 1; // dmg à ajuster plus tard

	public Pickaxe(LocalController c) {
		super(c);
		this.setName("Pickaxe");
	}

	public void useTool(Direction d) {
		Behaviour behav = owner.getBehaviour();
		if (behav.cell(owner, d, Category.ADVERSAIRE)) {
			behav.ret.getBehaviour().protect(behav.ret, d, mobDmg);
		} else if (behav.cell(owner, d, Category.JUMPABLE)) {
			behav.ret.getBehaviour().wizz(behav.ret, d);
		}
	}
}
