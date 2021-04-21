package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;

import Data.Items.Item;
import main.Game;
import resources.Images;

public class StartScreen extends Screen {

	public JButton play;
	private JButton[] startButtons;
	private Game game;
	private String time;
	int miningLvlGain;
	int woodcuttingLvlGain;
	int fishingLvlGain;
	
	public StartScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.START.ID], game);
		this.game=game;
		defineButtons();
		startButtons = new JButton[] {play};
		miningLvlGain = game.PD.getLevel(page_ID.MINING);
		woodcuttingLvlGain = game.PD.getLevel(page_ID.WOODCUTTING);
		fishingLvlGain = game.PD.getLevel(page_ID.FISHING);
		game.PD.simulating = true;
		time = game.PD.calculateAwayTime();
		game.PD.simulating = false;
		miningLvlGain = game.PD.getLevel(page_ID.MINING)-miningLvlGain;
		woodcuttingLvlGain = game.PD.getLevel(page_ID.WOODCUTTING)-woodcuttingLvlGain;
		fishingLvlGain = game.PD.getLevel(page_ID.FISHING)-fishingLvlGain;
		game.PD.saveData();
	}
	
	public void addButtons(Game game) {
		for (int i = 0; i < startButtons.length; i++) {
			game.add(startButtons[i]);
		}
	}
	
	public void removeButtons(Game game) {
		for (int i = 0; i < startButtons.length; i++) {
			game.remove(startButtons[i]);
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(background,0,0,background.getWidth()*Game.SCREENSCALE,background.getHeight()*Game.SCREENSCALE,null);
		g.drawImage(Images.skillicons[1],80*Game.SCREENSCALE,20*Game.SCREENSCALE,Images.skillicons[1].getWidth()*Game.SCREENSCALE,Images.skillicons[1].getHeight()*Game.SCREENSCALE,null);
		g.drawImage(Images.skillicons[0],130*Game.SCREENSCALE,20*Game.SCREENSCALE,Images.skillicons[0].getWidth()*Game.SCREENSCALE,Images.skillicons[0].getHeight()*Game.SCREENSCALE,null);
		g.drawImage(Images.skillicons[2],180*Game.SCREENSCALE,20*Game.SCREENSCALE,Images.skillicons[2].getWidth()*Game.SCREENSCALE,Images.skillicons[2].getHeight()*Game.SCREENSCALE,null);
		displayItems(game.PD.miningItems,g,80,44);
		displayItems(game.PD.woodcuttingItems,g,130,44);
		displayItems(game.PD.fishingItems,g,180,44);
		displayText("AFK time",g,128,5);
		displayText(time,g,113,10);
		displayText("+ "+miningLvlGain+" Lvl",g,75,38);
		displayText("+ "+woodcuttingLvlGain+" Lvl",g,125,38);
		displayText("+ "+fishingLvlGain+" Lvl",g,175,38);
	}
		
	private void defineButtons(){
		play = new JButton();
		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("lab");
				game.SM.changePage(new LabScreen(game));
				}
			});
		play.setBounds(124*Game.SCREENSCALE,126*Game.SCREENSCALE,42*Game.SCREENSCALE,20*Game.SCREENSCALE);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		play.setFocusPainted(false);
	}


	
	
}
