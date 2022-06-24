package info3.game.network;

import info3.game.Vec2;

public class JoinGame extends NetworkMessage {
	private static final long serialVersionUID = 3365083741581125257L;

	public Vec2 screenSize;

	public JoinGame(Vec2 screen) {
		this.screenSize = screen;
	}
}
