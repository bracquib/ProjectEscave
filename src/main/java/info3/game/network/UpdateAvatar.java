package info3.game.network;

import info3.game.Vec2;

public class UpdateAvatar extends NetworkMessage {
	private static final long serialVersionUID = -1971833338337993227L;

	public Vec2 position;
	public int avatarId;

	public UpdateAvatar(int id, Vec2 pos) {
		this.position = pos;
		this.avatarId = id;
	}
}
