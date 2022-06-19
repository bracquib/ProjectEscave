package info3.game.physics;

import java.util.ArrayList;

import info3.game.Model;
import info3.game.Vec2;
import info3.game.entities.Block;

public class RayCasting {
	// 432101234 + 2 pour le ray tracing
	// *****P***** -> nécessite une range de 11
	public final int MAXRANGE = 13;

	private ArrayList<Line> map2Lines(int x, int y) {
		ArrayList<Line> lines = new ArrayList<Line>();
		Block[][] mapZone = Model.getMapZone(x, y, MAXRANGE, MAXRANGE);
		for (int i = 1; i < MAXRANGE - 1; i++) {
			for (int j = 1; j < MAXRANGE - 1; j++) {
				Block bl = mapZone[i][j];
				Vec2 pos = bl.getPosition();

				if (mapZone[i - 1][j] == null)
					lines.add(new Line(pos.getX(), pos.getY(), pos.getX(), pos.getY() + 32));
				if (mapZone[i][j - 1] == null)
					lines.add(new Line(pos.getX(), pos.getY(), pos.getX() + 32, pos.getY()));
				if (mapZone[i][j + 1] == null)
					lines.add(new Line(pos.getX(), pos.getY() + 32, pos.getX() + 32, pos.getY() + 32));
				if (mapZone[i + 1][j] == null)
					lines.add(new Line(pos.getX() + 32, pos.getY(), pos.getX() + 32, pos.getY() + 32));
			}
		}
		return lines;
	}
}
