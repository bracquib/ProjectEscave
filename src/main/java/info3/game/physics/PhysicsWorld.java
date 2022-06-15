package info3.game.physics;

import java.util.ArrayList;
import java.util.HashSet;

import info3.game.Model;
import info3.game.Vec2;
import info3.game.entities.Block;
import info3.game.entities.Entity;

public class PhysicsWorld {

	Model model;
	public static final Vec2 GRAVITY = new Vec2(0.0f, 175f);

	public PhysicsWorld(Model model) {
		this.model = model;
	}

	/**
	 * Calcul tous les changements de position dans le model dû aux forces du monde
	 * et aux collisions
	 * 
	 * @param elapsed Temps écoulé depuis le dernier tick
	 * 
	 *
	 * @return void
	 */
	public void tick(long elapsed) {
		float elapsedSec = elapsed / 1000.0f;
		ArrayList<RigidBody> entities = this.model.getEntities();
		Block[][] map = this.model.getMap();

		for (RigidBody rb : entities) {

			step(rb, elapsedSec);

			HashSet<CollisionType> collisions = new HashSet<CollisionType>();
			Block floor = null;
			for (int i = 0; i < model.getMap().length; i++) {
				for (int j = 0; j < model.getMap()[0].length; j++) {

					if (map[i][j] == null) {
						continue;
					}

					try {
						CollisionType coll = rb.isColliding(map[i][j]);
						if (coll == CollisionType.NONE)
							continue;
						if (collisions.add(coll) && coll == CollisionType.DOWN) {
							floor = map[i][j];
						}
						clearCoords(rb, map[i][j], coll);
					} catch (Exception e) {
						// Ne devrait jamais arriver
						e.printStackTrace();
						return;
					}

				}
			}

			if (!collisions.contains(CollisionType.DOWN)) {
				this.computeGravity(rb, elapsedSec);
			} else {
				this.computeFrictionX(rb, floor);
			}
			for (CollisionType coll : collisions) {
				switch (coll) {
				case UP:
					if (rb.getSpeed().getY() < 0)
						rb.getSpeed().nullY();
					break;
				case DOWN:
					if (rb.getSpeed().getY() > 0)
						rb.getSpeed().nullY();
					break;
				case LEFT:
					if (rb.getSpeed().getX() < 0)
						rb.getSpeed().nullX();
					break;
				case RIGHT:
					if (rb.getSpeed().getX() > 0)
						rb.getSpeed().nullX();
					break;
				default:
					break;

				}
			}
		}
	}

	/**
	 * Ajout au RigidBody passé en paramètre la vitesse dû à la gravité que ce
	 * dernier à gagner pendant le temps elapsed
	 * 
	 * @param rb      RigidBody soumis à la gravité
	 * @param elapsed Temps écoulé depuis le dernier tick
	 * 
	 *
	 * @return void
	 */
	private void computeGravity(RigidBody rb, float elapsed) {
		Vec2 gravityForce = PhysicsWorld.GRAVITY.multiply(rb.getMass());
		rb.addForce(gravityForce);
		Vec2 speed = rb.getForce().divide(rb.getMass()).multiply(elapsed);
		rb.addSpeed(speed);
		rb.setForce(Vec2.nullVector());
	}

	/**
	 * Calcul la nouvelle vitesse selon l'axe X d'un RigidBody lorsqu'il est au
	 * contact d'une entité
	 * 
	 * @param rb RigidBody soumis à la friction
	 * @param e  Entity en friction avec un RigidBody
	 * 
	 *
	 * @return void
	 */
	private void computeFrictionX(RigidBody rb, Entity e) {
		if (rb.getFrictionFactor() == 0 || e.getFrictionFactor() == 0)
			return;
		// rb.getSpeed().setX(Math.round(rb.getSpeed().getX() / (rb.getFrictionFactor()
		// * e.getFrictionFactor())));
	}

	/**
	 * Calcul et met à jour la nouvelle position d'une entité dynamique après un
	 * temps et selon les forces qui lui sont appliquées et sa vitesse initiale
	 * 
	 * @param rb      RigidBody dont on veut calculer la nouvelle position
	 * @param elapsed Temps écoulé depuis le dernier tick
	 * 
	 *
	 * @return void
	 */
	private void step(RigidBody rb, float elapsed) {
		Vec2 newPos = rb.getPosition().add(rb.getSpeed().multiply(elapsed));
		rb.setPosition(newPos);
	}

	private void clearCoords(RigidBody rb, Block bl, CollisionType coll) {
		switch (coll) {
		case UP:
			float diffUP = bl.getPosition().getY() + ((BoxCollider) bl.getCollider()).height - rb.getPosition().getY();
			rb.setPosition(rb.getPosition().add(new Vec2(0f, diffUP)));
			break;
		case DOWN:
			float diffDOWN = bl.getPosition().getY()
					- (rb.getPosition().getY() + ((BoxCollider) rb.getCollider()).height);
			rb.setPosition(rb.getPosition().add(new Vec2(0f, diffDOWN)));
			break;
		case LEFT:
			float diffLEFT = bl.getPosition().getX() + ((BoxCollider) bl.getCollider()).width - rb.getPosition().getX();
			rb.setPosition(rb.getPosition().add(new Vec2(diffLEFT, 0f)));
			break;
		case RIGHT:
			float diffRIGHT = rb.getPosition().getX() + ((BoxCollider) rb.getCollider()).width
					- bl.getPosition().getX();
			rb.setPosition(rb.getPosition().add(new Vec2(-diffRIGHT, 0f)));
			break;
		default:
			break;
		}
	}
}
