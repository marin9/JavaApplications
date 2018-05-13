package calculators;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import m2math.MathParser;

public class Scientific extends Pane{
	private TextField display;
	private Button[] buttons;
	private ButtonListener listener;
	
	private double ans=0;
	private boolean rad=false;
	
	public Scientific(){
		initGUI();
		setListeners();	
	}	
	
	private void initGUI(){
		VBox vbox=new VBox(7);	
		vbox.setPadding(new Insets(5));
		display=new TextField("");
		display.setFocusTraversable(false);
		display.setAlignment(Pos.CENTER_LEFT);
		display.setPrefColumnCount(8);
		display.setStyle("-fx-background-color: #E0E0E0;" + "-fx-font-size: 20px;" + "-fx-font-weight: bold;");
		buttons=new Button[44];
		
		HBox hbox9=new HBox(9);	
		buttons[43]=new Button("sin");
		buttons[42]=new Button("cos");
		buttons[41]=new Button("tan");
		buttons[40]=new Button("del"); 
		buttons[39]=new Button("C");  
		hbox9.getChildren().addAll(buttons[43], buttons[42], buttons[41], buttons[40], buttons[39]);
		
		HBox hbox8=new HBox(9);	
		buttons[38]=new Button("asin");
		buttons[37]=new Button("acos");
		buttons[36]=new Button("atan");
		buttons[35]=new Button("\u03C0"); 
		buttons[34]=new Button("DEG");  
		hbox8.getChildren().addAll(buttons[38], buttons[37], buttons[36], buttons[35], buttons[34]);
		
		HBox hbox7=new HBox(9);	
		buttons[33]=new Button("nPr");
		buttons[32]=new Button("nCr");
		buttons[31]=new Button("n!");
		buttons[30]=new Button("abs"); 
		buttons[29]=new Button("Ans");  
		hbox7.getChildren().addAll(buttons[33], buttons[32], buttons[31], buttons[30], buttons[29]);
		 
		HBox hbox6=new HBox(9);	
		buttons[28]=new Button("x\u207B\u00B9");
		buttons[27]=new Button("e\u207F");
		buttons[26]=new Button("ln");
		buttons[25]=new Button("log"); 
		buttons[24]=new Button("log\u2093");  
		hbox6.getChildren().addAll(buttons[28], buttons[27], buttons[26], buttons[25], buttons[24]);
		
		HBox hbox5=new HBox(9);			
		buttons[23]=new Button("x\u00B2");
		buttons[22]=new Button("x\u207F");  
		buttons[21]=new Button("\u221Ax");
		buttons[20]=new Button("\u207F\u221Ax");
		buttons[19]=new Button(",");  
		hbox5.getChildren().addAll(buttons[23], buttons[22], buttons[21], buttons[20], buttons[19]);
	    
		HBox hbox4=new HBox(9);
		buttons[18]=new Button("7");
		buttons[17]=new Button("8");
		buttons[16]=new Button("9");
		buttons[15]=new Button("(");   
		buttons[14]=new Button(")"); 
		hbox4.getChildren().addAll(buttons[18], buttons[17], buttons[16], buttons[15], buttons[14]);
		
		HBox hbox3=new HBox(9);
		buttons[13]=new Button("4");
		buttons[12]=new Button("5");
		buttons[11]=new Button("6");
		buttons[10]=new Button("*");	
		buttons[9]=new Button("/");
		hbox3.getChildren().addAll(buttons[13], buttons[12], buttons[11], buttons[10], buttons[9]);
		
		HBox hbox2=new HBox(9);
		buttons[8]=new Button("1");
		buttons[7]=new Button("2");
		buttons[6]=new Button("3");
		buttons[5]=new Button("+");	   
		buttons[4]=new Button("-");
		hbox2.getChildren().addAll(buttons[8], buttons[7], buttons[6], buttons[5], buttons[4]);
		
		HBox hbox1=new HBox(9);
		buttons[3]=new Button("0");
		buttons[2]=new Button(",");
		buttons[1]=new Button("Exp");
		buttons[0]=new Button("=");
		hbox1.getChildren().addAll(buttons[3], buttons[2], buttons[1], buttons[0]);
				
		vbox.getChildren().addAll(display, hbox9, hbox8, hbox7, hbox6, hbox5, hbox4, hbox3, hbox2, hbox1);
	    getChildren().add(vbox);	    
	    
	    for(int i=0;i<44;++i){
	    	buttons[i].setPrefHeight(30);
	    	buttons[i].setPrefWidth(45);
    	
	    	if(i<19) buttons[i].getStylesheets().add(getClass().getResource("/calculators/button_cyan.css").toExternalForm());
	    	else{
	    		buttons[i].setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;");
	    		buttons[i].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm());
	    	}
	    }
	    
