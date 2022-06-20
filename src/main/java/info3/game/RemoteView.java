package info3.game;

import info3.game.assets.Paintable;
import info3.game.network.CreateAvatar;
import info3.game.network.DeleteAvatar;

public class RemoteView extends View {
	ClientThread client;

	/**
	 * Constructeur réservé aux tests
	 */
	public RemoteView() {
		super();
	}

	public RemoteView(ClientThread client) {
		super();
		this.controller = client.controller;
		this.client = client;
	}

	@Override
	public Avatar createAvatar(int id, Vec2 pos, Paintable img) {
		Avatar av = new Avatar(id, img);
		av.setPosition(pos);
		this.avatars.put(id, av);
		((LocalController) this.controller).sendToClients(new CreateAvatar(id, pos, img));
		return av;
	}

	@Override
	public void deleteAvatar(int id) {
		super.deleteAvatar(id);
		((LocalController) this.controller).sendToClients(new DeleteAvatar(id));
	}

	@Override
	public void setController(Controller c) {
		this.controller = c;
	}

	@Override
	protected int getWidth() {
		return 1024; // TODO: add a network packet to sync view size
	}

	@Override
	protected int getHeight() {
		return 768;
	}
}
