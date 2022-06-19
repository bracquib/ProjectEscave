package info3.game.entities;

import info3.game.Controller;

public abstract class Tool extends Entity {

	private String name;
	// les objets spéciaux sont ceux qui ne peuvent pas quitter l'inventaire
	protected boolean special;

	public Tool(Controller c) {
		super(c);
	}

	public abstract void useTool();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSpecial() {
		return this.special;
	}
}
