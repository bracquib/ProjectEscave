package info3.game.menu;

import java.util.HashMap;

public class GameOptions {
	public int playerCount = 1;
	public static GameOptions instance = new GameOptions();
	public static HashMap<String, String> Automates = new HashMap<String, String>();
	public static String fichierGal;
}
