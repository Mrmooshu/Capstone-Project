package screens;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import main.Game;
import resources.Images;

public class FishingScreen extends Screen{

	public FishingScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.FISHING.ID], game);
	}

	
	public JButton[] getButtons() {
		return new JButton[]{zones,skills,items,tasks,settings};
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		super.draw(g);
	}
	
}
