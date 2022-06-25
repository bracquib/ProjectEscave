package info3.game.entities;

import java.util.ArrayList;

import info3.game.Avatar;
import info3.game.HUD;
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
	private int hungerPoints;
	private int maxHunger = 10;
	private int thirstPoints;
	private int maxThirst = 10;
	private Inventory inventory;
	private int compt;
	Entity controlledEntity;
	public ArrayList<Integer> pressedKeys;
	private Avatar background;
	public HUD hud;

	static final float bgW = 2461 * 2;
	static final float bgH = 1675 * 2;
	static Vec2[] bgDiffs = { new Vec2(-bgW, -bgH), new Vec2(0, -bgH), new Vec2(bgW, -bgH), new Vec2(-bgW, 0),
			new Vec2(bgW, 0), new Vec2(-bgW, bgH), new Vec2(0, bgH), new Vec2(bgW, bgH) };

	public Player(LocalController c, PlayerColor color, Vec2 pos, int points) {
		super(1, c, points);
		this.color = color;
		this.avatarOffset = new Vec2(0, -20);
		this.collider = new BoxCollider(Block.SIZE - 3, Block.SIZE - 3, 1, 1);

		this.hungerPoints = maxHunger;
		this.thirstPoints = maxThirst;

		this.setPosition(pos);
		this.setCategory(Category.PLAYER);
		this.setAutomata(Model.getAutomata("Player"));
		this.setBehaviour(new PlayerBehaviour());
		this.avatarOffset = new Vec2(0, -4);
		AnimatedImage sprite = new AnimatedImage(this.avatarPath(), 6, 200, true);
		AnimatedImage spriteBackground = new AnimatedImage("bg_animated.png", 12, 1250, true);
		spriteBackground.fixed = true;
		spriteBackground.layer = -1;
		sprite.layer = 1;
		this.avatar = this.controller.createAvatar(this.getPosition().add(this.avatarOffset), sprite);

		Vec2 bgPos = setBackground();
		this.background = this.controller.createAvatar(bgPos, spriteBackground);
		int i = 0;
		for (Vec2 diff : bgDiffs) {
			this.background.duplicates[i] = this.controller.createAvatar(bgPos.add(diff), spriteBackground);
			i++;
		}
		this.inventory = Inventory.createInventory(c, this);
		this.hud = new HUD(this.controller, this);

		this.pressedKeys = new ArrayList<Integer>();
		this.setControlledEntity(this);
		this.playAnimation("spawn", 9, 100, 0, -10, false);
	}

	public void setControlledEntity(Entity entity) {
		this.controlledEntity = entity;
		this.controller.syncCamera(this.color, entity);
	}

	@Override
	public void setPosition(Vec2 pos) {
		super.setPosition(pos);
		if (this.background != null) {
			Vec2 bgPos = this.setBackground();
			this.background.setPosition(bgPos);

			int i = 0;
			for (Vec2 diff : bgDiffs) {
				this.controller.updateAvatar(this.background.duplicates[i].getId(), bgPos.add(diff));
				i++;
			}
		}
	}

	public Vec2 setBackground() {
		Vec2 goodPos = new Vec2(0, 0);
		float heightMap = Model.getMap().height * Block.SIZE;
		float widthMap = Model.getMap().width * Block.SIZE;
		float xPlayer = this.getPosition().getX();
		float yPlayer = this.getPosition().getY();
		float ratioX = xPlayer / widthMap;
		float ratioY = yPlayer / heightMap;
		float posInBackX = bgW * ratioX;
		float posInBackY = bgH * ratioY;

		Vec2 viewSize = this.controller.viewFor(this.color).getDimensions();
		goodPos.setX(-posInBackX + viewSize.getX() / 2 + 16);
		goodPos.setY(-posInBackY + viewSize.getY() / 2 + 16);
		return goodPos;
	}

	@Override
	public void tick(long el) {
		super.tick(el);
		compt += el;
		if (compt > 100000) {
			this.setHungerPoints(this.hungerPoints - 1);
			this.setThirstPoints(this.thirstPoints - 1);
			compt = 0;
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
		// Pas en doublure ni en statue
		if (anim.isFinished() && this.getBehaviour() instanceof PlayerBehaviour
				&& this.getCategory() == Category.PLAYER) {
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

	public int getHungerPoints() {
		return this.hungerPoints;
	}

	public void setHungerPoints(int hungerPoints) {
		int diff = this.hungerPoints - hungerPoints;
		this.hungerPoints = hungerPoints;
		if (this.hungerPoints > maxHunger)
			this.hungerPoints = maxHunger;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				this.hud.loseFood(this.hungerPoints);
			}
		} else if (diff < 0) {
			for (int i = 0; i < -diff; i++) {
				this.hud.gainFood(this.hungerPoints);
			}
		}
	}

	public void feed(int feedPoints) {
		this.setHungerPoints(this.hungerPoints + feedPoints);
	}

	public int getThirstPoints() {
		return this.thirstPoints;
	}

	public void setThirstPoints(int thirstPoints) {
		int diff = this.thirstPoints - thirstPoints;
		this.thirstPoints = thirstPoints;
		if (this.thirstPoints > maxThirst)
			this.thirstPoints = maxThirst;
		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				this.hud.loseWater(this.thirstPoints);
			}
		} else if (diff < 0) {
			for (int i = 0; i < -diff; i++) {
				this.hud.gainWater(this.thirstPoints);
			}
		}
	}

	public void water(int waterPoints) {
		this.setThirstPoints(this.thirstPoints + waterPoints);
	}

	public Inventory getInventory() {
		return inventory;
	}

	public Entity getControlledEntity() {
		return this.controlledEntity;
	}

}
