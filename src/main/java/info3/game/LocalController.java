package info3.game;

import java.util.ArrayList;

import info3.game.entities.Cowboy;
import info3.game.entities.Entity;
import info3.game.network.KeyPress;
import info3.game.network.MoveCamera;
import info3.game.network.NetworkMessage;

public class LocalController extends Controller {
	ArrayList<View> views;
	ArrayList<Entity> spawnQueue = new ArrayList<Entity>();

	public LocalController() {
		super();
		this.views = new ArrayList<View>();
	}

	public LocalController(ArrayList<View> views) {
		this.views = views;
		for (View v : views) {
			v.setController(this);
		}
	}

	@Override
	public void addView(View v) {
		this.views.add(v);
	}

	@Override
	public void keyPressed(KeyPress e) {
		System.out.println("key press on the server : " + e.code);
		if (e.code == 32) {
			Cowboy c = new Cowboy(this);
			c.setPosition(new Vec2(100, 100));
			this.spawn(c);
		}
		// Mouvements de camera
		if (e.code >= 37 && e.code <= 40) {
			switch (e.code) {
			case 37:
				// Left
				this.views.get(0).camera.translate(new Vec2(-5, 0));
				break;
			case 38:
				// Up
				this.views.get(0).camera.translate(new Vec2(0, 5));
				break;
			case 39:
				// Right
				this.views.get(0).camera.translate(new Vec2(5, 0));
				break;
			case 40:
				// Down
				this.views.get(0).camera.translate(new Vec2(0, -5));
				break;
			}
			this.sendToClients(new MoveCamera(this.views.get(0).camera.getPos()));
		}
	}

	@Override
	public void tick(long elapsed) {
		this.entities.addAll(this.spawnQueue);
		this.spawnQueue.clear();
		for (Entity e : this.entities) {
			e.tick(elapsed);
		}
	}

	@Override
	public void spawn(Entity e) {
		this.spawnQueue.add(e);
	}

	@Override
	public Avatar createAvatar(Vec2 pos, String string, int imageLen, int animationDelay) {
		int id = Controller.avatarID;
		Controller.avatarID++;
		Avatar a = null;
		for (View v : this.views) {
			a = v.createAvatar(id, pos, string, imageLen, animationDelay);
		}
		return a;
	}

	public void sendToClients(NetworkMessage msg) {
		for (View v : views) {
			if (v instanceof RemoteView) {
				RemoteView rv = (RemoteView) v;
				if (rv.client != null) {
					rv.client.send(msg);
				}
			}
		}
	}

	@Override
	protected void removeView(RemoteView view) {
		this.views.remove(view);
	}
}
