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
import screens.LabScreen.lab_page_ID;
import screens.LabScreen.skill_tab;

public class MiningScreen extends Screen{

	public enum stat_tab {
		POWER,CRITCHANCE,CRITMOD,STATS;
	}
	public enum ore {
		COPPER(11,100,1),IRON(12,200,3),SILVER(13,600,12),TUNGSTON(14,2400,60),GOLD(15,12000,360),COBALT(16,72000,2520);
		private final int ID;
		private final int durability;
		private final double exp;
		
		private ore(int ID,int durability,double exp) {
			this.ID = ID;
			this.durability = durability;
			this.exp = exp;
		}
		
		public ore getNext() {
			if (this == COBALT) {
				return this;
			}
			return values()[ordinal()+1%values().length];
		}
		public ore getPrev() {
			if (this == COPPER) {
				return this;
			}
			return values()[ordinal()-1%values().length];
		}
		
	}
	
	private final int MINEBASEINTERVAL = 300;
	private int mineIntervalCounter;
	
	private stat_tab statTab;
	private ore selectedOre;
	
	
	private Game game;
		
	public JButton oreLeft,oreRight,miningPowerSelect,miningCritChanceSelect,miningCritModSelect,statUpgrade,upgradePage,statPage;
	private LinkedList<JButton> miningButtons;

	public Animation mining = new Animation(Images.mininganimation, 3);
	
