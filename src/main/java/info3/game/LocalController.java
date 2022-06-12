package info3.game;

import java.util.ArrayList;

import info3.game.entities.Cowboy;
import info3.game.entities.Entity;
import info3.game.entities.Player;
import info3.game.network.CreateAvatar;
import info3.game.network.KeyPress;
import info3.game.network.MoveCamera;
import info3.game.network.NetworkMessage;
import info3.game.network.Welcome;

public class LocalController extends Controller {
	ArrayList<View> views;
	public Model model;

	public LocalController() {
		super();
		this.views = new ArrayList<View>();
		this.model = new Model(this);
	}

	public LocalController(ArrayList<View> views) {
		this.views = views;
		for (View v : views) {
			v.setController(this);
		}
		this.model = new Model(this);
	}

	@Override
	public void addView(View v) {
		synchronized (this.views) {
			this.views.add(v);
		}
		v.player = this.model.spawnPlayer();
		this.sendTo(v.player, new Welcome(v.player.getColor()));
		for (Entity e : this.model.allEntities()) {
			Avatar a = e.getAvatar();
			this.sendTo(v.player, new CreateAvatar(a.id, a.getPosition(), a.fileName, a.imageCount, a.animationDelay));
		}
	}

	@Override
	public void keyPressed(Player p, KeyPress e) {
		System.out.println("[DEBUG] " + p.name() + " pressed " + e.code);
		if (e.code == 32) {
			Cowboy c = new Cowboy(this);
			c.setPosition(new Vec2(100, 100));
			this.model.spawn(c);
		}
		// Mouvements de camera
		if (e.code >= 37 && e.code <= 40) {
			View view = viewFor(p);
			switch (e.code) {
			case 37:
				// Left
				p.setPosition(p.getPosition().add(new Vec2(-5, 0)));
				break;
			case 38:
				// Up
				p.setPosition(p.getPosition().add(new Vec2(0, 5)));
				break;
			case 39:
				// Right
				p.setPosition(p.getPosition().add(new Vec2(5, 0)));
				break;
			case 40:
				// Down
				p.setPosition(p.getPosition().add(new Vec2(0, -5)));
				break;
			}
			view.camera.syncWith(p);
			if (view instanceof RemoteView) {
				RemoteView rv = (RemoteView) view;
				rv.client.send(new MoveCamera(view.camera.getPos()));
			}
		}
	}

	private View viewFor(Player p) {
		for (View v : this.views) {
			if (v.player == p) {
				return v;
			}
		}
		return null;
	}

	@Override
	public void tick(long elapsed) {
		this.model.tick(elapsed);
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

	public void sendTo(Player p, NetworkMessage msg) {
		for (View v : views) {
			if (v instanceof RemoteView) {
				RemoteView rv = (RemoteView) v;
				if (rv.client != null && rv.player == p) {
					rv.client.send(msg);
					return;
				}
			}
		}
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
		synchronized (this.views) {
			this.views.remove(view);
		}
	}
}
