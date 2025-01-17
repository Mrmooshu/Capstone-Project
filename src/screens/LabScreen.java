package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.JButton;
import Data.Items;
import Data.UpgradeTracker.Upgrade;
import main.Game;
import resources.Animation;
import resources.Images;
import resources.ItemGraphic;

public class LabScreen extends Screen{
	
	public enum lab_page_ID {
		GENERATOR(0),CHARACTER(1),SHOP(2),PRESTIGE(3);
		public final int ID;
		
		private lab_page_ID(int ID) {
			this.ID = ID;
		}
	}
	public enum skill_tab {
		MINING(0),WOODCUTTING(1),FISHING(2);
		public final int ID;
		
		private skill_tab(int ID) {
			this.ID = ID;
		}
	}
	
	private lab_page_ID labPage;
	private skill_tab skillTab;
	private int charSelected;
	private final static int varriantNum = 3;
	
	private Game game;
	
	public Animation generator = new Animation(Images.generator, 2);
	public Animation handle = new Animation(Images.handle, 1);

	private Items inventory;
	
	public JButton genPage,genButton;
	public JButton charPage,skill1Button,skill2Button,skill3Button,upgradeCharButton,charLeftButton,charRightButton;
	public JButton shopPage;
	public JButton presPage;
	
	private LinkedList<JButton> labButtons;
	
