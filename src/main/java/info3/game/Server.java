package info3.game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public static void main(String[] args) {
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
	Controller controller;

	public TickerThread(Controller c) {
		this.controller = c;
	}

	public void run() {
		long start = System.currentTimeMillis();
		while (true) {
			try {
				long end = System.currentTimeMillis();
				controller.tick(end - start);
				start = end;
				Thread.sleep(30);
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
				thread.run();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}