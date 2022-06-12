package info3.game;

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
	public Avatar createAvatar(int id, Vec2 pos, String filename, int imageLen, long animationSpeed) {
		RemoteAvatar av = new RemoteAvatar(id, (LocalController) this.controller, filename, imageLen, animationSpeed);
		av.setPosition(pos);
		this.avatars.put(id, av);
		return av;
	}

	@Override
	public void setController(Controller c) {
		this.controller = c;
	}
}
