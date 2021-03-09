package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Game;
import resources.Images;

public class ZoneScreen extends Screen{
	
	public JButton labButton, miningButton, woodcuttingButton, fishingButton;

	public ZoneScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.ZONES.ID], game);
		labButton = new JButton();
		miningButton = new JButton();
		woodcuttingButton = new JButton();
		fishingButton = new JButton();
		labButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.SM.changePage(new LabScreen(game));
			}
		});
		miningButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.SM.changePage(new MiningScreen(game));
			}
		});
		woodcuttingButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.SM.changePage(new WoodcuttingScreen(game));
			}
		});
		fishingButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.SM.changePage(new FishingScreen(game));
			}
		});
		labButton.setBounds((17)*Game.SCREENSCALE,(32)*Game.SCREENSCALE, 32*Game.SCREENSCALE, 32*Game.SCREENSCALE);
	    labButton.setContentAreaFilled(false);
	    miningButton.setBounds((73)*Game.SCREENSCALE,(32)*Game.SCREENSCALE, 32*Game.SCREENSCALE, 32*Game.SCREENSCALE);
	    miningButton.setContentAreaFilled(false);
	    woodcuttingButton.setBounds((129)*Game.SCREENSCALE,(32)*Game.SCREENSCALE, 32*Game.SCREENSCALE, 32*Game.SCREENSCALE);
	    woodcuttingButton.setContentAreaFilled(false);
	    fishingButton.setBounds((185)*Game.SCREENSCALE,(32)*Game.SCREENSCALE, 32*Game.SCREENSCALE, 32*Game.SCREENSCALE);
	    fishingButton.setContentAreaFilled(false);
	}
	
	public JButton[] getButtons() {
		return new JButton[]{zones,skills,items,tasks,settings,labButton,miningButton,woodcuttingButton,fishingButton};
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		super.draw(g);
	}
	
}
