package info3.game;

import info3.game.entities.Food;
import info3.game.entities.Pickaxe;
import info3.game.entities.Sword;
import info3.game.entities.Tool;
import info3.game.entities.Water;

;

public class Inventory {

	public final int INVENTORY_SIZE = 10;
	private Tool tools[];
	private Tool currentTool;
	private int size;

	public Inventory(Controller c) {
		this.currentTool = new Pickaxe(c);
		this.tools = new Tool[INVENTORY_SIZE];
	}

	public Tool getCurrentTool() {
		return this.currentTool;
	}

	public void setCurrentTool(Tool t) {
		this.currentTool = t;
	}

	public Tool[] getTools() {
		return this.tools;
	}

	public void pick(Tool t) {
		// ramasser un objet
		if (!this.isFull()) {
			tools[size] = t;
			size++;
		}
	}

	public void throW(Tool t) {
		// se débarasser d'un objet
		if (!this.isEmpty()) {
			Tool toThrow = this.findTool(t);
			// test si toThrow a ete trouvé ?

			if (!toThrow.isSpecial()) {
				Tool tools_copy[] = new Tool[this.size - 1];
				int j = 0;
				for (int i = 0; i < this.size; i++) {
					if (tools[i] != toThrow) {
						tools_copy[j] = tools[i];
						j++;
					}
				}
				this.tools = tools_copy;
				this.size = tools_copy.length;
			}
			// }
		}
	}

	public void use(Tool t) {
		// TODO
		// utiliser un objet
		// appelle la méthode useTool()
	}

	public boolean isFull() {
		return size >= INVENTORY_SIZE;
	}

	public boolean isEmpty() {
		return size <= 0;
	}

	public Tool findTool(int i) {
		if (i >= 0 && i < this.size)
			return tools[i];
		// throw new ObjectNotFoundException();
		return null;
	}

	public Tool findTool(Tool t) {
		for (int i = 0; i < this.size; i++) {
			if (tools[i].getClass() == t.getClass())
				return tools[i];
			// comparer directement les objets ?
		}
		// throw new ObjectNotFoundException();
		return null;
	}

	public int findIndex(Tool t) {
		for (int i = 0; i < this.size; i++) {
			if (tools[i].getClass() == t.getClass())
				return i;
			// comparer directement les objets ?
		}
		// throw new ObjectNotFoundException();
		return -1;
	}

	public void printInventory() {
		System.out.print("[ Inventory : ");
		for (int i = 0; i < this.size; i++) {
			System.out.print(findTool(i).getName() + " ");
		}
		System.out.println("]");
	}

	public static void main(String args[]) throws Exception {
		LocalController c = new LocalController();
		Inventory inv = new Inventory(c);
		// System.out.println(inv.getClass());
		Pickaxe pi = new Pickaxe(c);
		Sword sw = new Sword(c);
		Food fo = new Food(c, new Vec2(0, 0));
		Water wa = new Water(c, new Vec2(0, 0));
		inv.pick(pi);
		inv.pick(wa);
		inv.pick(fo);
		inv.pick(sw);
		inv.throW(pi);
		inv.throW(sw);
		inv.throW(fo);
		inv.throW(wa);
		// System.out.println((inv.findTool(0).getClass()));
		// System.out.println((inv.findTool(new Pickaxe(c)).getClass()));
		inv.printInventory();
	}

}
