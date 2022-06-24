package info3.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UTFDataFormatException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

import info3.game.assets.Paintable;
import info3.game.entities.Player;
import info3.game.network.CreateAvatar;
import info3.game.network.DeleteAvatar;
import info3.game.network.JoinGame;
import info3.game.network.KeyPress;
import info3.game.network.KeyRelease;
import info3.game.network.MouseClick;
import info3.game.network.MultiMessage;
import info3.game.network.NetworkMessage;
import info3.game.network.SyncCamera;
import info3.game.network.UpdateAvatar;
import info3.game.network.Welcome;
import info3.game.network.WheelScroll;
import info3.game.network.WindowResize;

public class RemoteController extends Controller {
	Socket sock;
	String ip;
	int port;
	NetworkSenderThread networkSender;
	NetworkReceiverThread networkReceiver;
	protected LocalView view;

	public RemoteController(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;

		this.networkSender = new NetworkSenderThread();
		this.networkReceiver = new NetworkReceiverThread(this);
	}

	@Override
	public void keyPressed(Player p, KeyPress e) {
		this.networkSender.send(p, e);
	}

	@Override
	public void keyReleased(Player p, KeyRelease e) {
		this.networkSender.send(p, e);
	}

	@Override
	public void tick(long elapsed) {
		// Le serveur tick de lui-même, pas besoin de lui signaler qu'il faut ticker
	}

	@Override
	public void addView(View v) {
		if (v instanceof LocalView && this.view == null) {
			try {
				this.sock = new Socket(ip, port);
				this.networkReceiver.setSocket(this.sock);
				this.networkSender.setSocket(this.sock);
				this.networkReceiver.start();
				this.networkSender.start();

				this.view = (LocalView) v;
				this.networkSender.send(null, new JoinGame(v.getDimensions()));
			} catch (ConnectException ce) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				this.addView(v);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Avatar createAvatar(Vec2 pos, Paintable image, boolean dup) {
		int id = Controller.avatarID;
		Controller.avatarID++;
		Avatar av = new Avatar(id, image, dup);
		av.setPosition(pos);
		this.view.createAvatar(av);
		return av;
	}

	@Override
	protected void removeView(RemoteView view) {
		// Ne doit jamais être appelé normalement
	}

	@Override
	protected void mouseScroll(Player p, WheelScroll wheelScroll) {
		this.networkSender.send(p, wheelScroll);
	}

	@Override
	protected void mouseClick(Player player, MouseClick mouseClick) {
		this.networkSender.send(player, mouseClick);
	}

	@Override
	public void deleteAvatar(int avatarId) {
		this.view.deleteAvatar(avatarId);
	}

	@Override
	protected void updateAvatar(int i, Vec2 pos) {
		this.view.updateAvatar(i, pos);
	}

	@Override
	public void windowResize(Player p, Vec2 size) {
		this.networkSender.send(p, new WindowResize(size));
	}
}

class NetworkSenderThread extends Thread {
	Socket socket;
	ObjectOutputStream stream;
	ArrayBlockingQueue<NetworkMessage> queue;

	public NetworkSenderThread() {
		this.queue = new ArrayBlockingQueue<NetworkMessage>(10);
		this.setName("Sender");
	}

	public void setSocket(Socket sock) throws UnknownHostException, IOException {
		this.socket = sock;
		this.stream = new ObjectOutputStream(this.socket.getOutputStream());
	}

	public void send(Player p, NetworkMessage msg) {
		try {
			if (p != null) {
				msg.player = p.getColor();
			}
			this.queue.put(msg);
		} catch (InterruptedException e) {
			// TODO: do something with it?
			System.out.println("[ERROR] NetworkThread.send");
		}
	}

	@Override
	public void run() {
		System.out.println("sender started");
		while (true) {
			try {
				NetworkMessage msg = this.queue.take();
				stream.writeObject(msg);
			} catch (Exception e) {
				try {
					this.socket.close();
				} catch (IOException ioex) {
					ioex.printStackTrace();
				}
			}
		}
	}
}

class NetworkReceiverThread extends Thread {
	Socket socket;
	ObjectInputStream stream;
	RemoteController controller;

	public NetworkReceiverThread(RemoteController c) {
		this.controller = c;
		this.setName("Receiver");
	}

	public void setSocket(Socket sock) {
		this.socket = sock;
	}

	@Override
	public void run() {
		try {
			System.out.println("receiver started");
			this.stream = new ObjectInputStream(this.socket.getInputStream());
			while (true) {
				Object msg = this.stream.readObject();
				this.handleMessage(msg);
			}
		} catch (StreamCorruptedException | UTFDataFormatException ex) {
			System.out.println("[WARN] Corrupted stream");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				this.socket.close();
			} catch (IOException ioex) {
				ioex.printStackTrace();
			}
		}
	}

	private void handleMessage(Object msg) {
		if (msg instanceof CreateAvatar) {
			CreateAvatar ca = (CreateAvatar) msg;
			Avatar av = new Avatar(ca.id, ca.image, false);
			av.setPosition(ca.position);
			this.controller.view.createAvatar(av);
		} else if (msg instanceof UpdateAvatar) {
			UpdateAvatar ua = (UpdateAvatar) msg;
			try {
				this.controller.view.isPainting.acquire();
				if (ua.newPaintable != null) {
					this.controller.view.updateAvatar(ua.avatarId, ua.newPaintable, ua.position);
				}
				if (ua.position != null) {
					this.controller.view.updateAvatar(ua.avatarId, ua.position);
				}
				if (ua.newPath != null) {
					this.controller.view.updateAvatar(ua.avatarId, ua.newPath);
				}
				this.controller.view.isPainting.release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (msg instanceof MultiMessage) {
			MultiMessage mm = (MultiMessage) msg;
			for (NetworkMessage m : mm.messages) {
				this.handleMessage(m);
			}
		} else if (msg instanceof SyncCamera) {
			SyncCamera sc = (SyncCamera) msg;
			this.controller.view.camera.setAvatar(this.controller.view.getAvatar(sc.avatarId));
		} else if (msg instanceof Welcome) {
			Welcome w = (Welcome) msg;
			this.controller.view.setPlayer(new Player(new LocalController(), w.yourColor, new Vec2(0), false, 3));
		} else if (msg instanceof DeleteAvatar) {
			DeleteAvatar da = (DeleteAvatar) msg;
			this.controller.view.deleteAvatar(da.id);
		} else {
			System.out.println("[WARN] Unknown message type: " + msg.getClass().getName());
		}
	}
}