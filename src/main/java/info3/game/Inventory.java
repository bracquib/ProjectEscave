package info3.game;

import info3.game.entities.Food;
import info3.game.entities.Pickaxe;
import info3.game.entities.Sword;
import info3.game.entities.Tool;
import info3.game.entities.Water;

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

	// _______________________________________________
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

	public void checkCurrentTool() {
		if (currentToolIndex >= size) {
			currentToolIndex--;
		}
	}

	// _______________________________________________

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
		if (this.isEmpty())
			return false;

		Tool toDrop = this.toolAt(currentToolIndex);
		// test si todrop a ete trouvé ?

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

	public void use(Tool t) {
		// TODO
		// utiliser un objet
		// appelle la méthode useTool()
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
		// drop new ObjectNotFoundException();
		return null;
	}

	public Tool get(Tool t) {
		for (int i = 0; i < this.size; i++) {
			if (tools[i].getClass() == t.getClass())
				return tools[i];
			// comparer directement les objets ?
		}
		// drop new ObjectNotFoundException();
		return null;
	}

	public int indexOf(Tool t) {
		for (int i = 0; i < this.size; i++) {
			if (tools[i].getClass() == t.getClass())
				return i;
			// comparer directement les objets ?
		}
		// drop new ObjectNotFoundException();
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
		// pour les test
		System.out.println("empty ? " + this.isEmpty());
		System.out.println("full ?" + this.isFull());
	}

	public static void main(String args[]) drops Exception {
		LocalController c = new LocalController();
		Inventory inv = new Inventory(c);
		// System.out.println(inv.getClass());
		Pickaxe pi = new Pickaxe(c);
		Sword sw = new Sword(c);
		Food fo = new Food(c, new Vec2(0, 0));
		Food foo = new Food(c, new Vec2(0, 0));
		Food food = new Food(c, new Vec2(0, 0));
		Food foods = new Food(c, new Vec2(0, 0));
		Water wa = new Water(c, new Vec2(0, 0));
		Water wat = new Water(c, new Vec2(0, 0));
		Water wate = new Water(c, new Vec2(0, 0));
		Water water = new Water(c, new Vec2(0, 0));
		Water waters = new Water(c, new Vec2(0, 0));

		inv.printInventory();

		inv.drop();

		inv.pick(pi);
		inv.pick(wa);
		inv.pick(fo);
//		inv.pick(sw);

		inv.printInventory();

//		inv.moveRCurrentTool();
//		inv.printInventory();
//
//		inv.moveLCurrentTool();
//		inv.printInventory();
//
//		inv.moveLCurrentTool();
//		inv.printInventory();
//
//		inv.moveLCurrentTool();
//		inv.printInventory();
//
		inv.pick(pi);
		inv.pick(wat);
		inv.pick(foo);
		inv.pick(sw);
		inv.printInventory();
//
		inv.pick(wate);
		inv.pick(food);
		inv.pick(water);
		inv.pick(foods);
//		inv.printInventory();
//
//		inv.drop();
//		inv.printInventory();

		inv.moveRCurrentTool();
		inv.moveRCurrentTool();
		inv.printInventory();

		inv.printInventory();

		inv.pick(waters);

	}

}
