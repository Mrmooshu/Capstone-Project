package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import screens.Screen;

public class Images {

	
	public static BufferedImage[] backgrounds,headerUI,labUI,miningUI,woodcuttingUI,fishingUI;
	public static BufferedImage[] generator, handle;
	
	public Images() {
		
		backgrounds = new BufferedImage[Screen.page_ID.values().length];
		headerUI = new BufferedImage[Screen.headerUI_position_data.length];
		labUI = new BufferedImage[0];
		generator = new BufferedImage[12];
		handle = new BufferedImage[16];
		
		try {
			backgrounds[Screen.page_ID.LAB.ID] = ImageIO.read(getClass().getResourceAsStream("/resources/Backgrounds/lab_background.png"));
			headerUI[0] = ImageIO.read(getClass().getResourceAsStream("/resources/UI/headerUI.png"));
			headerUI[1] = ImageIO.read(getClass().getResourceAsStream("/resources/UI/zones.png"));
			
			readSpriteSheet("/resources/animations/generator_animation.png", generator, 50, 48);
			readSpriteSheet("/resources/animations/handle_animation.png", handle, 22, 20);
			
			
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
