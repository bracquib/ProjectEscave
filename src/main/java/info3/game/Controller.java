package info3.game;

import info3.game.assets.Paintable;
import info3.game.entities.PlayerColor;
import info3.game.network.KeyPress;
import info3.game.network.KeyRelease;
import info3.game.network.MouseClick;
import info3.game.network.WheelScroll;

public abstract class Controller {
	static Controller controller;

	protected Controller() {
		Controller.controller = this;
	}

	public abstract void keyPressed(PlayerColor p, KeyPress e);

	public abstract void keyReleased(PlayerColor p, KeyRelease e);

	public abstract void windowResize(PlayerColor p, Vec2 size);

	public abstract void tick(long elapsed);

	public abstract void addView(View v);

	static int avatarID = 0;

	protected abstract void removeView(RemoteView view);

	public Avatar createAvatar(Vec2 pos, Paintable image) {
		return this.createAvatar(pos, image, true);
	}

	public abstract Avatar createAvatar(Vec2 pos, Paintable image, boolean withDuplicates);

	protected abstract void mouseScroll(PlayerColor p, WheelScroll wheelScroll);

	protected abstract void mouseClick(PlayerColor player, MouseClick mouseClick);

	public abstract void deleteAvatar(int avatarId);

	protected abstract void updateAvatar(int i, Vec2 pos);
}
