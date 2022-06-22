package info3.game.entities;

import info3.game.LocalController;
import info3.game.Model;
import info3.game.Vec2;
import info3.game.assets.Image;
import info3.game.automata.Category;
import info3.game.automata.behaviors.StatueBehaviour;
import info3.game.physics.RigidBody;

public class Statue extends RigidBody {
	PlayerColor color;
	Player player;

	public Statue(LocalController c, Player p, Vec2 pos, int points) {
		super(1, c, points);
		this.setPosition(pos);
		this.player = p;
		this.color = this.player.getColor();
		this.setAutomata(Model.getAutomata("Statue"));
		this.setBehaviour(new StatueBehaviour());
		this.setCategory(Category.TEAM);
		this.avatar = this.controller.createAvatar(new Vec2(this.getPosition()), new Image(this.avatarPath()));
	}

	private String avatarPath() {
		return "mole-violet.png";
	}

	public Player getPlayer() {
		return this.player;
	}
}