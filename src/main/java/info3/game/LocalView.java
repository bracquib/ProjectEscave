package info3.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.RandomAccessFile;

import javax.swing.JFrame;
import javax.swing.JLabel;

import info3.game.entities.Entity;
import info3.game.graphics.GameCanvas;
import info3.game.sound.RandomFileInputStream;

public class LocalView extends View {
	Controller controller;

	JFrame frame;
	JLabel text;
	GameCanvas canvas;
	CanvasListener listener;
	Sound music;

	public LocalView(Controller controller) {
		this.controller = controller;
		// creating a cowboy, that would be a model
		// in an Model-View-Controller pattern (MVC)
		// creating a listener for all the events
		// from the game canvas, that would be
		// the controller in the MVC pattern
		this.listener = new CanvasListener(this);
		// creating the game canvas to render the game,
		// that would be a part of the view in the MVC pattern
		this.canvas = new GameCanvas(this.listener);

		System.out.println("  - creating frame...");
		Dimension d = new Dimension(1024, 768);
		m_frame = m_canvas.createFrame(d);

		System.out.println("  - setting up the frame...");
		setupFrame();
	}

	@Override
	public int spawnAvatar(String filename) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * Then it lays out the frame, with a border layout, adding a label to the north
	 * and the game canvas to the center.
	 */
	private void setupFrame() {
		this.frame.setTitle("Game");
		this.frame.setLayout(new BorderLayout());

		this.frame.add(this.canvas, BorderLayout.CENTER);

		this.text = new JLabel();
		this.text.setText("Tick: 0ms FPS=0");
		this.frame.add(this.text, BorderLayout.NORTH);

		// center the window on the screen
		this.frame.setLocationRelativeTo(null);

		// make the vindow visible
		this.frame.setVisible(true);
	}

	/*
	 * ================================================================ All the
	 * methods below are invoked from the GameCanvas listener, once the window is
	 * visible on the screen.
	 * ==============================================================
	 */

	/*
	 * Called from the GameCanvas listener when the frame
	 */
	String m_musicName;

	void loadMusic() {
		m_musicName = m_musicNames[m_musicIndex];
		String filename = "resources/" + m_musicName + ".ogg";
		m_musicIndex = (m_musicIndex + 1) % m_musicNames.length;
		try {
			RandomAccessFile file = new RandomAccessFile(filename, "r");
			RandomFileInputStream fis = new RandomFileInputStream(file);
			m_canvas.playMusic(fis, 0, 1.0F);
		} catch (Throwable th) {
			th.printStackTrace(System.err);
			System.exit(-1);
		}
	}

	private int m_musicIndex = 0;
	private String[] m_musicNames = new String[] { "Runaway-Food-Truck" };

	private long m_textElapsed;

	/*
	 * This method is invoked almost periodically, given the number of milli-seconds
	 * that elapsed since the last time this method was invoked.
	 */
	void tick(long elapsed) {

		for (Entity e : this.entities) {
			e.tick(elapsed);
		}

		// Update every second
		// the text on top of the frame: tick and fps
		m_textElapsed += elapsed;
		if (m_textElapsed > 1000) {
			m_textElapsed = 0;
			float period = m_canvas.getTickPeriod();
			int fps = m_canvas.getFPS();

			String txt = "Tick=" + period + "ms";
			while (txt.length() < 15)
				txt += " ";
			txt = txt + fps + " fps   ";
			m_text.setText(txt);
		}
	}

	/*
	 * This request is to paint the Game Canvas, using the given graphics. This is
	 * called from the GameCanvasListener, called from the GameCanvas.
	 */
	void paint(Graphics g) {

		// get the size of the canvas
		int width = this.canvas.getWidth();
		int height = this.canvas.getHeight();

		// erase background
		g.setColor(Color.gray);
		g.fillRect(0, 0, width, height);

		for (Avatar a : this.avatars) {
			a.paint(g, Vec2.ZERO);
		}
	}

}
