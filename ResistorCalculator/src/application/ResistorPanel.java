package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ResistorPanel extends JPanel{	
	private Image image, silverImage, goldImage;
	private int digit1, digit2, digit3, multiplier, tolerance;
	private String value;
	private Font font;
	
	
	
	public ResistorPanel(){
		setBackground(Color.WHITE);
		digit1=1;
		digit2=0;
		digit3=-1;
		multiplier=1;
		tolerance=6;
		value="(100 \u00B1 5) \u03A9";
		font=new Font("", Font.BOLD, 16);
		
		try {
			image=ImageIO.read(getClass().getResource("/images/resistor.png"));
			silverImage=ImageIO.read(getClass().getResource("/images/silver.png"));
			goldImage=ImageIO.read(getClass().getResource("/images/gold.png"));
		} catch (IOException e) {}
	
	}
	
	
	
	public void setFirst(int v){
		digit1=v;
		calculate();
		this.repaint();
	}
	
	public void setSecond(int v){
		digit2=v;
		calculate();
		this.repaint();
	}
	
	public void setThird(int v){
		digit3=v;
		calculate();
		this.repaint();
	}
	
	public void setMull(int v){
		multiplier=v;
		calculate();
		this.repaint();
	}
	
	public void setTolerance(int v){
		tolerance=v;
		calculate();
		this.repaint();
	}
	
	public void setResult(String res){
		value=res;
		calculate();
		this.repaint();
	}
	
	private void calculate(){
		if(digit3==-1) value=""+digit1+digit2;
		else value=""+digit1+digit2+digit3;
	
		char p=' ';
		double v=Double.parseDouble(value)*Window.MULTIPLIER[multiplier];
			
		if(v>1000000){
			v=v/1000000;
			p='M';
		}else if(v>1000){
			v=v/1000;
			p='k';
		}
			
		double t=v*Window.TOLERANCE[tolerance]/100;
		
		String vs=""+v;
		String ts=""+t;
		if(ts.length()>5) ts=ts.substring(0, 5);
		if(vs.length()>5) vs=vs.substring(0, 5);
	
		value="("+vs+" \u00B1 "+ts+") "+p+"\u03A9";
	}
	
	
	
	@Override
	public void paintComponent(Graphics graphics){
		graphics.drawImage(image, 0, 0, null);
		
		graphics.setColor(Window.digitColor[digit1]);
		graphics.fillRect(70, 10, 10, 50);
		graphics.setColor(Window.digitColor[digit2]);
		graphics.fillRect(90, 10, 10, 50);
		if(digit3!=-1){
			graphics.setColor(Window.digitColor[digit3]);
			graphics.fillRect(110, 10, 10, 50);
		}	
		
		if(multiplier==8){
			graphics.drawImage(goldImage, 140, 10, null);
		}else if(multiplier==9){
			graphics.drawImage(silverImage, 140, 10, null);
		}else{
			graphics.setColor(Window.multiplierColor[multiplier]);
			graphics.fillRect(140, 10, 10, 50);
		}
		if(tolerance==6){
			graphics.drawImage(goldImage, 170, 10, null);
		}else if(tolerance==7){
			graphics.drawImage(silverImage, 170, 10, null);
		}else{
			graphics.setColor(Window.toleranceColor[tolerance]);
			graphics.fillRect(190, 10, 10, 50);
		}
		
		graphics.setColor(Color.BLACK);
		graphics.setFont(font);
		graphics.drawString(value, 40, 85);
		
	}

}