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
		String[] items = { "pioche", "sword", "gourde_pleine", "comestibles",
				"classic_block/player_" + this.player.name().toLowerCase() };

		int i = 0;
		this.inventoryItems = new Avatar[Inventory.INVENTORY_SIZE];
		for (String item : items) {
			Image img = new Image(item + ".png");
			img.fixed = true;
			img.layer = 2;
			Vec2 pos = new Vec2(startX + 74 * i, startY);
			// miam le code sale
			if (i == 2) {
				pos.y -= 2;
			}
			if (i == 4) {
				pos = pos.add(16);
			}
			this.inventoryItems[i] = this.controller.createAvatar(pos, new Vec2(1), img);

			i++;
		}
	}

	public void updateAvatars() {
		int totalWidth = 74 * Inventory.INVENTORY_SIZE - 10;
		int startX = (this.controller.viewFor(this.player.getColor()).getWidth() - totalWidth) / 2;
		int startY = this.controller.viewFor(this.player.getColor()).getHeight() - 130;
		for (int i = 0; i < this.inventoryCells.length; i++) {
			this.controller.updateAvatar(this.inventoryCells[i].getId(), new Vec2(startX + 74 * i, startY));
		}
	}

	public void unselect(int idx) {
		this.controller.updatePaintable(this.inventoryCells[idx],
				this.inventoryCells[idx].image.duplicateFromPath("inventory-cell.png"));
	}

	public void select(int idx) {
		this.controller.updatePaintable(this.inventoryCells[idx],
				this.inventoryCells[idx].image.duplicateFromPath("inventory-cell-selected.png"));
	}
}
