package info3.game.entities;

import java.awt.Graphics;

import info3.game.Avatar;
import info3.game.LocalController;
import info3.game.Vec2;
import info3.game.network.UpdateAvatar;
import info3.game.physics.BoxCollider;
import info3.game.physics.Collider;

/**
 * Représente une entité du jeu.
 * 
 * Tout (ou presque) ce qui s'affiche à l'écran dans le jeu est une entité.
 * 
 * La logique des entités est contrôlée par un automate (pas encore implémenté).
 * 
 * Elle possèdent un `Avatar` qui contrôle comment elles doivent être affichées.
 *
 */
public abstract class Entity {
	/**
	 * La position de l'entité en coordonnées globales
	 */
	Vec2 position;

	/**
	 * L'avatar de l'entité
	 */
	protected Avatar avatar;
	protected Vec2 avatarOffset;

	Collider collider;
	float frictionFactor;
	protected LocalController controller;

	public Vec2 getPosition() {
		return this.position;
	}

	public void setPosition(Vec2 pos) {
		this.position = pos;
		if (this.avatar != null) {
			this.avatar.setPosition(pos.add(this.avatarOffset));
			this.controller.sendToClients(new UpdateAvatar(this.avatar.getId(), this.avatar.getPosition()));
		}
	}

	public Entity(LocalController c) {
		this.controller = c;
		BoxCollider collider = new BoxCollider();
		collider.height = 32;
		collider.width = 32;
		this.collider = collider;
		this.frictionFactor = 0.9f;
	}

	/**
	 * Cette fonction est appelée à chaque tick de jeu
	 * 
	 * @param elapsed Le nombre de millisecondes qui se sont écoulées depuis le
	 *                dernier tick
	 */
	public void tick(long elapsed) {
		if (this.avatar != null) {
			this.avatar.tick(elapsed);
		}
	}

	/**
	 * Dessine cette entité à l'écran
	 * 
	 * @param g         La toile sur laquelle on dessine
	 * @param cameraPos La position de la caméra en coordonées globales
	 */
	public void paint(Graphics g, Vec2 cameraPos) {
		Vec2 screenPos = this.position.globalToScreen(cameraPos);
		this.avatar.paint(g, screenPos);
	}

	public Avatar getAvatar() {
		return this.avatar;
	}

	public final Collider getCollider() {
		return this.collider;
	}

	public LocalController getController() {
		return this.controller;
	}

	public float getFrictionFactor() {
		return frictionFactor;
	}
}
