package calculators;

import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import m2math.MathParser;

public class Programmer extends Pane{
	private TextField display, binDisplay, decDisplay, octDisplay, hexDisplay;
	private ToggleButton binButton, decButton, octButton, hexButton;
	private Button[] buttons;
	
	private double ans=0;
	private char base;
	
	
	public Programmer(){
		initGUI();
		setListeners();	
	}	
	
	private void initGUI(){
		VBox vbox=new VBox(7);	
		vbox.setPadding(new Insets(5));
		
		binDisplay=new TextField("");
		decDisplay=new TextField("");
		octDisplay=new TextField("");
		hexDisplay=new TextField("");
		binDisplay.setAlignment(Pos.CENTER_RIGHT);
		decDisplay.setAlignment(Pos.CENTER_RIGHT);
		octDisplay.setAlignment(Pos.CENTER_RIGHT);
		hexDisplay.setAlignment(Pos.CENTER_RIGHT);		
		binDisplay.setPrefColumnCount(19);
		decDisplay.setPrefColumnCount(19);
		octDisplay.setPrefColumnCount(19);
		hexDisplay.setPrefColumnCount(19);
		binDisplay.setEditable(false);
		decDisplay.setEditable(false);
		octDisplay.setEditable(false);
		hexDisplay.setEditable(false);
		
		binButton=new ToggleButton("BIN");
		decButton=new ToggleButton("DEC");
		base='d';
		decButton.setSelected(true);
		octButton=new ToggleButton("OCT");
		hexButton=new ToggleButton("HEX");
		
		HBox hbox10=new HBox(9);
		hbox10.getChildren().addAll(binButton, binDisplay);		
		
		HBox hbox9=new HBox(9);
		hbox9.getChildren().addAll(decButton, decDisplay);		
		
		HBox hbox8=new HBox(9);
		hbox8.getChildren().addAll(octButton, octDisplay);		
		
		HBox hbox7=new HBox(9);
		hbox7.getChildren().addAll(hexButton, hexDisplay);		
		
		
		display=new TextField("");
		display.setFocusTraversable(false);
		display.setAlignment(Pos.CENTER_LEFT);
		display.setPrefColumnCount(8);
		display.setStyle("-fx-background-color: #E0E0E0;" + "-fx-font-size: 20px;" + "-fx-font-weight: bold;");
		buttons=new Button[36];		
		 
		HBox hbox6=new HBox(9);	
		buttons[35]=new Button("shL");
		buttons[34]=new Button("shR");
		buttons[33]=new Button("x\u00B2");
		buttons[32]=new Button("x\u207F");
		buttons[31]=new Button("del"); 
		buttons[30]=new Button("C");  
		hbox6.getChildren().addAll(buttons[35], buttons[34], buttons[33], buttons[32], buttons[31], buttons[30]);
		
		HBox hbox5=new HBox(9);			
		buttons[29]=new Button("asR");
		buttons[28]=new Button("and");  
		buttons[27]=new Button("or");
		buttons[26]=new Button("xor");
		buttons[25]=new Button("not");  
		buttons[24]=new Button("mod");  
		hbox5.getChildren().addAll(buttons[29], buttons[28], buttons[27], buttons[26], buttons[25], buttons[24]);
	    
		HBox hbox4=new HBox(9);
		buttons[23]=new Button("7");
		buttons[22]=new Button("8");
		buttons[21]=new Button("9");
		buttons[20]=new Button("F");
		buttons[19]=new Button("(");   
		buttons[18]=new Button(")"); 
		hbox4.getChildren().addAll(buttons[23], buttons[22], buttons[21], buttons[20], buttons[19], buttons[18]);
		
		HBox hbox3=new HBox(9);
		buttons[17]=new Button("4");
		buttons[16]=new Button("5");
		buttons[15]=new Button("6");
		buttons[14]=new Button("E");
		buttons[13]=new Button("*");	
		buttons[12]=new Button("/");
		hbox3.getChildren().addAll(buttons[17], buttons[16], buttons[15], buttons[14], buttons[13], buttons[12]);
		
		HBox hbox2=new HBox(9);
		buttons[11]=new Button("1");
		buttons[10]=new Button("2");
		buttons[9]=new Button("3");
		buttons[8]=new Button("D");
		buttons[7]=new Button("+");	   
		buttons[6]=new Button("-");
		hbox2.getChildren().addAll(buttons[11], buttons[10], buttons[9], buttons[8], buttons[7], buttons[6]);
		
		HBox hbox1=new HBox(9);
		buttons[5]=new Button("0");
		buttons[4]=new Button("A");
		buttons[3]=new Button("B");
		buttons[2]=new Button("C");
		buttons[1]=new Button("Ans");
		buttons[0]=new Button("=");
		hbox1.getChildren().addAll(buttons[5], buttons[4], buttons[3], buttons[2], buttons[1], buttons[0]);
				
		vbox.getChildren().addAll(hbox10, hbox9, hbox8, hbox7, display, hbox6, hbox5, hbox4, hbox3, hbox2, hbox1);
	    getChildren().add(vbox);
	    
	    
	    binButton.setPrefWidth(45);
	    octButton.setPrefWidth(45);
	    decButton.setPrefWidth(45);
	    hexButton.setPrefWidth(45);
	    binButton.getStylesheets().add(getClass().getResource("/calculators/toggle_button.css").toExternalForm()); 
	    decButton.getStylesheets().add(getClass().getResource("/calculators/toggle_button.css").toExternalForm()); 
	    octButton.getStylesheets().add(getClass().getResource("/calculators/toggle_button.css").toExternalForm()); 
	    hexButton.getStylesheets().add(getClass().getResource("/calculators/toggle_button.css").toExternalForm()); 
	    
	    
	    for(int i=0;i<36;++i){
	    	buttons[i].setPrefHeight(30);
	    	buttons[i].setPrefWidth(45);
    	
	    	if(i<24) buttons[i].getStylesheets().add(getClass().getResource("/calculators/button_cyan.css").toExternalForm());
	    	else{
	    		buttons[i].setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;");
	    		buttons[i].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm());
	    	}
	    }
	    
