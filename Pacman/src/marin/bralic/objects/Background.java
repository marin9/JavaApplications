package marin.bralic.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background extends GameObject{
	private Image backgroundImage;
	private int width, height;
	private short[][] map;
	
	
	public Background(){		
		reset();				
		loadContents();
	}

	@Override
	protected void loadContents() {
		try {
			backgroundImage=ImageIO.read(getClass().getResource("/media/map_background.png"));
		} catch (IOException e) {
			System.out.println("Map background file error.");
		} 
	}
	
	@Override
	public void reset(){
		BufferedImage moveImage=null;
		try {
			moveImage = ImageIO.read(getClass().getResource("/media/map_items.png"));
			width=moveImage.getWidth(null);
			height=moveImage.getHeight(null);
			map=new short[width][height];
			
			for(int y=0;y<height;++y){
				for(int x=0;x<width;++x){
					if(moveImage.getRGB(x, y)==-10240) map[x][y]=1;
					else if(moveImage.getRGB(x, y)==Color.PINK.getRGB()) map[x][y]=2;
					else map[x][y]=0;	
				}
			}
			
		} catch (IOException e) {
			System.out.println("Move map image error.");
		}
	}

	public int eat(int x, int y){
		try{
			int item=(int)map[x][y];
			map[x][y]=0;		
			return item;
		}catch(ArrayIndexOutOfBoundsException e){
			return 0;
		}
		
	}
	
	
	@Override
	public void update() {}

	@Override
	public void collision() {}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(backgroundImage, 0, 0, null);
		
		for(int y=11;y<height;y=y+8){
			for(int x=11;x<width;x=x+8){
				if(map[x][y]==1){
					g.setColor(Color.YELLOW);
					g.fillOval(x-1, y-1, 3, 3);
				}else if(map[x][y]==2){
					g.setColor(Color.PINK);
					g.fillOval(x-3, y-3, 6, 6);
				}
				
			}
		}
	}

}
