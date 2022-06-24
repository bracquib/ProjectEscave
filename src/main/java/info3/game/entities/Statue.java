package info3.game.entities;

import info3.game.LocalController;
import info3.game.Model;
import info3.game.Vec2;
import info3.game.assets.AnimatedImage;
import info3.game.automata.Category;
import info3.game.automata.behaviors.StatueBehaviour;
import info3.game.physics.BoxCollider;
import info3.game.physics.RigidBody;

public class Statue extends RigidBody {
	PlayerColor color;
	Player player;

	public Statue(LocalController c, Player p, Vec2 pos, int points) {
		super(1, c, points);
		this.setPosition(pos);
		this.player = p;
		this.color = this.player.getColor();
		this.avatarOffset = new Vec2(0, -52);

		this.avatar = this.controller.createAvatar(this.getPosition().add(this.avatarOffset),
				new AnimatedImage("statue/statue_immobile_morte_verte.png", 4, 100, true));
		this.setAutomata(Model.getAutomata("Statue"));
		this.setBehaviour(new StatueBehaviour());
		this.setCategory(Category.TEAM);
		this.collider = new BoxCollider(Block.SIZE - 3, Block.SIZE - 3, 1.5f, 1.5f);

	}

	public Player getPlayer() {
		return this.player;
	}
}
