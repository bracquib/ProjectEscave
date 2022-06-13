package info3.game.physics;

import java.util.ArrayList;
import java.util.HashSet;

import info3.game.Model;
import info3.game.Vec2;
import info3.game.entities.Block;
import info3.game.entities.Entity;

public class PhysicsWorld {

	Model model;
	public static final Vec2 GRAVITY = new Vec2(0.0f, 9.81f);

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
		ArrayList<RigidBody> entities = this.model.getEntities();
		Block[][] map = this.model.getMap();

		for (RigidBody rb : entities) {
			HashSet<CollisionType> collisions = new HashSet<CollisionType>();
			Block floor = null;
			for (int i = 0; i < model.getMap().length; i++) {
				for (int j = 0; j < model.getMap()[0].length; j++) {
					if (map[i][j] == null) {
						continue;
					}
					try {
						CollisionType coll = rb.isColliding(map[i][j]);
						if (collisions.add(coll) && coll == CollisionType.DOWN) {
							floor = map[i][j];
						}
					} catch (Exception e) {
						// Ne devrait jamais arriver
						e.printStackTrace();
						return;
					}

				}
			}
			if (!collisions.contains(CollisionType.DOWN)) {
				this.computeGravity(rb, elapsed);
			} else {
				this.computeFrictionX(rb, floor);
			}
			for (CollisionType coll : collisions) {
				switch (coll) {
				case UP:
				case DOWN:
					if (rb.getSpeed().getY() > 0)
						rb.getSpeed().nullY();
					break;
				case LEFT:
				case RIGHT:
					if (rb.getSpeed().getX() < 0)
						rb.getSpeed().nullX();
					break;
				default:
					break;
				}
			}
			step(rb, elapsed);
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
	private void computeGravity(RigidBody rb, long elapsed) {
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
		rb.getSpeed().setX(Math.round(rb.getSpeed().getX() / (rb.getFrictionFactor() * e.getFrictionFactor())));
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
	private void step(RigidBody rb, long elapsed) {
		Vec2 newPos = rb.getPosition().add(rb.getSpeed().multiply(elapsed));
		rb.setPosition(newPos);
	}
}
