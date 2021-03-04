package screens;

import java.awt.Graphics;

import main.Game;

public class ScreenManager {

	public Screen[] pages;
	public int page_count = Screen.page_ID.values().length;
	public int current_page;
	
	public ScreenManager(Game game) {
		pages = new Screen[page_count];
		pages[Screen.page_ID.LAB.ID] = new LabScreen(game);
		pages[Screen.page_ID.ITEM.ID] = new ItemScreen(game);
		current_page = Screen.page_ID.LAB.ID;
	}
	
	public void tick() {
		pages[current_page].tick();
	}
	
	public void draw(Graphics g) {
		pages[current_page].draw(g);
	}
	
	public void changePage(Screen.page_ID page) {
		current_page = page.ID;
	}
	
}
