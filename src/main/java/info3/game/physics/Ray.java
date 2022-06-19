package info3.game.physics;

import info3.game.Vec2;

public class Ray {
	Vec2 origin;
	Vec2 direction;

	Ray(float x, float y) {
		this.origin = new Vec2(x, y);
		this.direction = new Vec2(0, 1);
	}

}
