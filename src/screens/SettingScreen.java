package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import main.Game;
import resources.Images;

public class SettingScreen extends Screen{

	public JButton quitButton,resetDataButton;
	private JButton[] settingsButtons;
	private Game game;
	
	public SettingScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.SETTINGS.ID], game);
		this.game=game;
		defineButtons();
		settingsButtons = new JButton[] {quitButton,resetDataButton};
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
	private void defineButtons() {
		quitButton = new JButton();
		quitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.PD.saveData();
				System.exit(0);
			}
		});
		quitButton.setBounds((125)*Game.SCREENSCALE,(125)*Game.SCREENSCALE, 42*Game.SCREENSCALE, 22*Game.SCREENSCALE);
		quitButton.setContentAreaFilled(false);
		quitButton.setBorderPainted(false);
		quitButton.setFocusPainted(false);
		
		resetDataButton = new JButton("reset data");
		resetDataButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.PD.resetData();
			}
		});
		resetDataButton.setBounds((125)*Game.SCREENSCALE,(100)*Game.SCREENSCALE, 42*Game.SCREENSCALE, 22*Game.SCREENSCALE);
		resetDataButton.setContentAreaFilled(false);
		resetDataButton.setBorderPainted(false);
		resetDataButton.setFocusPainted(false);
	}
	
}
