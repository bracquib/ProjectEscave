package info3.game.physics;

import info3.game.Vec2;

public class BoxCollider extends Collider {
	public float width;
	public float height;

	@Override
	public CollisionType isColliding(Vec2 pos, Collider other, Vec2 otherPos) throws Exception {
		if (other instanceof BoxCollider)
			return testCollision(pos, (BoxCollider) other, otherPos);

		throw new Exception("Collision not implemented");
	}

	public CollisionType testCollision(Vec2 pos, BoxCollider other, Vec2 otherPos) {
		if ((otherPos.getX() >= pos.getX() + this.width) // trop à droite
				|| (otherPos.getX() + other.width <= pos.getX()) // trop à gauche
				|| (otherPos.getY() >= pos.getY() + this.height) // trop en bas
				|| (otherPos.getY() + other.height <= pos.getY())) // trop en haut
			// No collision
			return CollisionType.NONE;
		else
		// Collision
		if (pos.getX() < otherPos.getX()) { // Collision LEFT
			if (pos.getY() <= otherPos.getY()) { // Collision UP LEFT
				// * Decide UP / LEFT
				float diffX = pos.getX() + this.width - otherPos.getX();
				float diffY = pos.getY() + this.height - otherPos.getY();
				return diffX <= diffY ? CollisionType.LEFT : CollisionType.UP;
			} else { // Collision DOWN LEFT
				// * Decide DOWN / LEFT
				float diffX = pos.getX() + this.width - otherPos.getX();
				float diffY = otherPos.getY() + other.height - pos.getY();
				return diffX <= diffY ? CollisionType.LEFT : CollisionType.DOWN;
			}
		} else { // Collision RIGHT
			if (pos.getY() <= otherPos.getY()) { // Collision UP LEFT
				// * Decide UP / RIGHT
				float diffX = otherPos.getX() + other.width - pos.getX();
				float diffY = pos.getY() + this.height - otherPos.getY();
				return diffX <= diffY ? CollisionType.RIGHT : CollisionType.UP;
			} else { // Collision DOWN RIGHT
				// * Decide DOWN / RIGHT
				float diffX = otherPos.getX() + other.width - pos.getX();
				float diffY = otherPos.getY() + other.height - pos.getY();
				return diffX <= diffY ? CollisionType.RIGHT : CollisionType.DOWN;
			}
		}
	}
}
