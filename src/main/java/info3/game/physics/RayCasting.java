package info3.game.physics;

import java.util.ArrayList;

import info3.game.Model;
import info3.game.Vec2;
import info3.game.entities.Block;

public class RayCasting {
	// 432101234 + 2 pour le ray tracing
	// *****P***** -> nécessite une range de 11
	public final int MAXRANGE = 13;

	/*
	 * Retourne 1er block touché par le RayCasting
	 */
	public Block singleCast(Vec2 mousePos, Vec2 playerPos, int range) {
		Block closest = null;
		float minLength = Float.POSITIVE_INFINITY;

		Ray ray = new Ray(playerPos, mousePos.sub(playerPos).normalized());

		Vec2 coord = playerPos.multiply(64).round();
		Block[][] mapZone = Model.getMapZone((int) coord.getX(), (int) coord.getY(), range, range);
		for (int i = 1; i < MAXRANGE - 1; i++) {
			for (int j = 1; j < MAXRANGE - 1; j++) {
				Block bl = mapZone[i][j];
				Vec2 blPos = bl.getPosition();

				ArrayList<Line> lines = new ArrayList<Line>();
				if (mapZone[i - 1][j] == null)
					lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX(), blPos.getY() + 64));
				if (mapZone[i][j - 1] == null)
					lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX() + 64, blPos.getY()));
				if (mapZone[i][j + 1] == null)
					lines.add(new Line(blPos.getX(), blPos.getY() + 64, blPos.getX() + 64, blPos.getY() + 64));
				if (mapZone[i + 1][j] == null)
					lines.add(new Line(blPos.getX() + 64, blPos.getY(), blPos.getX() + 64, blPos.getY() + 64));

				for (Line line : lines) {
					Vec2 intersec = ray.intersect(line);
					if (intersec != null) {
						float len = intersec.sub(playerPos).length();
						if (len < minLength) {
							minLength = len;
							closest = bl;
						}
					}
				}
			}
		}
		return closest;
	}

	public ArrayList<Ray> multiCast(float angleBetween) {
		ArrayList<Ray> rays = new ArrayList<Ray>();
		return rays;
	}

}
