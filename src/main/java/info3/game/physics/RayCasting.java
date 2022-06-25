package info3.game.physics;

import java.awt.Polygon;
import java.util.ArrayList;

import info3.game.Model;
import info3.game.Vec2;
import info3.game.entities.Block;

public class RayCasting {

	private static final int BLOC_SIZE = 33;

	/*
	 * Retourne 1er block touchÃ© par le RayCasting
	 */
	public static Block singleCast(Vec2 mousePos, Vec2 playerPos, int range) {
		Block closest = null;
		float minLength = Float.POSITIVE_INFINITY;

		Ray ray = new Ray(playerPos, playerPos.sub(mousePos).normalized());

		Vec2 coord = playerPos.divide(BLOC_SIZE).round();
		Block[][] mapZone = Model.getMapZone((int) coord.getX() - range, (int) coord.getY() - range, range * 2,
				range * 2);
		for (int i = 1; i < range * 2 - 1; i++) {
			for (int j = 1; j < range * 2 - 1; j++) {
				Block bl = mapZone[i][j];
				if (bl == null) {
					continue;
				}
				Vec2 blPos = bl.getPosition();

				ArrayList<Line> lines = new ArrayList<Line>();
				if (mapZone[i - 1][j] == null)
					lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX(), blPos.getY() + BLOC_SIZE));
				if (mapZone[i][j - 1] == null)
					lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX() + BLOC_SIZE, blPos.getY()));
				if (mapZone[i][j + 1] == null)
					lines.add(new Line(blPos.getX(), blPos.getY() + BLOC_SIZE, blPos.getX() + BLOC_SIZE,
							blPos.getY() + BLOC_SIZE));
				if (mapZone[i + 1][j] == null)
					lines.add(new Line(blPos.getX() + BLOC_SIZE, blPos.getY(), blPos.getX() + BLOC_SIZE,
							blPos.getY() + BLOC_SIZE));

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

	/*
	 * Retourne 1er block touchÃ© par le RayCasting
	 */
	public static Block singleCastDirection(Vec2 direction, Vec2 playerPos, int range) {
		Block closest = null;
		float minLength = Float.POSITIVE_INFINITY;

		Ray ray = new Ray(playerPos, direction);

		Vec2 coord = playerPos.divide(BLOC_SIZE).round();
		Block[][] mapZone = Model.getMapZone((int) coord.getX() - range, (int) coord.getY() - range, range * 2,
				range * 2);
		for (int i = 1; i < range * 2 - 1; i++) {
			for (int j = 1; j < range * 2 - 1; j++) {
				Block bl = mapZone[i][j];
				if (bl == null) {
					continue;
				}
				Vec2 blPos = bl.getPosition();

				ArrayList<Line> lines = new ArrayList<Line>();
				if (mapZone[i - 1][j] == null)
					lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX(), blPos.getY() + BLOC_SIZE));
				if (mapZone[i][j - 1] == null)
					lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX() + BLOC_SIZE, blPos.getY()));
				if (mapZone[i][j + 1] == null)
					lines.add(new Line(blPos.getX(), blPos.getY() + BLOC_SIZE, blPos.getX() + BLOC_SIZE,
							blPos.getY() + BLOC_SIZE));
				if (mapZone[i + 1][j] == null)
					lines.add(new Line(blPos.getX() + BLOC_SIZE, blPos.getY(), blPos.getX() + BLOC_SIZE,
							blPos.getY() + BLOC_SIZE));

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

	public static Block singleCast(Ray ray, int range) {
		Block closest = null;
		float minLength = Float.POSITIVE_INFINITY;

		Vec2 coord = ray.origin.divide(BLOC_SIZE).round();
		Block[][] mapZone = Model.getMapZone((int) coord.getX() - range, (int) coord.getY() - range, range * 2,
				range * 2);
		for (int i = 1; i < range * 2 - 1; i++) {
			for (int j = 1; j < range * 2 - 1; j++) {
				Block bl = mapZone[i][j];
				if (bl == null) {
					continue;
				}
				Vec2 blPos = bl.getPosition();

				ArrayList<Line> lines = new ArrayList<Line>();
				if (mapZone[i - 1][j] == null)
					lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX(), blPos.getY() + BLOC_SIZE));
				if (mapZone[i][j - 1] == null)
					lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX() + BLOC_SIZE, blPos.getY()));
				if (mapZone[i][j + 1] == null)
					lines.add(new Line(blPos.getX(), blPos.getY() + BLOC_SIZE, blPos.getX() + BLOC_SIZE,
							blPos.getY() + BLOC_SIZE));
				if (mapZone[i + 1][j] == null)
					lines.add(new Line(blPos.getX() + BLOC_SIZE, blPos.getY(), blPos.getX() + BLOC_SIZE,
							blPos.getY() + BLOC_SIZE));

				for (Line line : lines) {
					Vec2 intersec = ray.intersect(line);
					if (intersec != null) {
						float len = intersec.sub(ray.origin).length();
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

	public ArrayList<Vec2> lightCast(Vec2 playerPos, int range) {

		// Récupérer les blocs avec collision de lumière possible dans la range demandée
		// Récupérer les lignes avec collisions potentiels
		// Récupérer les vertices avec
		// Pour chaque vertice, créer une ray et récupérerer les points d'intersecs en
		// castant sur toutes les lines
		// Si plusieurs intersec, checker si la 2eme intersec est avec le block de la
		// premiere intersec
		// Si cest le cas on save la premiere intersec
		// Sinon on garde la 2eme intersec

		Vec2 coords = posPixelToBlock(playerPos);
		Block[][] mapZone = Model.getMapZone((int) coords.getX() - range, (int) coords.getY() - range, range * 2,
				range * 2);

		ArrayList<Line> lines = getLinesInMapZone(mapZone, range);
		ArrayList<Vec2> vertices = new ArrayList<Vec2>();
		for (Line l : lines) {
			vertices.add(l.pt1);
			vertices.add(l.pt2);
		}

		ArrayList<Vec2> finalIntersecs = new ArrayList<Vec2>();
		ArrayList<Line> allLines = getAllLinesInMapZone(mapZone);
		for (Vec2 vertice : vertices) {
			Ray ray = new Ray(playerPos, playerPos.sub(vertice));
			// Parcours des lines bloc par bloc (4 lines par bloc)
			for (int i = 0; i < allLines.size() / 4; i++) {
				ArrayList<Vec2> intersecs = new ArrayList<Vec2>();
				// Parcours des lines une par une
				for (int j = 0; j < 4; j++) {
					Vec2 temp = ray.intersect(allLines.get(i * 4 + j));
					if (temp != null)
						intersecs.add(temp);
				}
				if (intersecs.size() >= 1) {
					if (intersecs.size() == 1) {
						finalIntersecs.add(intersecs.get(0));
					}
					if (intersecs.size() == 2) {
						if (intersecs.get(0).equals(intersecs.get(1))) {
							finalIntersecs.add(intersecs.get(0));
							continue;
						} else {
							finalIntersecs.add(closestIn(intersecs, playerPos));
						}
					} else {
						finalIntersecs.add(closestIn(intersecs, playerPos));
					}
					break;
				}
			}
		}
		return finalIntersecs;
	}

	// Retourne uniquement les arretes en contact avec le vide
	public static ArrayList<Line> getLinesInMapZone(Block[][] mapZone, float range) {
		ArrayList<Line> lines = new ArrayList<Line>();
		for (int i = 1; i < range * 2 - 1; i++) {
			for (int j = 1; j < range * 2 - 1; j++) {
				Block bl = mapZone[i][j];
				if (bl == null) {
					continue;
				}
				Vec2 blPos = bl.getPosition();
				if (mapZone[i - 1][j] == null)
					lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX(), blPos.getY() + BLOC_SIZE));
				if (mapZone[i][j - 1] == null)
					lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX() + BLOC_SIZE, blPos.getY()));
				if (mapZone[i][j + 1] == null)
					lines.add(new Line(blPos.getX(), blPos.getY() + BLOC_SIZE, blPos.getX() + BLOC_SIZE,
							blPos.getY() + BLOC_SIZE));
				if (mapZone[i + 1][j] == null)
					lines.add(new Line(blPos.getX() + BLOC_SIZE, blPos.getY(), blPos.getX() + BLOC_SIZE,
							blPos.getY() + BLOC_SIZE));
			}
		}
		return lines;
	}

	// Retourne les 4 arretes formant un bloc si celui si est en contact avec du
	// vide
	// Sinon, retourne null
	private ArrayList<Line> getLinesFromBlock(Block[][] mapZone, int i, int j) {
		ArrayList<Line> lines = new ArrayList<Line>();
		Block block = mapZone[i][j];
		if (block == null) {
			return null;
		}
		Vec2 blPos = block.getPosition();
		if (mapZone[i - 1][j] == null || mapZone[i][j - 1] == null || mapZone[i + 1][j] == null
				|| mapZone[i][j + 1] == null) {
			lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX(), blPos.getY() + BLOC_SIZE));
			lines.add(new Line(blPos.getX(), blPos.getY(), blPos.getX() + BLOC_SIZE, blPos.getY()));
			lines.add(new Line(blPos.getX(), blPos.getY() + BLOC_SIZE, blPos.getX() + BLOC_SIZE,
					blPos.getY() + BLOC_SIZE));
			lines.add(new Line(blPos.getX() + BLOC_SIZE, blPos.getY(), blPos.getX() + BLOC_SIZE,
					blPos.getY() + BLOC_SIZE));
		}
		return lines;
	}

	private ArrayList<Line> getAllLinesInMapZone(Block[][] mapZone) {
		ArrayList<Line> lines = new ArrayList<Line>();
		for (int i = 1; i < mapZone.length - 1; i++) {
			for (int j = 1; j < mapZone[0].length - 1; j++) {
				lines.addAll(getLinesFromBlock(mapZone, i, j));
			}
		}
		return lines;
	}

	// converti une position dans le référentiel pixel au référentiel block
	private static Vec2 posPixelToBlock(Vec2 pos) {
		Vec2 coords = pos.divide(BLOC_SIZE).round();
		return coords;
	}

	// Retourne l'intersection la plus proche entre une ray et un ensemble de lines
	public static Vec2 closestIntersecInDirection(Ray ray, ArrayList<Line> lines) {
		Vec2 closest = null;
		float minLength = Float.POSITIVE_INFINITY;

		for (Line line : lines) {
			Vec2 intersec = ray.intersect(line);
			if (intersec != null) {
				float len = intersec.sub(ray.origin).length();
				if (len < minLength) {
					minLength = len;
					closest = intersec;
				}
			}
		}
		return closest;
	}

	private Vec2 closestIn(ArrayList<Vec2> points, Vec2 pos) {
		float smallestLen = Float.POSITIVE_INFINITY;
		Vec2 closest = null;
		for (Vec2 pt : points) {
			float len = pt.sub(pos).length();
			if (len < smallestLen) {
				closest = pt;
				smallestLen = len;
			}
		}
		return closest;
	}

	public static ArrayList<Vec2> getLightVertices(Vec2 playerPos, int range, float offset) {
		Vec2 coord = playerPos.divide(BLOC_SIZE).round();
		Block[][] mapZone = Model.getMapZone((int) coord.getX() - range, (int) coord.getY() - range, range * 2,
				range * 2);
		ArrayList<Line> lines = getLinesInMapZone(mapZone, range);
		ArrayList<Vec2> vertices = new ArrayList<Vec2>();
		for (Line l : lines) {
			vertices.add(l.pt1);
			vertices.add(l.pt2);
		}
		ArrayList<Vec2> points = new ArrayList<Vec2>();
		for (Vec2 vertice : vertices) {
			Vec2 dir = new Vec2(playerPos.sub(vertice));
			Ray ray = new Ray(playerPos, dir);
			Vec2 intersec = closestIntersecInDirection(ray, lines);
			if (intersec == null)
				continue;
			points.add(intersec.add(dir.normalized().multiply(offset)));
		}
		return points;
	}

	public static Polygon vertices2Polygon(ArrayList<Vec2> vertices, Vec2 pos, Vec2 camPos) {
		Polygon polygon = new Polygon();
		if (vertices == null || pos == null || camPos == null)
			return null;
		ArrayList<Vec2> vertex = new ArrayList<Vec2>(vertices);
		// Vertex gauches
		for (int i = 0; i < vertex.size(); i++) {
			float minAngle = 91;
			Vec2 minVertice = null;
			for (Vec2 vertice : vertex) {
				if (vertice == null)
					continue;
				if (pos.sub(vertice).getX() < 0) {
					float angle = vertice.sub(pos).angleDeg();
					if (angle < minAngle) {
						minAngle = angle;
						minVertice = vertice;
					}
				}
			}
			if (minVertice == null)
				break;
			Vec2 vert = new Vec2(minVertice).globalToScreen(camPos);

			polygon.addPoint((int) vert.getX(), (int) vert.getY());
			vertex.remove(minVertice);
		}

		// Vertex droites
		for (int i = 0; i < vertex.size(); i++) {
			float maxAngle = -91;
			Vec2 maxVertice = null;
			for (Vec2 vertice : vertex) {
				if (vertice == null)
					continue;
				if (pos.sub(vertice).getX() >= 0) {
					float angle = vertice.sub(pos).angleDeg();
					if (angle > maxAngle) {
						maxAngle = angle;
						maxVertice = vertice;
					}
				}
			}
			if (maxVertice == null)
				break;
			Vec2 vert = new Vec2(maxVertice).globalToScreen(camPos);
			polygon.addPoint((int) vert.getX(), (int) vert.getY());
			vertex.remove(maxVertice);
		}

		return polygon;
	}
}