	public MiningScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.MINING.ID], game);
		this.game = game;
		defineButtons();
		miningButtons = new LinkedList<JButton>();
		statTab = stat_tab.POWER;
		selectedOre=ore.COPPER;
		miningButtons.add(oreLeft);
		miningButtons.add(oreRight);
		miningButtons.add(statPage);
		miningButtons.add(upgradePage);
		miningButtons.add(miningPowerSelect);
		miningButtons.add(miningCritChanceSelect);
		miningButtons.add(miningCritModSelect);
		miningButtons.add(statUpgrade);
		mineIntervalCounter = (int) (MINEBASEINTERVAL-game.UT.miningSpeed.getTotal());
	    mining.setFrameCounterToEnd();
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
		case CRITCHANCE:
			return game.UT.miningPage1;
		case CRITMOD:
			return game.UT.miningPage2;
		case STATS:
			break;
			}
		return null;
	}
	
	private void mineOre() {
		Random rand = new Random();
		int oreGained = 0;
		double a = selectedOre.durability;
		double b = game.UT.miningPower.getTotal();
		double c = game.UT.miningCritChance.getTotal();
		while (c >= 100) {
			b = b*game.UT.miningCritMod.getTotal();
			c = c-100;
		}
		if (rand.nextInt(100) <= c) {
			b = b*game.UT.miningCritMod.getTotal();
		}
		while ( b >= a) {
			b = b-a;
			oreGained++;
		}
		if (rand.nextInt(selectedOre.durability) <= b) {
			oreGained++;
		}
		game.inventory.itemList[selectedOre.ID].Increase(oreGained);
		game.PD.addExp(Screen.page_ID.MINING, selectedOre.exp*oreGained);
		
		if (oreGained > 0) {
			onScreenItems.add(new ItemGraphic(game.inventory.itemList[selectedOre.ID], oreGained, 45, rand.nextInt(32)+125,rand.nextInt(8)+100));
		}
		
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		mineIntervalCounter--;
		mining.drawOneCycle(g, 153*Game.SCREENSCALE, 88*Game.SCREENSCALE, 22*Game.SCREENSCALE, 20*Game.SCREENSCALE);
		switch(statTab) {
		case POWER:
			g.drawImage(Images.miningUI[0],4*Game.SCREENSCALE,30*Game.SCREENSCALE,Images.miningUI[0].getWidth()*Game.SCREENSCALE,Images.miningUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case CRITCHANCE:
			g.drawImage(Images.miningUI[0],4*Game.SCREENSCALE,30*Game.SCREENSCALE,Images.miningUI[0].getWidth()*Game.SCREENSCALE,Images.miningUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case CRITMOD:
			g.drawImage(Images.miningUI[0],4*Game.SCREENSCALE,30*Game.SCREENSCALE,Images.miningUI[0].getWidth()*Game.SCREENSCALE,Images.miningUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		case STATS:
			g.drawImage(Images.miningUI[1],4*Game.SCREENSCALE,30*Game.SCREENSCALE,Images.miningUI[0].getWidth()*Game.SCREENSCALE,Images.miningUI[0].getHeight()*Game.SCREENSCALE,null);
			break;
		}
		g.drawImage(Images.miningRocks[selectedOre.ID-11],130*Game.SCREENSCALE,106*Game.SCREENSCALE,Images.miningRocks[selectedOre.ID-11].getWidth()*Game.SCREENSCALE,Images.miningRocks[selectedOre.ID-11].getHeight()*Game.SCREENSCALE,null);
		if (statTab != stat_tab.STATS) {
			g.drawImage(Images.staticons[0],15*Game.SCREENSCALE,37*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[1],32*Game.SCREENSCALE,37*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[2],49*Game.SCREENSCALE,37*Game.SCREENSCALE,32,32,null);
			displayText("Level:"+findSelected().getCurrentLevel(),g,10,96);
			displayText("Bonus:"+findSelected().getText(),g,10,56);
			displayText(findSelected().getBonusText(),g,10,61);
			if (findSelected().getCurrentLevel()==findSelected().getLevelMax()) {
				displayText("MAX",g,10,78);
			}
			else {
				displayText(game.inventory.itemList[findSelected().getCost()[0]].Quanity()+"/"+findSelected().getCost()[1],g,38,78);
				displayIcon(game.inventory.itemList[findSelected().getCost()[0]],g,27,74);
				displayText("cost:",g,10,78);
			}
			if (findSelected().getCurrentLevel() == 0) {
				g.drawImage(Images.miningUIicons[2],53*Game.SCREENSCALE,93*Game.SCREENSCALE,32,32,null);
			}
			else {
				g.drawImage(Images.miningUIicons[1],53*Game.SCREENSCALE,93*Game.SCREENSCALE,32,32,null);
			}
		}
		else {
			displayItems(Arrays.copyOfRange(game.inventory.itemList,10,13), g, 7, 77);
			displayItems(Arrays.copyOfRange(game.inventory.itemList,14,17), g, 37, 77);
			g.drawImage(Images.staticons[0],7*Game.SCREENSCALE,33*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[1],7*Game.SCREENSCALE,44*Game.SCREENSCALE,32,32,null);
			g.drawImage(Images.staticons[2],7*Game.SCREENSCALE,56*Game.SCREENSCALE,32,32,null);
			displayText("mining power",g,19,33);
			displayText("crit chance",g,19,44);
			displayText("crit mod",g,19,56);
			displayText(""+game.UT.miningPower.getTotalText(),g,19,38);
			displayText(""+getPercent(game.UT.miningCritChance.getTotal())+"%",g,19,49);
			displayText(""+game.UT.miningCritMod.getTotalText(),g,19,60);
		}

		
		g.drawImage(game.inventory.itemList[selectedOre.ID].Icon(),137*Game.SCREENSCALE,29*Game.SCREENSCALE,48,48,null);
		displayText("upgrades",g,10,112);
		displayText("stats",g,45,112);
		
		g.drawImage(Images.miningcharacter[0],160*Game.SCREENSCALE,40*Game.SCREENSCALE,Images.miningcharacter[0].getWidth()*Game.SCREENSCALE,Images.miningcharacter[0].getHeight()*Game.SCREENSCALE,null);
		displayText(""+game.PD.getLevel(Screen.page_ID.MINING),g, 10, 128);
		displayText(""+game.PD.getExp(Screen.page_ID.MINING),g, 10, 136);
		if (mineIntervalCounter == 0) {
			mineOre();
			mineIntervalCounter = (int) (MINEBASEINTERVAL-game.UT.miningSpeed.getTotal());
			mining.setFrameCounter(0);
		}
	}
	
	private void defineButtons() {
		oreLeft = new JButton();
		oreLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedOre = selectedOre.getPrev();
				}
			});
		oreLeft.setBounds(124*Game.SCREENSCALE,30*Game.SCREENSCALE,12*Game.SCREENSCALE,14*Game.SCREENSCALE);
		oreLeft.setContentAreaFilled(false);
		
		oreRight = new JButton();
		oreRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedOre = selectedOre.getNext();
				}
			});
		oreRight.setBounds(154*Game.SCREENSCALE,30*Game.SCREENSCALE,12*Game.SCREENSCALE,14*Game.SCREENSCALE);
		oreRight.setContentAreaFilled(false);
		
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
				miningButtons.add(miningCritChanceSelect);
				miningButtons.add(miningCritModSelect);
				miningButtons.add(statUpgrade);
				addButtons(game);
				}
			});
		upgradePage.setBounds(7*Game.SCREENSCALE,109*Game.SCREENSCALE,30*Game.SCREENSCALE,10*Game.SCREENSCALE);
		upgradePage.setContentAreaFilled(false);
		
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
		statPage.setBounds(38*Game.SCREENSCALE,109*Game.SCREENSCALE,30*Game.SCREENSCALE,10*Game.SCREENSCALE);
		statPage.setContentAreaFilled(false);
		
		miningPowerSelect = new JButton();
		miningPowerSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.POWER;
				}
			});
		miningPowerSelect.setBounds(12*Game.SCREENSCALE,34*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		miningPowerSelect.setContentAreaFilled(false);
		
		miningCritChanceSelect = new JButton();
		miningCritChanceSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.CRITCHANCE;
				}
			});
		miningCritChanceSelect.setBounds(29*Game.SCREENSCALE,34*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		miningCritChanceSelect.setContentAreaFilled(false);

		miningCritModSelect = new JButton();
		miningCritModSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				statTab = stat_tab.CRITMOD;
				}
			});
		miningCritModSelect.setBounds(46*Game.SCREENSCALE,34*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		miningCritModSelect.setContentAreaFilled(false);
		
		statUpgrade = new JButton();
		statUpgrade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				attemptUpgrade(findSelected());
				}
			});
		statUpgrade.setBounds(50*Game.SCREENSCALE,90*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		statUpgrade.setContentAreaFilled(false);
		
	}
	
}
