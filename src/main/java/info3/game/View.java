package info3.game;

import java.util.HashMap;

import info3.game.assets.Paintable;
import info3.game.entities.Player;

public abstract class View {
	static View view;

	/**
	 * Chaque vue affiche un joueur particulier.
	 */
	private Player player;

	protected Controller controller;

	protected HashMap<Integer, Avatar> avatars;

	protected Camera camera;

	protected View() {
		this.avatars = new HashMap<Integer, Avatar>();
		this.camera = new Camera(null);
	}

	public abstract Avatar createAvatar(int id, Vec2 pos, Paintable image, boolean dup);

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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
		this.setFollowedAvatar(player.getAvatar());
	}

	public void setFollowedAvatar(Avatar a) {
		this.camera.setAvatar(a);
	}

	protected abstract int getWidth();

	protected abstract int getHeight();

	public abstract void updateAvatar(int id, Paintable p, Vec2 pos);
}
