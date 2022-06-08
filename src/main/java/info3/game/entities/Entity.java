package info3.game.entities;

import java.awt.Graphics;

import info3.game.Avatar;
import info3.game.Controller;
import info3.game.Vec2;

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
	Avatar avatar;

	Controller controller;

	public Vec2 getPosition() {
		return this.position;
	}

	public Entity(Controller c) {
		this.controller = c;
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
}
