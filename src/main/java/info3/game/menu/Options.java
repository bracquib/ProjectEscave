package info3.game.menu;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Options extends State implements Statemethods {

	private OptionButton[] buttons = new OptionButton[3];
	private BufferedImage backgroundImg, playerspng;
	private int menuX, menuY, menuWidth, menuHeight, playerX, playerY;

	public Options(GameJerem game) {
		super(game);
		loadButtons();
		loadPlayer();
		loadBackground();
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth() * GameJerem.SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * GameJerem.SCALE);
		menuX = GameJerem.GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int) (45 * GameJerem.SCALE);
	}

	private void loadPlayer() {
		playerspng = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_PLAYERS);
		playerX = GameJerem.GAME_WIDTH / 2 - 90;
		playerY = (int) (150 * GameJerem.SCALE);
	}

	private void loadButtons() {

		buttons[0] = new OptionButton(GameJerem.GAME_WIDTH / 2 + 40, (int) (230 * GameJerem.SCALE), 1,
				Gamestate.OPTIONS, LoadSave.OPTIONS_AUTOMATES, LoadSave.OPTIONS_IP);
		buttons[1] = new OptionButton(GameJerem.GAME_WIDTH / 2 + 100, (int) (270 * GameJerem.SCALE), 2,
				Gamestate.OPTIONS, LoadSave.OPTIONS_IP, LoadSave.OPTIONS_AUTOMATES);
		buttons[2] = new OptionButton(GameJerem.GAME_WIDTH / 2 + 70, (int) (310 * GameJerem.SCALE), 2, Gamestate.MENU,
				LoadSave.BUTTON_QUIT, LoadSave.BUTTON_QUIT1);
	}

	@Override
	public void update() {
		for (OptionButton mb : buttons)
			mb.update();
	}

	@Override
	public void draw(Graphics g) {

		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
		g.drawImage(playerspng, playerX, playerY, playerspng.getWidth() * 2, playerspng.getHeight() * 2, null);

		for (OptionButton mb : buttons)
			mb.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (OptionButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (OptionButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				break;
			}
		}

		resetButtons();
	}

	private void resetButtons() {
		for (OptionButton mb : buttons)
			mb.resetBools();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (OptionButton mb : buttons)
			mb.setMouseOver(false);

		for (OptionButton mb : buttons)
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
	}

}
