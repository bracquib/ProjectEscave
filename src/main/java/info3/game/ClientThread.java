package info3.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import info3.game.network.KeyPress;
import info3.game.network.NetworkMessage;

public class ClientThread extends Thread {
	Socket sock;
	ObjectInputStream inputStream;
	ObjectOutputStream outputStream;
	Controller controller;

	ClientThread(Socket client, Controller c) {
		this.sock = client;
		this.controller = c;
		this.controller.addView(new RemoteView(this));
		try {
			this.inputStream = new ObjectInputStream(this.sock.getInputStream());
			this.outputStream = new ObjectOutputStream(this.sock.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			Object msg;
			try {
				msg = this.inputStream.readObject();
				if (msg instanceof KeyPress) {
					KeyPress k = (KeyPress) msg;
					this.controller.keyPressed(k);
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO: remove view from controller
				e.printStackTrace();
				break;
			}
		}
	}

	public void send(NetworkMessage msg) {
		try {
			this.outputStream.writeObject(msg);
			this.outputStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}