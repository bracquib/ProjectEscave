package info3.game.menu;

import java.awt.Container;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageContainer extends Container {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image img;

	public ImageContainer(String img) {
		super();
		try {
			this.img = ImageIO.read(new File(img));
			/*
			 * Dimension size = new Dimension(this.img.getWidth(null),
			 * this.img.getHeight(null)); setPreferredSize(size); setMinimumSize(size);
			 * setMaximumSize(size); setSize(size);
			 */
			// setLayout(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * @Override public void paint(Graphics g) {
	 * 
	 * g.drawImage(img, 800, 800, null); super.paint(g);
	 * 
	 * }
	 */
}
