package main;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import resources.Images;
import screens.LabScreen;
import screens.Screen;
import screens.ScreenManager;

public class Game extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;

	public static final int SCREENSCALE = 3;
	public static final int WIDTH = 290;
	public static final int HEIGHT = 150;
	public static final int SCREENWIDTH = WIDTH*SCREENSCALE;
	public static final int SCREENHEIGHT = HEIGHT*SCREENSCALE;
	
	private Thread thread;
	private boolean isRunning = false;
	
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	
	private ScreenManager SM;
	private Screen screen;
	
	public Game() {
		setPreferredSize(new Dimension(SCREENWIDTH, SCREENHEIGHT));
		setFocusable(true);
		new Images();
		SM = new ScreenManager(this);
		setLayout(null);
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		long start, elapsed, wait;
		while(isRunning) {
			start = System.nanoTime();
			tick();
			repaint();
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			
			if (wait < 0) {
				wait = 5;
			}
			
			try {
				Thread.sleep(wait);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void tick() {
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.clearRect(0, 0, SCREENWIDTH, SCREENHEIGHT);
		SM.draw(g);
	}
}
