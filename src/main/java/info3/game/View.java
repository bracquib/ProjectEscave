package info3.game;

import java.util.HashMap;

import info3.game.assets.Paintable;
import info3.game.entities.PlayerColor;

public abstract class View {
	static View view;

	/**
	 * Chaque vue affiche un joueur particulier.
	 */
	private PlayerColor player;

	protected Controller controller;

	protected HashMap<Integer, Avatar> avatars;

	protected Camera camera;

	private Vec2 dimensions;

	protected View() {
		this.avatars = new HashMap<Integer, Avatar>();
		this.camera = new Camera(null, this);
		View.view = this;
	}

	public abstract void createAvatar(Avatar av);

	public void deleteAvatar(int id) {
		Avatar av = this.avatars.remove(id);
		if (av != null && av.duplicates != null) {
			for (int dup : av.duplicates) {
				this.controller.deleteAvatar(dup);
			}
		}
	}

	public void updateAvatar(int id, Vec2 pos) {
		Avatar a = this.avatars.get(id);
		if (a != null) {
			synchronized (a) {
				a.setPosition(pos);
			}
		}
	}

	public void updateAvatar(int id, String path) {
		Avatar a = this.avatars.get(id);
		if (a != null) {
			synchronized (a) {
				a.setPaintablePath(path);
			}
		}
	}

	public abstract void setController(Controller c);

	protected Avatar getAvatar(int avatarId) {
		return this.avatars.get(avatarId);
	}

	public PlayerColor getPlayer() {
		return player;
	}

	public void setPlayer(PlayerColor player) {
		this.player = player;
	}

	public void setFollowedAvatar(Avatar a) {
		this.camera.setAvatar(a);
	}

	protected int getWidth() {
		return (int) this.dimensions.getX();
	}

	protected int getHeight() {
		return (int) this.dimensions.getY();
	}

	public abstract void updateAvatar(int id, Paintable p, Vec2 pos);

	public void setDimensions(Vec2 size) {
		this.dimensions = size;
	}

	public Vec2 getDimensions() {
		return this.dimensions;
	}
}
