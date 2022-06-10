package info3.game;

import info3.game.network.KeyPress;

public abstract class Controller {
	static Controller controller;

	public Model model;

	protected Controller() {
		Controller.controller = this;
		this.model = new Model(this);
	}

	public abstract void keyPressed(KeyPress e);

	public abstract void tick(long elapsed);

	public abstract void addView(View v);

	static int avatarID = 0;

	public abstract Avatar createAvatar(Vec2 pos, String string, int imageLen, int animationDelay);

	protected abstract void removeView(RemoteView view);
}
