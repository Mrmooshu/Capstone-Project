package screens;

import java.awt.Graphics;

import Data.Items;
import Data.Items.Item;
import main.Game;
import resources.Images;

public class ItemScreen extends Screen {

	private Items inventory;
	
	public ItemScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.ITEM.ID], game);
		this.inventory = game.inventory;
	}

	
	private void displayIcon(Item item, Graphics g, int x, int y) {
		g.drawImage(item.Icon(),x*Game.SCREENSCALE,y*Game.SCREENSCALE,item.Icon().getWidth()*Game.ITEMICONSCALE,item.Icon().getHeight()*Game.ITEMICONSCALE,null);
	}
	private void displayNum(int n, Graphics g, int x, int y) {
		g.drawImage(super.numToImage(n),x*Game.SCREENSCALE,y*Game.SCREENSCALE,super.numToImage(n).getWidth(),super.numToImage(n).getHeight(),null);
	}
	public void displayMisc(Items inv, Graphics g, int x, int y) {
		for (int i = 0; i < inv.misc_list.length; i++) {
			displayIcon(inv.misc_list[i], g, x, y+(i*10));
			if (inv.misc_list[i].Quanity() == 0) {
				displayNum(0, g, x+11, y+4+(i*10));
			}
			else {
				String num = String.valueOf(inv.misc_list[i].Quanity());
				for (int j = 0; j < num.length(); j++) {
					displayNum(Integer.parseInt(num.substring(j,j+1)), g, x+11+(j*3), y+4+(i*10));
				}
			}
		}
	}
	public void displayMining(Items inv, Graphics g, int x, int y) {
		for (int i = 0; i < inv.mining_list.length; i++) {
			displayIcon(inv.mining_list[i], g, x, y+(i*10));
			if (inv.mining_list[i].Quanity() == 0) {
				displayNum(0, g, x+11, y+4+(i*10));
			}
			else {
				String num = String.valueOf(inv.mining_list[i].Quanity());
				for (int j = 0; j < num.length(); j++) {
					displayNum(Integer.parseInt(num.substring(j,j+1)), g, x+11+(j*3), y+4+(i*10));
				}
			}
		}
	}
	public void displayWoodcutting(Items inv, Graphics g, int x, int y) {
		for (int i = 0; i < inv.woodcutting_list.length; i++) {
			displayIcon(inv.woodcutting_list[i], g, x, y+(i*10));
			if (inv.woodcutting_list[i].Quanity() == 0) {
				displayNum(0, g, x+11, y+4+(i*10));
			}
			else {
				String num = String.valueOf(inv.woodcutting_list[i].Quanity());
				for (int j = 0; j < num.length(); j++) {
					displayNum(Integer.parseInt(num.substring(j,j+1)), g, x+11+(j*3), y+4+(i*10));
				}
			}
		}
	}
	public void displayFishing(Items inv, Graphics g, int x, int y) {
		for (int i = 0; i < inv.fishing_list.length; i++) {
			displayIcon(inv.fishing_list[i], g, x, y+(i*10));
			if (inv.fishing_list[i].Quanity() == 0) {
				displayNum(0, g, x+11, y+4+(i*10));
			}
			else {
				String num = String.valueOf(inv.fishing_list[i].Quanity());
				for (int j = 0; j < num.length(); j++) {
					displayNum(Integer.parseInt(num.substring(j,j+1)), g, x+11+(j*3), y+4+(i*10));
				}
			}
		}
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		displayMisc(inventory, g, 11, 37);
		displayMining(inventory, g, 68, 37);
		displayWoodcutting(inventory, g, 126, 37);
		displayFishing(inventory, g, 184, 37);
	}
}
