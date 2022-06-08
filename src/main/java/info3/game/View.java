package info3.game;

import java.util.HashMap;

public abstract class View {
	static View view;

	protected Controller controller;

	protected HashMap<Integer, Avatar> avatars;

	protected View() {
		this.avatars = new HashMap<Integer, Avatar>();
	}

	public abstract Avatar createAvatar(int id, Vec2 pos, String filename, int imageLen, int animationSpeed);

	public void updateAvatar(int id, Vec2 pos) {
		Avatar a = this.avatars.get(id);
		if (a != null) {
			a.setPosition(pos);
		}
	}

	public abstract void setController(Controller c);
}
