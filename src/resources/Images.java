package resources;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import screens.Screen;

public class Images {
	
//	color constants
	public static Color RED = new Color(255,0,0);
	public static Color GREEN = new Color(0,255,0);
	public static Color BLUE = new Color(0,0,255);
	public static Color CYAN = new Color(0,255,255);
	public static Color ORANGE = new Color(255,127,80);
	public static Color PURPLE = new Color(148,0,211);
	public static Color LIME = new Color(173,255,47);
	
	public static Color DARKRED = new Color(150,0,0);
	public static Color DARKGREEN = new Color(0,150,0);
	public static Color DARKBLUE = new Color(0,0,150);


	//images
	public static BufferedImage[] backgrounds,skillicons,staticons,items,numbers,letters;
	public static BufferedImage[] LabUIicons,LabUI;
	public static BufferedImage[] miningcharacter,miningUIicons,miningUI,miningRocks;	
	public static BufferedImage[] woodcuttingcharacter,woodcuttingUIicons,woodcuttingUI,woodcuttingTrees;
	public static BufferedImage[] fishingcharacter,fishingUIicons,fishingUI;
	public static BufferedImage[] numbersred;
	//animation frames
	public static BufferedImage[] generator, handle, mininganimation, choppinganimation;
	//image
	public static BufferedImage headerUI;
	
	public Images() {
		
		backgrounds = new BufferedImage[Screen.page_ID.values().length];
		LabUIicons = new BufferedImage[3];
		LabUI = new BufferedImage[1];
		skillicons = new BufferedImage[9];
		staticons = new BufferedImage[12];
		
		miningcharacter = new BufferedImage[3];
		miningUI = new BufferedImage[2];
		miningUIicons = new BufferedImage[4];
		miningRocks = new BufferedImage[6];
		
		woodcuttingcharacter = new BufferedImage[3];
		woodcuttingUI = new BufferedImage[2];
		woodcuttingUIicons = new BufferedImage[4];
		woodcuttingTrees = new BufferedImage[6];
		
		fishingcharacter = new BufferedImage[3];
		
		items = new BufferedImage[200];
		numbers = new BufferedImage[10];
		numbersred = new BufferedImage[10];
		letters = new BufferedImage[35];

		generator = new BufferedImage[12];
		handle = new BufferedImage[16];
		mininganimation = new BufferedImage[9];
		choppinganimation = new BufferedImage[9];
		
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
			
			readSpriteSheet("/resources/UI/woodcutting_UI_icons.png",woodcuttingUIicons,16,16);
			woodcuttingUI[0] = ImageIO.read(getClass().getResourceAsStream("/resources/UI/woodcutting_upgrade_interface.png"));
			woodcuttingUI[1] = ImageIO.read(getClass().getResourceAsStream("/resources/UI/woodcutting_stat_interface.png"));

			readSpriteSheet("/resources/UI/skill_icons.png", skillicons, 16, 16);
			readSpriteSheet("/resources/UI/stat_icons.png", staticons, 16, 16);
			
//			skilling sprites
			readSpriteSheet("/resources/Characters/golems.png",miningcharacter,80,80);
			readSpriteSheet("/resources/Props/mining_rocks.png",miningRocks,42,14);
			
			readSpriteSheet("/resources/Characters/sentinels.png",woodcuttingcharacter,80,80);
			readSpriteSheet("/resources/Props/woodcutting_trees.png",woodcuttingTrees,64,64);
			
			readSpriteSheet("/resources/Characters/robots.png",fishingcharacter,80,80);


			
//			item and text icons
			readSpriteSheet("/resources/Items/items.png", items, 16, 16);
			readSpriteSheet("/resources/Text/numbers.png", numbers, 10, 10);
			readSpriteSheet("/resources/Text/letters.png", letters, 10, 10);
			
//			animations
			readSpriteSheet("/resources/Animations/generator_animation.png", generator, 50, 48);
			readSpriteSheet("/resources/Animations/handle_animation.png", handle, 22, 20);
			readSpriteSheet("/resources/Animations/miningAnimation.png", mininganimation, 48, 27);
			readSpriteSheet("/resources/Animations/choppingAnimation.png", choppinganimation, 48, 31);
			
//			color changed versions
//			numbers= changeColor(numbers,LIME);
			
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
	
//	this function uses and modifies some code from geeksforgeeks.org
	public static BufferedImage changeColor(BufferedImage image, Color color) {
		for (int i = 0; i < image.getHeight(); i++) {
			for(int j = 0; j < image.getWidth(); j++) {
				int p = image.getRGB(j,i);
				int a = (p>>24)&color.getAlpha();
				int r = (p>>16)&color.getRed();
				int g = (p>>8)&color.getGreen();
				int b = p&color.getBlue();
				p = (a<<24) | (r<<16) | (g<<8) | b;
                image.setRGB(j, i, p);
			}
		}
		return image;
		
	}
	
	public static BufferedImage[] changeColor(BufferedImage[] images, Color color) {
		for (int i = 0; i < images.length; i++) {
			images[i] = changeColor(images[i], color);
		}
		return images;		
	}
	
}