	public LabScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.LAB.ID], game);
		this.game = game;
		this.inventory = game.inventory;
		defineButtons();
		labButtons = new LinkedList<JButton>();
		changeLabPage(lab_page_ID.GENERATOR);
	    handle.setFrameCounterToEnd();
	}
	
	public void addButtons(Game game) {
		super.addButtons(game);
		for (int i = 0; i < labButtons.size(); i++) {
			game.add(labButtons.get(i));
		}
	}
	
	public void removeButtons(Game game) {
		super.removeButtons(game);
		for (int i = 0; i < labButtons.size(); i++) {
			game.remove(labButtons.get(i));
		}
	}
	
	private void changeLabPage(lab_page_ID pageID) {
		if (pageID == lab_page_ID.GENERATOR) {
			removeButtons(game);
			labButtons.clear();
		    labButtons.add(genButton);
		    labButtons.add(genPage);
		    labButtons.add(charPage);
		    labButtons.add(shopPage);
		    labButtons.add(presPage);
			addButtons(game);
		}
		else if (pageID == lab_page_ID.CHARACTER) {
			removeButtons(game);
			labButtons.clear();
			labButtons.add(skill1Button);
			labButtons.add(skill2Button);
			labButtons.add(skill3Button);
			labButtons.add(upgradeCharButton);
			labButtons.add(charLeftButton);
			labButtons.add(charRightButton);
		    labButtons.add(genPage);
		    labButtons.add(charPage);
		    labButtons.add(shopPage);
		    labButtons.add(presPage);
			addButtons(game);
			skillTab = skill_tab.MINING;
			charSelected=0;
		}
		else if (pageID == lab_page_ID.SHOP) {
			removeButtons(game);
			labButtons.clear();
		    labButtons.add(genPage);
		    labButtons.add(charPage);
		    labButtons.add(shopPage);
		    labButtons.add(presPage);
			addButtons(game);
		}
		else if (pageID == lab_page_ID.PRESTIGE) {
			removeButtons(game);
			labButtons.clear();
		    labButtons.add(genPage);
		    labButtons.add(charPage);
		    labButtons.add(shopPage);
		    labButtons.add(presPage);
			addButtons(game);
		}
		labPage = pageID;
	}
	
	private void drawGeneratorPage(Graphics g) {
		displayItems(Arrays.copyOfRange(game.inventory.itemList,0,1), g, 20, 40);
		generator.drawNextFrame(g, (Game.WIDTH/2 - 25)*Game.SCREENSCALE, (Game.HEIGHT/2 - 24)*Game.SCREENSCALE, 50*Game.SCREENSCALE, 48*Game.SCREENSCALE);
		handle.drawOneCycle(g, (Game.WIDTH/2 - 47)*Game.SCREENSCALE, (Game.HEIGHT/2 - 10)*Game.SCREENSCALE, 22*Game.SCREENSCALE, 20*Game.SCREENSCALE);
		drawOnScreenItems(g);
	}
	
	private void drawCharacterPage(Graphics g) {
		g.drawImage(Images.LabUI[0],5*Game.SCREENSCALE,32*Game.SCREENSCALE,Images.LabUI[0].getWidth()*Game.SCREENSCALE,Images.LabUI[0].getHeight()*Game.SCREENSCALE,null);
		g.drawImage(Images.skillicons[1],8*Game.SCREENSCALE,35*Game.SCREENSCALE,32,32,null);
		g.drawImage(Images.skillicons[0],25*Game.SCREENSCALE,35*Game.SCREENSCALE,32,32,null);
		g.drawImage(Images.skillicons[2],42*Game.SCREENSCALE,35*Game.SCREENSCALE,32,32,null);
		displayText(""+charSelected,g,40,70);
		displayText("Level:"+findSelected().getCurrentLevel(),g,13,130);
		displayText("Bonus:"+findSelected().getText(),g,13,89);
		displayText(findSelected().getBonusText(),g,13,94);
		if (findSelected().getCurrentLevel()==findSelected().getLevelMax()) {
			displayText("MAX",g,12,112);
		}
		else {
			displayCost(game.inventory.itemList[findSelected().getCostID()].Quanity(),findSelected().getCostQuanity(),game.inventory.itemList[findSelected().getCostID()],g,12,111);
		}
		if (findSelected().getCurrentLevel() == 0) {
			g.drawImage(Images.LabUIicons[2],79*Game.SCREENSCALE,126*Game.SCREENSCALE,32,32,null);
		}
		else {
			g.drawImage(Images.LabUIicons[1],79*Game.SCREENSCALE,126*Game.SCREENSCALE,32,32,null);
		}
		switch(skillTab) {
		case MINING:
			g.drawImage(Images.miningcharacter[charSelected],150*Game.SCREENSCALE,50*Game.SCREENSCALE,Images.miningcharacter[charSelected].getWidth()*Game.SCREENSCALE,Images.miningcharacter[charSelected].getHeight()*Game.SCREENSCALE,null);
			break;
		case WOODCUTTING:
			g.drawImage(Images.woodcuttingcharacter[charSelected],150*Game.SCREENSCALE,50*Game.SCREENSCALE,Images.woodcuttingcharacter[charSelected].getWidth()*Game.SCREENSCALE,Images.woodcuttingcharacter[charSelected].getHeight()*Game.SCREENSCALE,null);
			break;
		case FISHING:
			g.drawImage(Images.fishingcharacter[charSelected],150*Game.SCREENSCALE,50*Game.SCREENSCALE,Images.fishingcharacter[charSelected].getWidth()*Game.SCREENSCALE,Images.fishingcharacter[charSelected].getHeight()*Game.SCREENSCALE,null);
			break;
		}

	}
	
	private void drawShopPage(Graphics g) {
		
	}
	
	private void drawPrestigePage(Graphics g) {
		
	}
	

	
	private Upgrade findSelected() {
		switch(skillTab) {
		case MINING:
			switch(charSelected) {
			case 0:
				return game.UT.labMining0;
			case 1:
				return game.UT.labMining1;
			case 2:
				return game.UT.labMining2;
			}
		case WOODCUTTING:
			switch(charSelected) {
			case 0:
				return game.UT.labWoodcutting0;
			case 1:
				return game.UT.labWoodcutting1;
			case 2:
				return game.UT.labWoodcutting2;
			}
		case FISHING:
			switch(charSelected) {
			case 0:
				return game.UT.labFishing0;
			case 1:
				return game.UT.labFishing1;
			case 2:
				return game.UT.labFishing2;
			}
		}
		return null;
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		if (labPage == lab_page_ID.GENERATOR) {
			drawGeneratorPage(g);
		}
		else if (labPage == lab_page_ID.CHARACTER) {
			drawCharacterPage(g);
		}
		else if (labPage == lab_page_ID.SHOP) {
			drawShopPage(g);
		}
		else if (labPage == lab_page_ID.PRESTIGE) {
			drawPrestigePage(g);
		}
	}
	
	private void defineButtons() {
		genPage = new JButton();
		genPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLabPage(lab_page_ID.GENERATOR);
				}
			});
		genPage.setBounds(264*Game.SCREENSCALE,34*Game.SCREENSCALE,26*Game.SCREENSCALE,25*Game.SCREENSCALE);
		genPage.setContentAreaFilled(false);
		genPage.setBorderPainted(false);
		genPage.setFocusPainted(false);
	    
		charPage = new JButton();
		charPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLabPage(lab_page_ID.CHARACTER);
				}
			});
		charPage.setBounds(264*Game.SCREENSCALE,62*Game.SCREENSCALE,26*Game.SCREENSCALE,25*Game.SCREENSCALE);
		charPage.setContentAreaFilled(false);
		charPage.setBorderPainted(false);
		charPage.setFocusPainted(false);
		
		shopPage = new JButton();
		shopPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLabPage(lab_page_ID.SHOP);
				}
			});
		shopPage.setBounds(264*Game.SCREENSCALE,90*Game.SCREENSCALE,26*Game.SCREENSCALE,25*Game.SCREENSCALE);
		shopPage.setContentAreaFilled(false);
		shopPage.setBorderPainted(false);
		shopPage.setFocusPainted(false);
		
		presPage = new JButton();
		presPage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				changeLabPage(lab_page_ID.PRESTIGE);
				}
			});
		presPage.setBounds(264*Game.SCREENSCALE,118*Game.SCREENSCALE,26*Game.SCREENSCALE,25*Game.SCREENSCALE);
		presPage.setContentAreaFilled(false);
		presPage.setBorderPainted(false);
		presPage.setFocusPainted(false);
		
		genButton = new JButton();
		genButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Random rand = new Random();
				inventory.itemList[0].Increase(1);
				handle.setFrameCounter(0);
				onScreenItems.add(new ItemGraphic(game.inventory.itemList[0], 1, 50, rand.nextInt(24)+123,rand.nextInt(9)+60,1));
				}
			});
		genButton.setBounds((Game.WIDTH/2-50)*Game.SCREENSCALE,(Game.HEIGHT/2-40)*Game.SCREENSCALE, 100*Game.SCREENSCALE, 80*Game.SCREENSCALE);
		genButton.setContentAreaFilled(false);
		genButton.setBorderPainted(false);
		genButton.setFocusPainted(false);
		
		skill1Button = new JButton();
		skill1Button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				skillTab = skill_tab.MINING;
				charSelected=0;
				}
			});
		skill1Button.setBounds(5*Game.SCREENSCALE,32*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		skill1Button.setContentAreaFilled(false);
		skill1Button.setBorderPainted(false);
		skill1Button.setFocusPainted(false);
		
		skill2Button = new JButton();
		skill2Button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				skillTab = skill_tab.WOODCUTTING;
				charSelected=0;
				}
			});
		skill2Button.setBounds(22*Game.SCREENSCALE,32*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		skill2Button.setContentAreaFilled(false);
		skill2Button.setBorderPainted(false);
		skill2Button.setFocusPainted(false);
		
		skill3Button = new JButton();
		skill3Button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				skillTab = skill_tab.FISHING;
				charSelected=0;
				}
			});
		skill3Button.setBounds(39*Game.SCREENSCALE,32*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		skill3Button.setContentAreaFilled(false);
		skill3Button.setBorderPainted(false);
		skill3Button.setFocusPainted(false);
		
		upgradeCharButton = new JButton();
		upgradeCharButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				attemptUpgrade(findSelected());
				}
			});
		upgradeCharButton.setBounds(69*Game.SCREENSCALE,123*Game.SCREENSCALE,32*Game.SCREENSCALE,16*Game.SCREENSCALE);
		upgradeCharButton.setContentAreaFilled(false);
		upgradeCharButton.setBorderPainted(false);
		upgradeCharButton.setFocusPainted(false);
		
		charLeftButton = new JButton();
		charLeftButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				charSelected--;
				if (charSelected < 0) {
					charSelected = varriantNum-1;
				}
				}
			});
		charLeftButton.setBounds(5*Game.SCREENSCALE,66*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		charLeftButton.setContentAreaFilled(false);
		charLeftButton.setBorderPainted(false);
		charLeftButton.setFocusPainted(false);
		
		charRightButton = new JButton();
		charRightButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				charSelected++;
				if (charSelected >= varriantNum) {
					charSelected = 0;
				}
				}
			});
		charRightButton.setBounds(90*Game.SCREENSCALE,66*Game.SCREENSCALE,16*Game.SCREENSCALE,16*Game.SCREENSCALE);
		charRightButton.setContentAreaFilled(false);
		charRightButton.setBorderPainted(false);
		charRightButton.setFocusPainted(false);
	}
	

}
