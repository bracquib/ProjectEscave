package info3.game.cavegenerator;

import java.util.HashMap;
import java.util.Map;

public class PerlinNoise2 {
	HashMap<Vec2, Vec2> gradients;
	HashMap<Vec2, Float> memory;

	public PerlinNoise2() {
		this.gradients = new HashMap<Vec2, Vec2>();
		this.memory = new HashMap<Vec2, Float>();

	}

	public static <V> V getValue(Vec2 key, HashMap<Vec2, V> map) {
		for (Map.Entry<Vec2, V> entry : map.entrySet()) {
			Vec2 TempKey = entry.getKey();
			V TempValue = entry.getValue();
			if (TempKey.getX() == key.getX() && TempKey.getY() == key.getY())
				return TempValue;
		}
		return null;
	}

	public static float[][] genPerlinNoiseGrid(int width, int height, float perlinStep) {
		PerlinNoise2 pn = new PerlinNoise2();
		float[] grid[] = new float[width][];
		for (int i = 0; i < width; i++) {
			grid[i] = new float[height];
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				grid[i][j] = Math.abs(pn.get(i * perlinStep, j * perlinStep));
			}
		}
		return grid;
	}

	private Vec2 randVec() {
		float theta = (float) (Math.random() * 2 * Math.PI);
		return new Vec2((float) Math.cos(theta), (float) Math.sin(theta));
	}

	private float dotProdGrid(float x, float vx, float y, float vy) {
		Vec2 gVect;
		Vec2 dVect = new Vec2(x - vx, y - vy);
		Vec2 vectVxVy = new Vec2(vx, vy);
		if (PerlinNoise2.getValue(vectVxVy, this.gradients) != null) {
			gVect = PerlinNoise2.getValue(vectVxVy, this.gradients);
		} else {
			gVect = this.randVec();
			this.gradients.put(vectVxVy, gVect);
		}
		return dVect.getX() * gVect.getX() + dVect.getY() * gVect.getY();
	}

	private float smootherStep(float x) {
		return (float) (6 * Math.pow(x, 5) - 15 * Math.pow(x, 4) + 10 * Math.pow(x, 3));
	}

	private float interpolate(float x, float a, float b) {
		return a + this.smootherStep(x) * (b - a);
	}

	private float get(float x, float y) {
		if (PerlinNoise2.getValue(new Vec2(x, y), this.memory) != null) {
			return PerlinNoise2.getValue(new Vec2(x, y), this.memory);

		}

		float xf = (float) Math.floor(x);
		float yf = (float) Math.floor(y);

		float tl = this.dotProdGrid(x, y, xf, yf);
		float tr = this.dotProdGrid(x, y, xf + 1, yf);
		float bl = this.dotProdGrid(x, y, xf, yf + 1);
		float br = this.dotProdGrid(x, y, xf + 1, yf + 1);
		float xt = this.interpolate(x - xf, tl, tr);
		float xb = this.interpolate(x - xf, bl, br);
		float v = this.interpolate(y - yf, xt, xb);
		this.memory.put(new Vec2(x, y), v);
		return v;

	}
}
