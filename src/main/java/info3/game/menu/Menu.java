package info3.game.menu;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import info3.game.Client;
import info3.game.LocalController;
import info3.game.Server;
import info3.game.Sound;

public class Menu extends State implements Statemethods {

	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg;
	private int menuX, menuY, menuWidth, menuHeight;
	public static Sound sound = new Sound();

	public Menu(MainMenu game) {
		super(game);
		loadButtons();
		loadBackground();
		sound.play(13);
		sound.loop(13);

	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImg.getWidth() * MainMenu.SCALE);
		menuHeight = (int) (backgroundImg.getHeight() * MainMenu.SCALE);
		menuX = MainMenu.GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int) (45 * MainMenu.SCALE);

	}

	private void loadButtons() {
		buttons[0] = new MenuButton(MainMenu.GAME_WIDTH / 2, (int) (150 * MainMenu.SCALE), 0, Gamestate.PLAYING, () -> {
			sound.stop(13);

			sound.play(14);
			if (Options.ip.equals("")) {
				Client.startGame();
				this.game.gameThread.interrupt();
				this.game.gamewindow.dispose();
			} else {
				if (Options.ip.equals("127.0.0.1")) {
					Thread serverThread = new Thread(() -> {
						LocalController controller = new LocalController();
						Server.run(controller);
					});
					serverThread.start();
				}
				Client.startGame(Options.ip, 1906);
				this.game.gameThread.interrupt();
				this.game.gamewindow.dispose();
			}
		});
		buttons[1] = new MenuButton(MainMenu.GAME_WIDTH / 2, (int) (220 * MainMenu.SCALE), 1, Gamestate.OPTIONS, () -> {
			sound.play(14);
		});
		buttons[2] = new MenuButton(MainMenu.GAME_WIDTH / 2, (int) (290 * MainMenu.SCALE), 2, Gamestate.QUIT, () -> {
			sound.play(14);
		});
	}

	@Override
	public void update() {
		for (MenuButton mb : buttons)
			mb.update();
	}

	@Override
	public void draw(Graphics g) {

		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

		for (MenuButton mb : buttons)
			mb.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.action.exec();
				mb.applyGamestate();
				break;
			}
		}

		resetButtons();

	}

	private void resetButtons() {
		for (MenuButton mb : buttons)
			mb.resetBools();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (MenuButton mb : buttons)
			mb.setMouseOver(false);

		for (MenuButton mb : buttons)
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}

	}

}