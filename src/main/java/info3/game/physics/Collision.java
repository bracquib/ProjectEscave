package info3.game.physics;

import info3.game.entities.Entity;

public class Collision {
	public CollisionType typeA;
	public CollisionType typeB;
	public Entity objA;
	public Entity objB;

	public Collision(Entity objA, CollisionType typeA, Entity objB, CollisionType typeB) {
		this.objA = objA;
		this.objB = objB;
		this.typeA = typeA;
		this.typeB = typeB;
	}

}
