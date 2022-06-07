package info3.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CowboyTest {

	@Test
	public void testCowboy() {
		Cowboy c = null;
		try {
			c = new Cowboy();
		} catch (IOException e) {
			fail("Failed to initialize Cowboy");
		}
		assertEquals(10, c.m_x);
		c.tick(0);
		assertEquals(10, c.m_x);
	}

}
