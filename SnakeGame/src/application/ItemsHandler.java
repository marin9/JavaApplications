package application;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

public class ItemsHandler {
	public static final int NOTHING=-1;
	public static final int STRAWBERRY=0;
	public static final int CHERRY=1;
	public static final int PEAR=2;
	public static final int BANANA=3;
	public static final int APPLE=4;
	public static final int SPEED_UP=5;
	public static final int CUT=6;
	public static final int SLOW_TIME=7;
	public static final int SCORE_PACK=8;
	
	
	private int DOT_SIZE;
	private LinkedList<Item> itemList;		
	private Image[] itemImage;
	private Random rand;
	private int itemsNumber;
	
	
	public ItemsHandler(int dot_size){
		DOT_SIZE=dot_size;
		itemList=new LinkedList<Item>();		
		rand=new Random();
		
        loadImages();
		initialize();
	}

	public void loadImages(){
		itemImage=new Image[9];

		try{
	    itemImage[0]=ImageIO.read(getClass().getResource("/images/item1.png"));
		itemImage[1]=ImageIO.read(getClass().getResource("/images/item2.png"));
		itemImage[2]=ImageIO.read(getClass().getResource("/images/item3.png"));
		itemImage[3]=ImageIO.read(getClass().getResource("/images/item4.png"));
			
		itemImage[4]=ImageIO.read(getClass().getResource("/images/item5.png"));
		itemImage[5]=ImageIO.read(getClass().getResource("/images/item6.png"));
		itemImage[6]=ImageIO.read(getClass().getResource("/images/item7.png"));
		itemImage[7]=ImageIO.read(getClass().getResource("/images/item8.png"));
		itemImage[8]=ImageIO.read(getClass().getResource("/images/item9.png"));
		}catch(IOException e){}
	}
	
	public void initialize(){
		itemList.removeAll(itemList);	

		
		itemsNumber=Math.abs(rand.nextInt()%3)+1;

		itemList.add(new Item(Math.abs(rand.nextInt() % 38 +1), Math.abs(rand.nextInt() % 38 +1), Math.abs(rand.nextInt()%5), -1));				
		for(int i=1;i<itemsNumber;++i) 
			itemList.add(new Item(Math.abs(rand.nextInt() % 38 +1), Math.abs(rand.nextInt() % 38 +1), Math.abs(rand.nextInt()%4)+5, 25+Math.abs(rand.nextInt()%7)));
		
	}
	
	
	public int getItemNumber(){
		return itemsNumber;
	}
		
	public void step(){
		Item item;
		
		for(int i=0;i<itemsNumber;++i){
			item=itemList.get(i);
			if(item.step()==0){
				itemList.remove(i);
				--itemsNumber;
			}
		}
	}
	
	public int checkItem(int x, int y){
		Item item;
		int id;
		
		for(int i=0;i<itemsNumber;++i){
			item=itemList.get(i);		
			if(item.getX()==x && item.getY()==y){
				id=item.getId();
				itemList.remove(i);
				--itemsNumber;
				return id;
			}
		}
		
		return NOTHING;
	}
	
	public void drawItems(Graphics g){
		for(Item item:itemList)
			g.drawImage(itemImage[item.getId()], item.getX()*DOT_SIZE, item.getY()*DOT_SIZE, null);
	}
	

	private class Item{
		private int x, y, id, time;
		
		public Item(int x, int y, int item_id, int time){
			this.x=x;
			this.y=y;
			this.time=time;
			id=item_id;
		}
		
		public int step(){
			return --time;
		}
		
		public int getId(){
			return id;
		}
		
		public int getX(){
			return x;
		}
		
		public int getY(){
			return y;
		}
		
	}
		
}