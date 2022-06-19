package info3.game.assets;

import java.awt.image.BufferedImage;

public class AnimatedImage extends Paintable {

	private static final long serialVersionUID = -7309704678040085838L;
	transient BufferedImage[] frames;
	int frameCount;
	/**
	 * Indique à quel indice de l'animation on se trouve.
	 * 
	 * Reste à 0 dans le cas d'une image statique.
	 */
	int imageIndex = 0;

	/**
	 * Indique combien de temps s'est écoulé depuis le dernier changement d'image.
	 */
	long imageElapsed = 0;

	/**
	 * Indique le temps après lequel on change d'image dans l'animation.
	 * 
	 * Si il vaut 0, on n'a pas d'animation, même si on a plusieurs images. Le
	 * contrôle se fait alors avec la méthode `nextFrame`.
	 */
	public long animationDelay;

	public AnimatedImage(String path, int fc, int delay) {
		super(path);
		this.frameCount = fc;
		this.animationDelay = delay;
	}

	@Override
	public void load() {
		Image img = AssetServer.load(new Image(this.getPath()));
		BufferedImage image = img.imageToPaint();
		int width = image.getWidth() / this.frameCount;
		int height = image.getHeight();

		this.frames = new BufferedImage[this.frameCount];
		for (int i = 0; i < this.frameCount; i++) {
			int x = i * width;
			this.frames[i] = image.getSubimage(x, 0, width, height);
		}
		this.loaded = true;
	}

	@Override
	public void tick(long elapsed) {
		this.imageElapsed += elapsed;
		if (this.animationDelay > 0 && this.imageElapsed > this.animationDelay) {
			this.imageElapsed = 0;
			this.nextFrame();
		}
	}

	@Override
	public BufferedImage imageToPaint() {
		if (this.frames != null) {
			return this.frames[this.imageIndex];
		}
		return null;
	}

	/**
	 * Passe à l'image suivante de l'animation
	 */
	void nextFrame() {
		if (this.frames != null) {
			this.imageIndex = (this.imageIndex + 1) % this.frames.length;
		}
	}
}
