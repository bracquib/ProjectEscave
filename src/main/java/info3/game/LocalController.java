package info3.game;

import java.util.ArrayList;

import info3.game.entities.Cowboy;
import info3.game.entities.Entity;
import info3.game.network.KeyPress;
import info3.game.network.NetworkMessage;

public class LocalController extends Controller {
	ArrayList<View> views;

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
		this.spawn(new Cowboy(this));
	}

	@Override
	public void tick(long elapsed) {
		for (Entity e : this.entities) {
			e.tick(elapsed);
		}
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
				rv.client.send(msg);
			}
		}
	}
}
