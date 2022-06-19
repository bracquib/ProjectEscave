package info3.game.physics;

import info3.game.Vec2;

public class Ray {
	Vec2 origin;
	Vec2 direction;

	Ray(float x, float y) {
		this.origin = new Vec2(x, y);
		this.direction = new Vec2(0, 1);
	}

	public boolean cast(Line line) {
		float x1 = line.pt1.getX();
		float y1 = line.pt1.getY();
		float x2 = line.pt2.getX();
		float y2 = line.pt2.getY();

		float x3 = this.origin.getX();
		float y3 = this.origin.getY();
		float x4 = this.origin.getX() + this.direction.getX();
		float y4 = this.origin.getY() + this.direction.getY();

		float den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (den == 0)
			return false;

		float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / den;
		float u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / den;
		if (t > 0 && t < 1 && u > 0) {
			Vec2 intersect = new Vec2(x1 + t * (x2 - x1), y1 + t * (y2 - y1));

			return true;
		} else
			return false;

	}

}
