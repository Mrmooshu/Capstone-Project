package screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JButton;

import Data.Items;
import Data.Items.Item;
import Data.UpgradeTracker.Upgrade;
import main.Game;
import resources.Animation;
import resources.Images;
import resources.ItemGraphic;

public class Screen {
	
	public enum page_ID {
		LAB(0),ZONES(1),SKILLS(2),ITEM(3),TASKS(4),SETTINGS(5),MINING(6),WOODCUTTING(7),FISHING(8);
		public final int ID;
		
		private page_ID(int ID) {
			this.ID = ID;
		}
	}
	private int BUTTON_WIDTH=192,BUTTON_HEIGHT=60,BUTTON_OFFSET=BUTTON_WIDTH+6;
	
	private final static String LETTERS = "abcdefghijklmnopqrstuvwxyz.!? +-%:/";

	private final static String NUMBERS = "0123456789";
	
	private Game game;
		
	protected LinkedList<ItemGraphic> onScreenItems;
	
	public BufferedImage background;
	public JButton zones,skills,items,tasks,settings;
	public JButton[] headerButtons;
	
	public Screen(BufferedImage background, Game game) {
		this.background = background;
		this.game=game;
		onScreenItems = new LinkedList<ItemGraphic>();
		zones = new JButton();
		skills = new JButton();
		items = new JButton();
		tasks = new JButton();
		settings = new JButton();
		headerButtons = new JButton[] {zones,skills,items,tasks,settings};
		defineButtons();
	}
	
	public void addButtons(Game game) {
		for (int i = 0; i < headerButtons.length; i++) {
			game.add(headerButtons[i]);
		}
	}
	
	public void removeButtons(Game game) {
		for (int i = 0; i < headerButtons.length; i++) {
			game.remove(headerButtons[i]);
		}
	}
	
	protected static BufferedImage numToImage(int n) {
		return Images.numbers[n];
	}
	protected static BufferedImage letterToImage(char c) {
		if (c == '.') {
			return Images.letters[26];
		}
		else if (c == ' ') {
			return Images.letters[29];
		}
		else if (c == '+') {
			return Images.letters[30];
		}
		else if (c == '-') {
			return Images.letters[31];
		}
		else if (c == '%') {
			return Images.letters[32];
		}
		else if (c == ':') {
			return Images.letters[33];
		}
		else if (c == '/') {
			return Images.letters[34];
		}
		return Images.letters[(int)Character.toUpperCase(c)-65];
	}
	
	public static void displayText(String text, Graphics g, int x, int y) {
		for (int i = 0; i < text.length(); i++) {
			if (LETTERS.indexOf(text.toLowerCase().charAt(i))!=-1){
				displayLetter(text.charAt(i), g, x+i*3, y);
			}
			if (NUMBERS.indexOf(text.charAt(i))!=-1) {
				displayNum(Character.getNumericValue(text.charAt(i)), g, x+i*3, y);
			}
		}
	}
	
	
	protected void displayIcon(Item item, Graphics g, int x, int y) {
		g.drawImage(item.Icon(),x*Game.SCREENSCALE,y*Game.SCREENSCALE,item.Icon().getWidth()*Game.ITEMICONSCALE,item.Icon().getHeight()*Game.ITEMICONSCALE,null);
	}
	protected static void displayNum(int n, Graphics g, int x, int y) {
		g.drawImage(numToImage(n),x*Game.SCREENSCALE,y*Game.SCREENSCALE,numToImage(n).getWidth(),numToImage(n).getHeight(),null);
	}
	protected static void displayLetter(char c, Graphics g, int x, int y) {
		g.drawImage(letterToImage(c),x*Game.SCREENSCALE,y*Game.SCREENSCALE,letterToImage(c).getWidth(),letterToImage(c).getHeight(),null);
	}
	
	protected void displayItems(Item[] items, Graphics g, int x, int y) {
		for (int i = 0; i < items.length; i++) {
			displayIcon(items[i], g, x, y+(i*10));
			if (items[i].Quanity() == 0) {
				displayNum(0, g, x+11, y+4+(i*10));
			}
			else {
				String num = String.valueOf(items[i].Quanity());
				for (int j = 0; j < num.length(); j++) {
					displayNum(Integer.parseInt(num.substring(j,j+1)), g, x+11+(j*3), y+4+(i*10));
				}
			}
		}
	}
	
	protected void displayExpBar(Graphics g,int x,int y,long expEnd,page_ID skill) {
		float a = (float)game.PD.getExp(skill);
		float b = a/(float)expEnd;
		int c = (int) (b*99);
		g.setColor(Images.CYAN);
		g.fillRect(x, y, c, 3*Game.SCREENSCALE);
	}
	
	public int getPercent(double val) {
		return (int)(100 * val);
	}
	
	public double getSeconds(double val) {
		return (double)Math.round((val/60)*100)/100;
	}
	
	protected void attemptUpgrade(Upgrade upgrade) {
		if (upgrade.getCurrentLevel()==upgrade.getLevelMax()) {
			System.out.print("max level");
		}
		else if (upgrade.getCost()[1] <= game.inventory.itemList[upgrade.getCost()[0]].Quanity()) {
			game.inventory.itemList[upgrade.getCost()[0]].Decrease(upgrade.getCost()[1]);
			upgrade.setCurrentLevel(upgrade.getCurrentLevel()+1);
			game.PD.saveData();
		}
		else {
			System.out.print("cant afford");
		}
	}
	
	protected void drawOnScreenItems(Graphics g) {
		for (int i = 0; i < onScreenItems.size(); i++) {
			if (onScreenItems.get(i).getLifespan() <= 0) {
				onScreenItems.remove(i);
				i--;
			}
			else {
				onScreenItems.get(i).draw(g);
			}
		}
	}
	

	
	public void draw(Graphics g) {
		g.drawImage(background,0,0,background.getWidth()*Game.SCREENSCALE,background.getHeight()*Game.SCREENSCALE,null);
		g.drawImage(Images.headerUI,0,0,Images.headerUI.getWidth()*Game.SCREENSCALE,Images.headerUI.getHeight()*Game.SCREENSCALE,null);

	}
	
	private void defineButtons() {
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
}
