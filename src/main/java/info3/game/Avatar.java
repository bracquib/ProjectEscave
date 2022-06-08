package info3.game;

import java.awt.Graphics;

public abstract class Avatar {
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
		this.position = position;
	}

	public Vec2 getScale() {
		return scale;
	}

	public void setScale(Vec2 scale) {
		this.scale = scale;
	}

	Vec2 scale;

	protected Avatar(int id) {
		this.id = id;
		this.position = new Vec2(0.0f, 0.0f);
		this.scale = new Vec2(1.0f, 1.0f);
	}

	public abstract void tick(long elapsed);

	public abstract void paint(Graphics g, Vec2 cameraPos);
}
