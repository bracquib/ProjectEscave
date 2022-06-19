package info3.game.entities;

import info3.game.LocalController;
import info3.game.Vec2;
import info3.game.assets.AnimatedImage;
import info3.game.physics.RigidBody;

public class Player extends RigidBody {
	PlayerColor color;

	public Player(LocalController c, PlayerColor color, Vec2 pos, boolean local) {
		super(1, c);
		this.setPosition(pos);
		this.color = color;
		this.avatarOffset = new Vec2(0, -10);
		if (local) {
			this.avatar = this.controller.createAvatar(this.getPosition().add(this.avatarOffset),
					new AnimatedImage(this.avatarPath(), 9, 100));
		}
	}

	@Override
	public void tick(long el) {
		super.tick(el);
	}

	private String avatarPath() {
		return "mole-" + this.name().toLowerCase() + ".png";
	}

	public String name() {
		switch (this.color) {
		case BLUE:
			return "Bleu";
		case RED:
			return "Rouge";
		case GREEN:
			return "Vert";
		case YELLOW:
			return "Jaune";
		case ORANGE:
			return "Orange";
		case PURPLE:
			return "Violet";
		case WHITE:
			return "Blanc";
		case BLACK:
			return "Noir";
		}
		return "_";
	}

	public PlayerColor getColor() {
		return this.color;
	}

	public static PlayerColor colorFromInt(int player) {
		switch (player) {
		case 0:
			return PlayerColor.YELLOW;
		case 1:
			return PlayerColor.RED;
		case 2:
			return PlayerColor.GREEN;
		case 3:
			return PlayerColor.BLUE;
		case 4:
			return PlayerColor.ORANGE;
		case 5:
			return PlayerColor.PURPLE;
		case 6:
			return PlayerColor.WHITE;
		default:
			return PlayerColor.BLACK;
		}
	}
}
