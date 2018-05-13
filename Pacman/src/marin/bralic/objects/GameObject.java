package marin.bralic.objects;

import java.awt.Graphics2D;

public abstract class GameObject {
	
	protected abstract void loadContents();
	public abstract void reset();
	
	public abstract void update();
	public abstract void collision();
	public abstract void render(Graphics2D g);
	
}
