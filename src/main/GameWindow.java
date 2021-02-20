package main;
import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GameWindow {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Game Title");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		frame.add(new Game(), BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
