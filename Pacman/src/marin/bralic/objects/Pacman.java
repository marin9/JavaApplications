package marin.bralic.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pacman extends GameObject{
	private float SPEED;
	private Image[][] pacImage;	
	private Image pacImageStoped;
	private short[][] move_map;
	
	private int x, y, anim_frame;
	private Move ort, mem_ort, old_ort;
	private boolean anim_next;
	private float count;

	
	public Pacman(float speed){
		SPEED=speed;
		pacImage=new Image[4][6];
		reset();
		loadContents();
	}	
	
	@Override
	protected void loadContents() {
		BufferedImage _image=null;
		BufferedImage map_image=null;
		
		//object images
		try {
			_image=ImageIO.read(getClass().getResource("/media/object_images.png"));
			map_image=ImageIO.read(getClass().getResource("/media/map_move.png"));
			pacImageStoped=_image.getSubimage(60, 156, 14, 14);
		} catch (IOException e) {
			System.out.println("No object images.");
		}
		
		for(int j=0;j<4;++j){
			for(int i=0;i<6;++i){
				pacImage[j][i]=_image.getSubimage(i*15, 171+j*15, 14, 14);
			}
		}
		
		//move map
		int mapX=map_image.getWidth(null);
        int mapY=map_image.getHeight(null);
		move_map=new short[mapX][mapY];
		
		for(int j=0;j<mapY;++j){
			for(int i=0;i<mapX;++i){
				if(map_image.getRGB(i, j)==Color.RED.getRGB()) move_map[i][j]=1;
				else move_map[i][j]=0;
			}
		}
	}
	
	@Override
	public void reset(){
		count=0;
		anim_next=true;
		x=105;
		y=181;
		ort=mem_ort=Move.STOPED;
		old_ort=Move.LEFT;
		anim_frame=0;		
	}
	
	
	public int X(){
		return x;
	}
	
	public int Y(){
		return y;
	}
	
	public Move ort(){
		return ort;
	}
	
	public Move oldOrt(){
		return old_ort;
	}
	
	public void setOrientation(Move new_ort){
		mem_ort=new_ort;
	}

	@Override
	public void update() {		
		count+=SPEED;
		
		if(count>=1){						
			if(anim_next) ++anim_frame;
			else --anim_frame;
			if(anim_frame==5 || anim_frame==0) anim_next=!anim_next;
			
			if(ort==Move.DOWN) ++y;
			else if(ort==Move.UP) --y;
			else if(ort==Move.LEFT) --x;
			else if(ort==Move.RIGHT) ++x;
						
			count=0;
			
			if(x==-13 && y==109 ) x=221;
			else if(x==222 && y==109 ) x=-12;		
		}
		
		
	}

	@Override
	public void collision() {
		try{
			if(mem_ort==Move.UP && move_map[x][y-1]==1 ||
					   mem_ort==Move.DOWN && move_map[x][y+1]==1 ||
					   mem_ort==Move.LEFT && move_map[x-1][y]==1 ||
					   mem_ort==Move.RIGHT && move_map[x+1][y]==1){
				         old_ort=ort;
						 ort=mem_ort;						 
					}else if(!(ort==Move.UP && move_map[x][y-1]==1 ||
							 ort==Move.DOWN && move_map[x][y+1]==1 ||
							 ort==Move.LEFT && move_map[x-1][y]==1 ||
							 ort==Move.RIGHT && move_map[x+1][y]==1)){
						ort=Move.STOPED;			
					}	
		}catch(ArrayIndexOutOfBoundsException e){}
					
	}

	@Override
	public void render(Graphics2D g) {
		if(ort!=Move.STOPED) g.drawImage(pacImage[ort.getValue()][anim_frame], x, y, null);
		else g.drawImage(pacImageStoped, x, y, null);		
	}

}
