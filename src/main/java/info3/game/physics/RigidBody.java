package info3.game.physics;

import info3.game.LocalController;
import info3.game.Vec2;
import info3.game.entities.Entity;

public class RigidBody extends Entity {
	private Vec2 force;
	private Vec2 speed;
	private float mass;

	// WTF is this constructor used for???
	public RigidBody(Entity e, float mass) {
		super(e.getController());
		force = Vec2.nullVector();
		speed = Vec2.nullVector();
		this.mass = mass;
	}

	public RigidBody(float mass, LocalController c) {
		super(c);
		force = Vec2.nullVector();
		speed = Vec2.nullVector();
		this.mass = mass;
	}

	public void addSpeed(Vec2 speed) {
		this.speed = this.speed.add(speed);
	}

	public void addForce(Vec2 force) {
		this.force = this.force.add(force);
	}

	public CollisionType isColliding(Entity other) throws Exception {
		return this.getCollider().isColliding(this.getPosition(), other.getCollider(), other.getPosition());
	}

	/*
	 * Getters & Setters
	 */

	// mass
	public void setMass(float mass) {
		this.mass = mass;
	}

	public final float getMass() {
		return this.mass;
	}

	// speed
	public void setSpeed(Vec2 speed) {
		this.speed = speed;
	}

	public final Vec2 getSpeed() {
		return this.speed;
	}

	// force
	public void setForce(Vec2 force) {
		this.force = force;
	}

	public final Vec2 getForce() {
		return this.force;
	}

}
