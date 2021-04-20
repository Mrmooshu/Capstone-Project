package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;

import Data.Items.Item;
import Data.UpgradeTracker.Upgrade;
import main.Game;
import resources.Animation;
import resources.Images;
import resources.ItemGraphic;
import screens.Screen.page_ID;
import screens.WoodcuttingScreen.Wood;
import screens.WoodcuttingScreen.stat_tab;

public class FishingScreen extends Screen{
	
	public enum stat_tab {
		POWER,SPEED,FRENZYPOWER,FRENZYDURATION,STATS;
	}
	public enum Fish {
		FISH1(31,100,new BigInteger("5"),1),FISH2(32,200,new BigInteger("15"),11),FISH3(33,600,new BigInteger("60"),21),FISH4(34,2400,new BigInteger("300"),31),FISH5(35,12000,new BigInteger("1800"),41),FISH6(36,72000,new BigInteger("12600"),51);
		private final int ID;
		private final int durability;
		private final BigInteger exp;
		private final int unlockLevel;

		
		private Fish(int ID,int durability,BigInteger exp,int unlockLevel) {
			this.ID = ID;
			this.durability = durability;
			this.exp = exp;
			this.unlockLevel = unlockLevel;
		}
		
		public Fish getNext() {
			if (this == FISH6) {
				return this;
			}
			return values()[ordinal()+1%values().length];
		}
		public Fish getPrev() {
			if (this == FISH1) {
				return this;
			}
			return values()[ordinal()-1%values().length];
		}
		
	}

	private final static int FISHINGMENUX = 186;
	private final static int FISHINGMENUY = 30;
	private final static int CASTBASEINTERVAL = 300;
	private int castIntervalCounter;
	private BigInteger expfornextlevel;
	
	private stat_tab statTab;
	private Fish selectedFish;
	private int frenzyMeter;
	private int frenzyLevel;
	private int frenzyTimer;
	private int activeFrenzyLevel;
	
	private Game game;
	
	public JButton fishLeft,fishRight,fishingPowerSelect,fishingSpeedSelect,frenzyPowerSelect,frenzyDurationSelect,statUpgrade,upgradePage,statPage,frenzyActivate;
	private LinkedList<JButton> fishingButtons;

	public Animation casting = new Animation(Images.fishinganimation, 3);
	
