package info3.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;

import info3.game.network.CreateAvatar;
import info3.game.network.KeyPress;
import info3.game.network.NetworkMessage;
import info3.game.network.UpdateAvatar;

public class RemoteController extends Controller {
	Socket sock;
	NetworkSenderThread networkSender;
	NetworkReceiverThread networkReceiver;
	protected View view;

	public RemoteController(String ip, int port) throws IOException {
		super();
		this.sock = new Socket(ip, port);
		this.networkSender = new NetworkSenderThread(this.sock);
		this.networkReceiver = new NetworkReceiverThread(this.sock, this);
		this.networkReceiver.start();
		this.networkSender.start();
	}

	@Override
	public void keyPressed(KeyPress e) {
		this.networkSender.send(e);
	}

	@Override
	public void tick(long elapsed) {
		// Le serveur tick de lui-même, pas besoin de lui signaler qu'il faut ticker
	}

	@Override
	public void addView(View v) {
		this.view = v;
	}

	@Override
	public Avatar createAvatar(Vec2 pos, String string, int imageLen, int animationDelay) {
		int id = Controller.avatarID;
		Controller.avatarID++;
		return this.view.createAvatar(id, pos, string, imageLen, animationDelay);
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

	public void send(NetworkMessage msg) {
		try {
			this.queue.put(msg);
		} catch (InterruptedException e) {
			// TODO: do something with it?
			System.out.println("NetworkThread.send: error");
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
				if (msg instanceof CreateAvatar) {
					CreateAvatar ca = (CreateAvatar) msg;
					this.controller.view.createAvatar(ca.id, ca.position, ca.filename, ca.imageLen, ca.animationDelay);
				} else if (msg instanceof UpdateAvatar) {
					UpdateAvatar ua = (UpdateAvatar) msg;
					this.controller.view.updateAvatar(ua.avatarId, ua.position);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				this.socket.close();
			} catch (IOException ioex) {
				ioex.printStackTrace();
			}
		}
	}
}