package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;

import Data.UpgradeTracker.Upgrade;
import main.Game;
import resources.Animation;
import resources.Images;
import resources.ItemGraphic;
import screens.MiningScreen.stat_tab;
import screens.Screen.page_ID;

public class WoodcuttingScreen extends Screen{
	
	public enum stat_tab {
		POWER,SPEED,QUICKCHOP,POWERCHOP,STATS;
	}
	public enum wood {
		OAK(21,100,1),BIRCH(22,200,3),TEAK(23,600,12),WILLOW(24,2400,60),MAHOGANY(25,12000,360),MAPLE(26,72000,2520);
		private final int ID;
		private final int durability;
		private final double exp;
		
		private wood(int ID,int durability,double exp) {
			this.ID = ID;
			this.durability = durability;
			this.exp = exp;
		}
		
		public wood getNext() {
			if (this == MAPLE) {
				return this;
			}
			return values()[ordinal()+1%values().length];
		}
		public wood getPrev() {
			if (this == OAK) {
				return this;
			}
			return values()[ordinal()-1%values().length];
		}
		
	}
	
	private final int WOODCUTTINGMENUX = 186;
	private final int WOODCUTTINGMENUY = 30;
	private final int CHOPBASEINTERVAL = 300;
	private int chopIntervalCounter;
	private long expfornextlevel;

	
	private stat_tab statTab;
	private wood selectedTree;
	
	private Game game;
	
	public JButton treeLeft,treeRight,woodcuttingPowerSelect,woodcuttingSpeedSelect,quickChopSelect,powerChopSelect,statUpgrade,upgradePage,statPage;
	private LinkedList<JButton> woodcuttingButtons;

