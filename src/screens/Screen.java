package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import main.Game;
import resources.Images;

public class Screen {
	
	public enum page_ID {
		LAB(0),SETTING(1),MINING(2),WOODCUTTING(3),FISHING(4);
		public final int ID;
		
		private page_ID(int ID) {
			this.ID = ID;
		}
	}
	private int BUTTON_WIDTH=192,BUTTON_HEIGHT=60,BUTTON_OFFSET=BUTTON_WIDTH+6;
	
	
	
	static public int[][] headerUI_position_data = {{0,0},{2,3}};
	public BufferedImage background;
	public BufferedImage[] clickables;
	public JButton zones,skills,items,settings;	
	
	public Screen(BufferedImage background, Game game) {
		this.background = background;
		zones = new JButton();
		skills = new JButton();
		items = new JButton();
		settings = new JButton();
		zones.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("zones");
			}
		});
		skills.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("skills");
			}
		});
		items.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("items");
			}
		});
		settings.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("settings");
			}
		});
		
		zones.setBounds(6,9, BUTTON_WIDTH, BUTTON_HEIGHT);
	    zones.setContentAreaFilled(false);
		game.add(zones);
		skills.setBounds(6+(BUTTON_OFFSET),9, BUTTON_WIDTH, BUTTON_HEIGHT);
		skills.setContentAreaFilled(false);
		game.add(skills);
		items.setBounds(6+(BUTTON_OFFSET*2),9, BUTTON_WIDTH, BUTTON_HEIGHT);
		items.setContentAreaFilled(false);
		game.add(items);
		settings.setBounds(6+(BUTTON_OFFSET*4),9, BUTTON_WIDTH-125, BUTTON_HEIGHT);
		settings.setContentAreaFilled(false);
		game.add(settings);
	}
	
	public void tick() {
		
	}
	public void draw(Graphics g) {
		g.drawImage(background,0,0,background.getWidth()*Game.SCREENSCALE,background.getHeight()*Game.SCREENSCALE,null);
		for (int i = 0; i < Images.headerUI.length; i++) {
			g.drawImage(Images.headerUI[i],headerUI_position_data[i][0]*Game.SCREENSCALE,headerUI_position_data[i][1]*Game.SCREENSCALE,Images.headerUI[i].getWidth()*Game.SCREENSCALE,Images.headerUI[i].getHeight()*Game.SCREENSCALE,null);
		}
	}
}
