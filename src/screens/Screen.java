package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import main.Game;
import resources.Images;

public class Screen {
	
	public enum page_ID {
		LAB(0),ZONES(1),SKILLS(2),ITEM(3),TASKS(4),SETTINGS(5),MINING(6),WOODCUTTING(7),FISHING(8);
		public final int ID;
		
		private page_ID(int ID) {
			this.ID = ID;
		}
	}
	private int BUTTON_WIDTH=192,BUTTON_HEIGHT=60,BUTTON_OFFSET=BUTTON_WIDTH+6;
	
	public BufferedImage background;
	public JButton zones,skills,items,tasks,settings;	
	
	public Screen(BufferedImage background, Game game) {
		this.background = background;
		zones = new JButton();
		skills = new JButton();
		items = new JButton();
		tasks = new JButton();
		settings = new JButton();
		zones.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("zones");
				game.SM.changePage(new ZoneScreen(game));
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
				game.SM.changePage(new ItemScreen(game));
			}
		});
		tasks.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("tasks");
			}
		});
		settings.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("settings");
				game.SM.changePage(new SettingScreen(game));
			}
		});
		
		zones.setBounds(6,9, BUTTON_WIDTH, BUTTON_HEIGHT);
	    zones.setContentAreaFilled(false);
		skills.setBounds(6+(BUTTON_OFFSET),9, BUTTON_WIDTH, BUTTON_HEIGHT);
		skills.setContentAreaFilled(false);
		items.setBounds(6+(BUTTON_OFFSET*2),9, BUTTON_WIDTH, BUTTON_HEIGHT);
		items.setContentAreaFilled(false);
		tasks.setBounds(6+(BUTTON_OFFSET*3),9, BUTTON_WIDTH, BUTTON_HEIGHT);
		tasks.setContentAreaFilled(false);
		settings.setBounds(6+(BUTTON_OFFSET*4),9, BUTTON_WIDTH-125, BUTTON_HEIGHT);
		settings.setContentAreaFilled(false);
	}
	
	public JButton[] getButtons() {
		return new JButton[]{zones,skills,items,tasks,settings};
	}
	
	public BufferedImage numToImage(int n) {
		return Images.numbers[n];
	}
	public BufferedImage letterToImage(char c) {
		return Images.letters[(int)Character.toUpperCase(c)-65];
	}
	
	public void tick() {
		
	}
	public void draw(Graphics g) {
		g.drawImage(background,0,0,background.getWidth()*Game.SCREENSCALE,background.getHeight()*Game.SCREENSCALE,null);
		g.drawImage(Images.headerUI,0,0,Images.headerUI.getWidth()*Game.SCREENSCALE,Images.headerUI.getHeight()*Game.SCREENSCALE,null);
	}
}
