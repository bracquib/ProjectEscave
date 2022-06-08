package info3.game.network;

import info3.game.Vec2;

public class CreateAvatar extends NetworkMessage {
	private static final long serialVersionUID = 2693943721618745832L;

	public int id;
	public String filename;
	public int imageLen;
	public int animationDelay;
	public Vec2 position;

	public CreateAvatar(int id, Vec2 position, String filename, int imageLen, int animationSpeed) {
		this.id = id;
		this.filename = filename;
		this.imageLen = imageLen;
		this.animationDelay = animationSpeed;
		this.position = position;
	}
}
