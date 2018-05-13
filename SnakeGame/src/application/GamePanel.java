package application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener{
	private int WIDTH, HEIGHT, DELAY, DOT_SIZE=15;
	private int[] x=new int[40*40];
	private int[] y=new int[40*40];
	private Color scoreColor=new Color(100, 150, 255);
	private Font scoreFont=new Font("", Font.BOLD, 15);
	private Timer timer;
	private boolean play, pause, inGame;
	
	private ItemsHandler items;
	private int dots, addDots, score;
	private Orientation ort, new_ort;
	
	
	public GamePanel(){
		WIDTH=640;
		HEIGHT=640;
		inGame=false;
				
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		
		addKeyListener(new KeyListener());
	}
		
	private void initGame(){
		ort=new_ort=Orientation.UP;
		dots=4;
		score=addDots=0;
		DELAY=200;
		play=inGame=true;
		pause=false;
		
		for(int i=0;i<dots;++i){
			x[i]=WIDTH/DOT_SIZE/2;
			y[i]=HEIGHT/DOT_SIZE/2+i;		
		}
		
		items=new ItemsHandler(DOT_SIZE);
		
		timer=new Timer(DELAY, this);
		timer.start();
	}
		
	private void gameOver(){
		timer.stop();
		play=false;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(inGame){
			step();
			collision();
		}		
		repaint();
	}
	
	private void step(){
		items.step();
		
	    ort=new_ort;		
		for(int i=dots;i>0;--i){
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		
		if(ort==Orientation.LEFT) x[0]=x[0]-1;
		else if(ort==Orientation.RIGHT) x[0]=x[0]+1;
		else if(ort==Orientation.UP) y[0]=y[0]-1;
		else y[0]=y[0]+1;
		
		//Add dots
		if(addDots>0){
			--addDots;
			++dots;
		}
		
	}
	
	private void collision(){
		//To other side
		if(x[0]<0) x[0]=WIDTH/DOT_SIZE;
		if(y[0]<0) y[0]=HEIGHT/DOT_SIZE;
		if(x[0]>WIDTH/DOT_SIZE) x[0]=0;
		if(y[0]>HEIGHT/DOT_SIZE) y[0]=0;
		
		//Grab item
		itemAction(items.checkItem(x[0], y[0]));
								
		
		//Crashed itself
		for(int i=1;i<dots;++i) if(x[0]==x[i] && y[0]==y[i]) gameOver();		
	}
	
	private void itemAction(int id){
		if(id==ItemsHandler.NOTHING) 
			return;
		else if(id==ItemsHandler.APPLE){
			addDots+=10;
			score+=50;
			items.initialize();
		}else if(id==ItemsHandler.PEAR){
			addDots+=4;
			score+=30;
			items.initialize();
		}else if(id==ItemsHandler.BANANA){
			addDots+=3;
			score+=20;
			items.initialize();
		}else if(id==ItemsHandler.STRAWBERRY){
			addDots+=1;
			score+=10;
			items.initialize();
		}else if(id==ItemsHandler.CHERRY){
			addDots+=1;
			score+=10;
			items.initialize();
		}else if(id==ItemsHandler.CUT){
			addDots=0;
			dots=dots/2;
		}else if(id==ItemsHandler.SCORE_PACK){
			score=score+200;
		}else if(id==ItemsHandler.SLOW_TIME){
			DELAY=DELAY+60;
		}else if(id==ItemsHandler.SPEED_UP){
			DELAY=DELAY-10;
			if(DELAY>100) score+=50;
			else if(DELAY>60) score+=100;
			else if(DELAY>40) score+=200;
			else if(DELAY>20) score+=400;
		}
								
		if(DELAY>120) DELAY-=8;
		else if(DELAY>80) DELAY-=4;
		else  DELAY-=1;
		
		timer.setDelay(DELAY);
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);	
		drawFrame(g);
	}
	
	private void drawFrame(Graphics g){	
		if(!inGame){			
			g.setColor(new Color(0, 150, 0));
			g.setFont(new Font("", Font.BOLD, 90));
			g.drawString("Snake", 200, 200);
			
			g.setFont(new Font("", Font.BOLD, 30));
			g.setColor(Color.RED);
			g.drawString("E - exit", 250, 250);
			g.setColor(Color.GREEN);
			g.drawString("S - start", 250, 280);
			g.setColor(Color.ORANGE);
			g.drawString("N - new game", 250, 310);
			g.setColor(Color.BLUE);
			g.drawString("P - pause", 250, 340);
			
			return;
		}
		
		//Draw snake
		g.setColor(Color.RED);
		g.fillOval(x[0]*DOT_SIZE, y[0]*DOT_SIZE, DOT_SIZE, DOT_SIZE);
		g.setColor(Color.GREEN);
		for(int i=1;i<dots;++i){
			g.fillOval(x[i]*DOT_SIZE, y[i]*DOT_SIZE, DOT_SIZE, DOT_SIZE);
		}
		
		//Draw items
		items.drawItems(g);
		
		//Draw score
		g.setColor(scoreColor);
		g.setFont(scoreFont);
		g.drawString("Score: "+score, 10, 20);
		
		//Draw pause, end screen
		if(!play){
			g.setColor(new Color(150, 50, 50));
			g.setFont(new Font("", Font.BOLD, 40));
			g.drawString("Game over", 180, 200);
			g.drawString("Score: "+score, 200, 250);
		}else if(pause){
			g.setColor(new Color(150, 255, 100));
			g.setFont(new Font("", Font.BOLD, 40));
			g.drawString("Pause", 200, 250);
		}
	}
	
	
	
	private class KeyListener extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			int key=e.getKeyCode();
			
			if(inGame){
				if(key==KeyEvent.VK_LEFT && ort!=Orientation.RIGHT) new_ort=Orientation.LEFT;
				else if(key==KeyEvent.VK_RIGHT && ort!=Orientation.LEFT) new_ort=Orientation.RIGHT;
				else if(key==KeyEvent.VK_UP && ort!=Orientation.DOWN) new_ort=Orientation.UP;
				else if(key==KeyEvent.VK_DOWN && ort!=Orientation.UP) new_ort=Orientation.DOWN;
				else if(key==KeyEvent.VK_P && !pause){
					pause=true;
					timer.stop();
					repaint();
				}else if(key==KeyEvent.VK_P && pause){
					pause=false;
					timer.start();
				}else if(key==KeyEvent.VK_E){
					System.exit(0);
				}else if(key==KeyEvent.VK_N){
					timer.stop();
					initGame();
				}
			}else{
				if(key==KeyEvent.VK_E) System.exit(0);
				else if(key==KeyEvent.VK_S) initGame();
			}
			
		}				
	}

	private enum Orientation{
		LEFT(), RIGHT(), UP(), DOWN();
	}
	
}