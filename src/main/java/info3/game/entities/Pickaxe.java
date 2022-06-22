package info3.game.entities;

import info3.game.Inventory;
import info3.game.LocalController;
import info3.game.Model;
import info3.game.Vec2;
import info3.game.automata.Direction;
import info3.game.physics.RayCasting;

public class Pickaxe extends Weapon {
	int mobDmg = 25;
	int blockdmg = 1; // dmg à ajuster plus tard

	public Pickaxe(LocalController c, Player owner) {
		super(c, owner);
		this.setName("Pickaxe");
	}

	public boolean useTool(Direction d) { // pas owner de pickaxe car null
		// Behaviour behav = owner.getBehaviour();
		Vec2 mousePos = owner.mousePos;
		Block underCursor = Model.getBlock((int) mousePos.getX() / 32, (int) mousePos.getY() / 32);

		Vec2 playerPos = owner.getPosition();
		Vec2[] pp = new Vec2[4];
		pp[0] = playerPos.add(15);
		pp[1] = playerPos.add(new Vec2(15, 16));
		pp[2] = playerPos.add(new Vec2(16, 15));
		pp[3] = playerPos.add(16);

		for (int i = 0; i < 4; i++) {
			Block target = RayCasting.singleCast(mousePos, pp[i], 3);

			if (target != null && target == underCursor) {
				Vec2 coords = new Vec2(target.getPosition()).divide(32);
				Model.deleteBlock((int) coords.getX(), (int) coords.getY());
				Inventory inv = owner.getInventory();
				inv.pick(inv.toolAt(4));
			}
		}

		/*
		 * if (behav.cell(owner, d, Category.ADVERSAIRE)) {
		 * behav.ret.getBehaviour().protect(behav.ret, d, mobDmg); } else if
		 * (behav.cell(owner, d, Category.JUMPABLE)) {
		 * behav.ret.getBehaviour().wizz(behav.ret, d);
		 * 
		 * }
		 */
		return true;
	}
}
