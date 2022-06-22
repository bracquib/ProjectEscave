package info3.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JLabel;

import info3.game.assets.AssetServer;
import info3.game.assets.Paintable;
import info3.game.graphics.GameCanvas;

public class LocalView extends View {
	JFrame frame;
	JLabel text;
	GameCanvas canvas;
	CanvasListener listener;
	Semaphore isPainting;
	protected SortedSet<Avatar> sortedAvatars;

	public LocalView(Controller controller) {
		super();
		this.sortedAvatars = new TreeSet<Avatar>((x, y) -> {
			int cmp = x.image.layer - y.image.layer;
			if (cmp == 0) {
				return y.id - x.id;
			} else {
				return cmp;
			}
		});
		this.isPainting = new Semaphore(1);

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

	private long textElapsed;

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
		textElapsed += elapsed;
		if (textElapsed > 1000) {
			textElapsed = 0;
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

		Vec2 cameraPos = this.camera.getPos();
		try {
			this.isPainting.acquire();
			for (Avatar a : this.getVisibleAvatars()) {
				synchronized (a) {
					a.paint(g, cameraPos);
				}
			}
			this.isPainting.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
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
		Vec2 cameraPos = this.camera.getPos();
		synchronized (this.sortedAvatars) {
			for (Avatar a : this.sortedAvatars) {
				if (a.image.fixed || a.position.distance(cameraPos) < radius) {
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
		synchronized (this.sortedAvatars) {
			this.sortedAvatars.add(av);
		}
		return av;
	}

	@Override
	public void deleteAvatar(int id) {
		super.deleteAvatar(id);
		this.sortedAvatars.removeIf((x) -> x.getId() == id);
	}

	@Override
	public void setController(Controller c) {
		this.controller = c;
	}

	@Override
	protected int getWidth() {
		return this.frame == null ? 1024 : this.frame.getWidth();
	}

	@Override
	protected int getHeight() {
		return this.frame == null ? 768 : this.frame.getHeight();
	}

	@Override
	public void updateAvatar(int id, Paintable p, Vec2 pos) {
		Avatar av = this.avatars.get(id);
		Paintable loaded = AssetServer.load(p);
		av.setPaintable(loaded);
		av.setPosition(pos);
	}
}
