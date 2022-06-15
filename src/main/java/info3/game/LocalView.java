package info3.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import info3.game.assets.Paintable;
import info3.game.graphics.GameCanvas;
import info3.game.sound.RandomFileInputStream;

public class LocalView extends View {
	JFrame frame;
	JLabel text;
	GameCanvas canvas;
	CanvasListener listener;
	Sound music;

	public LocalView(Controller controller) {
		super();
		this.controller = controller;
		this.controller.addView(this);

		this.listener = new CanvasListener(this);

		this.canvas = new GameCanvas(this.listener);
		Dimension d = new Dimension(1024, 768);
		this.frame = this.canvas.createFrame(d);
		setupFrame();
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
		this.text.setText("Tick: 0ms FPS=0 AvatarsOnScreen=0");
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
			this.canvas.playMusic(fis, 0, 1.0F);
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
		synchronized (this.avatars) {
			for (Avatar a : this.avatars.values()) {
				a.tick(elapsed);
			}
		}

		// Update every second
		// the text on top of the frame: tick and fps
		m_textElapsed += elapsed;
		if (m_textElapsed > 1000) {
			m_textElapsed = 0;
			float period = this.canvas.getTickPeriod();
			int fps = this.canvas.getFPS();

			String txt = "Tick=" + period + "ms";
			while (txt.length() < 15)
				txt += " ";
			txt += fps + " fps   ";
			txt += " AvatarsOnScreen=" + this.getVisibleAvatars().size();
			this.text.setText(txt);
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

		for (Avatar a : this.getVisibleAvatars()) {
			a.paint(g, this.camera.getPos());
		}
	}

	/**
	 * Liste les avatars visibles
	 * 
	 * @return une liste des avatars affichées à l'écran
	 */
	public ArrayList<Avatar> getVisibleAvatars() {
		int width = this.canvas.getWidth();
		int height = this.canvas.getHeight();
		int radius = Math.max(width, height) * 2;
		ArrayList<Avatar> result = new ArrayList<>();
		synchronized (this.avatars) {
			for (Avatar a : this.avatars.values()) {
				if (a.position.distance(this.camera.getPos()) < radius) {
					result.add(a);
				}
			}
		}
		return result;
	}

	@Override
	public Avatar createAvatar(int id, Vec2 pos, Paintable img) {
		Avatar av = new Avatar(id, img);
		av.position = pos;
		synchronized (this.avatars) {
			this.avatars.put(id, av);
		}
		return av;
	}

	@Override
	public void setController(Controller c) {
		this.controller = c;
	}
}
