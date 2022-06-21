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

	public Player(LocalController c, PlayerColor color, Vec2 pos, boolean local, int points) {
		super(1, c, points);
		this.color = color;
		if (local) {
			this.setPosition(pos);
			this.setCategory(Category.PLAYER);
			this.setAutomata(Model.getAutomata("Player"));
			this.setBehaviour(new PlayerBehaviour());
			this.avatarOffset = new Vec2(0, -4);
			AnimatedImage sprite = new AnimatedImage(this.avatarPath(), 6, 200);
			sprite.layer = 1;
			this.avatar = this.controller.createAvatar(this.getPosition().add(this.avatarOffset), sprite);
			this.inventory = Inventory.createInventory(c, this);

			this.hungerPoints = maxHunger;
			this.thirstPoints = maxThirst;
		}
	}

	@Override
	public void tick(long el) {
		super.tick(el);
		float curXSpeed = this.getSpeed().getX();
		if (Math.abs(curXSpeed) > 5) {
			if (curXSpeed < 0) {
				this.getSpeed().setX(curXSpeed + 4f);
			} else {
				this.getSpeed().setX(curXSpeed - 4f);
			}
		} else {
			this.getSpeed().setX(0);
		}

		if (this.controller.isKeyPressed(65)) { // touche A
			this.playAnimation("spawn", 9, 100, -10);
		}
		if (this.controller.isKeyPressed(66)) {
			this.playAnimation("idle-right", 6, 200, -4);
		}
		if (this.controller.isKeyPressed(67)) {
			this.playAnimation("attack", 5, 200, -4);
		}
		if (this.controller.isKeyPressed(68)) {
			this.playAnimation("check", 6, 200, -4);
		}
		if (this.controller.isKeyPressed(69)) {
			this.playAnimation("dig", 21, 200, -4);
		}
		if (this.controller.isKeyPressed(70)) {
			this.playAnimation("idle-left", 6, 200, -4);
		}
		if (this.controller.isKeyPressed(71)) {
			this.playAnimation("mine", 5, 200, -32);
		}
		if (this.controller.isKeyPressed(72)) {
			this.playAnimation("walk-left", 5, 200, -4);
		}
		if (this.controller.isKeyPressed(73)) {
			this.playAnimation("walk-right", 5, 100, -4);
		}

	}

	@Override
	public String animationDir() {
		return "player/" + this.name().toLowerCase();
	}

	private String avatarPath() {
		return "player/" + this.name().toLowerCase() + "/idle-right.png";
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
