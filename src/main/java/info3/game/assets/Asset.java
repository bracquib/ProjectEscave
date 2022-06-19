package info3.game.assets;

import java.io.Serializable;

public abstract class Asset implements Serializable {
	String path;

	public Asset(String path) {
		this.path = path;
	}

	public abstract void load();

	public String getPath() {
		return path;
	}
}
