package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Game;
import resources.Images;

public class ZoneScreen extends Screen{
	
	public JButton labButton, miningButton, woodcuttingButton, fishingButton;
	private JButton[] zoneButtons;
	private Game game;

	public ZoneScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.ZONES.ID], game);
		this.game=game;
		defineButtons();
	}
	
	public void addButtons(Game game) {
		super.addButtons(game);
		for (int i = 0; i < zoneButtons.length; i++) {
			game.add(zoneButtons[i]);
		}
	}
	
	public void removeButtons(Game game) {
		super.removeButtons(game);
		for (int i = 0; i < zoneButtons.length; i++) {
			game.remove(zoneButtons[i]);
		}
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		super.draw(g);
	}
	
	private void defineButtons() {
		labButton = new JButton();
		miningButton = new JButton();
		woodcuttingButton = new JButton();
		fishingButton = new JButton();
		zoneButtons = new JButton[] {labButton, miningButton, woodcuttingButton, fishingButton};
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
	    labButton.setBorderPainted(false);
	    labButton.setFocusPainted(false);
	    miningButton.setBounds((73)*Game.SCREENSCALE,(32)*Game.SCREENSCALE, 32*Game.SCREENSCALE, 32*Game.SCREENSCALE);
	    miningButton.setContentAreaFilled(false);
	    miningButton.setBorderPainted(false);
	    miningButton.setFocusPainted(false);
	    woodcuttingButton.setBounds((129)*Game.SCREENSCALE,(32)*Game.SCREENSCALE, 32*Game.SCREENSCALE, 32*Game.SCREENSCALE);
	    woodcuttingButton.setContentAreaFilled(false);
	    woodcuttingButton.setBorderPainted(false);
		woodcuttingButton.setFocusPainted(false);
	    fishingButton.setBounds((185)*Game.SCREENSCALE,(32)*Game.SCREENSCALE, 32*Game.SCREENSCALE, 32*Game.SCREENSCALE);
	    fishingButton.setContentAreaFilled(false);
	    fishingButton.setBorderPainted(false);
	    fishingButton.setFocusPainted(false);
	}
	
}
