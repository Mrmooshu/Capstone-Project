package resources;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import screens.Screen;

public class Images {

	
	public static BufferedImage[] backgrounds,headerUI,pageUI,clickables;
	
	public Images() {
		
		backgrounds = new BufferedImage[Screen.page_ID.values().length];
		headerUI = new BufferedImage[2];
		pageUI = new BufferedImage[10];
		clickables = new BufferedImage[10];
		
		
		try {
			backgrounds[Screen.page_ID.MAIN.ID] = ImageIO.read(getClass().getResourceAsStream("/resources/Backgrounds/BackgroundTest.png"));
			headerUI[0] = ImageIO.read(getClass().getResourceAsStream("/resources/UI/headerUI.png"));
			headerUI[1] = ImageIO.read(getClass().getResourceAsStream("/resources/UI/zones.png"));

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
