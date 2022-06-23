package info3.game.assets;

import java.awt.image.BufferedImage;

public abstract class Paintable extends Asset implements Cloneable {
	public int layer = 0;
	public boolean fixed = false;

	public Paintable(String path) {
		super(path);
	}

	public abstract void tick(long elapsed);

	public abstract BufferedImage imageToPaint();

	public abstract Paintable duplicateFromPath(String path);

	public Paintable clone() {
		try {
			Paintable c = (Paintable) super.clone();
			if (this.loaded) {
				c.load();
			}
			return c;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
