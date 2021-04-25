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

public class MiningScreen extends Screen{

	public enum stat_tab {
		POWER,SPEED,CRITCHANCE,CRITMOD,STATS;
	}

	public enum Ore {
		COPPER(11,100,new BigInteger("5"),1),IRON(12,200,new BigInteger("15"),11),SILVER(13,600,new BigInteger("60"),21),TUNGSTON(14,2400,new BigInteger("300"),31),GOLD(15,12000,new BigInteger("1800"),41),COBALT(16,72000,new BigInteger("12600"),51);
		private final int ID;
		private final int durability;
		private final BigInteger exp;
		private final int unlockLevel;
		
		private Ore(int ID,int durability,BigInteger exp,int unlockLevel) {
			this.ID = ID;
			this.durability = durability;
			this.exp = exp;
			this.unlockLevel = unlockLevel;
		}
		
		public Ore getNext() {
			if (this == COBALT) {
				return this;
			}
			return values()[ordinal()+1%values().length];
		}
		public Ore getPrev() {
			if (this == COPPER) {
				return this;
			}
			return values()[ordinal()-1%values().length];
		}
	}
	
	private static final int MININGMENUX = 186;
	private static final int MININGMENUY = 30;
	private static final int MINEBASEINTERVAL = 300;
	private int mineIntervalCounter;
	private BigInteger expfornextlevel;
	
	private stat_tab statTab;
	private Ore selectedOre;
	
	
	private Game game;
		
	public JButton oreLeft,oreRight,miningPowerSelect,miningSpeedSelect,miningCritChanceSelect,miningCritModSelect,statUpgrade,upgradePage,statPage;
	private LinkedList<JButton> miningButtons;

	public Animation mining = new Animation(Images.mininganimation, 3);
	public Animation critical = new Animation(Images.miningcritanimation, 3);
	
