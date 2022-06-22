package info3.game.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GameMenu extends JFrame {
	private static final long serialVersionUID = -1839957779772518515L;
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

	private Image backgroundImage;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(backgroundImage, 0, 0, null);
	}

	public GameMenu() {
		super("Escave");
		fenetreLancement();
		try {
			this.backgroundImage = ImageIO.read(new File("src/main/resources/background_image.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void fenetreLancement() {
		// Creation de la window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);

		JPanel panel = new JPanel(new GridBagLayout());
		JPanel panel1 = new JPanel(new GridBagLayout());
		JPanel contentPane = (JPanel) getContentPane();
		GridBagConstraints constraints = new GridBagConstraints();

		// Les boutons
		ImageIcon imageForOne = new ImageIcon("src/main/resources/boutton_test.jpg");

		JButton join = new JButton("Rejoindre une partie", imageForOne);
		join.setPreferredSize(new Dimension(78, 76));

		JButton create = new JButton("Créer une partie", imageForOne);
		create.setPreferredSize(new Dimension(78, 76));

		JButton exit = new JButton("Quitter");

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		constraints.gridx = 0;
		constraints.ipady = 80;
		constraints.ipadx = 250;
		constraints.insets = new Insets(300, 0, 0, 50);
		panel.add(join, constraints);

		constraints.gridx = 1;
		constraints.ipady = 80;
		constraints.ipadx = 250;
		constraints.insets = new Insets(300, 50, 0, 0);
		panel.add(create, constraints);

		constraints.ipady = 20;
		constraints.ipadx = 200;
		constraints.insets = new Insets(0, 0, 300, 0);
		panel1.add(exit, constraints);
		panel.setOpaque(false);
		panel1.setOpaque(false);
		panel.setBackground(new Color(0, 0, 0, 0));
		panel1.setBackground(new Color(0, 0, 0, 0));
		contentPane.setBackground(new Color(0, 0, 0, 0));
		contentPane.setOpaque(false);
		contentPane.add(panel, BorderLayout.CENTER);
		contentPane.add(panel1, BorderLayout.PAGE_END);

		this.pack();
		device.setFullScreenWindow(this);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GameMenu();
			}
		});
	}
}