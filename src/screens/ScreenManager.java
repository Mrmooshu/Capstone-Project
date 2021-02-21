package screens;

import java.awt.Graphics;

public class ScreenManager {

	public Screen[] pages;
	public int page_count = Screen.page_ID.values().length;
	public int current_page;
	
	public ScreenManager() {
		pages = new Screen[page_count];
		pages[Screen.page_ID.MAIN.ID] = new LabScreen();
		current_page = Screen.page_ID.MAIN.ID;
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