	public MiningScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.MINING.ID], game);
		this.game = game;
		defineButtons();
		miningButtons = new LinkedList<JButton>();
		statTab = stat_tab.POWER;
		selectedOre=game.PD.oreSelected;
		miningButtons.add(oreLeft);
		miningButtons.add(oreRight);
		miningButtons.add(statPage);
		miningButtons.add(upgradePage);
		miningButtons.add(miningPowerSelect);
		miningButtons.add(miningSpeedSelect);
		miningButtons.add(miningCritChanceSelect);
		miningButtons.add(miningCritModSelect);
		miningButtons.add(statUpgrade);
		mineIntervalCounter = (int) (Math.max(MINEBASEINTERVAL-game.UT.miningSpeed.getTotal(),20));
	    mining.setFrameCounterToEnd();
	    critical.setFrameCounterToEnd();
	    expfornextlevel = game.PD.getNextLevelReq(page_ID.MINING);
	}

	
	public void addButtons(Game game) {
		super.addButtons(game);
		for (int i = 0; i < miningButtons.size(); i++) {
			game.add(miningButtons.get(i));
		}
	}
	
	public void removeButtons(Game game) {
		super.removeButtons(game);
		for (int i = 0; i < miningButtons.size(); i++) {
			game.remove(miningButtons.get(i));
		}
	}
	
	private Upgrade findSelected() {
		switch(statTab) {
		case POWER:
			return game.UT.miningPage0;
		case SPEED:
			return game.UT.miningPage1;
		case CRITCHANCE:
			return game.UT.miningPage2;
		case CRITMOD:
			return game.UT.miningPage3;
		case STATS:
			break;
			}
		return null;
	}
	
	public static Item[] simulateMineOre(Game game, long years, long days, long hours, long minutes) {
		game.PD.saveToggle = false;
		Item[] itemsGained = new Item[] {new Item(game.PD.oreSelected.ID), new Item(10)};
		MiningScreen page = new MiningScreen(game);
		double actionsPerMinute = 60/getSeconds(Math.max(MINEBASEINTERVAL-game.UT.miningSpeed.getTotal(),20));
		double actionsPerHour = actionsPerMinute*60;
		double actionsPerDay = actionsPerHour*24;
		long actionsPerYear = (long)(actionsPerDay*365);
		for (int i = 0; i < minutes; i++) {
			for (int j = 0; j < actionsPerMinute; j++) {
				Item[] gains = page.mineOre();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		for (int i = 0; i < hours; i++) {
			for (int j = 0; j < actionsPerHour; j++) {
				Item[] gains = page.mineOre();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		for (int i = 0; i < days; i++) {
			for (int j = 0; j < actionsPerDay; j++) {
				Item[] gains = page.mineOre();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		for (int i = 0; i < years; i++) {
			for (long j = 0; j < actionsPerYear; j++) {
				Item[] gains = page.mineOre();
				itemsGained[0].Increase(gains[0].Quanity());
				itemsGained[1].Increase(gains[1].Quanity());
			}
		}
		while (game.PD.getExp(page_ID.MINING).compareTo(game.PD.getNextLevelReq(page_ID.MINING)) == 1) {
			game.PD.incrementLevel(page_ID.MINING);
		}
		game.PD.saveToggle = true;
		return itemsGained;
	}
	
	private Item[] mineOre() {
		Random rand = new Random();
		int oreGained = 0;
		double durability = selectedOre.durability;
		double power = game.UT.miningPower.getTotal();
		double crit = game.UT.miningCritChance.getTotal()*100;
		while (crit >= 100) {
			power = power*game.UT.miningCritMod.getTotal();
			crit = crit-100;
			if (!game.PD.simulating) critical.setFrameCounter(0);
		}
		if (rand.nextInt(101) <= crit) {
			power = power*game.UT.miningCritMod.getTotal();
			if (!game.PD.simulating) critical.setFrameCounter(0);
		}
		while ( power >= durability) {
			power = power-durability;
			oreGained++;
		}
		if (rand.nextInt(selectedOre.durability) <= power) {
			oreGained++;
		}
		int clayGained = rand.nextInt(Math.max(oreGained,1))+game.PD.getLevel(page_ID.MINING);
	    game.inventory.itemList[10].Increase(clayGained);
		game.inventory.itemList[selectedOre.ID].Increase(oreGained);
		game.PD.addExp(Screen.page_ID.MINING, selectedOre.exp.multiply(new BigInteger(""+oreGained)).add(new BigInteger(""+clayGained)));
		if (!game.PD.simulating) {
			onScreenItems.add(new ItemGraphic(game.inventory.itemList[10], clayGained, 50, rand.nextInt(16)+123,rand.nextInt(9)+80,1));
			if (oreGained > 0) {
				onScreenItems.add(new ItemGraphic(game.inventory.itemList[selectedOre.ID], oreGained, 50, rand.nextInt(16)+145,rand.nextInt(9)+80,1));
			}
		}

		Item oreTotal = new Item(selectedOre.ID); oreTotal.Increase(oreGained);
		Item clayTotal = new Item(10); clayTotal.Increase(clayGained);
		
		if (game.PD.getExp(page_ID.MINING).compareTo(expfornextlevel) == 1){
			game.PD.incrementLevel(page_ID.MINING);
			expfornextlevel = game.PD.getNextLevelReq(page_ID.MINING);
		}
		return new Item[] {oreTotal, clayTotal};
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		mineIntervalCounter--;
		mining.drawOneCycle(g, 116*Game.SCREENSCALE, 92*Game.SCREENSCALE, Images.mininganimation[0].getWidth()*2, Images.mininganimation[0].getHeight()*2);
		critical.drawOneCycle(g, 116*Game.SCREENSCALE, 92*Game.SCREENSCALE, Images.miningcritanimation[0].getWidth()*2, Images.miningcritanimation[0].getHeight()*2);
		switch(statTab) {
		case POWER:
			g.drawImage(Images.miningUI[0],MININGMENUX*Game.SCREENSCALE,MININGMENUY*Game.SCREENSCALE,Images.miningUI[0].getWidth()*Game.SCREENSCALE,Images.miningUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case SPEED:
			g.drawImage(Images.miningUI[0],MININGMENUX*Game.SCREENSCALE,MININGMENUY*Game.SCREENSCALE,Images.miningUI[0].getWidth()*Game.SCREENSCALE,Images.miningUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case CRITCHANCE:
			g.drawImage(Images.miningUI[0],MININGMENUX*Game.SCREENSCALE,MININGMENUY*Game.SCREENSCALE,Images.miningUI[0].getWidth()*Game.SCREENSCALE,Images.miningUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case CRITMOD:
			g.drawImage(Images.miningUI[0],MININGMENUX*Game.SCREENSCALE,MININGMENUY*Game.SCREENSCALE,Images.miningUI[0].getWidth()*Game.SCREENSCALE,Images.miningUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case STATS:
			g.drawImage(Images.miningUI[1],MININGMENUX*Game.SCREENSCALE,MININGMENUY*Game.SCREENSCALE,Images.miningUI[0].getWidth()*Game.SCREENSCALE,Images.miningUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		}
		g.drawImage(Images.miningRocks[selectedOre.ID-11],130*Game.SCREENSCALE,106*Game.SCREENSCALE,Images.miningRocks[selectedOre.ID-11].getWidth()*Game.SCREENSCALE,Images.miningRocks[selectedOre.ID-11].getHeight()*Game.SCREENSCALE,null);
		if (statTab != stat_tab.STATS) {
			g.drawImage(Images.staticons[0],(MININGMENUX+17)*Game.SCREENSCALE,(MININGMENUY+7)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[1],(MININGMENUX+36)*Game.SCREENSCALE,(MININGMENUY+7)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[2],(MININGMENUX+55)*Game.SCREENSCALE,(MININGMENUY+7)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[3],(MININGMENUX+74)*Game.SCREENSCALE,(MININGMENUY+7)*Game.SCREENSCALE,32,32,null);
			displayText("Level:"+findSelected().getCurrentLevel(),g,MININGMENUX+7,MININGMENUY+67);
			displayText("Bonus:"+findSelected().getText(),g,MININGMENUX+7,MININGMENUY+25);
			displayText(findSelected().getBonusText(),g,MININGMENUX+7,MININGMENUY+32);
			if (findSelected().getCurrentLevel()==findSelected().getLevelMax()) {
				displayText("MAX",g,MININGMENUX+7,MININGMENUY+48);
			}
			else {
				displayCost(game.inventory.itemList[findSelected().getCostID()].Quanity(),findSelected().getCostQuanity(),game.inventory.itemList[findSelected().getCostID()],g,MININGMENUX+7,MININGMENUY+48);
			}
			if (findSelected().getCurrentLevel() == 0) {
				g.drawImage(Images.miningUIicons[2],(MININGMENUX+74)*Game.SCREENSCALE,(MININGMENUY+63)*Game.SCREENSCALE,32,32,null);
			}
			else {
				g.drawImage(Images.miningUIicons[1],(MININGMENUX+74)*Game.SCREENSCALE,(MININGMENUY+63)*Game.SCREENSCALE,32,32,null);
			}
		}
		else {
			displayItems(Arrays.copyOfRange(game.inventory.itemList,10,17), g, MININGMENUX+67, MININGMENUY+4);
			g.drawImage(Images.staticons[0],(MININGMENUX+3)*Game.SCREENSCALE,(MININGMENUY+4)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[1],(MININGMENUX+3)*Game.SCREENSCALE,(MININGMENUY+14)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[2],(MININGMENUX+3)*Game.SCREENSCALE,(MININGMENUY+24)*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[3],(MININGMENUX+3)*Game.SCREENSCALE,(MININGMENUY+34)*Game.SCREENSCALE,32,32,null);
			displayText("Mining power",g,MININGMENUX+15,MININGMENUY+6);
			displayText("mining speed",g,MININGMENUX+15,MININGMENUY+16);
			displayText("crit chance",g,MININGMENUX+15,MININGMENUY+26);
			displayText("crit mod",g,MININGMENUX+15,MININGMENUY+36);
			displayText(""+game.UT.miningPower.getTotalText(),g,MININGMENUX+15,MININGMENUY+10);
			displayText(""+getSeconds(Math.max(MINEBASEINTERVAL-game.UT.miningSpeed.getTotal(),20))+" sec delay",g,MININGMENUX+15,MININGMENUY+20);
			displayText(""+getPercent(game.UT.miningCritChance.getTotal())+"%",g,MININGMENUX+15,MININGMENUY+30);
			displayText(""+game.UT.miningCritMod.getTotalText(),g,MININGMENUX+15,MININGMENUY+40);
		}

		
		g.drawImage(game.inventory.itemList[selectedOre.ID].Icon(),137*Game.SCREENSCALE,29*Game.SCREENSCALE,48,48,null);
		displayText("upgrades",g,MININGMENUX+6,MININGMENUY+82);
		displayText("stats",g,MININGMENUX+43,MININGMENUY+82);
		displayText("filler",g,MININGMENUX+73,MININGMENUY+82);
		
		g.drawImage(Images.miningcharacter[0],48*Game.SCREENSCALE,40*Game.SCREENSCALE,Images.miningcharacter[0].getWidth()*Game.SCREENSCALE,Images.miningcharacter[0].getHeight()*Game.SCREENSCALE,null);
		displayText(""+game.PD.getLevel(Screen.page_ID.MINING),g, 270, 132);
		displayText(""+game.PD.getExp(Screen.page_ID.MINING),g, 10, 136);
		if (mineIntervalCounter == 0) {
			mineOre();
			mineIntervalCounter = (int) (Math.max(MINEBASEINTERVAL-game.UT.miningSpeed.getTotal(),20));
			mining.setFrameCounter(0);
		}
		drawOnScreenItems(g);
		displayExpBar(g, 228*Game.SCREENSCALE, 132*Game.SCREENSCALE, expfornextlevel, page_ID.MINING);
	}
	
	private void defineButtons() {
		oreLeft = new JButton();
		oreLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedOre = selectedOre.getPrev();
				game.PD.oreSelected = selectedOre;
				}
			});
		oreLeft.setBounds(124*Game.SCREENSCALE,30*Game.SCREENSCALE,12*Game.SCREENSCALE,14*Game.SCREENSCALE);
		oreLeft.setContentAreaFilled(false);
		oreLeft.setBorderPainted(false);
		oreLeft.setFocusPainted(false);
		
		oreRight = new JButton();
		oreRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedOre = selectedOre.getNext();
				game.PD.oreSelected = selectedOre;
				}
			});
		oreRight.setBounds(154*Game.SCREENSCALE,30*Game.SCREENSCALE,12*Game.SCREENSCALE,14*Game.SCREENSCALE);
		oreRight.setContentAreaFilled(false);
		oreRight.setBorderPainted(false);
		oreRight.setFocusPainted(false);
		
		upgradePage = new JButton();
		upgradePage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.POWER;
				removeButtons(game);
				miningButtons.clear();
				miningButtons.add(oreLeft);
				miningButtons.add(oreRight);
				miningButtons.add(statPage);
				miningButtons.add(upgradePage);
				miningButtons.add(miningPowerSelect);
				miningButtons.add(miningSpeedSelect);
				miningButtons.add(miningCritChanceSelect);
				miningButtons.add(miningCritModSelect);
				miningButtons.add(statUpgrade);
				addButtons(game);
				}
			});
		upgradePage.setBounds((MININGMENUX+3)*Game.SCREENSCALE,(MININGMENUY+79)*Game.SCREENSCALE,31*Game.SCREENSCALE,10*Game.SCREENSCALE);
		upgradePage.setContentAreaFilled(false);
		upgradePage.setBorderPainted(false);
		upgradePage.setFocusPainted(false);
		
		statPage = new JButton();
		statPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.STATS;
				removeButtons(game);
				miningButtons.clear();
				miningButtons.add(oreLeft);
				miningButtons.add(oreRight);
				miningButtons.add(statPage);
				miningButtons.add(upgradePage);
				addButtons(game);
				}
			});
		statPage.setBounds((MININGMENUX+35)*Game.SCREENSCALE,(MININGMENUY+79)*Game.SCREENSCALE,31*Game.SCREENSCALE,10*Game.SCREENSCALE);
		statPage.setContentAreaFilled(false);
		statPage.setBorderPainted(false);
		statPage.setFocusPainted(false);
		
		miningPowerSelect = new JButton();
		miningPowerSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.POWER;
				}
			});
		miningPowerSelect.setBounds((MININGMENUX+14)*Game.SCREENSCALE,(MININGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		miningPowerSelect.setContentAreaFilled(false);
		miningPowerSelect.setBorderPainted(false);
		miningPowerSelect.setFocusPainted(false);
		
		miningSpeedSelect = new JButton();
		miningSpeedSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.SPEED;
				}
			});
		miningSpeedSelect.setBounds((MININGMENUX+33)*Game.SCREENSCALE,(MININGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		miningSpeedSelect.setContentAreaFilled(false);
		miningSpeedSelect.setBorderPainted(false);
		miningSpeedSelect.setFocusPainted(false);
		
		miningCritChanceSelect = new JButton();
		miningCritChanceSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.CRITCHANCE;
				}
			});
		miningCritChanceSelect.setBounds((MININGMENUX+52)*Game.SCREENSCALE,(MININGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		miningCritChanceSelect.setContentAreaFilled(false);
		miningCritChanceSelect.setBorderPainted(false);
		miningCritChanceSelect.setFocusPainted(false);

		miningCritModSelect = new JButton();
		miningCritModSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.CRITMOD;
				}
			});
		miningCritModSelect.setBounds((MININGMENUX+71)*Game.SCREENSCALE,(MININGMENUY+4)*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		miningCritModSelect.setContentAreaFilled(false);
		miningCritModSelect.setBorderPainted(false);
		miningCritModSelect.setFocusPainted(false);
		
		statUpgrade = new JButton();
		statUpgrade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				attemptUpgrade(findSelected());
				}
			});
		statUpgrade.setBounds((MININGMENUX+64)*Game.SCREENSCALE,(MININGMENUY+60)*Game.SCREENSCALE,32*Game.SCREENSCALE,16*Game.SCREENSCALE);
		statUpgrade.setContentAreaFilled(false);
		statUpgrade.setBorderPainted(false);
		statUpgrade.setFocusPainted(false);
		
		
	}
	
}