	public Animation chopping = new Animation(Images.choppinganimation, 3);
	
	
	public WoodcuttingScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.WOODCUTTING.ID], game);
		this.game = game;
		defineButtons();
		woodcuttingButtons = new LinkedList<JButton>();
		statTab = stat_tab.POWER;
		selectedTree=wood.OAK;
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
	    expfornextlevel = game.PD.getNextLevelReq(page_ID.WOODCUTTING);
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
	
	private void chopWood() {
		Random rand = new Random();
		int woodGained = 0;
		double a = selectedTree.durability;
		double b = game.UT.woodcuttingPower.getTotal();
		double c = game.UT.powerChopChance.getTotal();
		while (c >= 100) {
			b = b*game.UT.miningCritMod.getTotal();
			c = c-100;
		}
		if (rand.nextInt(100) <= c) {
			b = b*game.UT.miningCritMod.getTotal();
		}
		while ( b >= a) {
			b = b-a;
			woodGained++;
		}
		if (rand.nextInt(selectedTree.durability) <= b) {
			woodGained++;
		}
		game.inventory.itemList[selectedTree.ID].Increase(woodGained);
		game.PD.addExp(Screen.page_ID.WOODCUTTING, selectedTree.exp*woodGained);
		
		if (woodGained > 0) {
			onScreenItems.add(new ItemGraphic(game.inventory.itemList[selectedTree.ID], woodGained, 50, rand.nextInt(32)+125,rand.nextInt(8)+70,1));
		}
		
	}
	
	
	public void draw(Graphics g) {
		super.draw(g);
		chopIntervalCounter--;
		chopping.drawOneCycle(g, 109*Game.SCREENSCALE, 92*Game.SCREENSCALE, Images.choppinganimation[0].getWidth()*2, Images.choppinganimation[0].getHeight()*2);
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
				displayText(game.inventory.itemList[findSelected().getCost()[0]].Quanity()+"/"+findSelected().getCost()[1],g,WOODCUTTINGMENUX+33,WOODCUTTINGMENUY+48);
				displayIcon(game.inventory.itemList[findSelected().getCost()[0]],g,WOODCUTTINGMENUX+22,WOODCUTTINGMENUY+44);
				displayText("cost:",g,WOODCUTTINGMENUX+7,WOODCUTTINGMENUY+48);
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
			displayText(""+getSeconds(CHOPBASEINTERVAL-game.UT.woodcuttingSpeed.getTotal())+" sec delay",g,WOODCUTTINGMENUX+15,WOODCUTTINGMENUY+20);
			displayText(""+getPercent(game.UT.quickChopChance.getTotal())+"%",g,WOODCUTTINGMENUX+15,WOODCUTTINGMENUY+30);
			displayText(""+getPercent(game.UT.powerChopChance.getTotal())+"%",g,WOODCUTTINGMENUX+15,WOODCUTTINGMENUY+40);
		}

		
		g.drawImage(game.inventory.itemList[selectedTree.ID].Icon(),137*Game.SCREENSCALE,29*Game.SCREENSCALE,48,48,null);
		displayText("upgrades",g,WOODCUTTINGMENUX+6,WOODCUTTINGMENUY+82);
		displayText("stats",g,WOODCUTTINGMENUX+43,WOODCUTTINGMENUY+82);
		displayText("filler",g,WOODCUTTINGMENUX+73,WOODCUTTINGMENUY+82);
		
		g.drawImage(Images.woodcuttingcharacter[0],48*Game.SCREENSCALE,40*Game.SCREENSCALE,Images.miningcharacter[0].getWidth()*Game.SCREENSCALE,Images.miningcharacter[0].getHeight()*Game.SCREENSCALE,null);
		displayText(""+game.PD.getLevel(Screen.page_ID.WOODCUTTING),g, 270, 132);
		displayText(""+game.PD.getExp(Screen.page_ID.WOODCUTTING),g, 10, 136);
		if (chopIntervalCounter == 0) {
			chopWood();
			chopIntervalCounter = (int) (Math.max(CHOPBASEINTERVAL-game.UT.woodcuttingSpeed.getTotal(),20));
			chopping.setFrameCounter(0);
		}
		if (game.PD.getExp(page_ID.WOODCUTTING) >= expfornextlevel){
			game.PD.incrementLevel(page_ID.WOODCUTTING);
			expfornextlevel = game.PD.getNextLevelReq(page_ID.WOODCUTTING);
		}
		drawOnScreenItems(g);
		displayExpBar(g, 228*Game.SCREENSCALE, 132*Game.SCREENSCALE, expfornextlevel, page_ID.WOODCUTTING);
	}
	
	
	private void defineButtons() {
		treeLeft = new JButton();
		treeLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedTree = selectedTree.getPrev();
				}
			});
		treeLeft.setBounds(124*Game.SCREENSCALE,30*Game.SCREENSCALE,12*Game.SCREENSCALE,14*Game.SCREENSCALE);
		treeLeft.setContentAreaFilled(false);
		
		treeRight = new JButton();
		treeRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedTree = selectedTree.getNext();
				}
			});
		treeRight.setBounds(154*Game.SCREENSCALE,30*Game.SCREENSCALE,12*Game.SCREENSCALE,14*Game.SCREENSCALE);
		treeRight.setContentAreaFilled(false);
		
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
		
		woodcuttingPowerSelect = new JButton();
		woodcuttingPowerSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.POWER;
				}
			});
		woodcuttingPowerSelect.setBounds((WOODCUTTINGMENUX+14)*Game.SCREENSCALE,(WOODCUTTINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		woodcuttingPowerSelect.setContentAreaFilled(false);
		
		woodcuttingSpeedSelect = new JButton();
		woodcuttingSpeedSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.SPEED;
				}
			});
		woodcuttingSpeedSelect.setBounds((WOODCUTTINGMENUX+33)*Game.SCREENSCALE,(WOODCUTTINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		woodcuttingSpeedSelect.setContentAreaFilled(false);
		
		quickChopSelect = new JButton();
		quickChopSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.QUICKCHOP;
				}
			});
		quickChopSelect.setBounds((WOODCUTTINGMENUX+52)*Game.SCREENSCALE,(WOODCUTTINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		quickChopSelect.setContentAreaFilled(false);

		powerChopSelect = new JButton();
		powerChopSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.POWERCHOP;
				}
			});
		powerChopSelect.setBounds((WOODCUTTINGMENUX+71)*Game.SCREENSCALE,(WOODCUTTINGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		powerChopSelect.setContentAreaFilled(false);
		
		statUpgrade = new JButton();
		statUpgrade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				attemptUpgrade(findSelected());
				}
			});
		statUpgrade.setBounds((WOODCUTTINGMENUX+64)*Game.SCREENSCALE,(WOODCUTTINGMENUY+60)*Game.SCREENSCALE,32*Game.SCREENSCALE,16*Game.SCREENSCALE);
		statUpgrade.setContentAreaFilled(false);
		
	}
}
