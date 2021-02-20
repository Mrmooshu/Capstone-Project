package screens;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import resources.Images;

public class Screen {
	
	public enum page_ID {
		MAIN(0),SETTING(1),MINING(2),WOODCUTTING(3),FISHING(4);
		public final int ID;
		
		private page_ID(int ID) {
			this.ID = ID;
		}
	}
	private int[][] position_data_UI = {{0,0},{2,3}};
	public BufferedImage background;
	public BufferedImage[] clickables;
	
	public Screen(BufferedImage background) {
		this.background = background;
	}
	
	public void switchScreen() {
	}
	
	public void tick() {
		
	}
	public void draw(Graphics g) {
		g.drawImage(background,0,0,background.getWidth()*Game.SCREENSCALE,background.getHeight()*Game.SCREENSCALE,null);
		for (int i = 0; i < Images.headerUI.length; i++) {
			g.drawImage(Images.headerUI[i],position_data_UI[i][0]*Game.SCREENSCALE,position_data_UI[i][1]*Game.SCREENSCALE,Images.headerUI[i].getWidth()*Game.SCREENSCALE,Images.headerUI[i].getHeight()*Game.SCREENSCALE,null);
		}
	}
}
