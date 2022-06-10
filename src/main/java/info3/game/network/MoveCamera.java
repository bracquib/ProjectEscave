package info3.game.network;

import info3.game.Vec2;

public class MoveCamera extends NetworkMessage {
	private static final long serialVersionUID = 8811547359177873107L;

	public Vec2 position;

	public MoveCamera(Vec2 pos) {
		this.position = pos;
	}
}
