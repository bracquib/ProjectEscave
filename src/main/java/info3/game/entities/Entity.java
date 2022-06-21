package info3.game.entities;

import java.awt.Graphics;

import info3.game.Avatar;
import info3.game.LocalController;
import info3.game.Model;
import info3.game.Vec2;
import info3.game.automata.Automata;
import info3.game.automata.AutomataState;
import info3.game.automata.Category;
import info3.game.automata.CurrentState;
import info3.game.automata.behaviors.Behaviour;
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
	protected Collider collider;
	float frictionFactor;
	protected Automata automata;
	protected CurrentState currentState;
	protected Behaviour behaviour;
	public int pointsDeVie;
	public int degatMob;
	public int degatEpee;
	public int degatPioche;
	protected Category category;
	protected Vec2 avatarOffset;
	public Vec2 mousePos;

	protected LocalController controller;

	public Vec2 getPosition() {
		return this.position;
	}

	public void setPosition(Vec2 pos) {
		Block[][] map = Model.getMap();
		Vec2 mapSize = new Vec2(map[0].length * 32, map.length * 32);

		// dépassement du haut
		if (pos.getX() >= 0 && pos.getX() <= mapSize.getX() && pos.getY() < 0) {
			pos = new Vec2(pos.getX(), pos.getY() + mapSize.getY());
		}

		// dépassement du bas
		else if (pos.getX() >= 0 && pos.getX() <= mapSize.getX() && pos.getY() > mapSize.getY()) {
			pos = new Vec2(pos.getX(), pos.getY() - mapSize.getY());
		}

		// dépassement à droite
		else if (pos.getY() >= 0 && pos.getY() <= mapSize.getY() && pos.getX() > mapSize.getX()) {
			pos = new Vec2(pos.getX() - mapSize.getX(), pos.getY());

		}

		// dépassement à gauche
		else if (pos.getY() >= 0 && pos.getY() <= mapSize.getY() && pos.getX() < 0) {
			pos = new Vec2(pos.getX() + mapSize.getX(), pos.getY());
		}

		this.position = pos;
		if (this.avatar != null) {
			if (this.avatarOffset != null) {
				this.avatar.setPosition(pos.add(this.avatarOffset));
			} else {
				this.avatar.setPosition(this.position);
			}
			this.controller.sendToClients(new UpdateAvatar(this.avatar.getId(), this.avatar.getPosition()));
		}
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setAutomata(Automata automata) {
		this.automata = automata;
		if (this.automata != null) {
			this.currentState = new CurrentState(this.automata.getInitialState());
		}
	}

	public void setAutomata(Automata automata, AutomataState state) {
		this.automata = automata;
		this.currentState = new CurrentState(state);
	}

	public void setBehaviour(Behaviour behaviour) {
		this.behaviour = behaviour;
	}

	public Behaviour getBehaviour() {
		return this.behaviour;
	}

	public Entity(LocalController c, int points) {
		this.controller = c;
		this.collider = new BoxCollider(32, 32, 0, 0);
		this.frictionFactor = 0.6f;
		this.pointsDeVie = points;
		this.automata = null;
		this.currentState = null;
		this.behaviour = null;
		this.degatMob = 0;
		this.degatEpee = 0;
		this.degatPioche = 0;
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
		if (this.automata != null && this.currentState != null) {
			this.automata.step(this, this.currentState.getState(), elapsed);
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

	public void setCurrentState(CurrentState s) {
		this.currentState = s;
	}

	public CurrentState getCurrentState() {
		return this.currentState;
	}
}
