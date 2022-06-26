package info3.game;

import info3.game.assets.Paintable;
import info3.game.network.Close;
import info3.game.network.CreateAvatar;
import info3.game.network.DeleteAvatar;
import info3.game.network.PlaySound;
import info3.game.network.SyncCamera;
import info3.game.network.UpdateAvatar;

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
	public void createAvatar(Avatar av) {
		this.avatars.put(av.getId(), av);
		this.client.send(new CreateAvatar(av));
	}

	@Override
	public void deleteAvatar(int id) {
		super.deleteAvatar(id);
		this.client.send(new DeleteAvatar(id));
	}

	@Override
	public void setController(Controller c) {
		this.controller = c;
	}

	@Override
	public void updateAvatar(int id, Vec2 pos) {
		super.updateAvatar(id, pos);
		this.client.send(new UpdateAvatar(id, pos));
	}

	@Override
	public void updateAvatar(int id, String path) {
		super.updateAvatar(id, path);
		this.client.send(new UpdateAvatar(id, path));
	}

	@Override
	public void updateAvatar(int id, Paintable p, Vec2 offset, Vec2 pos) {
		this.client.send(new UpdateAvatar(id, p, offset, pos));
	}

	@Override
	protected void syncCamera(Avatar syncWith) {
		this.setFollowedAvatar(syncWith);
		this.client.send(new SyncCamera(syncWith));
	}

	public void playSound(int idx) {
		this.client.send(new PlaySound(idx));
	}

	@Override
	public void close() {
		this.client.send(new Close());
	}
}
