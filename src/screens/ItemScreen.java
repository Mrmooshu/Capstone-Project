package screens;

import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JButton;

import Data.Items;
import Data.Items.Item;
import main.Game;
import resources.Images;

public class ItemScreen extends Screen {

	private Game game;
	
	public ItemScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.ITEM.ID], game);
		this.game = game;
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		displayItems(Arrays.copyOfRange(game.inventory.itemList,0,1), g, 11, 37);
		displayItems(Arrays.copyOfRange(game.inventory.itemList,10,17), g, 68, 37);
		displayItems(Arrays.copyOfRange(game.inventory.itemList,20,27), g, 126, 37);
		displayItems(Arrays.copyOfRange(game.inventory.itemList,30,37), g, 184, 37);
	}
}
