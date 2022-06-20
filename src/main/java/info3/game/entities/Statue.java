package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;
import info3.game.assets.Image;
import info3.game.automata.Category;
import info3.game.automata.StatueBehaviour;

public class Statue extends Entity {
	PlayerColor color;

	public Statue(LocalController c, PlayerColor color, Vec2 position, int points) {
		super(c, points);
		this.position = position;
		this.color = color;
		this.setAutomata(c.model.getAutomata("Statue"));
		this.setBehaviour(new StatueBehaviour());
		this.setCategory(Category.TEAM);
		this.avatar = this.controller.createAvatar(this.position, new Image("statue.png"));
	}
}