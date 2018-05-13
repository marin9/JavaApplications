package marin.bralic.application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import marin.bralic.objects.Background;
import marin.bralic.objects.GameObject;
import marin.bralic.objects.Ghost;
import marin.bralic.objects.Item;
import marin.bralic.objects.Move;
import marin.bralic.objects.Pacman;
import marin.bralic.sounds.SoundsHandler;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable{
	private final static int FRAME_PERIOD = 1000 / Constants.FPS;
	private double resolution;
		
	//Images
	private Image startImage, dialogImage, game_over_dialog;
	private Image[] images;  //life image, finish, lost 
	private BufferedImage image;		
	
	//Game objects
	private SoundsHandler sounds;
	private Pacman pacman;
	private Ghost red_ghost;
	private Ghost[] ghosts;
	private Item item;
	private LinkedList<Image> itemList;
	private Background background;
	private LinkedList<GameObject> objectList;
	
	//Game variable
	private GameStatus gameStatus;
	private boolean ready, newGame, ghost_run, ghost_search;
	private int life, score, progress, level;
	private int count, run_count, eated_count, ghost_mode_count, waka;
		
	   
	
	public Game(JFrame frame, double res, boolean sound){
		frame.add(this);
		frame.setBackground(Color.BLACK);
		resolution=res;	
		
		gameStatus=GameStatus.MENU;
		try {			
			startImage=ImageIO.read(getClass().getResource("/media/start.png"));
			dialogImage=ImageIO.read(getClass().getResource("/media/dialog.png"));
			game_over_dialog=ImageIO.read(getClass().getResource("/media/game_over_dialog.png"));
			image=ImageIO.read(getClass().getResource("/media/object_images.png"));
			images=new Image[3];
			images[0]=image.getSubimage(46, 186, 14, 14);
			images[1]=image.getSubimage(45, 156, 14, 14);
			images[2]=image.getSubimage(30, 156, 14, 14);
			
			sounds=new SoundsHandler(sound);
		} catch (IOException e) {
			System.out.println("Image not found.");
		}

		newGame();
	}
		
	
	public void newGame(){
		life=3;
		level=1;
		progress=0;
		score=0;	
		ghost_run=ghost_search=false;	
		ghost_mode_count=0;
		
		itemList=new LinkedList<Image>();
		objectList=new LinkedList<GameObject>();
		background=new Background();
		item=new Item(1);
		pacman=new Pacman(0.45f);
		
		red_ghost=new Ghost(0.45f, 1, pacman, sounds, null, 0);
		ghosts=new Ghost[]{red_ghost, 
				           new Ghost(0.45f, 2, pacman, sounds, null, Constants.FPS*4), 
				           new Ghost(0.45f, 3, pacman, sounds, red_ghost, Constants.FPS*12), 
				           new Ghost(0.45f, 4, pacman, sounds, null, Constants.FPS*24)}; 
		
				
		objectList.add(background);
		objectList.add(item);
		objectList.add(pacman);
		objectList.add(ghosts[0]);
		objectList.add(ghosts[1]);
		objectList.add(ghosts[2]);
		objectList.add(ghosts[3]);
	}
	
	private void nextLevel(){
		progress=0;
		++level;
		if(level%2==1) ++life;
		ghost_run=ghost_search=false;
		ghost_mode_count=0;
		
		for(GameObject o: objectList){
			if(!(o instanceof Item)) o.reset();				
		}
		//remove old item
		objectList.remove(item);
		Random r=new Random();
		item=new Item(Math.abs(r.nextInt()%5));
		objectList.add(1, item);
	}
	
	public void reset(){
		ghost_mode_count=0;
		ghost_run=ghost_search=false;
		for(GameObject o: objectList) 
			if(!(o instanceof Background)) o.reset();	
	}	
	
		
	public void pressedKey(int key){
		if(gameStatus==GameStatus.RUN){            //RUN
			if(key==KeyEvent.VK_P){
				gameStatus=GameStatus.PAUSE;
			}else if(key==KeyEvent.VK_ESCAPE){
				gameStatus=GameStatus.ESC;
			}else if(key==KeyEvent.VK_UP) pacman.setOrientation(Move.UP);
			else if(key==KeyEvent.VK_DOWN) pacman.setOrientation(Move.DOWN);
			else if(key==KeyEvent.VK_RIGHT) pacman.setOrientation(Move.RIGHT);
			else if(key==KeyEvent.VK_LEFT) pacman.setOrientation(Move.LEFT);
		}else if(gameStatus==GameStatus.PAUSE){         //PAUSE
			if(key==KeyEvent.VK_P){
				gameStatus=GameStatus.RUN;
			}else if(key==KeyEvent.VK_ESCAPE) gameStatus=GameStatus.ESC;			
		}else if(gameStatus==GameStatus.ESC){            //ESC		
			if(key==KeyEvent.VK_R){
				gameStatus=GameStatus.RUN;
			}else if(key==KeyEvent.VK_N){
				newGame=true;
				newGame();
			}
			else if(key==KeyEvent.VK_E) gameStatus=GameStatus.EXIT;
		}else if(gameStatus==GameStatus.GAME_OVER){       //GAME OVER
			if(key==KeyEvent.VK_ESCAPE || key==KeyEvent.VK_E) gameStatus=GameStatus.EXIT;
			else if(key==KeyEvent.VK_N){
				gameStatus=GameStatus.RUN;
				newGame();	
			}
		}
	}


	@Override
	public void run() {
		long beginTime, deltaTime, sleepTime;
		gameStatus=GameStatus.RUN;
		
		while(gameStatus!=GameStatus.EXIT){		
			newGame=false;
			gameStatus=GameStatus.RUN;
			beginTime=System.currentTimeMillis();
			
			//ready
			ready=true;
			sounds.playReadySound();
			while(System.currentTimeMillis()-beginTime<4000){
				repaint();
				try{  Thread.sleep(100);  }catch(InterruptedException e){}
			}
			ready=false;
			pacman.setOrientation(Move.LEFT);
			
			
			//play
			sounds.normalSound();
			while(gameStatus!=GameStatus.EXIT && gameStatus!=GameStatus.FINISH && gameStatus!=GameStatus.LOST && gameStatus!=GameStatus.GAME_OVER && !newGame){
				if(gameStatus==GameStatus.PAUSE || gameStatus==GameStatus.ESC) sounds.pauseAll();
				else sounds.resume();
				beginTime=System.currentTimeMillis(); 
				if(gameStatus!=GameStatus.PAUSE && gameStatus!=GameStatus.ESC)
					for(int i=0;i<Constants.GAME_SPEED;++i){					
						update();		
						collision();
					}
				repaint(); 
				
				deltaTime=System.currentTimeMillis()-beginTime;
				sleepTime=(int)(FRAME_PERIOD-deltaTime);							
				if(sleepTime>0) try{  Thread.sleep(sleepTime);  }catch(InterruptedException e){}					
			}
			
			
			if(newGame) continue;			
			//finish
			count=0;
			if(gameStatus==GameStatus.FINISH){
				sounds.pauseAll();
				while(System.currentTimeMillis()-beginTime<4000){
					repaint();				
					try{  Thread.sleep(300);  }catch(InterruptedException e){}
					++count;
				}
			    nextLevel();
			    gameStatus=GameStatus.RUN;
			//lost
			}else if(gameStatus==GameStatus.LOST){
				sounds.playLostSound();
				while(System.currentTimeMillis()-beginTime<3000){
					repaint();				
					try{  Thread.sleep(300);  }catch(InterruptedException e){}
					++count;
				}
				--life;
				if(life>=1){
					reset();
					gameStatus=GameStatus.RUN;	
				}else{
				    gameStatus=GameStatus.GAME_OVER;	
				}
			}
      
			//game over
			while(gameStatus==GameStatus.GAME_OVER){
				repaint();				
				try{  Thread.sleep(300);  }catch(InterruptedException e){}
				sounds.pauseAll();
			}									
		}	
		
		sounds.stopAll();
		System.exit(0);
	}
		
	private void update(){
		for(GameObject o: objectList) o.update();
		if(ghost_run) ++run_count;
		++ghost_mode_count;
		++waka;
	}
	
	private void collision(){				
		//ghost win
		for(int i=0;i<ghosts.length;++i)
            if(ghosts[i].isTooClose(pacman.X(), pacman.Y()) && ghosts[i].isDanger()){
			    gameStatus=GameStatus.LOST;
			    return;
		}	
		
		//set search mod
		if(ghost_mode_count>Constants.FPS*2*9 && !ghost_search && !ghost_run){
			for(int i=0;i<4;++i){
				ghosts[i].setSearchMod();		
			}
			ghost_search=true;
			ghost_mode_count=0;
		}
		
		//set circulating mod
		if(ghost_mode_count>Constants.FPS*2*15 && ghost_search && !ghost_run){
			for(int i=0;i<4;++i){
				ghosts[i].setCirculatingMod();		
			}
			ghost_search=false;
			ghost_mode_count=0;
		}
			
				
		//over ghost run
		if(run_count>Constants.FPS*12){
			sounds.normalSound();
			for(int i=0;i<ghosts.length;++i) ghosts[i].setRunOff();
			ghost_run=false;
		}		
		
		
		//pacman eat
		int s=
		  background.eat(pacman.X()+6, pacman.Y()+6)+
		  background.eat(pacman.X()+6+3, pacman.Y()+6)+
		  background.eat(pacman.X()+6, pacman.Y()+6+3)+
		  background.eat(pacman.X()+6-3, pacman.Y()+6)+
		  background.eat(pacman.X()+6, pacman.Y()+6-3);
				
		if(s==1){
			sounds.wakaSound(true);
			waka=0;
			score+=10;
			++progress;
		}else if(s==2){
			waka=0;
			sounds.wakaSound(true);
			score+=10;
			++progress;
			for(int i=0;i<ghosts.length;++i) ghosts[i].setRunOn();	
			eated_count=2;
			run_count=0;
			ghost_run=true;
			sounds.runSound();
		}else if(pacman.X()==105 && pacman.Y()==133 && item.canEat()){
			sounds.playEatItemSound();
			score+=300;
			itemList.addFirst(item.eat());
		}
		
		if(waka>40) sounds.wakaSound(false);
		
		//eat ghost
		if(ghost_run){
			for(int i=0;i<ghosts.length;++i){
				if(ghosts[i].isTooClose(pacman.X(), pacman.Y()) && ghosts[i].canEat()){
					sounds.eyesSound(true);
					ghosts[i].eat(eated_count);
					score=score+100*eated_count;
					eated_count*=2;		
				}
				
			}									
		}		
			
		for(GameObject o: objectList) o.collision();
		
		if(progress==244) gameStatus=GameStatus.FINISH;			
	}
		
	@Override
	public void paintComponent(Graphics graphics){
		Graphics2D g=(Graphics2D)graphics;		
		g.scale(resolution, resolution);
		
		if(gameStatus==GameStatus.MENU) graphics.drawImage(startImage, 0, 0, null);		
		else {	
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 250, 270);		
			for(GameObject o: objectList) o.render(g);
			for(int i=0;i<life;++i) g.drawImage(images[0], i*14+2, 250, null);
			g.setFont(new Font("", Font.BOLD, 15));
			g.setColor(Color.YELLOW);
			g.drawString(""+score, 90, 262);			
			for(int i=0;i<itemList.size() && i<5;++i) g.drawImage(itemList.get(i), 208-i*14, 250, null);			

			if(ready){
				g.setColor(Color.YELLOW);
				g.setFont(new Font("", Font.BOLD, 30));
				g.drawString("READY", 60, 100);
				g.drawString("LEVEL "+level, 50, 155);
			}
			if(gameStatus==GameStatus.PAUSE){
				g.setColor(Color.CYAN);
				g.setFont(new Font("", Font.BOLD, 40));
				g.drawString("PAUSE", 45, 130);
			}
			if(gameStatus==GameStatus.ESC){
				g.drawImage(dialogImage, 35, 65, null);
			}
			if(gameStatus==GameStatus.FINISH){
				g.setColor(Color.BLACK);
				g.fillRect(pacman.X(), pacman.Y(), 14, 14);
				if(count%2==0) g.drawImage(images[1], pacman.X(), pacman.Y(), null);
				else g.drawImage(images[0], pacman.X(), pacman.Y(), null);
			}			
			if(gameStatus==GameStatus.LOST){
				g.setColor(Color.BLACK);
				g.fillRect(pacman.X(), pacman.Y(), 14, 14);
				if(count%2==0) g.drawImage(images[2], pacman.X(), pacman.Y(), null);
				else g.drawImage(images[0], pacman.X(), pacman.Y(), null);
			}
			if(gameStatus==GameStatus.GAME_OVER){
				g.drawImage(game_over_dialog, 35, 65, null);
				g.setColor(Color.ORANGE);
				g.drawString("Score:  "+score, 60, 110);
			}
		}				
	}
	
	
	public static enum GameStatus{
		MENU(), 
		RUN(), PAUSE(), ESC(), 
		FINISH(), LOST(), GAME_OVER(), EXIT();
	}

}
