package application;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class GameFrame extends JFrame{
	
	public GameFrame(){
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Snake game");
		setResizable(false);
		
		add(new GamePanel());
		pack();
		setLocationRelativeTo(null);		
	}
	
	
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				GameFrame game=new GameFrame();
				game.setVisible(true);
			}			
		});
	}
}