package info3.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UTFDataFormatException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

import info3.game.assets.Paintable;
import info3.game.entities.Player;
import info3.game.network.CreateAvatar;
import info3.game.network.KeyPress;
import info3.game.network.MultiMessage;
import info3.game.network.NetworkMessage;
import info3.game.network.SyncCamera;
import info3.game.network.UpdateAvatar;
import info3.game.network.Welcome;

public class RemoteController extends Controller {
	Socket sock;
	NetworkSenderThread networkSender;
	NetworkReceiverThread networkReceiver;
	protected LocalView view;

	public RemoteController(String ip, int port) throws IOException {
		super();
		this.sock = new Socket(ip, port);
		this.networkSender = new NetworkSenderThread(this.sock);
		this.networkReceiver = new NetworkReceiverThread(this.sock, this);
		this.networkReceiver.start();
		this.networkSender.start();
	}

	@Override
	public void keyPressed(Player p, KeyPress e) {
		this.networkSender.send(p, e);
	}

	@Override
	public void tick(long elapsed) {
		// Le serveur tick de lui-même, pas besoin de lui signaler qu'il faut ticker
	}

	@Override
	public void addView(View v) {
		if (v instanceof LocalView) {
			this.view = (LocalView) v;
		}
	}

	@Override
	public Avatar createAvatar(Vec2 pos, Paintable image) {
		int id = Controller.avatarID;
		Controller.avatarID++;
		return this.view.createAvatar(id, pos, image);
	}

	@Override
	protected void removeView(RemoteView view) {
		// Ne doit jamais être appelé normalement
	}
}

class NetworkSenderThread extends Thread {
	Socket socket;
	ObjectOutputStream stream;
	ArrayBlockingQueue<NetworkMessage> queue;

	public NetworkSenderThread(Socket sock) throws UnknownHostException, IOException {
		this.socket = sock;
		this.stream = new ObjectOutputStream(this.socket.getOutputStream());
		this.queue = new ArrayBlockingQueue<NetworkMessage>(10);
		this.setName("Sender");
	}

	public void send(Player p, NetworkMessage msg) {
		try {
			msg.player = p.getColor();
			this.queue.put(msg);
		} catch (InterruptedException e) {
			// TODO: do something with it?
			System.out.println("[ERROR] NetworkThread.send");
		}
	}

	@Override
	public void run() {
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

	public NetworkReceiverThread(Socket s, RemoteController c) {
		this.socket = s;
		this.controller = c;
		this.setName("Receiver");
	}

	@Override
	public void run() {
		try {
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
			this.controller.view.createAvatar(ca.id, ca.position, ca.image);
		} else if (msg instanceof UpdateAvatar) {
			UpdateAvatar ua = (UpdateAvatar) msg;
			try {
				this.controller.view.isPainting.acquire();
				this.controller.view.updateAvatar(ua.avatarId, ua.position);
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
		} else {
			System.out.println("[WARN] Unknown message type: " + msg.getClass().getName());
		}
	}
}