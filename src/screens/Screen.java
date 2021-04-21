package screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
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
		LAB(0),ZONES(1),SKILLS(2),ITEM(3),TASKS(4),SETTINGS(5),MINING(6),WOODCUTTING(7),FISHING(8),START(9);
		public final int ID;
		
		private page_ID(int ID) {
			this.ID = ID;
		}
	}
	private int BUTTON_WIDTH=192,BUTTON_HEIGHT=60,BUTTON_OFFSET=BUTTON_WIDTH+6;
	
	private final static String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.!? +-%:/";

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
	protected static BufferedImage numToImage(int n,Color color) {
		return Images.changeColor(Images.numbers[n], color);
	}
	protected static BufferedImage letterToImage(char c) {
		if (c == '.') {
			return Images.letters[52];
		}
		else if (c == ' ') {
			return Images.letters[55];
		}
		else if (c == '+') {
			return Images.letters[56];
		}
		else if (c == '-') {
			return Images.letters[57];
		}
		else if (c == '%') {
			return Images.letters[58];
		}
		else if (c == ':') {
			return Images.letters[59];
		}
		else if (c == '/') {
			return Images.letters[60];
		}
		else if (Character.isUpperCase(c)) {
			return Images.letters[(int)c-39];
		}
		else if (Character.isLowerCase(c)) {
			return Images.letters[(int)c-97];
		}
		return null;
	}
	protected static BufferedImage letterToImage(char c, Color color) {
		BufferedImage[] coloredLetters = Images.changeColor(Images.letters, color);
		if (c == '.') {
			return coloredLetters[52];
		}
		else if (c == ' ') {
			return coloredLetters[55];
		}
		else if (c == '+') {
			return coloredLetters[56];
		}
		else if (c == '-') {
			return coloredLetters[57];
		}
		else if (c == '%') {
			return coloredLetters[58];
		}
		else if (c == ':') {
			return coloredLetters[59];
		}
		else if (c == '/') {
			return coloredLetters[60];
		}
		else if (Character.isUpperCase(c)) {
			return coloredLetters[(int)c-39];
		}
		else if (Character.isLowerCase(c)) {
			return coloredLetters[(int)c-97];
		}
		return null;	}
	public static void displayText(String text, Graphics g, int x, int y) {
		for (int i = 0; i < text.length(); i++) {
			if (LETTERS.indexOf(text.charAt(i))!=-1){
				displayLetter(text.charAt(i), g, x+i*3, y);
			}
			if (NUMBERS.indexOf(text.charAt(i))!=-1) {
				displayNum(Character.getNumericValue(text.charAt(i)), g, x+i*3, y);
			}
		}
	}
	public static void displayText(String text, Graphics g, int x, int y, Color color) {
		for (int i = 0; i < text.length(); i++) {
			if (LETTERS.indexOf(text.charAt(i))!=-1){
				displayLetter(text.charAt(i), g, x+i*3, y, color);
			}
			if (NUMBERS.indexOf(text.charAt(i))!=-1) {
				displayNum(Character.getNumericValue(text.charAt(i)), g, x+i*3, y, color);
			}
		}
	}
	
	protected void displayIcon(Item item, Graphics g, int x, int y) {
		g.drawImage(item.Icon(),x*Game.SCREENSCALE,y*Game.SCREENSCALE,item.Icon().getWidth()*Game.ITEMICONSCALE,item.Icon().getHeight()*Game.ITEMICONSCALE,null);
	}
	protected static void displayNum(int n, Graphics g, int x, int y) {
		g.drawImage(numToImage(n),x*Game.SCREENSCALE,y*Game.SCREENSCALE,numToImage(n).getWidth(),numToImage(n).getHeight(),null);
	}
	protected static void displayNum(int n, Graphics g, int x, int y, Color color) {
		g.drawImage(numToImage(n, color),x*Game.SCREENSCALE,y*Game.SCREENSCALE,numToImage(n).getWidth(),numToImage(n).getHeight(),null);
	}
	protected static void displayLetter(char c, Graphics g, int x, int y) {
		g.drawImage(letterToImage(c),x*Game.SCREENSCALE,y*Game.SCREENSCALE,letterToImage(c).getWidth(),letterToImage(c).getHeight(),null);
	}
	protected static void displayLetter(char c, Graphics g, int x, int y, Color color) {
		g.drawImage(letterToImage(c, color),x*Game.SCREENSCALE,y*Game.SCREENSCALE,letterToImage(c).getWidth(),letterToImage(c).getHeight(),null);
	}
	
	protected void displayItems(Item[] items, Graphics g, int x, int y) {
		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				displayIcon(items[i], g, x, y+(i*10));
				if (items[i].Quanity().toString() == "0") {
					displayBigNumbers(new BigInteger(""+0), g, x+11, y+4+(i*10));
				}
				else {
					BigInteger num = items[i].Quanity();
					displayBigNumbers(num, g, x+11, y+4+(i*10));
				}
			}
		}

	}
	
	public static void displayBigNumbers(BigInteger number, Graphics g, int x, int y) {
		if (number.compareTo(new BigInteger("1000")) == -1) {
			displayText(number.toString(),g,x,y);
		}
		else if (number.compareTo(new BigInteger("1000000")) == -1) {
			displayText(number.toString().substring(0, number.toString().length()-3)+"K",g,x,y,Images.THOUSANDCOLOR);
		}
		else if (number.compareTo(new BigInteger("1000000000")) == -1) {
			displayText(number.toString().substring(0, number.toString().length()-6)+"M",g,x,y,Images.MILLIONCOLOR);
		}
		else if (number.compareTo(new BigInteger("1000000000000")) == -1) {
			displayText(number.toString().substring(0, number.toString().length()-9)+"B",g,x,y,Images.BILLIONCOLOR);
		}
		else if (number.compareTo(new BigInteger("1000000000000000")) == -1) {
			displayText(number.toString().substring(0, number.toString().length()-12)+"t",g,x,y,Images.TRILLIONCOLOR);
		}
		else if (number.compareTo(new BigInteger("1000000000000000000")) == -1) {
			displayText(number.toString().substring(0, number.toString().length()-15)+"q",g,x,y,Images.QUADRILLIONCOLOR);
		}
		else if (number.compareTo(new BigInteger("1000000000000000000000")) == -1) {
			displayText(number.toString().substring(0, number.toString().length()-18)+"Q",g,x,y,Images.QUINTILLIONCOLOR);
		}
		else if (number.compareTo(new BigInteger("1000000000000000000000000")) == -1) {
			displayText(number.toString().substring(0, number.toString().length()-21)+"s",g,x,y,Images.SEXTILLIONCOLOR);
		}
		else {
			displayText("bruh moment",g,x,y,Images.RED);
		}
	}
	
	protected void displayCost(BigInteger quanity, BigInteger cost, Item item, Graphics g, int x, int y) {
		displayBigNumbers(quanity,g,x+25,y);
		displayBigNumbers(cost,g,x+43,y);
		displayText("/",g,x+38,y);
		displayText("cost:",g,x+11,y);
		displayIcon(item, g, x, y-4);
	}
	
	protected void displayExpBar(Graphics g,int x,int y,BigInteger expEnd,page_ID skill) {
		BigDecimal d = new BigDecimal(game.PD.getExp(skill)).divide(new BigDecimal(expEnd), 2, RoundingMode.HALF_UP);
		float b = Float.parseFloat(d.toString());
		int c = (int) (b*99);
		g.setColor(Images.CYAN);
		g.fillRect(x, y, c, 3*Game.SCREENSCALE);
	}
	
	public static int getPercent(double val) {
		return (int)(100 * val);
	}
	
	public static double getSeconds(double val) {
		return (double)Math.round((val/60)*100)/100;
	}
	
	protected void attemptUpgrade(Upgrade upgrade) {
		if (upgrade.getCurrentLevel()==upgrade.getLevelMax()) {
			System.out.println("max level");
		}
		else if (new BigInteger(""+upgrade.getCostQuanity()).compareTo(game.inventory.itemList[upgrade.getCostID()].Quanity()) == 0 || new BigInteger(""+upgrade.getCostQuanity()).compareTo(game.inventory.itemList[upgrade.getCostID()].Quanity()) == -1) {
			game.inventory.itemList[upgrade.getCostID()].Decrease(upgrade.getCostQuanity());
			upgrade.setCurrentLevel(upgrade.getCurrentLevel()+1);
			game.PD.saveData();
		}
		else {
			System.out.println("cant afford");
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
				System.out.println("zones");
				game.SM.changePage(new ZoneScreen(game));
			}
		});
		skills.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("skills");
			}
		});
		items.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("items");
				game.SM.changePage(new ItemScreen(game));
			}
		});
		tasks.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("tasks");
			}
		});
		settings.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("settings");
				game.SM.changePage(new SettingScreen(game));
			}
		});
		
		zones.setBounds(6,9, BUTTON_WIDTH, BUTTON_HEIGHT);
	    zones.setContentAreaFilled(false);
	    zones.setBorderPainted(false);
	    zones.setFocusPainted(false);
		skills.setBounds(6+(BUTTON_OFFSET),9, BUTTON_WIDTH, BUTTON_HEIGHT);
		skills.setContentAreaFilled(false);
		skills.setBorderPainted(false);
		skills.setFocusPainted(false);
		items.setBounds(6+(BUTTON_OFFSET*2),9, BUTTON_WIDTH, BUTTON_HEIGHT);
		items.setContentAreaFilled(false);
		items.setBorderPainted(false);
		items.setFocusPainted(false);
		tasks.setBounds(6+(BUTTON_OFFSET*3),9, BUTTON_WIDTH, BUTTON_HEIGHT);
		tasks.setContentAreaFilled(false);
		tasks.setBorderPainted(false);
		tasks.setFocusPainted(false);
		settings.setBounds(6+(BUTTON_OFFSET*4),9, BUTTON_WIDTH-125, BUTTON_HEIGHT);
		settings.setContentAreaFilled(false);
		settings.setBorderPainted(false);
		settings.setFocusPainted(false);
	}
}
