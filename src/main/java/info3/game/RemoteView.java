package info3.game;

import info3.game.assets.Paintable;
import info3.game.network.CreateAvatar;
import info3.game.network.DeleteAvatar;
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
		((LocalController) this.controller).sendToClients(new CreateAvatar(av));
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
	public void updateAvatar(int id, Paintable p, Vec2 pos) {
		((LocalController) this.controller).sendToClients(new UpdateAvatar(id, p, pos));
	}

	@Override
	public void setFollowedAvatar(Avatar syncWith) {
		((LocalController) this.controller).sendTo(this.getPlayer(), new SyncCamera(syncWith));
	}
}
