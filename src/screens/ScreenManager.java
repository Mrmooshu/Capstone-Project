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
		for (int i = 0; i < page.getButtons().length; i++) {
			game.add(page.getButtons()[i]);
		}
	}
	
	public void tick() {
		page.tick();
	}
	
	public void draw(Graphics g) {
		page.draw(g);
	}
	
	public void changePage(Screen screen) {
		for (int i = 0; i < page.getButtons().length; i++) {
			game.remove(page.getButtons()[i]);
		}
		page = screen;
		for (int i = 0; i < page.getButtons().length; i++) {
			game.add(page.getButtons()[i]);
		}
	}
	
}
