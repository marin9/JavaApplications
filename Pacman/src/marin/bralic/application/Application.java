package marin.bralic.application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Application extends JFrame{
	
	private Thread gameThread;
	private Game panel;	
	
	
	public Application(double resolution, boolean sound){
		this.setTitle("Pacman");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize((int)Math.round(225*resolution)+4, (int)Math.round(280*resolution));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addKeyListener(new Application.PacKeyListener());	
		this.setIconImage(new ImageIcon(getClass().getResource("/media/pac_icon.png")).getImage());
				
		panel=new Game(this, resolution, sound);
		gameThread=new Thread(panel);
	}
	
	
	public class PacKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode()==KeyEvent.VK_S && !gameThread.isAlive()) gameThread.start();	
			else if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE && !gameThread.isAlive()) System.exit(0);
			else panel.pressedKey(arg0.getKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent arg0) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}
		
	}
}
