package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import screens.Screen;

public class Images {

	//images
	public static BufferedImage[] backgrounds,labUI,miningUI,woodcuttingUI,fishingUI,miscItems,miningItems,woodcuttingItems,fishingItems,numbers;
	//animation frames
	public static BufferedImage[] generator, handle;
	//image
	public static BufferedImage headerUI;
	
	public Images() {
		
		backgrounds = new BufferedImage[Screen.page_ID.values().length];
		labUI = new BufferedImage[0];
		
		miscItems = new BufferedImage[1];
		miningItems = new BufferedImage[6];
		woodcuttingItems = new BufferedImage[6];
		fishingItems = new BufferedImage[6];
		numbers = new BufferedImage[10];

		generator = new BufferedImage[12];
		handle = new BufferedImage[16];
		
		try {
			backgrounds[Screen.page_ID.LAB.ID] = ImageIO.read(getClass().getResourceAsStream("/resources/Backgrounds/lab_background.png"));
			backgrounds[Screen.page_ID.ITEM.ID] = ImageIO.read(getClass().getResourceAsStream("/resources/Backgrounds/item_background.png"));

			
			headerUI = ImageIO.read(getClass().getResourceAsStream("/resources/UI/headerUI.png"));
			
			readSpriteSheet("/resources/Items/misc_items.png", miscItems, 16, 16);
			readSpriteSheet("/resources/Items/mining_items.png", miningItems, 16, 16);
			readSpriteSheet("/resources/Items/woodcutting_items.png", woodcuttingItems, 16, 16);
			readSpriteSheet("/resources/Items/fishing_items.png", fishingItems, 16, 16);
			readSpriteSheet("/resources/Text/numbers.png", numbers, 11, 11);
			
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
