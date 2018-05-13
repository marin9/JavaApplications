package marin.bralic.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import marin.bralic.sounds.SoundsHandler;

public class Ghost extends GameObject{
	private int n;
	private float SPEED, C_SPEED;
	private SoundsHandler sounds;
	private Color color;
	private Font font;
	private Image[][] ghostImage;
	private short[][] map;
	private Pacman pacman;
	private Ghost red_ghost;
	
	private Move ort;
	private int ex, ey, ecount, escore;
	private int x, y, anim_frame, frame_count;
	private long goOutTime, currentTime;
	private GhostStatus status, normal_status;
	private boolean do_collision, stay_in_home;
	private float count;
	
	
	public Ghost(float speed, int n, Pacman p, SoundsHandler s, Ghost red_g, long outTime){
		SPEED=C_SPEED=speed;
		goOutTime=outTime;
		this.n=n;
		pacman=p;
		sounds=s;
		if(red_g!=null) red_ghost=red_g;
		color=Color.CYAN;
		font=new Font("", Font.BOLD, 10);
		loadContents();	
		reset();
	}
	
	@Override
	protected void loadContents(){
		BufferedImage b_image=null;
		ghostImage=new Image[5][4];		
		
		try {
			//load ghosts images
			b_image=ImageIO.read(getClass().getResource("/media/object_images.png"));
			if(n==1){
				for(int i=0;i<4;++i) ghostImage[0][i]=b_image.getSubimage(i*14+i, 0, 14, 14);
				for(int i=0;i<4;++i) ghostImage[1][i]=b_image.getSubimage(i*14+i, 15, 14, 14);				
			}else if(n==2){
				for(int i=0;i<4;++i) ghostImage[0][i]=b_image.getSubimage(i*14+i, 30, 14, 14);
				for(int i=0;i<4;++i) ghostImage[1][i]=b_image.getSubimage(i*14+i, 45, 14, 14);		
			}else if(n==3){
				for(int i=0;i<4;++i) ghostImage[0][i]=b_image.getSubimage(i*14+i, 60, 14, 14);
				for(int i=0;i<4;++i) ghostImage[1][i]=b_image.getSubimage(i*14+i, 75, 14, 14);		
			}else if(n==4){
				for(int i=0;i<4;++i) ghostImage[0][i]=b_image.getSubimage(i*14+i, 90, 14, 14);
				for(int i=0;i<4;++i) ghostImage[1][i]=b_image.getSubimage(i*14+i, 105, 14, 14);		
			}
			
			for(int i=0;i<4;++i) ghostImage[2][i]=b_image.getSubimage(0, 156, 14, 14);
			for(int i=0;i<4;++i) ghostImage[3][i]=b_image.getSubimage(15, 156, 14, 14);
			for(int i=0;i<4;++i) ghostImage[4][i]=b_image.getSubimage(i*11+i*2, 150, 11, 5);
			
						
			b_image=ImageIO.read(getClass().getResource("/media/g_move_map.png"));
			
			//load move map
			int mapX=b_image.getWidth(null);
	        int mapY=b_image.getHeight(null);
			map=new short[mapX][mapY];
			
			for(int j=0;j<mapY;++j){
				for(int i=0;i<mapX;++i){
					if(b_image.getRGB(i, j)==Color.RED.getRGB()) map[i][j]=1;  //move
					else if(b_image.getRGB(i, j)==Color.YELLOW.getRGB()) map[i][j]=2; //slow tunel
					else if(b_image.getRGB(i, j)==new Color(0,255,255).getRGB()) map[i][j]=6; //check
					else map[i][j]=0;
				}
			}
		} catch (IOException e) {
			System.out.println("Image not found.");
		}
	}
	
	@Override
	public void reset(){
		status=normal_status=GhostStatus.CIRCULATING;
		C_SPEED=SPEED;	
        count=0;				
		anim_frame=0;	
		currentTime=frame_count=0;
		
		if(n==1){
			x=105;
			y=85;
			stay_in_home=false;
			ort=Move.LEFT;
		}else if(n==2){
			x=90;
			y=109;
			stay_in_home=true;
			ort=Move.UP;
		}else if(n==3){
			x=105;
			y=109;
			stay_in_home=true;
			ort=Move.DOWN;
		}else if(n==4){
			x=120;
			y=109;
			stay_in_home=true;
			ort=Move.UP;
		}
	}
	
	
    public void setRunOn(){
    	if(status==GhostStatus.EATED ) return;
		C_SPEED=SPEED*0.5f;
		status=GhostStatus.RUN;
		
		if(!isInHome()){
			if(ort==Move.UP) ort=Move.DOWN;
			else if(ort==Move.DOWN) ort=Move.UP;
			else if(ort==Move.LEFT) ort=Move.RIGHT;
			else if(ort==Move.RIGHT) ort=Move.LEFT;
		}		
	}
    
