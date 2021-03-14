package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.Game;
import resources.Images;

public class SettingScreen extends Screen{

	public JButton quitButton;
	private JButton[] settingsButtons;
	
	public SettingScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.SETTINGS.ID], game);
		quitButton = new JButton();
		settingsButtons = new JButton[] {quitButton};
		quitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.PD.saveData();
				System.exit(0);
			}
		});
		quitButton.setBounds((125)*Game.SCREENSCALE,(125)*Game.SCREENSCALE, 42*Game.SCREENSCALE, 22*Game.SCREENSCALE);
		quitButton.setContentAreaFilled(false);
	}
	
	public void addButtons(Game game) {
		super.addButtons(game);
		for (int i = 0; i < settingsButtons.length; i++) {
			game.add(settingsButtons[i]);
		}
	}
	
	public void removeButtons(Game game) {
		super.removeButtons(game);
		for (int i = 0; i < settingsButtons.length; i++) {
			game.remove(settingsButtons[i]);
		}
	}
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		super.draw(g);
	}

}
