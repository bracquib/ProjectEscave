package info3.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.assets.AssetServer;
import info3.game.assets.Paintable;

public class Avatar {
	int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	Vec2 position;

	public Vec2 getPosition() {
		return position;
	}

	public void setPosition(Vec2 position) {
		synchronized (this.position) {
			this.position = position;
		}
	}

	public Vec2 getScale() {
		return scale;
	}

	public void setScale(Vec2 scale) {
		this.scale = scale;
	}

	Vec2 scale;

	Paintable image;

	/**
	 * Crée un nouvel avatar.
	 * 
	 * @param img L'asset pour cet avatar
	 */
	public Avatar(int id, Paintable img) {
		this.id = id;
		this.position = new Vec2(0.0f, 0.0f);
		this.scale = new Vec2(1.0f, 1.0f);
		this.image = AssetServer.load(img);
	}

	/**
	 * Mets à jour le temps écoulé pour savoir si on doit passer à l'image suivante
	 * de l'animation ou non.
	 * 
	 * @param elapsed Le nombre de millisecondes qui se sont écoulées depuis le
	 *                dernier tick.
	 */
	public void tick(long elapsed) {
		this.image.tick(elapsed);
	}

	/**
	 * Dessine l'image sur l'écran.
	 * 
	 * @param g         La toile sur laquelle on dessine
	 * @param cameraPos La position de la caméra dans le monde
	 */
	public void paint(Graphics g, Vec2 cameraPos) {
		Vec2 screenCoords;
		if (!this.image.fixed) {
			screenCoords = this.position.globalToScreen(cameraPos);
		} else {
			screenCoords = this.position;
		}
		BufferedImage img = this.image.imageToPaint();
		if (img != null) {
			g.drawImage(img, (int) screenCoords.getX(), (int) screenCoords.getY(),
					((int) scale.getX()) * img.getWidth(), ((int) scale.getY()) * img.getHeight(), null);
		}
	}

	public void setPaintablePath(String path) {
		this.image = this.image.duplicateFromPath(path);
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Avatar && this.id == ((Avatar) other).id;
	}

}
