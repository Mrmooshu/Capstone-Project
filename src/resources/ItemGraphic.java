package resources;

import java.awt.Graphics;

import Data.Items.Item;
import main.Game;
import screens.Screen;

public class ItemGraphic {
	
	
	private Item item;
	private int amount,lifespan,startX,startY,riseDelay,delayCounter;
	
	public ItemGraphic(Item item, int amount, int lifespan, int startX, int startY) {
		this.item = item;
		this.amount = amount;
		this.lifespan = lifespan;
		this.startX = startX;
		this.startY = startY;
		riseDelay = 0;
		delayCounter = 0;
	}
	public ItemGraphic(Item item, int amount, int lifespan, int startX, int startY, int riseDelay) {
		this.item = item;
		this.amount = amount;
		this.lifespan = lifespan;
		this.startX = startX;
		this.startY = startY;
		this.riseDelay = riseDelay;
		delayCounter = 0;
	}
	
	public int getLifespan() {
		return lifespan;
	}
	public void draw(Graphics g){
		g.drawImage(item.Icon(),startX*Game.SCREENSCALE,startY*Game.SCREENSCALE,item.Icon().getWidth()*Game.SCREENSCALE,item.Icon().getHeight()*Game.SCREENSCALE,null);
		Screen.displayText("+"+amount, g, 10+startX,1+startY);
		if (delayCounter == riseDelay) {
			startY--;
			delayCounter = 0;
		}
		else {
			delayCounter++;
		}

		lifespan--;
	}
}
