package m2math;

public class ParsingErrorException extends Exception{
	private static final long serialVersionUID = 1L;	
	
	public ParsingErrorException(String msg){
		super(msg);
	}
}