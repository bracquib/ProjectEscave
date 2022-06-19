package info3.game;

import info3.game.entities.Tool;

;

public class Inventory {

	public final short INVENTORY_SIZE = 10;
	private Tool tools[];
	private int currentToolIndex;
	private int size;

	public Inventory(Controller c) {
		this.currentToolIndex = 0;
		this.tools = new Tool[INVENTORY_SIZE];
	}

	public Tool getCurrentTool() {
		return toolAt(currentToolIndex);
	}

	// plusieurs façons de se déplacer dans l'inventaire

	public void selectCurrentTool(int i) {
		this.currentToolIndex = (currentToolIndex + i) % this.size;
	}

	// décale la selection d'un cran vers la droite
	public void moveRCurrentTool() {
		this.currentToolIndex = (currentToolIndex + 1) % this.size;
	}

	// décale la selection d'un cran vers la gauche
	public void moveLCurrentTool() {
		if (currentToolIndex - 1 < 0)
			this.currentToolIndex += this.size;
		this.currentToolIndex = (currentToolIndex - 1) % this.size;
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

}
