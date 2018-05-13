package marin.bralic.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import marin.bralic.application.Constants;

public class Item extends GameObject{
	private Image image;	
	private Color color;
	private Font font;
	
	private int x, y, n;
	private long start_time, live_time, current_time, count;
	private ItemStatus status;
	
	
	public Item(int n){
		x=105;
		y=133;
		this.n=n;
		live_time=Constants.FPS*15;
		loadContents();
		color=Color.CYAN;
		font=new Font("", Font.BOLD, 10);
		status=ItemStatus.HIDE;
		reset();

		this.start_time=Constants.FPS*40; 
	}
	
	@Override
	protected void loadContents() {
		BufferedImage b_image;
		try {
			b_image=ImageIO.read(getClass().getResource("/media/object_images.png"));
			if(n==0) image=b_image.getSubimage(0, 231, 14, 14);
			else if(n==1) image=b_image.getSubimage(15, 231, 14, 14);				
			else if(n==2) image=b_image.getSubimage(30, 231, 14, 14);			
			else if(n==3) image=b_image.getSubimage(45, 231, 14, 14);			
			else if(n==4) image=b_image.getSubimage(60, 231, 14, 14);				
			
		} catch (IOException e) {
			System.out.println("No object images.");
		}
	}
	
	
	@Override
	public void reset(){
		if(status==ItemStatus.SHOW || status==ItemStatus.HIDE){
			status=ItemStatus.HIDE;
			count=current_time=0;
		}
	}
	
	public int getNumber(){
		return n;
	}
		
	public Image eat(){
		status=ItemStatus.EAT;
		count=0;
		return image;						
	}
	
	public boolean canEat(){
		return status==ItemStatus.SHOW;
	}
	
	public boolean isEated(){
		return status==ItemStatus.EAT || status==ItemStatus.EATED;
	}

	
	
	@Override
	public void update() {
		if(status==ItemStatus.EATED) return;
		++current_time;
		
		if(current_time-start_time>0 && status!=ItemStatus.SHOW && current_time-start_time<=live_time && status!=ItemStatus.EAT && status!=ItemStatus.EATED) status=ItemStatus.SHOW;
		else if(current_time-start_time>live_time && status!=ItemStatus.EAT && status!=ItemStatus.EATED) status=ItemStatus.HIDE;
		else if(status==ItemStatus.EAT && y>120 && count<Constants.FPS){
			++count;
			if(count%6==0) --y;
		}else if(y<=120) status=ItemStatus.EATED;				
	}

	@Override
	public void collision() {}

	@Override
	public void render(Graphics2D g) {
		if(status==ItemStatus.EATED) return;
		
		if(status==ItemStatus.SHOW) g.drawImage(image, x, y, null);		
		else if(status==ItemStatus.EAT){
            g.setColor(color);
            g.setFont(font);
			g.drawString("300", x, y);
		}
	}
	
	
	public static enum ItemStatus{
		HIDE(), SHOW(), EAT(), EATED();
	}

}
