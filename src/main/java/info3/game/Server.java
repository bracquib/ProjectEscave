package info3.game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import info3.game.assets.AssetServer;

public class Server {
	public static void main(String[] args) {
		AssetServer.init(false);
		// TODO: parse parameters
		ArrayList<View> clients = new ArrayList<View>();
		LocalController controller = new LocalController(clients);
		Server.run(controller);
	}

	private static void run(LocalController controller) {
		try {
			TickerThread ticker = new TickerThread(controller);
			ServerThread server = new ServerThread(controller);
			ticker.start();
			server.start();

			server.join();
			for (ClientThread ct : server.clients) {
				ct.join();
			}
			ticker.join();
		} catch (InterruptedException e) {
			// TODO: do something?
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class TickerThread extends Thread {
	LocalController controller;

	public TickerThread(LocalController c) {
		this.controller = c;
	}

	public void run() {
		long start = System.currentTimeMillis();
		while (true) {
			try {
				long end = System.currentTimeMillis();
				this.controller.tick(end - start);
				// Sync the clients
				synchronized (this.controller.views) {
					List<View> views = new ArrayList<>(this.controller.views);
					for (View view : views) {
						if (view instanceof RemoteView) {
							RemoteView rv = (RemoteView) view;
							rv.client.actualSend();
						}
					}
				}
				long execTime = System.currentTimeMillis() - end;
				start = end;
				Thread.sleep(Math.max(0, 30 - execTime));
			} catch (InterruptedException e) {
				// TODO ?
			}
		}
	}
}

class ServerThread extends Thread {
	ServerSocket sock;
	Controller controller;
	ArrayList<ClientThread> clients;

	public ServerThread(Controller c) throws IOException {
		this.controller = c;
		this.clients = new ArrayList<>();
		this.sock = new ServerSocket(1906);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Socket client = this.sock.accept();
				ClientThread thread = new ClientThread(client, this.controller);
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}