    public void setRunOff(){
    	if(status==GhostStatus.EATED) return;
    	if(stay_in_home){
    		status=normal_status;
    		return;
    	}
    	C_SPEED=SPEED;
    	status=normal_status;
    }     
	
    public void setCirculatingMod(){
    	if(status==GhostStatus.EATED){
    		normal_status=GhostStatus.CIRCULATING;
    		return;
    	}
    	if(!isInTunel()) C_SPEED=SPEED;
    	status=normal_status=GhostStatus.CIRCULATING;
    }
    
    public void setSearchMod(){
    	if(status==GhostStatus.EATED){
    		normal_status=GhostStatus.SEARCH;
    		return;
    	}
    	if(!isInTunel()) C_SPEED=SPEED;
    	status=normal_status=GhostStatus.SEARCH;
    }
       
	public void eat(int score){
		C_SPEED=SPEED*2.23f;
		status=GhostStatus.EATED;
		ex=x;
		ey=y;
		ecount=0;
		escore=score;
	}
	
	public boolean canEat(){
	    return status==GhostStatus.RUN;
	}
	    
	public boolean isDanger(){
	    return status==GhostStatus.CIRCULATING || status==GhostStatus.SEARCH;
	}
	
	public boolean isTooClose(int px, int py){
		if(Math.abs(px-x)<8 && Math.abs(py-y)<7) return true;
		else return false;
	}
	
	public int X(){
		return x;
	}
	
	public int Y(){
		return y;
	}
	

	
	@Override
	public void update() {		
		count+=C_SPEED;
		++currentTime;		

		if(count>=1 && !isInTunel() || count>=2.5 && isInTunel()){	
			++frame_count;
			if(frame_count%5==0) anim_frame=1-anim_frame;
			
			if(ort==Move.DOWN) ++y;
			else if(ort==Move.UP) --y;
			else if(ort==Move.LEFT) --x;
			else if(ort==Move.RIGHT) ++x;
						
			count=0;
			
			if(x==-13 && y==109 ) x=221;
			else if(x==222 && y==109 ) x=-12;		
			
			do_collision=true;
		}
		
		//draw eated score
		if(status==GhostStatus.EATED && ecount<60){
			++ecount;
			if(ecount%10==0) --ey;
		}
	}

	@Override
	public void collision() {
		if(!do_collision) return;
		
		//set go out
		if(currentTime>goOutTime && y==109 && stay_in_home) stay_in_home=false;
		//if goes out
		if(!isInHome() && !isInTunel() && (status==GhostStatus.CIRCULATING || status==GhostStatus.SEARCH)) C_SPEED=SPEED;
		//is back in home	
		if(x==105 && y==112 && status==GhostStatus.EATED){
			status=normal_status;			
			sounds.eyesSound(false);
		}
		
		        	
		//stay in home
		if(stay_in_home){			
			C_SPEED=SPEED*0.4f;
			if(y==105) ort=Move.DOWN;
			else if(y==113) ort=Move.UP;
		//go out
		}else if(isInHome() && !stay_in_home){	
			if(status!=GhostStatus.EATED) C_SPEED=SPEED*0.4f;
			
			if(status==GhostStatus.EATED) ort=Move.DOWN;			
			else if(x==90 && y==109) ort=Move.RIGHT;
			else if(x==120 && y==109) ort=Move.LEFT;
			else if(x==105 && y==109) ort=Move.UP;
			else if((x==90 || x==120) && y==105) ort=Move.DOWN;
			else if((x==90 || x==120 || x==105) && y==113) ort=Move.UP;				
		//normal mod
		}else if(status==GhostStatus.CIRCULATING){
			try{				
				if(map[x][y]==1 || map[x][y]==2) bandage(x, y);  	//intersection			
				else if(map[x][y]==6){                              //go to circulating destination
					if(n==1) goTo(225, -50);
					else if(n==2) goTo(-1, -35);
					else if(n==3) goTo(223, 247);
					else if(n==4) goTo(0, 235);				
				}	
			}catch(Exception e){};
			
		}else if(status==GhostStatus.RUN){
			try{				
				if(map[x][y]==1 || map[x][y]==2) bandage(x, y);  	//intersection			
				else if(map[x][y]==6){                              //go to circulating destination
					if(n==1) goTo(225, -50);
					else if(n==2) goTo(-1, -35);
					else if(n==3) goTo(223, 247);
					else if(n==4) goTo(0, 235);				
				}	
			}catch(Exception e){};
			
		//search mod
		}else if(status==GhostStatus.SEARCH){		
			try{
				if(map[x][y]==1 || map[x][y]==2) bandage(x, y);     //intersection			
				else if(map[x][y]==6){                              //go to circulating destination
					if(n==1) goTo(pacman.X()+1, pacman.Y());
					else if(n==2){
						if(pacman.ort()==Move.UP) goTo(pacman.X(), pacman.Y()-25);
						else if(pacman.ort()==Move.DOWN) goTo(pacman.X(), pacman.Y()+25);
						else if(pacman.ort()==Move.LEFT) goTo(pacman.X()-25, pacman.Y());
						else if(pacman.ort()==Move.RIGHT) goTo(pacman.X()+25, pacman.Y());
						else if(pacman.ort()==Move.STOPED){
							if(pacman.oldOrt()==Move.UP) goTo(pacman.X(), pacman.Y()-25);
							else if(pacman.oldOrt()==Move.DOWN) goTo(pacman.X(), pacman.Y()+25);
							else if(pacman.oldOrt()==Move.LEFT) goTo(pacman.X()-25, pacman.Y());
							else if(pacman.oldOrt()==Move.RIGHT) goTo(pacman.X()+25, pacman.Y());
						}
					}else if(n==3){
						int d_x=pacman.X()+(pacman.X()-red_ghost.X());
						int d_y=pacman.Y()+(pacman.Y()-red_ghost.Y());
		                goTo(d_x, d_y);
					}else if(n==4){
						int d=(int)Math.round(Math.sqrt((pacman.X()-x)*(pacman.X()-x)+(pacman.Y()-y)*(pacman.Y()-y)));
						if(d>60) goTo(pacman.X()+1, pacman.Y());
						else goTo(0, 235);
					}
				}		
			}catch(Exception e){}
				
		}else if(status==GhostStatus.EATED){
			goTo(105, 86);			
		}
		
		
		do_collision=false;		
	}

