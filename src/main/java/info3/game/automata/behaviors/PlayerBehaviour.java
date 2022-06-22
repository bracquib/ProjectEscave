package info3.game.automata.behaviors;

import info3.game.Inventory;
import info3.game.Model;
import info3.game.automata.Category;
import info3.game.automata.Direction;
import info3.game.entities.Entity;
import info3.game.entities.Player;
import info3.game.physics.RigidBody;

public class PlayerBehaviour extends Behaviour {

	public Entity ret;

	@Override
	public boolean true_(Entity e) {
		return true;
	}

	@Override
	public boolean key(Entity e, int keyCode) {
		// à faire
		// System.out.println("[DEBUG] Checking key " + keyCode);
		return e.getController().isKeyPressed(keyCode);
	}

	@Override
	public boolean myDir(Entity e, Direction d) {
		// pas besoin pour player
		return false;
	}

	@Override
	public boolean closest(Entity e, Category c, Direction d, int diam_vision) {
		int diam = 320; // en pixel
		return super.closest(e, c, d, diam);
	}

	@Override
	public boolean gotPower(Entity e) {

		if (e.pointsDeVie > 0) {
			return true;
		}

		return false;
	}

	@Override
	public boolean gotStuff(Entity e) {
		// pas besoin pour player
		return false;
	}

	@Override
	public void wizz(Entity e, Direction d) {
		// wizz=jump
		/*
		 * RigidBody p = new RigidBody(e, 1, 10); p.getSpeed().setY(-120);
		 */
//		((RigidBody) e).getSpeed().setY(-270);
//		return;
		// wizz = prise de contrôle de la statue
		// e.setCategory(Category.SOMETHING);
	}

	@Override
	public void pop(Entity e, Direction d) {
		// pop=hit à faire

		/*
		 * if (cell(e, d, Category.ADVERSAIRE)) { ret.getBehaviour().protect(ret, d,
		 * e.degatEpee); } else if (cell(e, d, Category.JUMPABLE)) {
		 * ret.getBehaviour().wizz(ret, d); // peut etre à ajuster } return;
		 */
		Player p = (Player) e;
		Inventory inv = p.getInventory();
		inv.mousePos = p.mousePos;
		inv.getBehaviour().pop(inv, d);

	}

	@Override
	public void move(Entity e, Direction d) {

		RigidBody p = (RigidBody) e;
		// new RigidBody(e, 1, 10);
		switch (d) {
		case EST:
			p.getSpeed().setX(190);
			break;
		case WEST:
			p.getSpeed().setX(-190);
			break;
		default:
			break;

		}

		return;
	}

	@Override
	public void move(Entity e) {
		// pas besoin pour player

	}

	@Override
	public void jump(Entity e) {
		// jump=wizz donc fait dans wizz
		((RigidBody) e).getSpeed().setY(-270);
	}

	@Override
	public void hit(Entity e) {
		// pop=hit donc fait dans pop
	}

	@Override
	public void pick(Entity e) {
		// pas besoin pour le player
	}

	@Override
	public void throw_(Entity e) {
		// pas besoin pour le player

	}

	@Override
	public void store(Entity e) {
		// pas besoin pour le player

	}

	@Override
	public void get(Entity e) {
		// pas besoin pour le player

	}

	@Override
	public void power(Entity e) {
		// jamais utilisé

	}

	@Override
	public void explode(Entity e) {
		// pas besoin pour le player

	}

	@Override
	public void egg(Entity e, Direction d) {
		// pas besoin pour le player

	}

	@Override
	public void protect(Entity e, Direction d, int dmg) {
		e.pointsDeVie -= dmg;
		System.out.println("HP=" + e.pointsDeVie);
		RigidBody p = (RigidBody) e;
		if (e.pointsDeVie == 0) {
			System.out.println("mort du joueur");
			Model.deleteentities(p);
		}
		switch (d) {
		case SOUTH:
			p.getSpeed().setY(-240);
			break;
		case EST:
			p.getSpeed().setX(240);
			break;
		case WEST:
			p.getSpeed().setX(-240);
			break;
		case NORTH:
			p.getSpeed().setY(240);
			break;
		case NORTHEST:
			p.getSpeed().setY(240);
			p.getSpeed().setX(240);
			break;
		case NORTHWEST:
			p.getSpeed().setY(240);
			p.getSpeed().setX(-240);
			break;
		case SOUTHEST:
			p.getSpeed().setY(-240);
			p.getSpeed().setX(240);
			break;
		case SOUTHWEST:
			p.getSpeed().setY(-240);
			p.getSpeed().setX(-240);
			break;
		default:
			break;
		}
		return;
	}

}
