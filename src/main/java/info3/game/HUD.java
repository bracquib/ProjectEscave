package info3.game;

import info3.game.assets.Image;
import info3.game.entities.Player;

public class HUD {
	private Avatar[] inventoryCells;
	private Avatar[] inventoryItems;
	private Player player;
	private LocalController controller;

	public HUD(LocalController c, Player player) {
		this.player = player;
		this.controller = c;
		this.inventoryCells = new Avatar[Inventory.INVENTORY_SIZE];

		int totalWidth = 74 * Inventory.INVENTORY_SIZE - 10;
		int startX = (this.controller.viewFor(this.player.getColor()).getWidth() - totalWidth) / 2;
		int startY = this.controller.viewFor(this.player.getColor()).getHeight() - 130;
		Image selectedCell = new Image("inventory-cell-selected.png");
		selectedCell.layer = 1;
		selectedCell.fixed = true;
		this.inventoryCells[0] = this.controller.createAvatar(new Vec2(startX, startY), new Vec2(1), selectedCell);
		for (int i = 1; i < this.inventoryCells.length; i++) {
			Image cell = new Image("inventory-cell.png");
			cell.layer = 1;
			cell.fixed = true;
			this.inventoryCells[i] = this.controller.createAvatar(new Vec2(startX + 74 * i, startY), new Vec2(1), cell);
		}
	}
}
