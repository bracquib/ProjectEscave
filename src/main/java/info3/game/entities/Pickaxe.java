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
		Vec2 mousePos = owner.mousePos;
		Block underCursor = Model.getBlock((int) mousePos.getX() / Block.SIZE, (int) mousePos.getY() / Block.SIZE);

		Vec2 playerPos = owner.getPosition();

		Direction orientation = owner.getPosition().orientation(owner.mousePos);
		switch (orientation) {
		case EST:
			owner.playAnimation("mine-right", 5, 200, -64, -64, false); // TODO: Remplacer les 0 par -32
			break;
		default:
			owner.playAnimation("mine-left", 5, 200, -64, -64, false); // TODO: Remplacer les 0 par -32
			break;
		}

		Block target = RayCasting.quadCast(mousePos, playerPos.add(Block.SIZE / 2), 3);

		if (target != null) { // && target == underCursor
			Vec2 coords = new Vec2(target.getPosition()).divide(Block.SIZE);
			Model.deleteBlock((int) coords.getX(), (int) coords.getY());

			if (target.id == 600) {
				if (owner.getInventory().coupleAt(2).getNumber() == 0) {
					Inventory inv = owner.getInventory();
					inv.pick(inv.toolAt(2));
					System.out.println("récupère de l'eau");
				}
			} else {
				Inventory inv = owner.getInventory();
				inv.pick(inv.toolAt(4));
			}
		}

		return true;
	}
}
