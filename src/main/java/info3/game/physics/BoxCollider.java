package info3.game.physics;

import info3.game.Vec2;

public class BoxCollider extends Collider {
	float width;
	float height;

	@Override
	public Collision isColliding(Vec2 pos, Collider other, Vec2 otherPos) {
		if (other instanceof BoxCollider)
			return testCollision(pos, other, otherPos);
	}

	public Collision testCollision(Vec2 pos, BoxCollider other, Vec2 otherPos) {
		Collision res = new Collision();
	}
}
