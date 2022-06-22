package info3.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class IPSelectionPage extends JFrame {

	private static Color BTN_COLOR = Color.green;

	IPSelectionPage() {
		setup();
		makeInterface();
	}

	private void setup() {
		this.setTitle("Escave");
		this.setSize(300, 300);
		// this.setIconImage(new
		// ImageIcon(getClass().getResource("image.jgp)).getImage());
		this.setResizable(false);
	}

	private JPanel genPanel(Color color) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBackground(color);
		panel.setOpaque(true);
		return panel;
	}

	private void joinGame() {

	}

	private void createGame() {

	}

	private void makeInterface() {
		JFrame frame = this;
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
		});

		JPanel bgPanel = new JPanel(new GridBagLayout());
		this.setContentPane(bgPanel);
		bgPanel.setOpaque(true);
		bgPanel.setBackground(Color.lightGray);

		JPanel panel05 = genPanel(Color.white);
		JPanel panel1 = genPanel(Color.white);
		JPanel panel15 = genPanel(Color.white);
		JPanel panel2 = genPanel(Color.white);
		panel05.setOpaque(false);
		panel1.setOpaque(false);
		panel15.setOpaque(false);
		panel2.setOpaque(false);

		GridBagConstraints grid = new GridBagConstraints();
		grid.fill = GridBagConstraints.BOTH;
		grid.weightx = 1;
		grid.weighty = 0.5;
		grid.gridy = 1;
		bgPanel.add(panel05, grid);
		grid.weightx = 1;
		grid.weighty = 0;
		grid.gridy = 2;
		bgPanel.add(panel1, grid);
		grid.weightx = 1;
		grid.weighty = 0.3;
		grid.gridy = 3;
		bgPanel.add(panel15, grid);
		grid.gridy = 4;
		grid.weighty = 1;
		bgPanel.add(panel2, grid);

		grid.fill = GridBagConstraints.NONE;
		JTextField IPTextField = new JTextField(" IP");
		IPTextField.setPreferredSize(new Dimension(150, 30));
		grid.weighty = 1;
		grid.weightx = 1;
		grid.gridy = 1;
		panel1.add(IPTextField, grid);
		grid.gridy = 2;

		JButton joinGameBtn = new JButton("Rejoindre une partie");
		joinGameBtn.setPreferredSize(new Dimension(170, 30));
		joinGameBtn.setBackground(BTN_COLOR);
		joinGameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Lancer le jeu en r�cup�rant l'IP
				frame.dispose();
				joinGame();
			}
		});
		panel1.add(joinGameBtn, grid);

		JButton createGameBtn = new JButton("Cr�er une partie");
		createGameBtn.setPreferredSize(new Dimension(170, 30));
		createGameBtn.setBackground(BTN_COLOR);
		createGameBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Cr�er une partie
				frame.dispose();
				createGame();
			}
		});
		grid.gridy = 1;
		panel2.add(createGameBtn, grid);

		this.setVisible(true);
	}

	public static void main(String[] args) {
		IPSelectionPage page = new IPSelectionPage();
	}
}
