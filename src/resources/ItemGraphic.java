package resources;

import java.awt.Graphics;

import Data.Items.Item;
import main.Game;
import screens.Screen;

public class ItemGraphic {
	
	
	private Item item;
	private int amount,lifespan,startX,startY;
	
	public ItemGraphic(Item item, int amount, int lifespan, int startX, int startY) {
		this.item = item;
		this.amount = amount;
		this.lifespan = lifespan;
		this.startX = startX;
		this.startY = startY;
	}
	
	public int getLifespan() {
		return lifespan;
	}
	public void draw(Graphics g){
		g.drawImage(item.Icon(),startX*Game.SCREENSCALE,startY*Game.SCREENSCALE,item.Icon().getWidth()*Game.SCREENSCALE,item.Icon().getHeight()*Game.SCREENSCALE,null);
		Screen.displayText("+"+amount, g, 10+startX,1+startY);
		startY--;
		lifespan--;
	}
}
