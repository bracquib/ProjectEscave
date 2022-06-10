package info3.game.physics;

import java.util.ArrayList;

import info3.game.Model;
import info3.game.Vec2;
import info3.game.entities.Entity;

public class PhysicsWorld {

	Model model;
	public static final Vec2 GRAVITY = new Vec2(0.0f, 9.81f);

	public PhysicsWorld(Model model) {
		this.model = model;
	}

	public void tick(long elapsed) {
		ArrayList<RigidBody> entities = this.model.getEntities();
		Entity[][] map = this.model.getMap();
		for (RigidBody entity : entities) {
			for (int i = 0; i < model.getMap().length; i++) {
				for (int j = 0; i < model.getMap()[0].length; j++) {
					CollisionType coll;
					try {
						coll = entity.isColliding(map[i][j]);
					} catch (Exception e) {
						// Ne devrait jamais arriver
						e.printStackTrace();
						return;
					}
					switch (coll) {
					case NONE:
						computeGravity(entity, elapsed);
					case DOWN:
						if (entity.getSpeed().getY() < 0)
							entity.getSpeed().nullY();
						break;
					case LEFT:
						if (entity.getSpeed().getX() < 0)
							entity.getSpeed().nullX();
						break;
					case RIGHT:
						if (entity.getSpeed().getX() > 0)
							entity.getSpeed().nullX();
						break;
					case UP:
						if (entity.getSpeed().getY() < 0)
							entity.getSpeed().nullY();
						computeGravity(entity, elapsed);
						break;
					}
					step(entity, elapsed);
					entity.getSpeed().setX(0.0f);
				}
			}
		}
	}

	private void computeGravity(RigidBody rb, long elapsed) {
		Vec2 gravityForce = PhysicsWorld.GRAVITY.multiply(rb.getMass());
		rb.addForce(gravityForce);
		Vec2 speed = rb.getForce().divide(rb.getMass()).multiply(elapsed);
		rb.addSpeed(speed);
		rb.setForce(Vec2.nullVector());
	}

	private void step(RigidBody rb, long elapsed) {
		Vec2 newPos = rb.getPosition().add(rb.getSpeed().multiply(elapsed));
		rb.setPosition(newPos);
	}
}
