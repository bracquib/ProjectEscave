package info3.game.network;

import info3.game.Avatar;
import info3.game.Vec2;
import info3.game.assets.Paintable;

public class CreateAvatar extends NetworkMessage {
	private static final long serialVersionUID = 2693943721618745832L;

	public int id;
	public Vec2 position;
	public Vec2 scale;
	public Paintable image;

	public CreateAvatar(Avatar av) {
		this.id = av.getId();
		this.image = av.getPaintable();
		this.position = av.getPosition();
		this.scale = av.getScale();
	}

	public CreateAvatar(int id, Vec2 position, Vec2 scale, Paintable image) {
		this.id = id;
		this.image = image;
		this.position = position;
		this.scale = scale;
	}
}
