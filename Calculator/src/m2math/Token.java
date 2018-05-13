package m2math;

class Token {
	private int type;
	private Object value;
	
	public Token(int tokenType, Object value){
		type=tokenType;
		this.value=value;
	}
	
	public int getType(){
		return type;
	}
	
	public Object getValue(){
		return value;
	}
}
