package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import screens.Screen;

public class Images {
	
//	constants
	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;


	//images
	public static BufferedImage[] backgrounds,skillicons,staticons,items,numbers,letters;
	public static BufferedImage[] LabUIicons,LabUI;
	public static BufferedImage[] miningcharacter,miningUIicons,miningUI,miningRocks;	
	public static BufferedImage[] woodcuttingcharacter,woodcuttingUI;
	public static BufferedImage[] fishingcharacter,fishingUI;
	public static BufferedImage[] numbersred;
	//animation frames
	public static BufferedImage[] generator, handle, mininganimation;
	//image
	public static BufferedImage headerUI;
	
	public Images() {
		
		backgrounds = new BufferedImage[Screen.page_ID.values().length];
		LabUIicons = new BufferedImage[3];
		LabUI = new BufferedImage[1];
		skillicons = new BufferedImage[9];
		staticons = new BufferedImage[9];
		
		miningcharacter = new BufferedImage[3];
		miningUI = new BufferedImage[2];
		miningUIicons = new BufferedImage[4];
		miningRocks = new BufferedImage[6];
		
		woodcuttingcharacter = new BufferedImage[3];
		
		fishingcharacter = new BufferedImage[3];
		
		items = new BufferedImage[200];
		numbers = new BufferedImage[10];
		numbersred = new BufferedImage[10];
		letters = new BufferedImage[35];

		generator = new BufferedImage[12];
		handle = new BufferedImage[16];
		mininganimation = new BufferedImage[9];
		
		try {
			backgrounds[Screen.page_ID.LAB.ID] = ImageIO.read(getClass().getResourceAsStream("/resources/Backgrounds/lab_background.png"));
			backgrounds[Screen.page_ID.ITEM.ID] = ImageIO.read(getClass().getResourceAsStream("/resources/Backgrounds/item_background.png"));
			backgrounds[Screen.page_ID.ZONES.ID] = ImageIO.read(getClass().getResourceAsStream("/resources/Backgrounds/zones_background.png"));
			backgrounds[Screen.page_ID.SETTINGS.ID] = ImageIO.read(getClass().getResourceAsStream("/resources/Backgrounds/settings_background.png"));
			backgrounds[Screen.page_ID.MINING.ID] = ImageIO.read(getClass().getResourceAsStream("/resources/Backgrounds/mining_background.png"));
			backgrounds[Screen.page_ID.WOODCUTTING.ID] = ImageIO.read(getClass().getResourceAsStream("/resources/Backgrounds/woodcutting_background.png"));
			backgrounds[Screen.page_ID.FISHING.ID] = ImageIO.read(getClass().getResourceAsStream("/resources/Backgrounds/fishing_background.png"));

//			UI
			headerUI = ImageIO.read(getClass().getResourceAsStream("/resources/UI/headerUI.png"));
			
			readSpriteSheet("/resources/UI/lab_UI_icons.png", LabUIicons, 16, 16);
			LabUI[0] = ImageIO.read(getClass().getResourceAsStream("/resources/UI/lab_character_interface.png"));
			
			readSpriteSheet("/resources/UI/mining_UI_icons.png",miningUIicons,16,16);
			miningUI[0] = ImageIO.read(getClass().getResourceAsStream("/resources/UI/mining_upgrade_interface.png"));
			miningUI[1] = ImageIO.read(getClass().getResourceAsStream("/resources/UI/mining_stat_interface.png"));

			readSpriteSheet("/resources/UI/skill_icons.png", skillicons, 16, 16);
			readSpriteSheet("/resources/UI/stat_icons.png", staticons, 16, 16);
			
//			skilling sprites
			readSpriteSheet("/resources/Characters/golems.png",miningcharacter,80,80);
			readSpriteSheet("/resources/Props/mining_rocks.png",miningRocks,42,14);
			
			readSpriteSheet("/resources/Characters/sentinels.png",woodcuttingcharacter,80,80);
			readSpriteSheet("/resources/Characters/robots.png",fishingcharacter,80,80);


			
//			item and text icons
			readSpriteSheet("/resources/Items/items.png", items, 16, 16);
			readSpriteSheet("/resources/Text/numbers.png", numbers, 10, 10);
			readSpriteSheet("/resources/Text/letters.png", letters, 10, 10);
			
//			animations
			readSpriteSheet("/resources/Animations/generator_animation.png", generator, 50, 48);
			readSpriteSheet("/resources/Animations/handle_animation.png", handle, 22, 20);
			readSpriteSheet("/resources/Animations/miningAnimation.png", mininganimation, 48, 27);
			
//			color changed versions
//			numbers= changeColor(numbers);
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void readSpriteSheet(String fileName, BufferedImage[] frameList, int frame_width, int frame_height) {
		try {
			for (int i = 0; i * frame_width < ImageIO.read(getClass().getResourceAsStream(fileName)).getWidth(); i++) {
				frameList[i] = (ImageIO.read(getClass().getResourceAsStream(fileName)).getSubimage(i * frame_width, 0, frame_width, frame_height));
			} 
		} catch (IOException e) {
			e.printStackTrace();
			}
	}
	
//	code for this function was derived from geeksforgeeks.org
	public static BufferedImage changeColor(BufferedImage image, int color) {
		for (int i = 0; i < image.getHeight(); i++) {
			for(int j = 0; j < image.getWidth(); j++) {
				int p = image.getRGB(j,i);
				int a = (p>>24)&0xff;
				switch(color) {
				case 0:
					int r = (p>>16)&0xff;
					p = (a<<24) | (r<<16) | (0<<8) | 0;
				case 1:
					int g = (p>>8)&0xff;
					p = (a<<24) | (0<<16) | (g<<8) | 0;
				case 2:
					int b = p&0xff;
					p = (a<<24) | (0<<16) | (0<<8) | b;
				}

                image.setRGB(j, i, p);
			}
		}
		return image;
		
	}
	
	public static BufferedImage[] changeColor(BufferedImage[] images, int color) {
		for (int i = 0; i < images.length; i++) {
			images[i] = changeColor(images[i], color);
		}
		return images;		
	}
	
}
