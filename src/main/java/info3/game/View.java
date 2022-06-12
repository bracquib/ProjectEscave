package info3.game;

import java.util.HashMap;

import info3.game.entities.Player;

public abstract class View {
	static View view;

	/**
	 * Chaque vue affiche un joueur particulier.
	 */
	protected Player player;

	protected Controller controller;

	protected HashMap<Integer, Avatar> avatars;

	protected Camera camera;

	protected View() {
		this.avatars = new HashMap<Integer, Avatar>();
		this.camera = new Camera(Vec2.ZERO);
	}

	public abstract Avatar createAvatar(int id, Vec2 pos, String filename, int imageLen, long animationSpeed);

	public void updateAvatar(int id, Vec2 pos) {
		Avatar a = this.avatars.get(id);
		if (a != null) {
			synchronized (a) {
				a.setPosition(pos);
			}
		}
	}

	public abstract void setController(Controller c);
}
