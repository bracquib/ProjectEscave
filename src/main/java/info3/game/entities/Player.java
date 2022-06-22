package info3.game.entities;

import info3.game.Inventory;
import info3.game.LocalController;
import info3.game.Model;
import info3.game.Vec2;
import info3.game.assets.AnimatedImage;
import info3.game.automata.Category;
import info3.game.automata.behaviors.PlayerBehaviour;
import info3.game.physics.RigidBody;

public class Player extends RigidBody {
	PlayerColor color;
	private float hungerPoints;
	private float maxHunger = 100;
	private float thirstPoints;
	private float maxThirst = 100;
	private Inventory inventory;
	private int compt;

	public Player(LocalController c, PlayerColor color, Vec2 pos, boolean local, int points) {
		super(1, c, points);
		this.setPosition(pos);
		this.color = color;
		this.setCategory(Category.PLAYER);
		this.setAutomata(Model.getAutomata("Player"));
		this.setBehaviour(new PlayerBehaviour());
		this.avatarOffset = new Vec2(0, -10);
		if (local) {
			AnimatedImage sprite = new AnimatedImage(this.avatarPath(), 9, 100);
			sprite.layer = 1;
			this.avatar = this.controller.createAvatar(this.getPosition().add(this.avatarOffset), sprite);
			// this.inventory = new Inventory(c, this);
			this.inventory = Inventory.createInventory(c, this);
		}
		this.hungerPoints = maxHunger;
		this.thirstPoints = maxThirst;
	}

	@Override
	public void tick(long el) {
		compt++;
		if (compt % 10000 == 0) {
			this.hungerPoints -= 5;
			this.thirstPoints -= 5;
		}
		super.tick(el);
		float curXSpeed = this.getSpeed().getX();
		if (Math.abs(curXSpeed) > 3) {
			if (curXSpeed < 0)
				this.getSpeed().setX(curXSpeed + 7f);
			else
				this.getSpeed().setX(curXSpeed - 7f);
		} else
			this.getSpeed().setX(0);
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

	public float getHungerPoints() {
		return this.hungerPoints;
	}

	public void setHungerPoints(float hungerPoints) {
		this.hungerPoints = hungerPoints;
		if (this.hungerPoints > maxHunger)
			this.hungerPoints = maxHunger;
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
		if (this.thirstPoints > maxThirst)
			this.thirstPoints = maxThirst;
	}

	public void water(float waterPoints) {
		this.thirstPoints += waterPoints;
		if (this.thirstPoints > maxThirst)
			this.thirstPoints = maxThirst;
	}

	public Inventory getInventory() {
		return inventory;
	}

}
