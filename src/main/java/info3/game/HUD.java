package info3.game;

import info3.game.assets.Image;
import info3.game.assets.Label;
import info3.game.entities.Player;

public class HUD {
	private Avatar[] inventoryCells;
	private Avatar[] inventoryItems;
	private Player player;
	private LocalController controller;
	private Avatar[] lifes;
	private Avatar[] food;
	private Avatar[] water;
	private Avatar[] labels;

	public HUD(LocalController c, Player player) {
		this.player = player;
		this.controller = c;
		this.inventoryCells = new Avatar[Inventory.INVENTORY_SIZE];

		int totalWidth = 74 * Inventory.INVENTORY_SIZE - 10;
		int startX = (this.controller.viewFor(this.player.getColor()).getWidth() - totalWidth) / 2;
		int startY = this.startY();
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
		this.labels = new Avatar[Inventory.INVENTORY_SIZE];
		for (String item : items) {
			Image img = new Image(item + ".png");
			img.fixed = true;
			img.layer = 2;
			Vec2 pos = new Vec2(startX + 74 * i, startY);
			int count = player.getInventory().coupleAt(i).getNumber();
			this.labels[i] = this.controller.createAvatar(pos.add(new Vec2(40, 60)),
					new Label(Integer.toString(count)));
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

		this.lifes = new Avatar[player.getPointsDeVie()];
		Image heart = new Image("barre_vie.png");
		heart.fixed = true;
		heart.layer = 2;
		startY -= 54;
		for (int j = 0; j < this.lifes.length; j++) {
			this.lifes[j] = this.controller.createAvatar(new Vec2(startX + 32 * j, startY), heart, false, new Vec2(1));
		}

		this.food = new Avatar[player.getHungerPoints()];
		Image foodImg = new Image("barre_nourriture.png");
		foodImg.fixed = true;
		foodImg.layer = 2;
		startY -= 32;
		for (int j = 0; j < this.food.length; j++) {
			this.food[j] = this.controller.createAvatar(new Vec2(startX + 32 * j, startY), foodImg, false, new Vec2(1));
		}

		this.water = new Avatar[player.getThirstPoints()];
		Image waterImg = new Image("barre_eau.png");
		waterImg.fixed = true;
		waterImg.layer = 2;
		startY -= 32;
		for (int j = 0; j < this.water.length; j++) {
			this.water[j] = this.controller.createAvatar(new Vec2(startX + 32 * j, startY), waterImg, false,
					new Vec2(1));
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

	private void moveAvatar(Avatar[] collection, int idx, float newY) {
		float currentX = collection[idx].getPosition().getX();
		this.controller.updateAvatar(collection[idx].getId(), new Vec2(currentX, newY));
	}

	public void loseLife(int newVal) {
		this.moveAvatar(this.lifes, newVal, -10000);
	}

	public void loseFood(int newVal) {
		this.moveAvatar(this.food, newVal, -10000);
	}

	public void loseWater(int newVal) {
		this.moveAvatar(this.water, newVal, -10000);
	}

	public void gainLife(int newVal) {
		this.moveAvatar(this.lifes, newVal, this.startY() - 184);
	}

	public void gainFood(int newVal) {
		this.moveAvatar(this.food, newVal, this.startY() - 226);
	}

	public void gainWater(int newVal) {
		this.moveAvatar(this.water, newVal, this.startY() - 268);
	}

	private int startY() {
		return this.controller.viewFor(this.player.getColor()).getHeight() - 130;
	}

	public void setCounter(int idx, int cpt) {
		this.controller.updatePaintable(this.labels[idx], new Label(Integer.toString(cpt)));
	}
}
