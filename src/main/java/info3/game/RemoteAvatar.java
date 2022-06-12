package info3.game;

import java.awt.Graphics;

import info3.game.network.CreateAvatar;
import info3.game.network.UpdateAvatar;

public class RemoteAvatar extends Avatar {
	LocalController controller;

	@Override
	public void setPosition(Vec2 pos) {
		super.setPosition(pos);
		this.controller.sendToClients(new UpdateAvatar(this.id, this.position));
	}

	public RemoteAvatar(int id, LocalController c, String filename, int imageLen, long animationSpeed) {
		super(id);
		this.fileName = filename;
		this.imageCount = imageLen;
		this.animationDelay = animationSpeed;
		this.controller = c;
		this.controller.sendToClients(new CreateAvatar(id, this.position, filename, imageLen, animationSpeed));
	}

	@Override
	public void tick(long elapsed) {
		// Pas besoin de faire quoi que ce soit parce que les animations
		// sont gérées côté client
	}

	@Override
	public void paint(Graphics g, Vec2 cameraPos) {
		// Pas besoin de faire quoi que ce soit parce que le dessin est
		// géré côté client
	}
}
