package info3.game.network;

import info3.game.Vec2;

public class UpdateAvatar extends NetworkMessage {
	private static final long serialVersionUID = -1971833338337993227L;

	public Vec2 position;
	public int avatarId;
	public String newPath;

	public UpdateAvatar(int id, Vec2 pos) {
		this.position = pos;
		this.avatarId = id;
	}

	public UpdateAvatar(int id, String path) {
		this.avatarId = id;
		this.newPath = path;
	}
}
