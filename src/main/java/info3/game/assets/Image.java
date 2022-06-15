package info3.game.assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image extends Paintable {

	private static final long serialVersionUID = -1054334314973696343L;
	private transient BufferedImage image;

	public Image(String path) {
		super(path);
	}

	@Override
	public void tick(long elapsed) {
	}

	@Override
	public BufferedImage imageToPaint() {
		return this.image;
	}

	@Override
	public void load() {
		File imageFile = new File(AssetServer.baseDir + this.getPath());
		if (imageFile.exists()) {
			try {
				this.image = ImageIO.read(imageFile);
			} catch (IOException e) {
				return;
			}
		}
	}

}
