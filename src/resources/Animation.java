package resources;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Animation {

	private BufferedImage[] frameArray;
	private int frameCounter, frameHold, holdCounter;
	
	public Animation(BufferedImage[] frameArray, int frameHold) {
		this.frameArray= frameArray;
		this.frameHold = frameHold;
		frameCounter = 0;
		holdCounter = 0;
	}
	
	public void drawNextFrame(Graphics g, int x, int y, int width, int height) {
		g.drawImage(frameArray[frameCounter], x, y, width, height, null);
		
		if (holdCounter == frameHold) {
			holdCounter = 0;
			frameCounter++;
		}
		else {
			holdCounter++;
		}
		if (frameCounter == frameArray.length) {
			frameCounter = 0;
		}
	}
	
	public void drawOneCycle(Graphics g, int x, int y, int width, int height) {
		if (frameCounter < frameArray.length-1) {
			frameCounter++;
		}	
		g.drawImage(frameArray[frameCounter], x, y, width, height, null);
	}
	
	public void setFrameCounter(int number) {
		frameCounter = number;
	}
}
