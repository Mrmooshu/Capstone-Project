package screens;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import main.Game;
import resources.Images;

public class SettingScreen extends Screen{

	public JButton quitButton,resetDataButton,addItemByID;
	private JButton[] settingsButtons;
	private Game game;
	private JTextField itemID;
	
	public SettingScreen(Game game) {
		super(Images.backgrounds[Screen.page_ID.SETTINGS.ID], game);
		this.game=game;
		itemID = new JTextField("enter item ID");
		itemID.setBounds(122*Game.SCREENSCALE, 60*Game.SCREENSCALE, 50*Game.SCREENSCALE, 10*Game.SCREENSCALE);
		defineButtons();
		settingsButtons = new JButton[] {quitButton,resetDataButton,addItemByID};
	}
	
	public void addButtons(Game game) {
		super.addButtons(game);
		game.add(itemID);
		for (int i = 0; i < settingsButtons.length; i++) {
			game.add(settingsButtons[i]);
		}
	}
	
	public void removeButtons(Game game) {
		super.removeButtons(game);
		game.remove(itemID);
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
		
		resetDataButton = new JButton("reset data");
		resetDataButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				game.PD.resetData();
			}
		});
		resetDataButton.setBounds((125)*Game.SCREENSCALE,(100)*Game.SCREENSCALE, 42*Game.SCREENSCALE, 22*Game.SCREENSCALE);
		resetDataButton.setContentAreaFilled(false);
		
		
		addItemByID = new JButton("addItem");
		addItemByID.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("addItem"))
					try {
						game.inventory.itemList[Integer.parseInt(itemID.getText())].Increase(100);
					}
					catch(Exception a) {
						
					}
			}
		});
		addItemByID.setBounds((125)*Game.SCREENSCALE,(75)*Game.SCREENSCALE, 42*Game.SCREENSCALE, 22*Game.SCREENSCALE);
		addItemByID.setContentAreaFilled(false);
	}
	
}
