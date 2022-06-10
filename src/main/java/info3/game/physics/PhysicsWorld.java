package info3.game.physics;

import info3.game.Vec2;
import info3.game.entities.Entity;

public class PhysicsWorld {
	Model model;

	private enum collisionType {
		TOP, DOWN, LEFT, RIGHT, NONE
	};

	public static final Vec2 GRAVITY = new Vec2(0.0f, 9.81f);

	public PhysicsWorld(Model model) {
		this.model = model;
	}

	public void tick(long elapsed) {
		for (Entity dynamicEntity : model.entities) {
			for (int i = 0; i < model.map.length; i++) {
				for (int j = 0; i < model.map[0].length; j++) {
					Collision coll = dynamicEntity.isColliding(model.map[i][j]);
					switch (coll) {
					case coll.
					}

				}
			}
		}
	}

	public boolean onGround() {
		return false;
	}
}
//model.map[][]
//model.entities[]
