package info3.game.assets;

import java.awt.image.BufferedImage;

public abstract class Paintable extends Asset {
	public Paintable(String path) {
		super(path);
	}

	public abstract void tick(long elapsed);

	public abstract BufferedImage imageToPaint();
}
