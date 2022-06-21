package info3.game.entities;

import info3.game.LocalController;
import info3.game.Model;
import info3.game.Vec2;
import info3.game.assets.Image;
import info3.game.automata.Category;
import info3.game.automata.behaviors.MushroomBehaviour;
import info3.game.physics.RigidBody;

public class Mushroom extends RigidBody {

	public int childRemain;

	public Mushroom(LocalController c, Vec2 pos, int points, int remain) {
		super(1, c, points);
		childRemain = remain;
		this.setPosition(pos);
		this.setCategory(Category.ADVERSAIRE);
		this.setAutomata(Model.getAutomata("Mushroom"));
		this.setBehaviour(new MushroomBehaviour());
		this.degatMob = 1;
		this.avatar = this.controller.createAvatar(new Vec2(this.getPosition()), new Image(this.avatarPath()));
	}

	@Override
	public void tick(long el) {
		super.tick(el);
	}

	private String avatarPath() {
		return "mole-violet.png";
	}
}
