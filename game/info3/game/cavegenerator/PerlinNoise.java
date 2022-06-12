package info3.game.cavegenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Provides Perlin noise which can be sampled in O(1) at any location.
 */
public final class PerlinNoise {

	/** The PRNG for gradient generation (for now). */
	private final Random rng;

	/** Cache computed gradients. */
	private final Map<Vec2, Vec2> gradients = new HashMap<Vec2, Vec2>();

	final List<Vec2> corners = new ArrayList<Vec2>();

	/**
	 * Create new Perlin noise.
	 * 
	 * @param seed      PRNG seed
	 * @param dimension the dimension of the noise
	 */
	public PerlinNoise(final int seed) {
		this.rng = new Random(seed);
		corners.add(new Vec2(0, 0));
		corners.add(new Vec2(1, 0));
		corners.add(new Vec2(0, 1));
		corners.add(new Vec2(1, 1));
	}

	public double sample(final Vec2 p) {
		double sum = 0;
		Vec2 pfloor = p.floor();
		for (Vec2 c : corners) {
			Vec2 q = pfloor.add(c);
			Vec2 g = gradient(q);
			double m = g.dot(p.sub(q));
			Vec2 t = p.sub(q).abs().multiply(-1).add(1);
			Vec2 w = t.pow(2).multiply(3).sub(t.pow(3).multiply(2));
			sum += w.product() * m;
			sum = Math.abs(sum);
		}
		return sum;
	}

	/**
	 * Calculate the gradient at a given point.
	 * 
	 * @param p the grid point
	 * @return the gradient
	 */
	private Vec2 gradient(final Vec2 p) {
		Vec2 gradient = gradients.get(p);
		if (gradient == null) {
			Vec2 vector = new Vec2(0, 0);
			vector.setX((float) (rng.nextDouble() - 0.5));
			vector.setY((float) (rng.nextDouble() - 0.5));

			gradient = new Vec2(vector).normalized();
			gradients.put(p, gradient);
		}
		return gradient;
	}
}