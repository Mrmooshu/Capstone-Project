package screens;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import main.Game;
import resources.Images;

public class MiningScreen extends Screen{

	public MiningScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.MINING.ID], game);
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
