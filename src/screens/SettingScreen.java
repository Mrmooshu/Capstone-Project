package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Game;
import resources.Images;

public class SettingScreen extends Screen{

	public JButton quitButton;
	
	public SettingScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.SETTINGS.ID], game);
		quitButton = new JButton();
		quitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		quitButton.setBounds((125)*Game.SCREENSCALE,(125)*Game.SCREENSCALE, 42*Game.SCREENSCALE, 22*Game.SCREENSCALE);
		quitButton.setContentAreaFilled(false);
	}
	
	public JButton[] getButtons() {
		return new JButton[]{zones,skills,items,tasks,settings,quitButton};
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		super.draw(g);
	}

}
