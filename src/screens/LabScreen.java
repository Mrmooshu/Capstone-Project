package screens;

import java.awt.Graphics;
import main.Game;
import resources.Animation;
import resources.Images;

public class LabScreen extends Screen{
	
	static public int[][] labUI_position_data = {{100,100},{50,50}};
	public Animation generator = new Animation(Images.generator, 2);
	public Animation handle = new Animation(Images.handle, 2);

	
	public LabScreen() {
		super(Images.backgrounds[Screen.page_ID.MAIN.ID]);
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		for (int i = 0; i < Images.labUI.length; i++) {
			g.drawImage(Images.labUI[i],labUI_position_data[i][0]*Game.SCREENSCALE,labUI_position_data[i][1]*Game.SCREENSCALE,Images.labUI[i].getWidth()*Game.SCREENSCALE,Images.labUI[i].getHeight()*Game.SCREENSCALE,null);
		}
		generator.drawNextFrame(g, Game.WIDTH/2 - 25, Game.HEIGHT/2 - 24, 50, 48);
		handle.drawNextFrame(g, Game.WIDTH/2 - 47, Game.HEIGHT/2 - 10, 22, 20);
	}

}