	public FishingScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.FISHING.ID], game);
		this.game = game;
		defineButtons();
		fishingButtons = new LinkedList<JButton>();
		statTab = stat_tab.POWER;
		selectedFish=game.PD.fishSelected;
		fishingButtons.add(fishLeft);
		fishingButtons.add(fishRight);
		fishingButtons.add(statPage);
		fishingButtons.add(upgradePage);
		fishingButtons.add(fishingPowerSelect);
		fishingButtons.add(fishingSpeedSelect);
		fishingButtons.add(frenzyPowerSelect);
		fishingButtons.add(frenzyDurationSelect);
		fishingButtons.add(statUpgrade);
		fishingButtons.add(frenzyActivate);
		castIntervalCounter = (int) (Math.max(CASTBASEINTERVAL-game.UT.fishingSpeed.getTotal(),20));
	    casting.setFrameCounterToEnd();
	    expfornextlevel = game.PD.getNextLevelReq(page_ID.FISHING);
	    frenzyMeter = game.PD.frenzyMeter;
	    frenzyLevel = game.PD.frenzyLevel;
	    frenzyTimer = 0;
	    activeFrenzyLevel = 0;
	}
	
	public void addButtons(Game game) {
		super.addButtons(game);
		for (int i = 0; i < fishingButtons.size(); i++) {
			game.add(fishingButtons.get(i));
		}
	}
	
	public void removeButtons(Game game) {
		super.removeButtons(game);
		for (int i = 0; i < fishingButtons.size(); i++) {
			game.remove(fishingButtons.get(i));
		}
	}

	
	private Upgrade findSelected() {
		switch(statTab) {
		case POWER:
			return game.UT.fishingPage0;
		case SPEED:
			return game.UT.fishingPage1;
		case FRENZYPOWER:
			return game.UT.fishingPage2;
		case FRENZYDURATION:
			return game.UT.fishingPage3;
		case STATS:
			break;
			}
		return null;
	}
	
	public static Item[] simulateCatchFish(Game game, long years, long days, long hours, long minutes) {
		game.PD.saveToggle = false;
		Item[] itemsGained = new Item[] {new Item(game.PD.fishSelected.ID), new Item(30)};
		FishingScreen page = new FishingScreen(game);
		double actionsPerMinute = 60/getSeconds(Math.max(CASTBASEINTERVAL-game.UT.miningSpeed.getTotal(),20));
		double actionsPerHour = actionsPerMinute*60;
		double actionsPerDay = actionsPerHour*24;
		long actionsPerYear = (long)(actionsPerDay*365);
		for (int i = 0; i < minutes; i++) {
			for (int j = 0; j < actionsPerMinute; j++) {
				Item[] gains = page.catchFish();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		for (int i = 0; i < hours; i++) {
			for (int j = 0; j < actionsPerHour; j++) {
				Item[] gains = page.catchFish();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		for (int i = 0; i < days; i++) {
			for (int j = 0; j < actionsPerDay; j++) {
				Item[] gains = page.catchFish();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		for (int i = 0; i < years; i++) {
			for (long j = 0; j < actionsPerYear; j++) {
				Item[] gains = page.catchFish();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		game.PD.saveToggle = true;
		return itemsGained;
	}
	
	private Item[] catchFish() {
		Random rand = new Random();
		int fishGained = 0;
		double a = selectedFish.durability;
		double b = game.UT.fishingPower.getTotal();
		if (rand.nextInt(selectedFish.durability) <= b) {
			fishGained++;
		}
		int seaweedGained = rand.nextInt(Math.max(fishGained,1))+game.PD.getLevel(page_ID.FISHING);
		if (frenzyTimer > 0) {
			seaweedGained *= activeFrenzyLevel;
			fishGained *= activeFrenzyLevel;
		}
		else {
			frenzyMeter += game.UT.frenzyPower.getTotal();
			if (frenzyMeter >= 100*(frenzyLevel+1)) {
				frenzyMeter -= 100*(frenzyLevel+1);
				game.PD.frenzyMeter = frenzyMeter;
				frenzyLevel++;
				game.PD.frenzyLevel = frenzyLevel;
			}
			game.PD.frenzyMeter = frenzyMeter;
		}
	    game.inventory.itemList[30].Increase(seaweedGained);
		game.inventory.itemList[selectedFish.ID].Increase(fishGained);
		game.PD.addExp(Screen.page_ID.FISHING, selectedFish.exp.multiply(new BigInteger(""+fishGained)).add(new BigInteger(""+seaweedGained)));
		
		onScreenItems.add(new ItemGraphic(game.inventory.itemList[30], seaweedGained, 50, rand.nextInt(16)+123,rand.nextInt(9)+70,1));
		if (fishGained > 0) {
			onScreenItems.add(new ItemGraphic(game.inventory.itemList[selectedFish.ID], fishGained, 50, rand.nextInt(16)+145,rand.nextInt(9)+70,1));
		}
		Item fishTotal = new Item(selectedFish.ID); fishTotal.Increase(fishGained);
		Item seaweedTotal = new Item(30); seaweedTotal.Increase(seaweedGained);
		
		if (game.PD.getExp(page_ID.FISHING).compareTo(expfornextlevel) == 1){
			game.PD.incrementLevel(page_ID.FISHING);
			expfornextlevel = game.PD.getNextLevelReq(page_ID.FISHING);
		}
		
		return new Item[] {fishTotal, seaweedTotal};
	}
	
	
	public void draw(Graphics g) {
		super.draw(g);
		castIntervalCounter--;
		casting.drawOneCycle(g, 116*Game.SCREENSCALE, 99*Game.SCREENSCALE, Images.fishinganimation[0].getWidth()*2, Images.fishinganimation[0].getHeight()*2);
		switch(statTab) {
		case POWER:
			g.drawImage(Images.fishingUI[0],FISHINGMENUX*Game.SCREENSCALE,FISHINGMENUY*Game.SCREENSCALE,Images.fishingUI[0].getWidth()*Game.SCREENSCALE,Images.fishingUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case SPEED:
			g.drawImage(Images.fishingUI[0],FISHINGMENUX*Game.SCREENSCALE,FISHINGMENUY*Game.SCREENSCALE,Images.fishingUI[0].getWidth()*Game.SCREENSCALE,Images.fishingUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case FRENZYPOWER:
			g.drawImage(Images.fishingUI[0],FISHINGMENUX*Game.SCREENSCALE,FISHINGMENUY*Game.SCREENSCALE,Images.fishingUI[0].getWidth()*Game.SCREENSCALE,Images.fishingUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case FRENZYDURATION:
			g.drawImage(Images.fishingUI[0],FISHINGMENUX*Game.SCREENSCALE,FISHINGMENUY*Game.SCREENSCALE,Images.fishingUI[0].getWidth()*Game.SCREENSCALE,Images.fishingUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case STATS:
			g.drawImage(Images.fishingUI[1],FISHINGMENUX*Game.SCREENSCALE,FISHINGMENUY*Game.SCREENSCALE,Images.fishingUI[0].getWidth()*Game.SCREENSCALE,Images.fishingUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		}
//		g.drawImage(Images.woodcuttingTrees[selectedFish.ID-21],115*Game.SCREENSCALE,56*Game.SCREENSCALE,Images.woodcuttingTrees[selectedFish.ID-21].getWidth()*Game.SCREENSCALE,Images.woodcuttingTrees[selectedFish.ID-21].getHeight()*Game.SCREENSCALE,null);
		if (statTab != stat_tab.STATS) {
			g.drawImage(Images.staticons[8],(FISHINGMENUX+17)*Game.SCREENSCALE,(FISHINGMENUY+7)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[9],(FISHINGMENUX+36)*Game.SCREENSCALE,(FISHINGMENUY+7)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[10],(FISHINGMENUX+55)*Game.SCREENSCALE,(FISHINGMENUY+7)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[11],(FISHINGMENUX+74)*Game.SCREENSCALE,(FISHINGMENUY+7)*Game.SCREENSCALE,32,32,null);
			displayText("Level:"+findSelected().getCurrentLevel(),g,FISHINGMENUX+7,FISHINGMENUY+67);
			displayText("Bonus:"+findSelected().getText(),g,FISHINGMENUX+7,FISHINGMENUY+25);
			displayText(findSelected().getBonusText(),g,FISHINGMENUX+7,FISHINGMENUY+32);
			if (findSelected().getCurrentLevel()==findSelected().getLevelMax()) {
				displayText("MAX",g,FISHINGMENUX+7,FISHINGMENUY+48);
			}
			else {
				displayCost(game.inventory.itemList[findSelected().getCostID()].Quanity(),findSelected().getCostQuanity(),game.inventory.itemList[findSelected().getCostID()],g,FISHINGMENUX+7,FISHINGMENUY+48);
			}
			if (findSelected().getCurrentLevel() == 0) {
				g.drawImage(Images.fishingUIicons[2],(FISHINGMENUX+74)*Game.SCREENSCALE,(FISHINGMENUY+63)*Game.SCREENSCALE,32,32,null);
			}
			else {
				g.drawImage(Images.fishingUIicons[1],(FISHINGMENUX+74)*Game.SCREENSCALE,(FISHINGMENUY+63)*Game.SCREENSCALE,32,32,null);
			}
		}
		else {
			displayItems(Arrays.copyOfRange(game.inventory.itemList,30,37), g, FISHINGMENUX+67, FISHINGMENUY+4);
			g.drawImage(Images.staticons[4],(FISHINGMENUX+3)*Game.SCREENSCALE,(FISHINGMENUY+4)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[5],(FISHINGMENUX+3)*Game.SCREENSCALE,(FISHINGMENUY+14)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[6],(FISHINGMENUX+3)*Game.SCREENSCALE,(FISHINGMENUY+24)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[7],(FISHINGMENUX+3)*Game.SCREENSCALE,(FISHINGMENUY+34)*Game.SCREENSCALE,32,32,null);
			displayText("fishing power",g,FISHINGMENUX+15,FISHINGMENUY+6);
			displayText("fishing speed",g,FISHINGMENUX+15,FISHINGMENUY+16);
			displayText("frenzy power",g,FISHINGMENUX+15,FISHINGMENUY+26);
			displayText("frenzy duration",g,FISHINGMENUX+15,FISHINGMENUY+36);
			displayText(""+game.UT.fishingPower.getTotalText(),g,FISHINGMENUX+15,FISHINGMENUY+10);
			displayText(""+getSeconds(Math.max(CASTBASEINTERVAL-game.UT.fishingSpeed.getTotal(),20))+" sec delay",g,FISHINGMENUX+15,FISHINGMENUY+20);
			displayText(""+game.UT.frenzyPower.getTotal(),g,FISHINGMENUX+15,FISHINGMENUY+30);
			displayText(""+getSeconds(game.UT.frenzyDuration.getTotal())+"sec delay",g,FISHINGMENUX+15,FISHINGMENUY+40);
		}

		
		g.drawImage(game.inventory.itemList[selectedFish.ID].Icon(),137*Game.SCREENSCALE,29*Game.SCREENSCALE,48,48,null);
		displayText("upgrades",g,FISHINGMENUX+6,FISHINGMENUY+82);
		displayText("stats",g,FISHINGMENUX+43,FISHINGMENUY+82);
		displayText("filler",g,FISHINGMENUX+73,FISHINGMENUY+82);
		
		g.drawImage(Images.fishingcharacter[0],48*Game.SCREENSCALE,39*Game.SCREENSCALE,Images.fishingcharacter[0].getWidth()*Game.SCREENSCALE,Images.fishingcharacter[0].getHeight()*Game.SCREENSCALE,null);
		displayText(""+game.PD.getLevel(Screen.page_ID.FISHING),g, 270, 132);
		displayText(""+game.PD.getExp(Screen.page_ID.FISHING),g, 10, 136);
		if (castIntervalCounter == 0) {
			catchFish();
			castIntervalCounter = (int) (Math.max(CASTBASEINTERVAL-game.UT.fishingSpeed.getTotal(),20));
			casting.setFrameCounter(0);
		}
		drawOnScreenItems(g);
		displayExpBar(g, 228*Game.SCREENSCALE, 132*Game.SCREENSCALE, expfornextlevel, page_ID.FISHING);
		
		g.setColor(Images.BLUE);
		g.fillRect(5*Game.SCREENSCALE, 30*Game.SCREENSCALE, (int) ((float)frenzyMeter/(float)(100*(frenzyLevel+1))*99), 3*Game.SCREENSCALE);
		displayText(""+frenzyLevel,g,9,43);
		
		if (frenzyTimer > 0) {
			frenzyTimer--;
			displayText(""+getSeconds(frenzyTimer),g,41,30);
		}
	}
	
	
	private void defineButtons() {
		fishLeft = new JButton();
		fishLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedFish = selectedFish.getPrev();
				game.PD.fishSelected = selectedFish;
				}
			});
		fishLeft.setBounds(124*Game.SCREENSCALE,30*Game.SCREENSCALE,12*Game.SCREENSCALE,14*Game.SCREENSCALE);
		fishLeft.setContentAreaFilled(false);
		fishLeft.setBorderPainted(false);
		fishLeft.setFocusPainted(false);
		
		fishRight = new JButton();
		fishRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedFish = selectedFish.getNext();
				game.PD.fishSelected = selectedFish;
				}
			});
		fishRight.setBounds(154*Game.SCREENSCALE,30*Game.SCREENSCALE,12*Game.SCREENSCALE,14*Game.SCREENSCALE);
		fishRight.setContentAreaFilled(false);
		fishRight.setBorderPainted(false);
		fishRight.setFocusPainted(false);
		
		upgradePage = new JButton();
		upgradePage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.POWER;
				removeButtons(game);
				fishingButtons.clear();
				fishingButtons.add(fishLeft);
				fishingButtons.add(fishRight);
				fishingButtons.add(statPage);
				fishingButtons.add(upgradePage);
				fishingButtons.add(fishingPowerSelect);
				fishingButtons.add(fishingSpeedSelect);
				fishingButtons.add(frenzyPowerSelect);
				fishingButtons.add(frenzyDurationSelect);
				fishingButtons.add(statUpgrade);
				fishingButtons.add(frenzyActivate);
				addButtons(game);
				}
			});
		upgradePage.setBounds((FISHINGMENUX+3)*Game.SCREENSCALE,(FISHINGMENUY+79)*Game.SCREENSCALE,31*Game.SCREENSCALE,10*Game.SCREENSCALE);
		upgradePage.setContentAreaFilled(false);
		upgradePage.setBorderPainted(false);
		upgradePage.setFocusPainted(false);
		
		statPage = new JButton();
		statPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.STATS;
				removeButtons(game);
				fishingButtons.clear();
				fishingButtons.add(fishLeft);
				fishingButtons.add(fishRight);
				fishingButtons.add(statPage);
				fishingButtons.add(upgradePage);
				fishingButtons.add(frenzyActivate);
				addButtons(game);
				}
			});
		statPage.setBounds((FISHINGMENUX+35)*Game.SCREENSCALE,(FISHINGMENUY+79)*Game.SCREENSCALE,31*Game.SCREENSCALE,10*Game.SCREENSCALE);
		statPage.setContentAreaFilled(false);
		statPage.setBorderPainted(false);
		statPage.setFocusPainted(false);
		
		fishingPowerSelect = new JButton();
		fishingPowerSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.POWER;
				}
			});
		fishingPowerSelect.setBounds((FISHINGMENUX+14)*Game.SCREENSCALE,(FISHINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		fishingPowerSelect.setContentAreaFilled(false);
		fishingPowerSelect.setBorderPainted(false);
		fishingPowerSelect.setFocusPainted(false);
		
		fishingSpeedSelect = new JButton();
		fishingSpeedSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.SPEED;
				}
			});
		fishingSpeedSelect.setBounds((FISHINGMENUX+33)*Game.SCREENSCALE,(FISHINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		fishingSpeedSelect.setContentAreaFilled(false);
		fishingSpeedSelect.setBorderPainted(false);
		fishingSpeedSelect.setFocusPainted(false);
		
		frenzyPowerSelect = new JButton();
		frenzyPowerSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.FRENZYPOWER;
				}
			});
		frenzyPowerSelect.setBounds((FISHINGMENUX+52)*Game.SCREENSCALE,(FISHINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		frenzyPowerSelect.setContentAreaFilled(false);
		frenzyPowerSelect.setBorderPainted(false);
		frenzyPowerSelect.setFocusPainted(false);

		frenzyDurationSelect = new JButton();
		frenzyDurationSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.FRENZYDURATION;
				}
			});
		frenzyDurationSelect.setBounds((FISHINGMENUX+71)*Game.SCREENSCALE,(FISHINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		frenzyDurationSelect.setContentAreaFilled(false);
		frenzyDurationSelect.setBorderPainted(false);
		frenzyDurationSelect.setFocusPainted(false);
		
		statUpgrade = new JButton();
		statUpgrade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				attemptUpgrade(findSelected());
				}
			});
		statUpgrade.setBounds((FISHINGMENUX+64)*Game.SCREENSCALE,(FISHINGMENUY+60)*Game.SCREENSCALE,32*Game.SCREENSCALE,16*Game.SCREENSCALE);
		statUpgrade.setContentAreaFilled(false);
		statUpgrade.setBorderPainted(false);
		statUpgrade.setFocusPainted(false);
		
		
		frenzyActivate = new JButton();
		frenzyActivate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (frenzyLevel >= 1) {
					frenzyTimer = (int) game.UT.frenzyDuration.getTotal();
					activeFrenzyLevel = frenzyLevel+1;
					frenzyLevel = 0;
					frenzyMeter = 0;
					}
				}
			});
		frenzyActivate.setBounds(3*Game.SCREENSCALE,37*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		frenzyActivate.setContentAreaFilled(false);
		frenzyActivate.setBorderPainted(false);
		frenzyActivate.setFocusPainted(false);
	}
	
}
