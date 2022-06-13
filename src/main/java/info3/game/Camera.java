package info3.game;

import info3.game.entities.Player;

public class Camera {
	Vec2 pos;

	public Camera(Vec2 pos) {
		this.pos = pos;
	}

	public void setPos(Vec2 pos) {
		this.pos = pos;
	}

	public Vec2 getPos() {
		return this.pos;
	}

	public void translate(Vec2 other) {
		this.setPos(this.getPos().add(other));
	}

	public void syncWith(Player p) {
		// TODO: remove hardcoded offset
		this.setPos(p.getPosition().add(new Vec2(-528, -368)));
	}
}
