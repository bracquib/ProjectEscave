package info3.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import info3.game.entities.Cowboy;

public class CowboyTest {

	@Test
	public void testCowboy() {
		ArrayList<View> views = new ArrayList<View>();
		views.add(new RemoteView());
		Cowboy c = new Cowboy(new LocalController(views));
		assertEquals(0, c.getPosition().x);
		c.tick(10);
		assertEquals(0, c.getPosition().y);
		assertEquals(0, c.getPosition().x);
		c.tick(20);
		assertEquals(2, c.getPosition().x);
	}
}
