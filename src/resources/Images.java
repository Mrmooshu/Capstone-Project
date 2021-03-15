package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import screens.Screen;

public class Images {

	//images
	public static BufferedImage[] backgrounds,LabUIicons,LabUI,skillicons,miningcharacter,woodcuttingcharacter,fishingcharacter,miningUI,woodcuttingUI,fishingUI,items,numbers,letters;
	//animation frames
	public static BufferedImage[] generator, handle;
	//image
	public static BufferedImage headerUI;
	
	public Images() {
		
		backgrounds = new BufferedImage[Screen.page_ID.values().length];
		LabUIicons = new BufferedImage[3];
		LabUI = new BufferedImage[1];
		skillicons = new BufferedImage[9];
		miningcharacter = new BufferedImage[3];
		woodcuttingcharacter = new BufferedImage[3];
		fishingcharacter = new BufferedImage[3];
		
		items = new BufferedImage[200];
		numbers = new BufferedImage[10];
		letters = new BufferedImage[35];

		generator = new BufferedImage[12];
		handle = new BufferedImage[16];
		
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
			readSpriteSheet("/resources/UI/skill_icons.png", skillicons, 16, 16);
//			character sprites
			readSpriteSheet("/resources/Characters/golems.png",miningcharacter,80,80);
			readSpriteSheet("/resources/Characters/sentinels.png",woodcuttingcharacter,80,80);
			readSpriteSheet("/resources/Characters/robots.png",fishingcharacter,80,80);


			
//			item icons
			readSpriteSheet("/resources/Items/items.png", items, 16, 16);
			readSpriteSheet("/resources/Text/numbers.png", numbers, 10, 10);
			readSpriteSheet("/resources/Text/letters.png", letters, 10, 10);
			
//			animations
			readSpriteSheet("/resources/Animations/generator_animation.png", generator, 50, 48);
			readSpriteSheet("/resources/Animations/handle_animation.png", handle, 22, 20);
			
			
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
	
}
