package m2math;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

public class MathParser {	

	public static double eval(String niz, boolean rad, double ans, char b) throws ParsingErrorException{
		Lexer lexer=new Lexer(niz, ans, b);		
		List<Token> tokens=lexer.getTokens(niz, ans, b);		
		List<Token> output=new LinkedList<Token>();
		Stog stog=new Stog();
		
		Token tmp;
		// Generate output
		for(int i=0;i<tokens.size()+1;++i){
			if(i==tokens.size()){
				while(!stog.isEmpty()){
					Token t=stog.pop();
					if(t.getType()==TokenType.ZAGRADA_L || t.getType()==TokenType.ZAGRADA_R) throw new ParsingErrorException("Mismatched parentheses.");
					output.add(t);
				}
				break;		
			}
			tmp=tokens.get(i);
			
			if(tmp.getType()==TokenType.BROJ){
				output.add(tmp);
				
			}else if(tmp.getType()==TokenType.FUNCT){
				stog.push(tmp);
	
			}else if(tmp.getType()==TokenType.FUNCT_ARGUMENT_SEPARATOR){
				while(!stog.isEmpty() && stog.getFirst().getType()!=TokenType.ZAGRADA_L){
					output.add(stog.pop());
				}				
				
			}else if(tmp.getType()>=TokenType.OPERATOR_1){
				int o1, o2;				
				while(!stog.isEmpty() && stog.getFirst().getType()>=TokenType.OPERATOR_1){
					o1=tmp.getType();
					o2=stog.getFirst().getType();
					
					if((TokenType.isLeftAssociative(o1) && o1<=o2) || (TokenType.isRightAssociative(o1) && o1<o2)){
						output.add(stog.pop());
					}else{
						break;
					}
				}				
				stog.push(tmp);				
				
			}else if(tmp.getType()==TokenType.ZAGRADA_L){
				stog.push(tmp);
				
			}else if(tmp.getType()==TokenType.ZAGRADA_R){
				Token t;				
				while((t=stog.pop()).getType()!=TokenType.ZAGRADA_L){
					output.add(t);
				}
				
				if(!stog.isEmpty() && stog.getFirst().getType()==TokenType.FUNCT) output.add(stog.pop());
				
			}
		}
		
		//evaluate
		int i=0;		
		while(output.size()!=1){
			tmp=output.get(i);
			
			if(tmp.getType()==0){
				++i;
				continue;
			}else{
				double res;
				
				if(((String)tmp.getValue()).charAt(0)=='m'){
					res=(Double)output.get(i-1).getValue()*(-1);
					
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
					
				}else if(((String)tmp.getValue()).charAt(0)=='+'){
					res=(Double)output.get(i-2).getValue()+(Double)output.get(i-1).getValue();
			
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
					
				}else if(((String)tmp.getValue()).charAt(0)=='-'){
					res=(Double)output.get(i-2).getValue()-(Double)output.get(i-1).getValue();
			
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
				}else if(((String)tmp.getValue()).charAt(0)=='*'){
					res=(Double)output.get(i-2).getValue()*(Double)output.get(i-1).getValue();
			
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
				}else if(((String)tmp.getValue()).charAt(0)=='/'){
					res=(Double)output.get(i-2).getValue()/(Double)output.get(i-1).getValue();
			
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
				}else if(((String)tmp.getValue()).charAt(0)=='%'){
						res=(Double)output.get(i-2).getValue()%(Double)output.get(i-1).getValue();
				
						output.remove(i);
						output.remove(i-1);
						output.remove(i-2);
						i=i-2;
						output.add(i, new Token(TokenType.BROJ, new Double(res)));
						++i;
						
				}else if(((String)tmp.getValue()).equals("<<")){
					res=((Double)output.get(i-2).getValue()).intValue()<<((Double)output.get(i-1).getValue()).intValue();
			
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;	
				}else if(((String)tmp.getValue()).equals(">>")){
					res=((Double)output.get(i-2).getValue()).intValue()>>((Double)output.get(i-1).getValue()).intValue();
			
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
				}else if(((String)tmp.getValue()).equals(">>>")){
					res=((Double)output.get(i-2).getValue()).intValue()>>>((Double)output.get(i-1).getValue()).intValue();
			
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;	
				}else if(((String)tmp.getValue()).equals("and")){
					res=((Double)output.get(i-2).getValue()).intValue()&((Double)output.get(i-1).getValue()).intValue();
			
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
				}else if(((String)tmp.getValue()).equals("or")){
					res=((Double)output.get(i-2).getValue()).intValue()|((Double)output.get(i-1).getValue()).intValue();
			
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
				}else if(((String)tmp.getValue()).equals("xor")){
					res=((Double)output.get(i-2).getValue()).intValue()^((Double)output.get(i-1).getValue()).intValue();
			
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
						
				}else if(((String)tmp.getValue()).charAt(0)=='^'){
					res=Math.pow((Double)output.get(i-2).getValue(), (Double)output.get(i-1).getValue());
			
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
				}else if(((String)tmp.getValue()).equals("sqrt")){
					res=Math.sqrt((Double)output.get(i-1).getValue());
					
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;					
				}else if(((String)tmp.getValue()).equals("sin")){
					res=rad? Math.sin((Double)output.get(i-1).getValue()) : Math.sin((Double)output.get(i-1).getValue()*Math.PI/180);
					
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;					
				}else if(((String)tmp.getValue()).equals("cos")){
					res=rad? Math.cos((Double)output.get(i-1).getValue()) : Math.cos((Double)output.get(i-1).getValue()*Math.PI/180);
					
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;					
				}else if(((String)tmp.getValue()).equals("tan")){
					res=rad? Math.tan((Double)output.get(i-1).getValue()) : Math.tan((Double)output.get(i-1).getValue()*Math.PI/180);
					
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;					
				}else if(((String)tmp.getValue()).equals("asin")){
					res=Math.asin((Double)output.get(i-1).getValue());
					
					if(!rad) res=res*180/Math.PI;
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;					
				}else if(((String)tmp.getValue()).equals("acos")){
					res=Math.acos((Double)output.get(i-1).getValue());
					
					if(!rad) res=res*180/Math.PI;
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;					
				}else if(((String)tmp.getValue()).equals("atan")){
					res=Math.atan((Double)output.get(i-1).getValue());
					
					if(!rad) res=res*180/Math.PI;
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;					
				}else if(((String)tmp.getValue()).equals("log")){
					res=Math.log10((Double)output.get(i-1).getValue());
					
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;					
				}else if(((String)tmp.getValue()).equals("ln")){
					res=Math.log((Double)output.get(i-1).getValue());
					
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;					
				}else if(((String)tmp.getValue()).equals("abs")){
					res=Math.abs((Double)output.get(i-1).getValue());
					
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;					
				}else if(((String)tmp.getValue()).equals("not")){
					res=~((Double)output.get(i-1).getValue()).intValue();
					
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;					
				}else if(((String)tmp.getValue()).equals("root")){
					double number=(Double)output.get(i-1).getValue();
					double root=(Double)output.get(i-2).getValue();
					
					if(number<0 && root%2==1) res=(-1)*Math.pow(Math.abs(number), 1/root);
					else res=Math.pow(number, 1/root);
								
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
				}else if(((String)tmp.getValue()).equals("nlog")){
					res=Math.log10((Double)output.get(i-1).getValue())/Math.log10((Double)output.get(i-2).getValue());
					
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
				}else if(((String)tmp.getValue()).equals("fact")){
					double x=Math.abs(((Double)output.get(i-1).getValue()).intValue());
					
					x=fact(x);
					
					output.remove(i);
					output.remove(i-1);
					i=i-1;
					output.add(i, new Token(TokenType.BROJ, new Double(x)));
					++i;
				}else if(((String)tmp.getValue()).charAt(0)=='c'){
					long n=Math.round((Double)output.get(i-2).getValue());
					long r=Math.round((Double)output.get(i-1).getValue());
									
					res=fact(n)/(fact(n-r)*fact(r));		
					
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
					
				}else if(((String)tmp.getValue()).charAt(0)=='p'){	
					long n=Math.round((Double)output.get(i-2).getValue());
					long r=Math.round((Double)output.get(i-1).getValue());
					
					res=fact(n)/fact(n-r);					
					
					output.remove(i);
					output.remove(i-1);
					output.remove(i-2);
					i=i-2;
					output.add(i, new Token(TokenType.BROJ, new Double(res)));
					++i;
		
				}
			}	
		}		
		
		if(output.size()>1) throw new ParsingErrorException("Output list size>1.");
		else return (Double)output.get(0).getValue();
	}	
	
	
	private static double fact(double x){		
		if(x==0) x=1;
		else
			for(double a=x-1;a>0;--a) x=x*a;
		
		return x;
	}
	
	
	public static double round(String number, int round){
		if(!number.contains(".")) return Double.parseDouble(number);		
		else if(round==0) return Math.round(Double.parseDouble(number));
		else{
			char[] c=number.toCharArray();
			String nFormat="";
			int l=c.length;
			
			for(int i=0;i<l;++i) 
				if(c[i]=='.'){
					nFormat=nFormat+".";
					for(int j=i+1;j<=i+round;++j){
						nFormat=nFormat+"#";
					}
					break;
				}
				else nFormat=nFormat+"#";
					
			
			if(number.contains("E")){
				String dec=number.substring(0, number.indexOf("E"));
				String exp=number.substring(number.indexOf("E"), number.length());
				
				DecimalFormat format=new DecimalFormat(nFormat);					
			    String result=format.format(Double.parseDouble(dec));
			    result=result.replace(',', '.');
				return Double.parseDouble(result+exp);
			}else{
				DecimalFormat format=new DecimalFormat(nFormat);					
			    String result=format.format(Double.parseDouble(number));
			    result=result.replace(',', '.');
				return Double.parseDouble(result);
			}		
		}			    
		
	}
}
