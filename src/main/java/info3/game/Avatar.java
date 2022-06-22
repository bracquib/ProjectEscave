package info3.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import info3.game.assets.AnimatedImage;
import info3.game.assets.AssetServer;
import info3.game.assets.Paintable;
import info3.game.entities.Block;

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

	private static transient final float DUPLICATE_RANGE = Block.SIZE * 10;

	public transient int[] duplicates;

	private void updateDuplicates(Vec2 pos) {
		if (this.duplicates == null) {
			return;
		}
		int width = Model.getMap().width * Block.SIZE;
		int height = Model.getMap().height * Block.SIZE;

		boolean top = pos.getY() < DUPLICATE_RANGE;
		boolean bottom = pos.getY() > height - DUPLICATE_RANGE;
		boolean left = pos.getX() < DUPLICATE_RANGE;
		boolean right = pos.getX() > width - DUPLICATE_RANGE;

		float x = pos.getX();
		float y = pos.getY();
		float rightX = width + x;
		float bottomY = height + y;
		float leftX = x - width;
		float topY = y - height;

		this.duplicate(top && left, 0, new Vec2(rightX, bottomY));
		this.duplicate(top, 1, new Vec2(x, bottomY));
		this.duplicate(top && right, 2, new Vec2(leftX, bottomY));

		this.duplicate(left, 3, new Vec2(rightX, y));
		this.duplicate(right, 4, new Vec2(leftX, y));

		this.duplicate(bottom && left, 5, new Vec2(rightX, topY));
		this.duplicate(bottom, 6, new Vec2(x, topY));
		this.duplicate(bottom && right, 7, new Vec2(leftX, topY));
	}

	private void duplicate(boolean matches, int i, Vec2 pos) {
		if (matches) {
			if (this.duplicates[i] == -1) {
				this.duplicates[i] = Controller.controller.createAvatar(pos, this.image.clone(), false, this.scale)
						.getId();
			}
			Controller.controller.updateAvatar(this.duplicates[i], pos);
		} else {
			if (this.duplicates[i] != -1) {
				Controller.controller.deleteAvatar(this.duplicates[i]);
				this.duplicates[i] = -1;
			}
		}
	}

	public void setPosition(Vec2 pos) {
		synchronized (this.position) {
			this.updateDuplicates(pos);
			this.position = pos;
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
	public Avatar(int id, Paintable img, boolean dup) {
		this.id = id;
		this.position = new Vec2(0.0f, 0.0f);
		this.scale = new Vec2(2.0f, 2.0f);
		this.image = AssetServer.load(img);
		if (dup) {
			this.duplicates = new int[8];
			Arrays.fill(this.duplicates, -1);
		}
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

	public void setPaintable(Paintable p) {
		this.image = p;
		if (this.image instanceof AnimatedImage) {
			AnimatedImage anim = (AnimatedImage) this.image;
			if (anim.isFinished())
				anim.restart();
		}
	}

	public Paintable getPaintable() {
		return this.image;
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof Avatar && this.id == ((Avatar) other).id;
	}
}
