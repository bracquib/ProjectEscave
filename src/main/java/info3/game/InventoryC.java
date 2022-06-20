package info3.game;

import info3.game.entities.Block;
import info3.game.entities.Food;
import info3.game.entities.Pickaxe;
import info3.game.entities.Player;
import info3.game.entities.Sword;
import info3.game.entities.Tool;
import info3.game.entities.Water;

;

public class InventoryC {

	public final short INVENTORY_SIZE = 5; // pickaxe, sword, water,food, block
	private InventoryCouple tools[];
	private int currentToolIndex;
	private int size;
	LocalController controller;
	private Player owner;

	public InventoryC(LocalController c, Player owner) {
		this.currentToolIndex = 0;
		this.tools = new InventoryCouple[INVENTORY_SIZE];
		this.controller = c;
		this.owner = owner;

	}

	public Tool getCurrentTool() {
		return toolAt(currentToolIndex);
	}

	// plusieurs façons de se déplacer dans l'inventaire

	public void selectCurrentTool(int i) {
		this.currentToolIndex = i % INVENTORY_SIZE;
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

	public InventoryCouple[] getTools() {
		return this.tools;
	}

	public boolean pick(Tool t) {
		// ramasser un objet
//		if (!this.isFull()) {
//			Tool tmp = this.get(t);
//			if (tmp == null || !tmp.isSpecial()) {
//				tools[size] = t;
//				size++;
//				return true;
//			}
//		}
//		return false;
	}

	public boolean drop() {
		// se débarasser d'un objet
		// l'objet en main devient le nouvel objet qui se trouve à l'index de celui
		// supprimé

//		if (this.isEmpty())
//			return false;
//
//		Tool toDrop = this.toolAt(currentToolIndex);
//
//		if (toDrop.isSpecial())
//			return false;
//
//		InventoryCouple newTools[] = new InventoryCouple[this.size - 1];
//		int j = 0;
//		for (int i = 0; i < this.size; i++) {
//			if (tools[i].getTool() != toDrop) {
//				newTools[j] = tools[i];
//				j++;
//			}
//		}
//		this.tools = newTools;
//		this.size = newTools.length;
//		this.checkCurrentTool();
//		return true;
	}

	public boolean use() {
		// utiliser l'objet en main

//		Tool current = this.toolAt(currentToolIndex);
//
//		if (current == null)
//			return false;
//
//		current.useTool();
//
//		if (current.isSpecial())
//			return true;
//
//		this.drop();
//		return true;

	}

	public boolean isFull() {
	}

	public boolean isEmpty() {
	}

	public Tool toolAt(int i) {
	}

	public Tool get(Tool t) {
	}

	public int indexOf(Tool t) {
	}

	public boolean addCouple(InventoryCouple c) {
		if (this.size >= this.INVENTORY_SIZE)
			return false;
		this.tools[size] = c;
		this.size++;
		return true;
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

	public static InventoryC createInventory(LocalController c, Player owner) {
		InventoryC inv = new InventoryC(c, owner);
		inv.addCouple(new InventoryCouple(new Pickaxe(c), 1));
		inv.addCouple(new InventoryCouple(new Sword(c), 1));
		inv.addCouple(new InventoryCouple(new Water(c, owner)));
		inv.addCouple(new InventoryCouple(new Food(c, owner)));
		inv.addCouple(new InventoryCouple(new Block(c)));

		return inv;

	}

}
