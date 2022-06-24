package info3.game.entities;

import java.util.ArrayList;

import info3.game.Inventory;
import info3.game.LocalController;
import info3.game.Model;
import info3.game.Vec2;
import info3.game.assets.AnimatedImage;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.automata.behaviors.PlayerBehaviour;
import info3.game.physics.BoxCollider;
import info3.game.physics.RigidBody;

public class Player extends RigidBody {
	PlayerColor color;
	private float hungerPoints;
	private float maxHunger = 100;
	private float thirstPoints;
	private float maxThirst = 100;
	private Inventory inventory;
	private int compt;
	Entity controlledEntity;
	public ArrayList<Integer> pressedKeys;

	public Player(LocalController c, PlayerColor color, Vec2 pos, boolean local, int points) {
		super(1, c, points);
		this.color = color;
		this.avatarOffset = new Vec2(0, -20);
		this.collider = new BoxCollider(Block.SIZE - 3, Block.SIZE - 3, 1, 1);

		if (local) {
			this.setPosition(pos);
			this.setCategory(Category.PLAYER);
			this.setAutomata(Model.getAutomata("Player"));
			this.setBehaviour(new PlayerBehaviour());
			this.avatarOffset = new Vec2(0, -4);
			AnimatedImage sprite = new AnimatedImage(this.avatarPath(), 6, 200, true);
			sprite.layer = 1;
			this.avatar = this.controller.createAvatar(this.getPosition().add(this.avatarOffset), sprite);
			this.inventory = Inventory.createInventory(c, this);

			this.pressedKeys = new ArrayList<Integer>();

			this.hungerPoints = maxHunger;
			this.thirstPoints = maxThirst;
			this.setControlledEntity(this);
			this.playAnimation("spawn", 9, 100, 0, -10, false);
		}
	}

	public void setControlledEntity(Entity entity) {
		this.controlledEntity = entity;
		this.controller.syncCamera(this.color, entity);
	}

	@Override
	public void tick(long el) {
		super.tick(el);
		compt++;
		if (compt % 10000 == 0) {
			this.hungerPoints -= 5;
			this.thirstPoints -= 5;
		}
		if (this.hungerPoints <= 0 || this.thirstPoints <= 0) {
			System.out.println("mort du joueur à cause de la faim ou de la soif");
			Model.deleteEntity(this);
		}

		super.tick(el);
		float curXSpeed = this.getSpeed().getX();
		if (Math.abs(curXSpeed) > 5) {
			if (curXSpeed < 0) {
				this.getSpeed().setX(curXSpeed + 7f);
			} else {
				this.getSpeed().setX(curXSpeed - 7f);
			}
		} else {
			this.getSpeed().setX(0);
		}

		AnimatedImage anim = (AnimatedImage) this.getPaintable();
		if (anim.isFinished() && this.getBehaviour() instanceof PlayerBehaviour) {
			if (this.getDirection() == Direction.EST) {
				this.playAnimation("idle-right", 6, 200, 0, -4, true);
			} else {
				this.playAnimation("idle-left", 6, 200, 0, -4, true);
			}
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

	public Entity getControlledEntity() {
		return this.controlledEntity;
	}

}
