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
		LAB(0),SKILLS(1),ITEM(2),SETTINGS(3),MINING(4),WOODCUTTING(5),FISHING(6);
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
				game.SM.changePage(Screen.page_ID.LAB);
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
				game.SM.changePage(Screen.page_ID.ITEM);
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
		tasks.setBounds(6+(BUTTON_OFFSET*3),9, BUTTON_WIDTH, BUTTON_HEIGHT);
		tasks.setContentAreaFilled(false);
		game.add(tasks);
		settings.setBounds(6+(BUTTON_OFFSET*4),9, BUTTON_WIDTH-125, BUTTON_HEIGHT);
		settings.setContentAreaFilled(false);
		game.add(settings);
	}
	
	public BufferedImage numToImage(int n) {
		return Images.numbers[n];
	}
	
	public void tick() {
		
	}
	public void draw(Graphics g) {
		g.drawImage(background,0,0,background.getWidth()*Game.SCREENSCALE,background.getHeight()*Game.SCREENSCALE,null);
		g.drawImage(Images.headerUI,0,0,Images.headerUI.getWidth()*Game.SCREENSCALE,Images.headerUI.getHeight()*Game.SCREENSCALE,null);
	}
}
