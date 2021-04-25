package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class WoodcuttingScreen extends Screen{
	
	public enum stat_tab {
		POWER,SPEED,QUICKCHOP,POWERCHOP,STATS;
	}
	public enum Wood {
		OAK(21,100,new BigInteger("5"),1),BIRCH(22,200,new BigInteger("15"),11),TEAK(23,600,new BigInteger("60"),21),WILLOW(24,2400,new BigInteger("300"),31),MAHOGANY(25,12000,new BigInteger("1800"),41),MAPLE(26,72000,new BigInteger("12600"),51);
		private final int ID;
		private final int durability;
		private final BigInteger exp;
		private final int unlockLevel;

		
		private Wood(int ID,int durability,BigInteger exp,int unlockLevel) {
			this.ID = ID;
			this.durability = durability;
			this.exp = exp;
			this.unlockLevel = unlockLevel;
		}
		
		public Wood getNext() {
			if (this == MAPLE) {
				return this;
			}
			return values()[ordinal()+1%values().length];
		}
		public Wood getPrev() {
			if (this == OAK) {
				return this;
			}
			return values()[ordinal()-1%values().length];
		}
		
	}
	
	private final static int WOODCUTTINGMENUX = 186;
	private final static int WOODCUTTINGMENUY = 30;
	private final static int CHOPBASEINTERVAL = 300;
	private int chopIntervalCounter;
	private BigInteger expfornextlevel;

	
	private stat_tab statTab;
	private Wood selectedTree;
	private int quickChopStack;
	
	private Game game;
	
	public JButton treeLeft,treeRight,woodcuttingPowerSelect,woodcuttingSpeedSelect,quickChopSelect,powerChopSelect,statUpgrade,upgradePage,statPage;
	private LinkedList<JButton> woodcuttingButtons;

	public Animation chopping = new Animation(Images.choppinganimation, 3);
	public Animation quickchopping = new Animation(Images.quickchopanimation, 3);
	
	
	public WoodcuttingScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.WOODCUTTING.ID], game);
		this.game = game;
		defineButtons();
		woodcuttingButtons = new LinkedList<JButton>();
		statTab = stat_tab.POWER;
		selectedTree=game.PD.treeSelected;
		woodcuttingButtons.add(treeLeft);
		woodcuttingButtons.add(treeRight);
		woodcuttingButtons.add(statPage);
		woodcuttingButtons.add(upgradePage);
		woodcuttingButtons.add(woodcuttingPowerSelect);
		woodcuttingButtons.add(woodcuttingSpeedSelect);
		woodcuttingButtons.add(quickChopSelect);
		woodcuttingButtons.add(powerChopSelect);
		woodcuttingButtons.add(statUpgrade);
		chopIntervalCounter = (int) (Math.max(CHOPBASEINTERVAL-game.UT.woodcuttingSpeed.getTotal(),20));
	    chopping.setFrameCounterToEnd();
	    quickchopping.setFrameCounterToEnd();
	    expfornextlevel = game.PD.getNextLevelReq(page_ID.WOODCUTTING);
	    quickChopStack = game.PD.quickChopStack;
	}

	public void addButtons(Game game) {
		super.addButtons(game);
		for (int i = 0; i < woodcuttingButtons.size(); i++) {
			game.add(woodcuttingButtons.get(i));
		}
	}
	
	public void removeButtons(Game game) {
		super.removeButtons(game);
		for (int i = 0; i < woodcuttingButtons.size(); i++) {
			game.remove(woodcuttingButtons.get(i));
		}
	}
	
	private Upgrade findSelected() {
		switch(statTab) {
		case POWER:
			return game.UT.woodcuttingPage0;
		case SPEED:
			return game.UT.woodcuttingPage1;
		case QUICKCHOP:
			return game.UT.woodcuttingPage2;
		case POWERCHOP:
			return game.UT.woodcuttingPage3;
		case STATS:
			break;
			}
		return null;
	}
	
	public static Item[] simulateChopWood(Game game, long years, long days, long hours, long minutes) {
		game.PD.saveToggle = false;
		Item[] itemsGained = new Item[] {new Item(game.PD.treeSelected.ID), new Item(20)};
		WoodcuttingScreen page = new WoodcuttingScreen(game);
		double actionsPerMinute = 60/getSeconds(Math.max(CHOPBASEINTERVAL-game.UT.miningSpeed.getTotal(),20));
		double actionsPerHour = actionsPerMinute*60;
		double actionsPerDay = actionsPerHour*24;
		long actionsPerYear = (long)(actionsPerDay*365);
		for (int i = 0; i < minutes; i++) {
			for (int j = 0; j < actionsPerMinute; j++) {
				Item[] gains = page.chopWood();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		for (int i = 0; i < hours; i++) {
			for (int j = 0; j < actionsPerHour; j++) {
				Item[] gains = page.chopWood();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		for (int i = 0; i < days; i++) {
			for (int j = 0; j < actionsPerDay; j++) {
				Item[] gains = page.chopWood();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		for (int i = 0; i < years; i++) {
			for (long j = 0; j < actionsPerYear; j++) {
				Item[] gains = page.chopWood();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		while (game.PD.getExp(page_ID.WOODCUTTING).compareTo(game.PD.getNextLevelReq(page_ID.WOODCUTTING)) == 1) {
			game.PD.incrementLevel(page_ID.WOODCUTTING);
		}
		game.PD.saveToggle = true;
		return itemsGained;
	}
	
	private Item[] chopWood() {
		Random rand = new Random();
		int woodGained = 0;
		double durability = selectedTree.durability;
		double power = game.UT.woodcuttingPower.getTotal();
		double quickChop = game.UT.quickChopChance.getTotal()*100;
		if (rand.nextInt(101) <= quickChop && quickChopStack < 4) {
			quickChopStack++;
			game.PD.quickChopStack = quickChopStack;
		}
		while ( power >= durability) {
			power = power-durability;
			woodGained++;
		}
		if (rand.nextInt(selectedTree.durability) <= power) {
			woodGained++;
		}
		int leafGained = rand.nextInt(Math.max(woodGained,1))+game.PD.getLevel(page_ID.WOODCUTTING);
	    game.inventory.itemList[20].Increase(leafGained);
		game.inventory.itemList[selectedTree.ID].Increase(woodGained);
		game.PD.addExp(Screen.page_ID.WOODCUTTING, selectedTree.exp.multiply(new BigInteger(""+woodGained)).add(new BigInteger(""+leafGained)));
		
		if (!game.PD.simulating) {
			onScreenItems.add(new ItemGraphic(game.inventory.itemList[20], leafGained, 50, rand.nextInt(16)+123,rand.nextInt(9)+70,1));
			if (woodGained > 0) {
				onScreenItems.add(new ItemGraphic(game.inventory.itemList[selectedTree.ID], woodGained, 50, rand.nextInt(16)+145,rand.nextInt(9)+70,1));
			}
		}

		Item woodTotal = new Item(selectedTree.ID); woodTotal.Increase(woodGained);
		Item leafTotal = new Item(20); leafTotal.Increase(leafGained);
		
		if (game.PD.getExp(page_ID.WOODCUTTING).compareTo(expfornextlevel) == 1){
			game.PD.incrementLevel(page_ID.WOODCUTTING);
			expfornextlevel = game.PD.getNextLevelReq(page_ID.WOODCUTTING);
		}
		
		return new Item[] {woodTotal, leafTotal};
	}
	
	
	public void draw(Graphics g) {
		super.draw(g);
		chopIntervalCounter--;
		chopping.drawOneCycle(g, 109*Game.SCREENSCALE, 92*Game.SCREENSCALE, Images.choppinganimation[0].getWidth()*2, Images.choppinganimation[0].getHeight()*2);
		quickchopping.drawOneCycle(g, 185*Game.SCREENSCALE, 92*Game.SCREENSCALE, -Images.quickchopanimation[0].getWidth()*2, Images.quickchopanimation[0].getHeight()*2);
		switch(statTab) {
		case POWER:
			g.drawImage(Images.woodcuttingUI[0],WOODCUTTINGMENUX*Game.SCREENSCALE,WOODCUTTINGMENUY*Game.SCREENSCALE,Images.woodcuttingUI[0].getWidth()*Game.SCREENSCALE,Images.woodcuttingUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case SPEED:
			g.drawImage(Images.woodcuttingUI[0],WOODCUTTINGMENUX*Game.SCREENSCALE,WOODCUTTINGMENUY*Game.SCREENSCALE,Images.woodcuttingUI[0].getWidth()*Game.SCREENSCALE,Images.woodcuttingUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case QUICKCHOP:
			g.drawImage(Images.woodcuttingUI[0],WOODCUTTINGMENUX*Game.SCREENSCALE,WOODCUTTINGMENUY*Game.SCREENSCALE,Images.woodcuttingUI[0].getWidth()*Game.SCREENSCALE,Images.woodcuttingUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case POWERCHOP:
			g.drawImage(Images.woodcuttingUI[0],WOODCUTTINGMENUX*Game.SCREENSCALE,WOODCUTTINGMENUY*Game.SCREENSCALE,Images.woodcuttingUI[0].getWidth()*Game.SCREENSCALE,Images.woodcuttingUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case STATS:
			g.drawImage(Images.woodcuttingUI[1],WOODCUTTINGMENUX*Game.SCREENSCALE,WOODCUTTINGMENUY*Game.SCREENSCALE,Images.woodcuttingUI[0].getWidth()*Game.SCREENSCALE,Images.woodcuttingUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		}
		g.drawImage(Images.woodcuttingTrees[selectedTree.ID-21],115*Game.SCREENSCALE,56*Game.SCREENSCALE,Images.woodcuttingTrees[selectedTree.ID-21].getWidth()*Game.SCREENSCALE,Images.woodcuttingTrees[selectedTree.ID-21].getHeight()*Game.SCREENSCALE,null);
		if (statTab != stat_tab.STATS) {
			g.drawImage(Images.staticons[4],(WOODCUTTINGMENUX+17)*Game.SCREENSCALE,(WOODCUTTINGMENUY+7)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[5],(WOODCUTTINGMENUX+36)*Game.SCREENSCALE,(WOODCUTTINGMENUY+7)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[6],(WOODCUTTINGMENUX+55)*Game.SCREENSCALE,(WOODCUTTINGMENUY+7)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[7],(WOODCUTTINGMENUX+74)*Game.SCREENSCALE,(WOODCUTTINGMENUY+7)*Game.SCREENSCALE,32,32,null);
			displayText("Level:"+findSelected().getCurrentLevel(),g,WOODCUTTINGMENUX+7,WOODCUTTINGMENUY+67);
			displayText("Bonus:"+findSelected().getText(),g,WOODCUTTINGMENUX+7,WOODCUTTINGMENUY+25);
			displayText(findSelected().getBonusText(),g,WOODCUTTINGMENUX+7,WOODCUTTINGMENUY+32);
			if (findSelected().getCurrentLevel()==findSelected().getLevelMax()) {
				displayText("MAX",g,WOODCUTTINGMENUX+7,WOODCUTTINGMENUY+48);
			}
			else {
				displayCost(game.inventory.itemList[findSelected().getCostID()].Quanity(),findSelected().getCostQuanity(),game.inventory.itemList[findSelected().getCostID()],g,WOODCUTTINGMENUX+7,WOODCUTTINGMENUY+48);
			}
			if (findSelected().getCurrentLevel() == 0) {
				g.drawImage(Images.woodcuttingUIicons[2],(WOODCUTTINGMENUX+74)*Game.SCREENSCALE,(WOODCUTTINGMENUY+63)*Game.SCREENSCALE,32,32,null);
			}
			else {
				g.drawImage(Images.woodcuttingUIicons[1],(WOODCUTTINGMENUX+74)*Game.SCREENSCALE,(WOODCUTTINGMENUY+63)*Game.SCREENSCALE,32,32,null);
			}
		}
		else {
			displayItems(Arrays.copyOfRange(game.inventory.itemList,20,27), g, WOODCUTTINGMENUX+67, WOODCUTTINGMENUY+4);
			g.drawImage(Images.staticons[4],(WOODCUTTINGMENUX+3)*Game.SCREENSCALE,(WOODCUTTINGMENUY+4)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[5],(WOODCUTTINGMENUX+3)*Game.SCREENSCALE,(WOODCUTTINGMENUY+14)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[6],(WOODCUTTINGMENUX+3)*Game.SCREENSCALE,(WOODCUTTINGMENUY+24)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[7],(WOODCUTTINGMENUX+3)*Game.SCREENSCALE,(WOODCUTTINGMENUY+34)*Game.SCREENSCALE,32,32,null);
			displayText("woodcutting power",g,WOODCUTTINGMENUX+15,WOODCUTTINGMENUY+6);
			displayText("woodcutting speed",g,WOODCUTTINGMENUX+15,WOODCUTTINGMENUY+16);
			displayText("quickchop chance",g,WOODCUTTINGMENUX+15,WOODCUTTINGMENUY+26);
			displayText("powerchop chance",g,WOODCUTTINGMENUX+15,WOODCUTTINGMENUY+36);
			displayText(""+game.UT.woodcuttingPower.getTotalText(),g,WOODCUTTINGMENUX+15,WOODCUTTINGMENUY+10);
			displayText(""+getSeconds(Math.max(CHOPBASEINTERVAL-game.UT.woodcuttingSpeed.getTotal(),20))+" sec delay",g,WOODCUTTINGMENUX+15,WOODCUTTINGMENUY+20);
			displayText(""+getPercent(game.UT.quickChopChance.getTotal())+"%",g,WOODCUTTINGMENUX+15,WOODCUTTINGMENUY+30);
			displayText(""+getPercent(game.UT.powerChopChance.getTotal())+"%",g,WOODCUTTINGMENUX+15,WOODCUTTINGMENUY+40);
		}

		
		g.drawImage(game.inventory.itemList[selectedTree.ID].Icon(),137*Game.SCREENSCALE,29*Game.SCREENSCALE,48,48,null);
		displayText("upgrades",g,WOODCUTTINGMENUX+6,WOODCUTTINGMENUY+82);
		displayText("stats",g,WOODCUTTINGMENUX+43,WOODCUTTINGMENUY+82);
		displayText("filler",g,WOODCUTTINGMENUX+73,WOODCUTTINGMENUY+82);
		
		g.drawImage(Images.woodcuttingcharacter[0],48*Game.SCREENSCALE,40*Game.SCREENSCALE,Images.woodcuttingcharacter[0].getWidth()*Game.SCREENSCALE,Images.woodcuttingcharacter[0].getHeight()*Game.SCREENSCALE,null);
		displayText(""+game.PD.getLevel(Screen.page_ID.WOODCUTTING),g, 270, 132);
		displayText(""+game.PD.getExp(Screen.page_ID.WOODCUTTING),g, 10, 136);
		if (chopIntervalCounter == 0) {
			if (quickChopStack >= 4) {
				quickchopping.setFrameCounter(0);
				chopWood();
				quickChopStack = 0;
				game.PD.quickChopStack = quickChopStack;
			}
			chopWood();
			chopIntervalCounter = (int) (Math.max(CHOPBASEINTERVAL-game.UT.woodcuttingSpeed.getTotal(),20));
			chopping.setFrameCounter(0);
		}
		drawOnScreenItems(g);
		displayExpBar(g, 228*Game.SCREENSCALE, 132*Game.SCREENSCALE, expfornextlevel, page_ID.WOODCUTTING);
		
		g.drawImage(Images.quickchopstacks[quickChopStack],10*Game.SCREENSCALE,30*Game.SCREENSCALE,Images.quickchopstacks[0].getWidth()*Game.SCREENSCALE,Images.quickchopstacks[0].getHeight()*Game.SCREENSCALE,null);
	}
	
	
	private void defineButtons() {
		treeLeft = new JButton();
		treeLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedTree = selectedTree.getPrev();
				game.PD.treeSelected = selectedTree;
				}
			});
		treeLeft.setBounds(124*Game.SCREENSCALE,30*Game.SCREENSCALE,12*Game.SCREENSCALE,14*Game.SCREENSCALE);
		treeLeft.setContentAreaFilled(false);
		treeLeft.setBorderPainted(false);
		treeLeft.setFocusPainted(false);
		
		treeRight = new JButton();
		treeRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedTree = selectedTree.getNext();
				game.PD.treeSelected = selectedTree;
				}
			});
		treeRight.setBounds(154*Game.SCREENSCALE,30*Game.SCREENSCALE,12*Game.SCREENSCALE,14*Game.SCREENSCALE);
		treeRight.setContentAreaFilled(false);
		treeRight.setBorderPainted(false);
		treeRight.setFocusPainted(false);
		
		upgradePage = new JButton();
		upgradePage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.POWER;
				removeButtons(game);
				woodcuttingButtons.clear();
				woodcuttingButtons.add(treeLeft);
				woodcuttingButtons.add(treeRight);
				woodcuttingButtons.add(statPage);
				woodcuttingButtons.add(upgradePage);
				woodcuttingButtons.add(woodcuttingPowerSelect);
				woodcuttingButtons.add(woodcuttingSpeedSelect);
				woodcuttingButtons.add(quickChopSelect);
				woodcuttingButtons.add(powerChopSelect);
				woodcuttingButtons.add(statUpgrade);
				addButtons(game);
				}
			});
		upgradePage.setBounds((WOODCUTTINGMENUX+3)*Game.SCREENSCALE,(WOODCUTTINGMENUY+79)*Game.SCREENSCALE,31*Game.SCREENSCALE,10*Game.SCREENSCALE);
		upgradePage.setContentAreaFilled(false);
		upgradePage.setBorderPainted(false);
		upgradePage.setFocusPainted(false);
		
		statPage = new JButton();
		statPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.STATS;
				removeButtons(game);
				woodcuttingButtons.clear();
				woodcuttingButtons.add(treeLeft);
				woodcuttingButtons.add(treeRight);
				woodcuttingButtons.add(statPage);
				woodcuttingButtons.add(upgradePage);
				addButtons(game);
				}
			});
		statPage.setBounds((WOODCUTTINGMENUX+35)*Game.SCREENSCALE,(WOODCUTTINGMENUY+79)*Game.SCREENSCALE,31*Game.SCREENSCALE,10*Game.SCREENSCALE);
		statPage.setContentAreaFilled(false);
		statPage.setBorderPainted(false);
		statPage.setFocusPainted(false);
		
		woodcuttingPowerSelect = new JButton();
		woodcuttingPowerSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.POWER;
				}
			});
		woodcuttingPowerSelect.setBounds((WOODCUTTINGMENUX+14)*Game.SCREENSCALE,(WOODCUTTINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		woodcuttingPowerSelect.setContentAreaFilled(false);
		woodcuttingPowerSelect.setBorderPainted(false);
		woodcuttingPowerSelect.setFocusPainted(false);
		
		woodcuttingSpeedSelect = new JButton();
		woodcuttingSpeedSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.SPEED;
				}
			});
		woodcuttingSpeedSelect.setBounds((WOODCUTTINGMENUX+33)*Game.SCREENSCALE,(WOODCUTTINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		woodcuttingSpeedSelect.setContentAreaFilled(false);
		woodcuttingSpeedSelect.setBorderPainted(false);
		woodcuttingSpeedSelect.setFocusPainted(false);
		
		quickChopSelect = new JButton();
		quickChopSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.QUICKCHOP;
				}
			});
		quickChopSelect.setBounds((WOODCUTTINGMENUX+52)*Game.SCREENSCALE,(WOODCUTTINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		quickChopSelect.setContentAreaFilled(false);
		quickChopSelect.setBorderPainted(false);
		quickChopSelect.setFocusPainted(false);

		powerChopSelect = new JButton();
		powerChopSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.POWERCHOP;
				}
			});
		powerChopSelect.setBounds((WOODCUTTINGMENUX+71)*Game.SCREENSCALE,(WOODCUTTINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		powerChopSelect.setContentAreaFilled(false);
		powerChopSelect.setBorderPainted(false);
		powerChopSelect.setFocusPainted(false);
		
		statUpgrade = new JButton();
		statUpgrade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				attemptUpgrade(findSelected());
				}
			});
		statUpgrade.setBounds((WOODCUTTINGMENUX+64)*Game.SCREENSCALE,(WOODCUTTINGMENUY+60)*Game.SCREENSCALE,32*Game.SCREENSCALE,16*Game.SCREENSCALE);
		statUpgrade.setContentAreaFilled(false);
		statUpgrade.setBorderPainted(false);
		statUpgrade.setFocusPainted(false);
		
	}
}
