package m2math;

class TokenType {	
	static final int BROJ=0;
	static final int ZAGRADA_L=1;
	static final int ZAGRADA_R=2;
	static final int FUNCT=3;       // functions: sqrt, sin, cos, tan, asin, acos, atan, log, ln, abs, not, root, nlog, fact
	static final int FUNCT_ARGUMENT_SEPARATOR=4;  // ,
	
	static final int OPERATOR_1=5;  // +, -                                          // left as
	static final int OPERATOR_2=6;  // *, /, %                                       // left as
	static final int OPERATOR_3=7;  // c, p, <<, >>, >>>, and, or, xor               // left as
	
	static final int OPERATOR_4=8;  // ^                                             // right as
	static final int OPERATOR_5=9;  // m (- sign)                                    // right as
		
		
	public static boolean isLeftAssociative(int tokenType){		
		if(tokenType==OPERATOR_1 || tokenType==OPERATOR_2 || tokenType==OPERATOR_3) return true;
		else return false;
	}
	
	public static boolean isRightAssociative(int tokenType){		
		if(tokenType==OPERATOR_4 || tokenType==OPERATOR_5) return true;
		else return false;
	}
}
