package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Data.Items;
import main.Game;
import resources.Animation;
import resources.Images;

public class LabScreen extends Screen{
	
	static public int[][] labUI_position_data = {{100,100},{50,50}};
	public Animation generator = new Animation(Images.generator, 2);
	public Animation handle = new Animation(Images.handle, 2);

	private Items inventory;
	
	public JButton genButton;
	
	public LabScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.LAB.ID], game);
		this.inventory = game.inventory;
		genButton = new JButton() {
			@Override
			public void paint(Graphics g) {
				
			}
		};
		genButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("+1");
				inventory.misc_list[0].Increase(1);
				handle.setFrameCounter(0);
			}
		});
		genButton.setBounds((Game.WIDTH/2-50)*Game.SCREENSCALE,(Game.HEIGHT/2-40)*Game.SCREENSCALE, 100*Game.SCREENSCALE, 80*Game.SCREENSCALE);
	    genButton.setContentAreaFilled(false);
	    genButton.setBorderPainted(false);
	    genButton.setOpaque(false);
		game.add(genButton);
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		for (int i = 0; i < Images.labUI.length; i++) {
			g.drawImage(Images.labUI[i],labUI_position_data[i][0]*Game.SCREENSCALE,labUI_position_data[i][1]*Game.SCREENSCALE,Images.labUI[i].getWidth()*Game.SCREENSCALE,Images.labUI[i].getHeight()*Game.SCREENSCALE,null);
		}
		generator.drawNextFrame(g, (Game.WIDTH/2 - 25)*Game.SCREENSCALE, (Game.HEIGHT/2 - 24)*Game.SCREENSCALE, 50*Game.SCREENSCALE, 48*Game.SCREENSCALE);
		handle.drawOneCycle(g, (Game.WIDTH/2 - 47)*Game.SCREENSCALE, (Game.HEIGHT/2 - 10)*Game.SCREENSCALE, 22*Game.SCREENSCALE, 20*Game.SCREENSCALE);
	}

}
