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
		this.inventoryCells[0] = new AvatarBuilder(selectedCell).position(new Vec2(startX, startY)).scale(new Vec2(1))
				.layer(1).fixed().build(this.controller);
		for (int i = 1; i < this.inventoryCells.length; i++) {
			Image cell = new Image("inventory-cell.png");
			this.inventoryCells[i] = new AvatarBuilder(cell).position(new Vec2(startX + 74 * i, startY))
					.scale(new Vec2(1)).layer(1).fixed().build(this.controller);
		}
		String[] items = { "pioche", "sword", "gourde_pleine", "comestibles",
				"classic_block/player_" + this.player.name().toLowerCase() };

		int i = 0;
		this.inventoryItems = new Avatar[Inventory.INVENTORY_SIZE];
		this.labels = new Avatar[Inventory.INVENTORY_SIZE];
		for (String item : items) {
			Image img = new Image(item + ".png");
			Vec2 pos = new Vec2(startX + 74 * i, startY);
			int count = player.getInventory().coupleAt(i).getNumber();
			this.labels[i] = new AvatarBuilder(new Label(Integer.toString(count))).position(pos.add(new Vec2(40, 60)))
					.fixed().layer(3).build(this.controller);
			// miam le code sale
			if (i == 2) {
				pos.y -= 2;
			}
			if (i == 4) {
				pos = pos.add(16);
			}
			this.inventoryItems[i] = new AvatarBuilder(img).position(pos).scale(new Vec2(1)).fixed(true).layer(2)
					.build(this.controller);

			i++;
		}

		this.lifes = new Avatar[player.getPointsDeVie()];
		Image heart = new Image("barre_vie.png");
		startY -= 54;
		for (int j = 0; j < this.lifes.length; j++) {
			this.lifes[j] = new AvatarBuilder(heart).position(new Vec2(startX + 32 * j, startY)).duplicate(false)
					.fixed(true).layer(2).scale(new Vec2(1)).build(this.controller);
		}

		this.food = new Avatar[player.getHungerPoints()];
		Image foodImg = new Image("barre_nourriture.png");
		startY -= 32;
		for (int j = 0; j < this.food.length; j++) {
			this.food[j] = new AvatarBuilder(foodImg).position(new Vec2(startX + 32 * j, startY)).duplicate(false)
					.fixed(true).layer(2).scale(new Vec2(1)).build(this.controller);
		}

		this.water = new Avatar[player.getThirstPoints()];
		Image waterImg = new Image("barre_eau.png");
		startY -= 32;
		for (int j = 0; j < this.water.length; j++) {
			this.water[j] = new AvatarBuilder(waterImg).position(new Vec2(startX + 32 * j, startY)).duplicate(false)
					.fixed(true).layer(2).scale(new Vec2(1)).build(this.controller);
		}
	}

	public void updateAvatars() {
		int totalWidth = 74 * Inventory.INVENTORY_SIZE - 10;
		int startX = (this.controller.viewFor(this.player.getColor()).getWidth() - totalWidth) / 2;
		int startY = this.startY();
		for (int i = 0; i < this.inventoryCells.length; i++) {
			Vec2 pos = new Vec2(startX + 74 * i, startY);
			this.controller.updateAvatar(this.labels[i].getId(), pos.add(new Vec2(40, 60)));
			this.controller.updateAvatar(this.inventoryCells[i].getId(), new Vec2(startX + 74 * i, startY));
			if (i == 2) {
				pos.y -= 2;
			}
			if (i == 4) {
				pos = pos.add(16);
			}
			this.controller.updateAvatar(this.inventoryItems[i].getId(), pos);
		}
		startY -= 54;
		for (int i = 0; i < this.lifes.length; i++) {
			if (this.lifes[i].getPosition().getY() > 0) {
				this.controller.updateAvatar(this.lifes[i].getId(), new Vec2(startX + 32 * i, startY));
			}
		}
		startY -= 32;
		for (int i = 0; i < this.food.length; i++) {
			if (this.food[i].getPosition().getY() > 0) {
				this.controller.updateAvatar(this.food[i].getId(), new Vec2(startX + 32 * i, startY));
			}
		}
		startY -= 32;
		for (int i = 0; i < this.water.length; i++) {
			if (this.water[i].getPosition().getY() > 0) {
				this.controller.updateAvatar(this.water[i].getId(), new Vec2(startX + 32 * i, startY));
			}
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
		this.moveAvatar(this.lifes, newVal - 1, this.startY() - 184);
	}

	public void gainFood(int newVal) {
		this.moveAvatar(this.food, newVal - 1, this.startY() - 86);
	}

	public void gainWater(int newVal) {
		this.moveAvatar(this.water, newVal - 1, this.startY() - 268);
	}

	private int startY() {
		return this.controller.viewFor(this.player.getColor()).getHeight() - 130;
	}

	public void setCounter(int idx, int cpt) {
		this.controller.updatePaintable(this.labels[idx], new Label(Integer.toString(cpt)));
	}
}