	    buttons[40].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm());
	    buttons[39].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm());
	    
	    buttons[3].setPrefWidth(99);
	    buttons[1].setPrefHeight(32);
	    
	    buttons[38].setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;");	 
	    buttons[37].setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;");	 
	    buttons[36].setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;");	 
	    buttons[35].setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;");	 
	    buttons[28].setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;");	
	    buttons[27].setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;");	 
	    buttons[23].setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;");	
	    buttons[22].setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;");	
	    buttons[21].setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;");	
	    buttons[20].setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;");	
	    buttons[1].setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;");	 	    
	    buttons[0].getStylesheets().add(getClass().getResource("/calculators/button_red.css").toExternalForm());
	}
	
	private void setListeners(){
		listener=new ButtonListener();
		
		for(int i=0;i<buttons.length;++i){
			buttons[i].setOnAction(listener);
		}		
	}
	
	
	private void calculate(){
		double result=0;
		
		try {
			Main.historyPane.append(display.getText()+"\n\n");
			
			result = MathParser.eval(display.getText(), rad, ans, 'd');
			result=MathParser.round(""+result, 12);
			ans=result;	
			
			Main.historyPane.append("   ="+result+"\n\n");
			
			display.setText(""+result);
			
		} catch (Exception e) {
			display.setText("Error.");
			Main.historyPane.append("   = Error.\n\n");
		} 
	}
	
	
	private class ButtonListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			Button btn=(Button)event.getSource();
			
			if(btn==buttons[0]) calculate();
			else if(btn==buttons[1]) display.appendText("*10^(");
			else if(btn==buttons[2]) display.appendText(".");
			else if(btn==buttons[3]) display.appendText("0");
			else if(btn==buttons[4]) display.appendText("-");
			else if(btn==buttons[5]) display.appendText("+");
			else if(btn==buttons[6]) display.appendText("3");
			else if(btn==buttons[7]) display.appendText("2");
			else if(btn==buttons[8]) display.appendText("1");
			else if(btn==buttons[9]) display.appendText("/");
			else if(btn==buttons[10]) display.appendText("*");
			else if(btn==buttons[11]) display.appendText("6");
			else if(btn==buttons[12]) display.appendText("5");
			else if(btn==buttons[13]) display.appendText("4");
			else if(btn==buttons[14]) display.appendText(")");
			else if(btn==buttons[15]) display.appendText("(");
			else if(btn==buttons[16]) display.appendText("9");
			else if(btn==buttons[17]) display.appendText("8");
			else if(btn==buttons[18]) display.appendText("7");
			else if(btn==buttons[19]) display.appendText(",");
			else if(btn==buttons[20]) display.appendText("root(");
			else if(btn==buttons[21]) display.appendText("\u221A(");
			else if(btn==buttons[22]) display.appendText("^(");	
			else if(btn==buttons[23]) display.appendText("^(2)");	
			else if(btn==buttons[24]) display.appendText("nlog(");
			else if(btn==buttons[25]) display.appendText("log(");	
			else if(btn==buttons[26]) display.appendText("ln(");	
			else if(btn==buttons[27]) display.appendText("e^(");	
			else if(btn==buttons[28]) display.appendText("^(-1)");	
			else if(btn==buttons[29]) display.appendText("Ans");	
			else if(btn==buttons[30]) display.appendText("abs(");	
			else if(btn==buttons[31]) display.appendText("fact(");	
			else if(btn==buttons[32]) display.appendText("c");	
			else if(btn==buttons[33]) display.appendText("p");	
			else if(btn==buttons[34]){
				rad=!rad;
				if(rad) buttons[34].setText("RAD");
				else buttons[34].setText("DEG");
			}
			else if(btn==buttons[35]) display.appendText("\u03C0");
			else if(btn==buttons[36]) display.appendText("atan(");	
			else if(btn==buttons[37]) display.appendText("acos(");	
			else if(btn==buttons[38]) display.appendText("asin(");	
			else if(btn==buttons[39]) display.setText("");	
			else if(btn==buttons[40]){
				String text=display.getText();
				if(text.equals("")) return;
				
				text=text.substring(0, text.length()-1);
				display.setText(text);
				display.selectPositionCaret(text.length());
			}
			else if(btn==buttons[41]) display.appendText("tan(");	
			else if(btn==buttons[42]) display.appendText("cos(");	
			else if(btn==buttons[43]) display.appendText("sin(");				
		}
	}

}
