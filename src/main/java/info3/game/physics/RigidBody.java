package info3.game.physics;

import info3.game.Controller;
import info3.game.Vec2;
import info3.game.entities.Entity;

public class RigidBody extends Entity {
	private Vec2 force;
	private Vec2 speed;
	private float mass;

	public RigidBody(float mass, Controller c) {
		super(c);
		force = Vec2.nullVector();
		speed = Vec2.nullVector();
		this.mass = mass;
	}

	public void addSpeed(Vec2 speed) {
		this.speed.add(speed);
	}

	public Collision isColliding(Entity other) {
		return this.collider.isColliding(this.getPosition(), other., force)
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

	// Force
	public void setForce(Vec2 force) {
		this.force = force;
	}

	public final Vec2 getForce() {
		return this.force;
	}

}
