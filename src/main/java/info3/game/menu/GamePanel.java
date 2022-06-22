package info3.game.menu;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2703227226283234010L;
	private MouseInputs mouseInputs;
	private GameJerem game;

	public GamePanel(GameJerem game2) {
		mouseInputs = new MouseInputs(this);
		this.game = game2;
		setPanelSize();
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(game.getGameWidth(), game.getGameHeight());
		setPreferredSize(size);
	}

	public void updateGame() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}

	public GameJerem getGame() {
		return game;
	}

}