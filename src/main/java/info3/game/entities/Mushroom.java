package info3.game.entities;

import info3.game.LocalController;
import info3.game.Model;
import info3.game.Vec2;
import info3.game.assets.AnimatedImage;
import info3.game.assets.Image;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.automata.behaviors.MushroomBehaviour;
import info3.game.physics.BoxCollider;
import info3.game.physics.RigidBody;

public class Mushroom extends RigidBody {

	public int childRemain;

	public Mushroom(Vec2 pos) {
		super(1, Model.controller, 100);
		childRemain = 3;
		this.setPosition(pos);
		this.setCategory(Category.ADVERSAIRE);
		this.collider = new BoxCollider(Block.SIZE, Block.SIZE, 0, 0);
		this.avatar = this.controller.createAvatar(new Vec2(this.getPosition()), new Image(this.avatarPath()));
	}

	public Mushroom(LocalController c, Vec2 pos, int points, int remain) {
		super(1, c, 100);
		childRemain = remain;
		this.setPosition(pos);
		this.setCategory(Category.ADVERSAIRE);
		this.setAutomata(Model.getAutomata("Mushroom"));
		this.setBehaviour(new MushroomBehaviour());
		this.degatMob = 1;
		this.avatarOffset = new Vec2(0, 0);
		this.collider = new BoxCollider(Block.SIZE, Block.SIZE, 0, 0);
		this.avatar = this.controller.createAvatar(new Vec2(this.getPosition()), new Image(this.avatarPath()));
		this.playAnimation("spawn-right", 4, 100, 0, -10, false);
	}

	@Override
	public void tick(long el) {
		super.tick(el);
		AnimatedImage anim = (AnimatedImage) this.getPaintable();
		if (anim.isFinished()) {
			if (this.getDirection() == Direction.EST)
				this.playAnimation("idle-right", 4, 200, 0, -64, true);
			else
				this.playAnimation("idle-left", 4, 200, 0, -64, true);
		}
	}

	private String avatarPath() {
		return "mushroom/idle-right.png";
	}

	@Override
	public String animationDir() {
		return "mushroom/";
	}
}
