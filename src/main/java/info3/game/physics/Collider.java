package info3.game.physics;

import info3.game.Vec2;

public abstract class Collider {
	public abstract Collision isColliding(Vec2 pos, Collider other, Vec2 otherPos);
}
