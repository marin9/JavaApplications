package m2math;

class Stog {
	private Token[] data;
	private int index;
	
	
	public Stog(){
		data=new Token[30];
		index=-1;
	}
	
	public void push(Token token){		
		++index;
		
		if(index>=data.length){
			Token[] pom=new Token[data.length];
			for(int i=0;i<index;++i){
				pom[i]=data[i];
			}
			
			data=new Token[data.length*2];
			for(int i=0;i<index;++i){
				data[i]=pom[i];
			}
			
			data[index]=token;
			
		}else{
			data[index]=token;
		}
	}
	
	public Token pop(){
		if(index<0) return null;
		else return data[index--];
	}
	
	public Token getFirst(){
		if(index<0) return null;
		else return data[index];
	}
	
	public boolean isEmpty(){
		return index==-1;
	}

}
