package marin.bralic.objects;

public enum Move {
	UP(3), DOWN(0), LEFT(1), RIGHT(2), STOPED(4);
	
	private int n;
	
	Move(int x){
		n=x;
	}
	
	public int getValue(){
		return n;
	}
	
}
