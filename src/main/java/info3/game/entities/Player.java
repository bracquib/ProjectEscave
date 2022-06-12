package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;

public class Player extends Entity {
	PlayerColor color;

	public Player(Controller c, PlayerColor color, Vec2 pos, boolean local) {
		super(c);
		this.position = pos;
		this.color = color;
		if (local) {
			this.avatar = this.controller.createAvatar(new Vec2(this.position), this.avatarPath(), 1, 0);
		}
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
			return "Purple";
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
			return PlayerColor.BLUE;
		case 1:
			return PlayerColor.RED;
		case 2:
			return PlayerColor.GREEN;
		case 3:
			return PlayerColor.YELLOW;
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
