package info3.game.automata;

import info3.game.entities.Entity;

public interface ICondition {

	boolean eval(Entity e);

	boolean eval(Entity e, Direction d);

	boolean eval(Entity e, int touche);

	boolean eval(Entity e, Direction d, Category c);

	boolean eval(Entity e, Category c, Direction d);

}
