package info3.game.entities;

import info3.game.LocalController;
import info3.game.Model;
import info3.game.Vec2;
import info3.game.assets.Image;
import info3.game.automata.Category;
import info3.game.automata.behaviors.SocleBehaviour;

public class Socle extends Entity {
	PlayerColor color;

	public boolean isActivated = false;

	public Socle(LocalController c, PlayerColor color, Vec2 position, int points) {
		super(c, points);
		this.setCategory(Category.JUMPABLE);
		this.position = position;
		this.color = color;
		this.setAutomata(Model.getAutomata("Socle"));
		this.setBehaviour(new SocleBehaviour());
		this.avatar = this.controller.createAvatar(this.position, new Image("socle.png"));
	}
}
