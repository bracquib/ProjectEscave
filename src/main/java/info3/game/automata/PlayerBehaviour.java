package info3.game.automata;

import info3.game.entities.Entity;

public class PlayerBehaviour implements Behaviour {

	@Override
	public boolean true_(Entity e) {
		return true;
	}

	@Override
	public boolean key(Entity e, int keyCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean myDir(Entity e, Direction d) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cell(Entity e, Direction d, Category c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean closest(Entity e, Category c, Direction d) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gotPower(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean gotStuff(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void wizz(Entity e, Direction d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pop(Entity e, Direction d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(Entity e, Direction d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void jump(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hit(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pick(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void throw_(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void store(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void get(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void power(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void explode(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void egg(Entity e) {
		// TODO Auto-generated method stub

	}

}
