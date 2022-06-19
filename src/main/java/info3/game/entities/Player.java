package info3.game.entities;

import info3.game.Controller;
import info3.game.Vec2;
import info3.game.physics.RigidBody;

public class Player extends RigidBody {
	PlayerColor color;
	private float hungerPoints;
	private float maxHunger = 100;
	private float thirstPoints;
	private float maxThirst = 100;

	public Player(Controller c, PlayerColor color, Vec2 pos, boolean local) {
		super(1, c);
		this.setPosition(pos);
		this.color = color;
		if (local) {
			this.avatar = this.controller.createAvatar(new Vec2(this.getPosition()), this.avatarPath(), 1, 0);
		}
		this.hungerPoints = maxHunger;
		this.thirstPoints = maxThirst;
	}

	@Override
	public void tick(long el) {
		super.tick(el);
		System.out.println(String.format("Player pos %f %f", this.getPosition().getX(), this.getPosition().getY()));
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

	public float getHungerPoints() {
		return this.hungerPoints;
	}

	public void setHungerPoints(float hungerPoints) {
		this.hungerPoints = hungerPoints;
	}

	public void feed(float feedPoints) {
		this.hungerPoints += feedPoints;
		if (this.hungerPoints > maxHunger)
			this.hungerPoints = maxHunger;
	}

	public float getThirstPoints() {
		return this.thirstPoints;
	}

	public void setThirstPoints(float thirstPoints) {
		this.thirstPoints = thirstPoints;
	}

	public void water(float waterPoints) {
		this.thirstPoints += waterPoints;
		if (this.thirstPoints > maxThirst)
			this.thirstPoints = maxThirst;
	}

}
