package info3.game;

import info3.game.entities.Pickaxe;
import info3.game.entities.Tool;

public class InventoryCouple {
	private int number;
	private Tool tool;

	public InventoryCouple(Tool tool, int number) {
		this.tool = tool;
		this.number = number;

	}

	public InventoryCouple(Tool tool) {
		this.tool = tool;
		this.number = 1;
	}

	public Tool getTool() {
		return this.tool;
	}

	public int getNumber() {
		return this.number;
	}

	public void add() {
		this.number++;
	}

	public void add(int i) {
		this.number += i;
	}

	public void sub() {
		this.number--;
	}

	public void sub(int i) {
		this.number -= i;
	}

	public void printCouple() {
		if (this.tool != null)
			System.out.print("( " + this.tool.getName() + " : " + this.getNumber() + " )");
	}

	public static void main(String[] args) {

		InventoryCouple c1 = new InventoryCouple(new Pickaxe(null));
		c1.printCouple();
	}
}