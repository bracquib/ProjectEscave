package info3.game;

import java.util.ArrayList;

import info3.game.assets.AssetServer;
import info3.game.assets.Image;
import info3.game.entities.Block;
import info3.game.entities.Food;
import info3.game.entities.Pickaxe;
import info3.game.entities.Player;
import info3.game.entities.PlayerColor;
import info3.game.entities.Sword;
import info3.game.entities.Tool;
import info3.game.entities.Water;
import info3.game.network.UpdateAvatar;

;

public class Inventory {

	public final short INVENTORY_SIZE = 5; // pickaxe, sword, water,food, block
	private Tool tools[];
	private Avatar[] cells;
	private int currentToolIndex;
	private int size;
	LocalController controller;
	private Player owner;

	public Inventory(LocalController c, Player owner) {
		this.currentToolIndex = 0;
		this.tools = new Tool[INVENTORY_SIZE];
		this.controller = c;
		this.owner = owner;
		this.cells = new Avatar[INVENTORY_SIZE];

		int totalWidth = 74 * INVENTORY_SIZE - 10;
		int startX = (this.controller.viewFor(null).getWidth() - totalWidth) / 2;
		int startY = this.controller.viewFor(null).getHeight() - 130;
		Image selectedCell = new Image("inventory-cell-selected.png");
		selectedCell.layer = 1;
		selectedCell.fixed = true;
		this.cells[0] = this.controller.createAvatar(new Vec2(startX, startY), selectedCell);
		for (int i = 1; i < this.cells.length; i++) {
			Image cell = new Image("inventory-cell.png");
			cell.layer = 1;
			cell.fixed = true;
			this.cells[i] = this.controller.createAvatar(new Vec2(startX + 74 * i, startY), cell);
		}
	}

	public Tool getCurrentTool() {
		return toolAt(currentToolIndex);
	}

	// plusieurs façons de se déplacer dans l'inventaire

	public void selectCurrentTool(int i) {
		this.cells[this.currentToolIndex].setPaintablePath("inventory-cell.png");
		this.controller.sendTo(this.owner,
				new UpdateAvatar(this.cells[this.currentToolIndex].getId(), "inventory-cell.png"));
		this.currentToolIndex = i % INVENTORY_SIZE;
		this.controller.sendTo(this.owner,
				new UpdateAvatar(this.cells[this.currentToolIndex].getId(), "inventory-cell-selected.png"));
		this.cells[this.currentToolIndex].setPaintablePath("inventory-cell-selected.png");
	}

	// décale la selection d'un cran vers la droite
	public void moveRCurrentTool() {
		this.selectCurrentTool((this.currentToolIndex + 1) % INVENTORY_SIZE);
	}

	// décale la selection d'un cran vers la gauche
	public void moveLCurrentTool() {
		if (currentToolIndex == 0) {
			this.selectCurrentTool(INVENTORY_SIZE - 1);
		} else {
			this.selectCurrentTool(this.currentToolIndex - 1);
		}
	}

	// verifie la validité de l'indice de l'objet en main
	public void checkCurrentTool() {
		while (currentToolIndex >= size) {
			currentToolIndex--;
		}
	}

	public Tool[] getTools() {
		return this.tools;
	}

	public boolean pick(Tool t) {
		// ramasser un objet
		if (!this.isFull()) {
			Tool tmp = this.get(t);
			if (tmp == null || !tmp.isSpecial()) {
				tools[size] = t;
				size++;
				return true;
			}
		}
		return false;
	}

	public boolean drop() {
		// se débarasser d'un objet
		// l'objet en main devient le nouvel objet qui se trouve à l'index de celui
		// supprimé

		if (this.isEmpty())
			return false;

		Tool toDrop = this.toolAt(currentToolIndex);

		if (toDrop.isSpecial())
			return false;

		Tool newTools[] = new Tool[this.size - 1];
		int j = 0;
		for (int i = 0; i < this.size; i++) {
			if (tools[i] != toDrop) {
				newTools[j] = tools[i];
				j++;
			}
		}
		this.tools = newTools;
		this.size = newTools.length;
		this.checkCurrentTool();
		return true;
	}

	public boolean use() {
		// utiliser l'objet en main

		Tool current = this.toolAt(currentToolIndex);

		if (current == null)
			return false;

		current.useTool();

		if (current.isSpecial())
			return true;

		this.drop();
		return true;

	}

	public boolean isFull() {
		return this.size >= INVENTORY_SIZE;
	}

	public boolean isEmpty() {
		return this.size <= 0;
	}

	public Tool toolAt(int i) {
		if (i >= 0 && i < this.size)
			return tools[i];
		return null;
	}

	public Tool get(Tool t) {
		for (int i = 0; i < this.size; i++) {
			if (tools[i].getClass() == t.getClass())
				return tools[i];
		}
		return null;
	}

	public int indexOf(Tool t) {
		for (int i = 0; i < this.size; i++) {
			if (tools[i].getClass() == t.getClass())
				return i;

		}
		return -1;
	}

	public void printInventory() {
		System.out.print("[ Inventory : ");
		for (int i = 0; i < 20; i++) {
			if (toolAt(i) != null)
				System.out.print(toolAt(i).getName() + " ");
		}
		if (this.getCurrentTool() != null)
			System.out.print("CURRENT = " + this.getCurrentTool().getName());
		System.out.println("]");

	}

	public static void main(String[] args) {
		AssetServer.init(false);
		ArrayList<View> views = new ArrayList<View>();
		views.add(new RemoteView());
		LocalController c = new LocalController(views);
		Vec2 v = new Vec2(0, 0);
		Player wi = new Player(c, PlayerColor.BLUE, v, true);

		Tool water = new Water(c, v, wi);
		Tool food = new Food(c, v, wi);
		Tool sword = new Sword(c);
		Tool pickaxe = new Pickaxe(c);
		Tool block = new Block(c, v, 0);

		wi.getInventory().pick(water);
		wi.getInventory().pick(food);
		wi.getInventory().pick(sword);
		wi.getInventory().pick(pickaxe);
		wi.getInventory().pick(block);

		wi.getInventory().printInventory();

	}

}
