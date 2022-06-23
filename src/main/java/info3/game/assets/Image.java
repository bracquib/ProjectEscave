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
		if (this.loaded) {
			return;
		}
		System.out.println("loading image");
		File imageFile = new File(AssetServer.baseDir + this.getPath());
		if (imageFile.exists()) {
			try {
				this.image = ImageIO.read(imageFile);
				this.loaded = true;
			} catch (IOException e) {
				System.out.println("[WARN] Couldn't read file: " + this.path);
				return;
			}
		} else {
			System.out.println("[WARN] Image file not found: " + this.path);
		}
	}

	@Override
	public Paintable duplicateFromPath(String path) {
		Image img = new Image(path);
		img.fixed = this.fixed;
		img.layer = this.layer;
		if (this.loaded) {
			img.load();
		}
		return img;
	}
}
