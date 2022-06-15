package info3.game;

import info3.game.assets.Paintable;
import info3.game.entities.Player;
import info3.game.network.KeyPress;

public abstract class Controller {
	static Controller controller;

	protected Controller() {
		Controller.controller = this;
	}

	public abstract void keyPressed(Player p, KeyPress e);

	public abstract void tick(long elapsed);

	public abstract void addView(View v);

	static int avatarID = 0;

	protected abstract void removeView(RemoteView view);

	public abstract Avatar createAvatar(Vec2 pos, Paintable image);
}
