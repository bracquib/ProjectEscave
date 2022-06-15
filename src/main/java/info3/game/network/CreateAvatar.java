package info3.game.network;

import info3.game.Vec2;
import info3.game.assets.Paintable;

public class CreateAvatar extends NetworkMessage {
	private static final long serialVersionUID = 2693943721618745832L;

	public int id;
	public Vec2 position;
	public Paintable image;

	public CreateAvatar(int id, Vec2 position, Paintable image) {
		this.id = id;
		this.image = image;
		this.position = position;
	}
}
