package m2math;

import java.util.LinkedList;
import java.util.List;

class Lexer {
	private char[] niz;
	private char base;
	private Token lastToken;
	private int currentIndex; 
	private double ans;

	public Lexer(String text, double ans, char b){ 
		niz=text.toCharArray();
		currentIndex=0;
		lastToken=null;
		this.ans=ans;
		base=b;
	}
	
	public List<Token> getTokens(String niz, double ans, char b){
		Lexer l=new Lexer(niz, ans, b);
		List<Token> lista=new LinkedList<Token>();
				
		while(true){
			Token t=null;
			try{				
				t=l.nextToken();
				
				if(t==null) return lista;
				else lista.add(t);
				
			}catch(ParsingErrorException e){
				return null;
			}
		}
	}
	

	private Token nextToken() throws ParsingErrorException{
		// End 
		if(currentIndex==niz.length) return lastToken=null;				
				
		char csym=niz[currentIndex];
		String t="";
		
		if(lastToken!=null && lastToken.getType()==TokenType.FUNCT && csym!='(') throw new ParsingErrorException("Missing parentheses after function.");
		
		// Pi
		if(csym=='p' && niz[currentIndex+1]=='i' || csym=='\u03c0'){
			if(csym=='p') currentIndex+=2;
			else currentIndex+=1;
			
			return lastToken=new Token(TokenType.BROJ, new Double(Math.PI));
			
		// e
		}else if(csym=='e'){
			++currentIndex;
			return lastToken=new Token(TokenType.BROJ, new Double(Math.E));
			
		// Ans
		}else if(csym=='A' && niz[currentIndex+1]=='n' && niz[currentIndex+2]=='s'){
			currentIndex+=3;
			return lastToken=new Token(TokenType.BROJ, new Double(ans));
			
		// Operator 1, 2
		}else if(csym=='+' || csym=='-'){
			++currentIndex;
			
			if(csym=='-' && (lastToken==null || lastToken.getType()==TokenType.ZAGRADA_L || lastToken.getType()==TokenType.FUNCT_ARGUMENT_SEPARATOR))
				return lastToken=new Token(TokenType.OPERATOR_5, "m");				
			else
				return lastToken=new Token(TokenType.OPERATOR_1, ""+csym);					
			
		}else if(csym=='*' || csym=='/'){
			++currentIndex;
			return lastToken=new Token(TokenType.OPERATOR_2, ""+csym);
			
		}else if(csym=='%'){
			++currentIndex;
			return lastToken=new Token(TokenType.OPERATOR_2, "%");
		
		}else if(csym=='<' && niz[currentIndex+1]=='<'){
			currentIndex+=2;
			return lastToken=new Token(TokenType.OPERATOR_3, "<<");
			
		}else if(csym=='>' && niz[currentIndex+1]=='>' && niz[currentIndex+2]=='>'){
			currentIndex+=3;
			return lastToken=new Token(TokenType.OPERATOR_3, ">>>");
		
		}else if(csym=='>' && niz[currentIndex+1]=='>'){
			currentIndex+=2;
			return lastToken=new Token(TokenType.OPERATOR_3, ">>");
		
		}else if(csym=='o' && niz[currentIndex+1]=='r'){
			currentIndex+=2;
			return lastToken=new Token(TokenType.OPERATOR_3, "or");
			
		}else if(csym=='x' && niz[currentIndex+1]=='o' && niz[currentIndex+2]=='r'){
			currentIndex+=3;
			return lastToken=new Token(TokenType.OPERATOR_3, "xor");
		
		}else if(csym=='a' && niz[currentIndex+1]=='n' && niz[currentIndex+2]=='d'){
			currentIndex+=3;
			return lastToken=new Token(TokenType.OPERATOR_3, "and");
		
		// Operators 3, 4, 5, funct
		}else if(csym=='^'){
			++currentIndex;
			return lastToken=new Token(TokenType.OPERATOR_4, "^");
			
		}else if(csym=='s' && niz[currentIndex+1]=='q' && niz[currentIndex+2]=='r' && niz[currentIndex+3]=='t' || csym=='\u221A'){			
			if(csym=='s') currentIndex+=4;
			else currentIndex+=1;
			
			return lastToken=new Token(TokenType.FUNCT, "sqrt");
		
		}else if(csym=='s' && niz[currentIndex+1]=='i' && niz[currentIndex+2]=='n'){			
			currentIndex+=3;
			return lastToken=new Token(TokenType.FUNCT, "sin");
		
		}else if(csym=='c' && niz[currentIndex+1]=='o' && niz[currentIndex+2]=='s'){			
			currentIndex+=3;
			return lastToken=new Token(TokenType.FUNCT, "cos");
		
		}else if(csym=='t' && niz[currentIndex+1]=='a' && niz[currentIndex+2]=='n'){			
			currentIndex+=3;
			return lastToken=new Token(TokenType.FUNCT, "tan");
			
		}else if(csym=='a' && niz[currentIndex+1]=='s' && niz[currentIndex+2]=='i' && niz[currentIndex+3]=='n' ){			
			currentIndex+=4;
			return lastToken=new Token(TokenType.FUNCT, "asin");
			
		}else if(csym=='a' && niz[currentIndex+1]=='c' && niz[currentIndex+2]=='o' && niz[currentIndex+3]=='s' ){			
			currentIndex+=4;
			return lastToken=new Token(TokenType.FUNCT, "acos");
			
		}else if(csym=='a' && niz[currentIndex+1]=='t' && niz[currentIndex+2]=='a' && niz[currentIndex+3]=='n' ){			
			currentIndex+=4;
			return lastToken=new Token(TokenType.FUNCT, "atan");
			
		}else if(csym=='l' && niz[currentIndex+1]=='o' && niz[currentIndex+2]=='g' ){			
			currentIndex+=3;
			return lastToken=new Token(TokenType.FUNCT, "log");
			
		}else if(csym=='l' && niz[currentIndex+1]=='n'){			
			currentIndex+=2;
			return lastToken=new Token(TokenType.FUNCT, "ln");
			
		}else if(csym=='f' && niz[currentIndex+1]=='a' && niz[currentIndex+2]=='c' && niz[currentIndex+3]=='t'){			
			currentIndex+=4;
			return lastToken=new Token(TokenType.FUNCT, "fact");
			
		}else if(csym=='a' && niz[currentIndex+1]=='b' && niz[currentIndex+2]=='s'){			
			currentIndex+=3;
			return lastToken=new Token(TokenType.FUNCT, "abs");
			
		}else if(csym=='n' && niz[currentIndex+1]=='o' && niz[currentIndex+2]=='t'){			
			currentIndex+=3;
			return lastToken=new Token(TokenType.FUNCT, "not");
			
		}else if(csym=='r' && niz[currentIndex+1]=='o' && niz[currentIndex+2]=='o' && niz[currentIndex+3]=='t'){			
			currentIndex+=4;
			return lastToken=new Token(TokenType.FUNCT, "root");
			
		}else if(csym=='n' && niz[currentIndex+1]=='l' && niz[currentIndex+2]=='o' && niz[currentIndex+3]=='g'){			
			currentIndex+=4;
			return lastToken=new Token(TokenType.FUNCT, "nlog");
			
		// c
		}else if(csym=='c'){
			++currentIndex;
			return lastToken=new Token(TokenType.OPERATOR_3, "c");
			
		// p
		}else if(csym=='p'){
			++currentIndex;
			return lastToken=new Token(TokenType.OPERATOR_3, "p");
		

		// Number
		}else if(csym>=48 && csym<=57 || csym=='A' || csym=='B' || csym=='C' || csym=='D' || csym=='E' || csym=='F'){
			while(currentIndex<niz.length && (niz[currentIndex]>=48 && niz[currentIndex]<=57 || niz[currentIndex]=='.' || 
					niz[currentIndex]=='A' || niz[currentIndex]=='B' || niz[currentIndex]=='C' || niz[currentIndex]=='D' || niz[currentIndex]=='E' || niz[currentIndex]=='F')){
				t=t+niz[currentIndex];
				++currentIndex;
			}
							
			if(niz[currentIndex-1]=='.') throw new ParsingErrorException("Expect digit after '.'.");
			else{ 
				if(base=='b') t=""+Long.parseLong(t, 2);
				else if(base=='o') t=""+Long.parseLong(t, 8);
				else if(base=='h') t=""+Long.parseLong(t, 16);				
				
				return lastToken=new Token(TokenType.BROJ, Double.parseDouble(t));				
			}
						
			
		// Left parenthesis
		}else if(csym=='('){
			++currentIndex;
			return lastToken=new Token(TokenType.ZAGRADA_L, ""+csym);
			
		// Right parenthesis
		}else if(csym==')'){
			++currentIndex;
			return lastToken=new Token(TokenType.ZAGRADA_R, ""+csym);
						
		// Space
		}else if(csym==' '){
			++currentIndex;
			return nextToken();
			
		// unction argument separator
		}else if(csym==','){
			++currentIndex;
			return lastToken=new Token(TokenType.FUNCT_ARGUMENT_SEPARATOR, ",");
			
		}else throw new ParsingErrorException("Non-existent symbol.");
		
	}
}
