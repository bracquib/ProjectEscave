package info3.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info3.game.assets.Paintable;
import info3.game.entities.Cowboy;
import info3.game.entities.Entity;
import info3.game.entities.Player;
import info3.game.network.CreateAvatar;
import info3.game.network.KeyPress;
import info3.game.network.NetworkMessage;
import info3.game.network.SyncCamera;
import info3.game.network.Welcome;
import info3.game.network.WheelScroll;

public class LocalController extends Controller {
	List<View> views;
	public Model model;

	public LocalController() {
		super();
		this.views = Collections.synchronizedList(new ArrayList<View>());
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
		v.setPlayer(this.model.spawnPlayer());
		this.sendTo(v.getPlayer(), new Welcome(v.getPlayer().getColor()));
		this.sendTo(v.getPlayer(), new SyncCamera(v.getPlayer().getAvatar()));
		for (Entity e : this.model.allEntities()) {
			Avatar a = e.getAvatar();
			this.sendTo(v.getPlayer(), new CreateAvatar(a.id, a.getPosition(), a.image));
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
			switch (e.code) {
			case 37:
				// Left
				p.getSpeed().setX(-150);
				break;
			case 38:
				// Up
				p.getSpeed().setY(-250);
				break;
			case 39:
				// Right
				p.getSpeed().setX(150);
				break;
			case 40:
				// Down
				p.addSpeed(new Vec2(0, 0));
				break;
			}
		}
	}

	public View viewFor(Player p) {
		for (View v : this.views) {
			if (v.getPlayer() == p) {
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
	public Avatar createAvatar(Vec2 pos, Paintable image) {
		int id = Controller.avatarID;
		Controller.avatarID++;
		Avatar a = null;
		synchronized (this.views) {
			for (View v : this.views) {
				a = v.createAvatar(id, pos, image);
			}
		}
		return a;
	}

	public void sendTo(Player p, NetworkMessage msg) {
		synchronized (this.views) {
			for (View v : views) {
				if (v instanceof RemoteView) {
					RemoteView rv = (RemoteView) v;
					if (rv.client != null && rv.getPlayer() == p) {
						rv.client.send(msg);
						return;
					}
				}
			}
		}
	}

	public void sendToClients(NetworkMessage msg) {
		synchronized (this.views) {
			for (View v : this.views) {
				if (v instanceof RemoteView) {
					RemoteView rv = (RemoteView) v;
					if (rv.client != null) {
						rv.client.send(msg);
					}
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

	@Override
	protected void mouseScroll(Player p, WheelScroll wheelScroll) {
		System.out.println("ça scrolle " + wheelScroll.up);
		Inventory inv = p.getInventory();
		if (wheelScroll.up) {
			inv.moveLCurrentTool();
		} else {
			inv.moveRCurrentTool();
		}
	}
}
