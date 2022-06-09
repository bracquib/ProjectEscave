package info3.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import info3.game.network.KeyPress;
import info3.game.network.MultiMessage;
import info3.game.network.NetworkMessage;

public class ClientThread extends Thread {
	Socket sock;
	ObjectInputStream inputStream;
	ObjectOutputStream outputStream;
	Controller controller;
	ArrayList<NetworkMessage> messageQueue;
	RemoteView view;

	ClientThread(Socket client, Controller c) {
		this.sock = client;
		this.controller = c;
		this.view = new RemoteView(this);
		this.controller.addView(this.view);
		this.messageQueue = new ArrayList<>();
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
				this.disconnect();
				break;
			}
		}
	}

	public void send(NetworkMessage msg) {
		synchronized (this.messageQueue) {
			this.messageQueue.add(msg);
		}
	}

	protected void actualSend() {
		NetworkMessage msg;
		synchronized (this.messageQueue) {
			int count = this.messageQueue.size();
			if (count == 0) {
				return;
			} else if (count == 1) {
				msg = this.messageQueue.get(0);
			} else {
				msg = new MultiMessage(this.messageQueue);
			}
			this.messageQueue.clear();
		}
		try {
			this.outputStream.writeObject(msg);
			this.outputStream.reset();
		} catch (IOException e) {
			this.disconnect();
		}
	}

	private void disconnect() {
		System.out.println(String.format("[WARN] Client %s was disconnected", this.getPlayerName()));
		this.controller.removeView(this.view);
	}

	private String getPlayerName() {
		return "_";
	}
}