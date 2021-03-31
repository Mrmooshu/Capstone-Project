package screens;

import java.awt.Graphics;

import javax.swing.JButton;

import main.Game;

public class ScreenManager {

	public Screen page;
	private Game game;
	private JButton[] activeButtons;
	
	public ScreenManager(Game game) {
		this.game = game;
		page = new LabScreen(game);
		page.addButtons(game);
	}
	
	public void draw(Graphics g) {
		page.draw(g);
	}
	
	public void changePage(Screen screen) {
		page.removeButtons(game);
		page = screen;
		page.addButtons(game);
		game.PD.saveData();
	}
	
}