	@Override
	public void render(Graphics2D g) {
		if(status==GhostStatus.EATED && ecount<60){
			g.setFont(font);
			g.setColor(color);
			g.drawString(""+escore+"00", ex, ey);
		}
		
		if(status==GhostStatus.EATED){
			g.drawImage(ghostImage[4][ort.getValue()], x+2, y+4, null);	
		}else if(status==GhostStatus.RUN) g.drawImage(ghostImage[anim_frame+2][0], x, y, null);
		else g.drawImage(ghostImage[anim_frame][ort.getValue()], x, y, null);							
	}
	
	
	public static enum GhostStatus{
		CIRCULATING(), SEARCH(), RUN(), EATED();
	}
	
	
	private boolean isInTunel(){
		return y==109 && (x<29 || x>180);	
	}
	
	private boolean isInHome(){
		if(y<125 && y>85 && x>80 && x<130) return true; 
		else return false;
	}	
	
	private void bandage(int nx, int ny){
		if(ort==Move.UP && map[x][y-1]==0 || ort==Move.DOWN && map[x][y+1]==0){
			if(map[x-1][y]==1) ort=Move.LEFT;
			else if(map[x+1][y]==1) ort=Move.RIGHT;	
		}else if(ort==Move.LEFT && map[x-1][y]==0 || ort==Move.RIGHT && map[x+1][y]==0){
			if(map[x][y-1]==1) ort=Move.UP;
			else if(map[x][y+1]==1) ort=Move.DOWN;	
		}	
	}

	private void goTo(int nx, int ny){
		//up, left, down, right
		double[] select=new double[4];
		select[0]=Math.sqrt((nx-x)*(nx-x)+(ny-(y-1))*(ny-(y-1)));	
		select[1]=Math.sqrt((nx-(x-1))*(nx-(x-1))+(ny-y)*(ny-y));
		select[2]=Math.sqrt((nx-x)*(nx-x)+(ny-(y+1))*(ny-(y+1)));
		select[3]=Math.sqrt((nx-(x+1))*(nx-(x+1))+(ny-y)*(ny-y));
		
		
		double s=65000;
		int selected=-1;
		
		//block up location and down in home
		if(x==93 && y==85 || x==117 && y==85 || x==93 && y==181 || x==117 && y==181) select[0]=-1;	
		if(x==105 && y==85 && status!=GhostStatus.EATED) select[2]=-1;
		
		//block back ort
		if(ort==Move.UP) select[2]=-1;			
		else if(ort==Move.LEFT) select[3]=-1;	
		else if(ort==Move.DOWN) select[0]=-1;	
		else if(ort==Move.RIGHT) select[1]=-1;			
		
		//block if black
		if(map[x][y-1]!=1) select[0]=-1;
		if(map[x-1][y]!=1) select[1]=-1;
		if(map[x][y+1]!=1) select[2]=-1;
		if(map[x+1][y]!=1) select[3]=-1;
		
		//find smalest
		for(int i=0;i<4;++i)
			if(select[i]!=-1 && select[i]<s){
				s=select[i];
				selected=i;
			}	
				
		//select ort
		if(selected==0) ort=Move.UP;
		else if(selected==1) ort=Move.LEFT;
		else if(selected==2) ort=Move.DOWN;
		else if(selected==3) ort=Move.RIGHT;
	}
	
}