	    buttons[31].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm()); 
	    buttons[30].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm()); 	    
	    
	    buttons[1].setPrefHeight(32);
	    buttons[24].setStyle("-fx-font-size: 11px;" + "-fx-font-weight: bold;");	 
	    buttons[1].setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;");	 
	    buttons[0].getStylesheets().add(getClass().getResource("/calculators/button_red.css").toExternalForm());

		setDecKeyboard();
	}
	
	private void setListeners(){
		ButtonListener listener=new ButtonListener();
		
		for(int i=0;i<buttons.length;++i){
			buttons[i].setOnAction(listener);
		}	
		
		ToggleListener tListener=new ToggleListener();		
		binButton.setOnAction(tListener);
		decButton.setOnAction(tListener);
		octButton.setOnAction(tListener);
		hexButton.setOnAction(tListener);
	}
	
	
	private void calculate(){
		long result=0;

		try {
			Main.historyPane.append(display.getText()+" ,  "+base+"\n\n");
			
			result = Math.round(MathParser.eval(display.getText(), false, ans, base));
			
			ans=result;			
			if(base=='b'){
				display.setText(Long.toBinaryString(result));
				Main.historyPane.append("   ="+Long.toBinaryString(result)+"\n\n");
				
			}else if(base=='d'){
				display.setText(""+result);
				Main.historyPane.append("   ="+result+"\n\n");
				
			}else if(base=='o'){
				display.setText(Long.toOctalString(result));
				Main.historyPane.append("   ="+Long.toOctalString(result)+"\n\n");
			}
			else if(base=='h'){
				display.setText(Long.toHexString(result));
				Main.historyPane.append("   ="+Long.toHexString(result)+"\n\n");
			}
			
			binDisplay.setText(Long.toBinaryString(result));
			decDisplay.setText(""+result);
			octDisplay.setText(Long.toOctalString(result));
			hexDisplay.setText(Long.toHexString(result));			
			
		} catch (Exception e) {
			display.setText("Error.");
			binDisplay.setText("");
			decDisplay.setText("");
			octDisplay.setText("");
			hexDisplay.setText("");
			Main.historyPane.append("   = Error.\n\n");
		} 
	}
	
	
	private void setBinKeyboard(){
		buttons[10].setDisable(true); //Button("2");
		buttons[9].setDisable(true);  //Button("3");
		buttons[17].setDisable(true); //Button("4");
		buttons[16].setDisable(true); //Button("5");
		buttons[15].setDisable(true); //Button("6");
		buttons[23].setDisable(true); //Button("7");
		buttons[22].setDisable(true); //Button("8");
		buttons[21].setDisable(true); //Button("9");
		buttons[4].setDisable(true);  //Button("A");
		buttons[3].setDisable(true);  //Button("B");
		buttons[2].setDisable(true);  //Button("C");
		buttons[8].setDisable(true);  //Button("D");
		buttons[14].setDisable(true); //Button("E");
		buttons[20].setDisable(true); //Button("F");
	}
	
	private void setDecKeyboard(){
		buttons[10].setDisable(false); //Button("2");
		buttons[9].setDisable(false);  //Button("3");
		buttons[17].setDisable(false); //Button("4");
		buttons[16].setDisable(false); //Button("5");
		buttons[15].setDisable(false); //Button("6");
		buttons[23].setDisable(false); //Button("7");
		buttons[22].setDisable(false); //Button("8");
		buttons[21].setDisable(false); //Button("9");
		buttons[4].setDisable(true);  //Button("A");
		buttons[3].setDisable(true);  //Button("B");
		buttons[2].setDisable(true);  //Button("C");
		buttons[8].setDisable(true);  //Button("D");
		buttons[14].setDisable(true); //Button("E");
		buttons[20].setDisable(true); //Button("F");
	}
	
	private void setOctKeyboard(){
		buttons[10].setDisable(false); //Button("2");
		buttons[9].setDisable(false);  //Button("3");
		buttons[17].setDisable(false); //Button("4");
		buttons[16].setDisable(false); //Button("5");
		buttons[15].setDisable(false); //Button("6");
		buttons[23].setDisable(false); //Button("7");
		buttons[22].setDisable(true); //Button("8");
		buttons[21].setDisable(true); //Button("9");
		buttons[4].setDisable(true);  //Button("A");
		buttons[3].setDisable(true);  //Button("B");
		buttons[2].setDisable(true);  //Button("C");
		buttons[8].setDisable(true);  //Button("D");
		buttons[14].setDisable(true); //Button("E");
		buttons[20].setDisable(true); //Button("F");
	}
	
	private void setHexKeyboard(){
		buttons[10].setDisable(false); //Button("2");
		buttons[9].setDisable(false);  //Button("3");
		buttons[17].setDisable(false); //Button("4");
		buttons[16].setDisable(false); //Button("5");
		buttons[15].setDisable(false); //Button("6");
		buttons[23].setDisable(false); //Button("7");
		buttons[22].setDisable(false); //Button("8");
		buttons[21].setDisable(false); //Button("9");
		buttons[4].setDisable(false);  //Button("A");
		buttons[3].setDisable(false);  //Button("B");
		buttons[2].setDisable(false);  //Button("C");
		buttons[8].setDisable(false);  //Button("D");
		buttons[14].setDisable(false); //Button("E");
		buttons[20].setDisable(false); //Button("F");
	}
	
	
	private class ToggleListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent arg0) {
			ToggleButton btn=(ToggleButton)arg0.getSource();
			
			if(btn==binButton){
				base='b';
				octButton.setSelected(false);
				decButton.setSelected(false);
				hexButton.setSelected(false);
				binButton.setSelected(true);
				setBinKeyboard();
				
			}else if(btn==decButton){
				base='d';
				octButton.setSelected(false);
				binButton.setSelected(false);
				hexButton.setSelected(false);
				decButton.setSelected(true);
				setDecKeyboard();
				
			}else if(btn==octButton){
				base='o';
				binButton.setSelected(false);
				decButton.setSelected(false);
				hexButton.setSelected(false);
				octButton.setSelected(true);
				setOctKeyboard();
				
			}else if(btn==hexButton){
				base='h';
				octButton.setSelected(false);
				decButton.setSelected(false);
				binButton.setSelected(false);
				hexButton.setSelected(true);	
				setHexKeyboard();
			}
			
			display.setText("");
			display.setText("");
			binDisplay.setText("");
			decDisplay.setText("");
			octDisplay.setText("");
			hexDisplay.setText("");
			ans=0;
		}
		
	}
	
	private class ButtonListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			Button btn=(Button)event.getSource();
			
			if(btn==buttons[0]) calculate();
			else if(btn==buttons[1]) display.appendText("Ans");
			else if(btn==buttons[2]) display.appendText("C");
			else if(btn==buttons[3]) display.appendText("B");
			else if(btn==buttons[4]) display.appendText("A");
			else if(btn==buttons[5]) display.appendText("0");
			else if(btn==buttons[6]) display.appendText("-");
			else if(btn==buttons[7]) display.appendText("+");
			else if(btn==buttons[8]) display.appendText("D");
			else if(btn==buttons[9]) display.appendText("3");
			else if(btn==buttons[10]) display.appendText("2");
			else if(btn==buttons[11]) display.appendText("1");
			else if(btn==buttons[12]) display.appendText("/");
			else if(btn==buttons[13]) display.appendText("*");
			else if(btn==buttons[14]) display.appendText("E");
			else if(btn==buttons[15]) display.appendText("6");
			else if(btn==buttons[16]) display.appendText("5");
			else if(btn==buttons[17]) display.appendText("4");
			else if(btn==buttons[18]) display.appendText(")");
			else if(btn==buttons[19]) display.appendText("(");
			else if(btn==buttons[20]) display.appendText("F");
			else if(btn==buttons[21]) display.appendText("9");
			else if(btn==buttons[22]) display.appendText("8");	
			else if(btn==buttons[23]) display.appendText("7");	
			else if(btn==buttons[24]) display.appendText("%");
			else if(btn==buttons[25]) display.appendText("not(");	
			else if(btn==buttons[26]) display.appendText("xor");	
			else if(btn==buttons[27]) display.appendText("or");	
			else if(btn==buttons[28]) display.appendText("and");	
			else if(btn==buttons[29]) display.appendText(">>>");	
			else if(btn==buttons[30]) {
				display.setText("");
				binDisplay.setText("");
				decDisplay.setText("");
				octDisplay.setText("");
				hexDisplay.setText("");
			}
			else if(btn==buttons[31]) {
				String text=display.getText();
				if(text.equals("")) return;
				
				text=text.substring(0, text.length()-1);
				display.setText(text);
				display.selectPositionCaret(text.length());
			}
			else if(btn==buttons[32]) display.appendText("^");	
			else if(btn==buttons[33]) {
				if(base=='b') display.appendText("^(10)");	
				else display.appendText("^2");	
			}
			else if(btn==buttons[34]) display.appendText(">>");
			else if(btn==buttons[35]) display.appendText("<<");			
		}
	}

}
