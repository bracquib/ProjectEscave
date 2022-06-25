package info3.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info3.game.assets.Image;
import info3.game.assets.Paintable;
import info3.game.entities.Block;
import info3.game.entities.Entity;
import info3.game.entities.Mushroom;
import info3.game.entities.Player;
import info3.game.entities.PlayerColor;
import info3.game.entities.Statue;
import info3.game.network.KeyPress;
import info3.game.network.KeyRelease;
import info3.game.network.MouseClick;
import info3.game.network.NetworkMessage;
import info3.game.network.UpdateAvatar;
import info3.game.network.Welcome;
import info3.game.network.WheelScroll;

public class LocalController extends Controller {
	List<View> views;

	public LocalController() {
		super();
		this.views = Collections.synchronizedList(new ArrayList<View>());
		Model.init(this);
	}

	public LocalController(ArrayList<View> views) {
		this.views = views;
		for (View v : views) {
			v.setController(this);
		}
		Model.init(this);
	}

	@Override
	public void addView(View v) {
		synchronized (this.views) {
			this.views.add(v);
		}
		v.setController(this);
		for (Entity e : Model.allEntities()) {
			Avatar a = e.getAvatar();
			if (a != null) { // if the file couldn't be loaded, a will be null
				v.createAvatar(a);
				if (a.duplicates != null) {
					for (Avatar dup : a.duplicates) {
						if (dup != null) {
							v.createAvatar(dup);
						}
					}
				}
			}
		}
		int playerNum = Model.playerCount.getAndIncrement();
		v.setPlayer(Player.colorFromInt(playerNum));
		Model.spawnPlayer(playerNum);
		this.sendTo(v.getPlayer(), new Welcome(v.getPlayer()));
	}

	@Override
	public void keyPressed(PlayerColor p, KeyPress e) {
		this.addPressedKey(p, e.code);

		Player player = Model.getPlayer(p);
		if (e.code == 32) {
			Vec2 newPos = new Vec2(player.getPosition());
			newPos.setX(newPos.getX() + Block.SIZE);
			Model.spawn(new Statue(this, player, newPos, 1));
			Image bg = new Image("bg_big.jpg");
			bg.fixed = true;
			bg.layer = -1;
			this.createAvatar(new Vec2(0, 0), bg);
		}

		if (e.code == 67) {
			Vec2 newPos = new Vec2(player.getPosition());
			newPos.setX(newPos.getX() + 64);
			Model.spawn(new Mushroom(player.getController(), player.getPosition(), 1, 1));
		}

		if (e.code >= 97 && e.code <= 102) {
			player.getInventory().selectCurrentTool(e.code - 97);
		}

		if (e.code >= 49 && e.code <= 53) {
			player.getInventory().selectCurrentTool(e.code - 49);
		}
	}

	@Override
	public void keyReleased(PlayerColor p, KeyRelease e) {
		this.removePressedKey(p, e.code);
	}

	public View viewFor(PlayerColor p) {
		for (View v : this.views) {
			if (v.getPlayer() == p) {
				return v;
			}
		}
		return null;
	}

	@Override
	public void tick(long elapsed) {
		Model.tick(elapsed);
	}

	@Override
	public Avatar createAvatar(Vec2 pos, Paintable image, boolean dup, Vec2 scale) {
		int id = Controller.avatarID;
		Controller.avatarID++;
		Avatar a = new Avatar(id, image, dup);
		a.setPosition(pos);
		a.setScale(scale);
		synchronized (this.views) {
			for (View v : this.views) {
				v.createAvatar(a);
			}
		}
		return a;
	}

	public void sendTo(Player p, NetworkMessage msg) {
		this.sendTo(p.getColor(), msg);
	}

	public void sendTo(PlayerColor p, NetworkMessage msg) {
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
	protected void mouseScroll(PlayerColor p, WheelScroll wheelScroll) {
		Inventory inv = Model.getPlayer(p).getInventory();
		if (wheelScroll.up) {
			inv.moveLCurrentTool();
		} else {
			inv.moveRCurrentTool();
		}
	}

	private void addPressedKey(PlayerColor color, int code) {
		Player p = Model.getPlayer(color);
		if (p == null) {
			return;
		}
		for (Integer key : p.pressedKeys) {
			if (key.equals(code))
				return;
		}
		p.pressedKeys.add(code);
	}

	private void removePressedKey(PlayerColor p, int code) {
		Player player = Model.getPlayer(p);
		if (player == null) {
			return;
		}
		player.pressedKeys.remove((Integer) code);
	}

	public boolean isKeyPressed(Entity forEntity, int code) {
		Player p = null;
		for (Player player : Model.getPlayers()) {
			if (player.getControlledEntity().equals(forEntity)) {
				p = player;
				break;
			}
		}
		if (p == null) {
			return false;
		}
		int realKeyCode;
		switch (code) {
		case 785: // FU
			realKeyCode = 38;
			break;
		case 768: // FD
			realKeyCode = 40;
			break;
		case 776: // FL
			realKeyCode = 37;
			break;
		case 782: // FR
			realKeyCode = 39;
			break;
		case 910: // SPace
			realKeyCode = 32;
			break;
		case 100: // d
			realKeyCode = 68;
			break;
		case 113: // q
			realKeyCode = 81;
			break;
		case 115: // s
			realKeyCode = 83;
			break;
		case 122: // z
			realKeyCode = 90;
			break;
		default:
			realKeyCode = code;
			break;
		}
		for (Integer key : p.pressedKeys) {
			if (key.equals(realKeyCode))
				return true;
		}
		return false;
	}

	@Override
	protected void mouseClick(PlayerColor p, MouseClick mouseClick) {
		Vec2 mouse = mouseClick.position.screenToGlobal(this.viewFor(p).camera.getPos()).wrapCoords();
		Player player = Model.getPlayer(p);
		player.mousePos = mouse;
		player.getBehaviour().pop(player, null);
	}

	@Override
	public void deleteAvatar(int avatarId) {
		for (View v : this.views) {
			v.deleteAvatar(avatarId);
		}
	}

	public void updatePaintable(Avatar av, Paintable p) {
		for (View v : this.views) {
			// TODO: move this to each view implementation
			if (v instanceof RemoteView) {
				RemoteView rv = (RemoteView) v;
				if (rv.client != null) {
					rv.client.send(new UpdateAvatar(av.getId(), p, av.getPosition()));
				}
			} else if (v instanceof LocalView) {
				LocalView lv = (LocalView) v;
				lv.updateAvatar(av.getId(), p, av.getPosition());
			}
		}
	}

	@Override
	public void updateAvatar(int i, Vec2 pos) {
		for (View v : this.views) {
			v.updateAvatar(i, pos);
		}
	}

	@Override
	public void windowResize(PlayerColor p, Vec2 size) {
		this.viewFor(p).setDimensions(size);
		Player player = Model.getPlayer(p);
		if (player != null) {
			player.hud.updateAvatars();
		}
	}

	public void syncCamera(PlayerColor p, Entity syncWith) {
		this.viewFor(p).syncCamera(syncWith.getAvatar());
	}
}
