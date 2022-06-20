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
	private InventoryCouple tools[]; // tableau de couples (objet : quantité)
	private int currentToolIndex; // index de l'objet en main
	private int size; // le nombre de catégories d'objets présentes dans l'inventaire
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

		InventoryCouple couple = getCouple(t);

		if (couple == null)
			return this.addCouple(new InventoryCouple(t, 1));

		return couple.add();

	}

	public boolean drop() {
		// se débarasser d'un objet
		// l'objet en main devient le nouvel objet qui se trouve à l'index de celui
		// supprimé

		if (this.isEmpty())
			return false;

		InventoryCouple toDrop = this.coupleAt(currentToolIndex);

		if (toDrop == null)
			return false;

		this.checkCurrentTool();
		return toDrop.sub();

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

	// l'inventaire est dit vide s'il ne contient aucun consumable
	// vraiment utile ?
	public boolean isEmpty() {
		for (int i = 0; i < this.size; i++) {
			InventoryCouple tmp = tools[i];
			if (!tmp.getTool().isSpecial() && tmp.getNumber() > 0)
				return false;
		}
		return true;
	}

	public InventoryCouple coupleAt(int i) {
		if (i >= 0 && i < this.size)
			return tools[i];
		return null;
	}

	public Tool toolAt(int i) {
		return coupleAt(i).getTool();
	}

	public InventoryCouple getCouple(Tool t) {
		for (int i = 0; i < this.size; i++) {
			InventoryCouple tmp = coupleAt(i);
			if (tmp.getTool().getClass() == t.getClass())
				return tmp;
		}
		return null;
	}

	public Tool getTool(Tool t) {
		if (getCouple(t) == null)
			return null;
		return getCouple(t).getTool();
	}

	public int indexOf(Tool t) {
		for (int i = 0; i < this.size; i++) {
			Tool tmp = toolAt(i);
			if (tmp.getClass() == t.getClass())
				return i;
		}
		return -1;
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
		for (int i = 0; i < this.size; i++) {
			if (coupleAt(i) != null)
				coupleAt(i).printCouple();

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

//	public static void main(String[] args) {
//
//	}

}
