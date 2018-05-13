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

public class Basic extends Pane{
	private TextField display;
	private Button[] buttons;
	private ButtonListener listener;
	
	private double ans=0;
	
	public Basic(){
		initGUI();
		setListeners();	
	}	
	
	private void initGUI(){
		VBox vbox=new VBox(9);	
		vbox.setPadding(new Insets(5));
		display=new TextField("");
		display.setFocusTraversable(false);
		display.setEditable(false);
		display.setAlignment(Pos.CENTER_LEFT);
		display.setPrefColumnCount(8);
		display.setStyle("-fx-background-color: #E0E0E0;" + "-fx-font-size: 20px;" + "-fx-font-weight: bold;");
		buttons=new Button[23];
		 
		HBox hbox1=new HBox(9);	
		buttons[0]=new Button("\u221A");
		buttons[1]=new Button("Ans");
		buttons[2]=new Button("del");
		buttons[3]=new Button("C"); 
		hbox1.getChildren().addAll(buttons[0], buttons[1], buttons[2], buttons[3]);
		
		HBox hbox2=new HBox(9);	
		buttons[4]=new Button("x\u207F");
		buttons[5]=new Button("(");
		buttons[6]=new Button(")");
		buttons[7]=new Button("/");  
		hbox2.getChildren().addAll(buttons[4], buttons[5], buttons[6], buttons[7]);
	    
		HBox hbox3=new HBox(9);
		buttons[8]=new Button("7");
		buttons[9]=new Button("8");
		buttons[10]=new Button("9");
		buttons[11]=new Button("*");   
		hbox3.getChildren().addAll(buttons[8], buttons[9], buttons[10], buttons[11]);
		
		HBox hbox4=new HBox(9);
		buttons[12]=new Button("4");
		buttons[13]=new Button("5");
		buttons[14]=new Button("6");
		buttons[15]=new Button("-");	   
		hbox4.getChildren().addAll(buttons[12], buttons[13], buttons[14], buttons[15]);
		
		HBox hbox5=new HBox(9);
		buttons[16]=new Button("1");
		buttons[17]=new Button("2");
		buttons[18]=new Button("3");
		buttons[19]=new Button("+");	   
		hbox5.getChildren().addAll(buttons[16], buttons[17], buttons[18], buttons[19]);
		
		HBox hbox6=new HBox(9);
		buttons[20]=new Button("0");
		buttons[21]=new Button(",");
		buttons[22]=new Button("=");
		hbox6.getChildren().addAll(buttons[20], buttons[21], buttons[22]);
				
		vbox.getChildren().addAll(display, hbox1, hbox2, hbox3, hbox4, hbox5, hbox6);
	    getChildren().add(vbox);	    
	    
	    for(int i=0;i<23;++i){
	    	buttons[i].setPrefHeight(30);
	    	buttons[i].setPrefWidth(45);
	    	buttons[i].getStylesheets().add(getClass().getResource("/calculators/button_cyan.css").toExternalForm());
	    }
	    buttons[20].setPrefWidth(99);
	    buttons[1].setPrefHeight(32);
	    buttons[2].setPrefHeight(32);
	    
	    buttons[1].setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;");
	    buttons[2].setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;");
	    
	    buttons[0].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm()); 
	    buttons[1].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm()); 
	    buttons[2].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm()); 
	    buttons[3].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm()); 
	    
	    buttons[4].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[5].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[6].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[7].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[11].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[15].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[19].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    
	    buttons[22].getStylesheets().add(getClass().getResource("/calculators/button_red.css").toExternalForm()); 
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
			
			result = MathParser.eval(display.getText(), false, ans, 'd');
			result=MathParser.round(""+result, 10);
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
			
			if(btn==buttons[0]) display.appendText("\u221A(");
			else if(btn==buttons[1]) display.appendText("Ans");
			else if(btn==buttons[2]){
				String text=display.getText();
				if(text.equals("")) return;
				
				text=text.substring(0, text.length()-1);
				display.setText(text);
				display.selectPositionCaret(text.length());
			}
			else if(btn==buttons[3]) display.setText("");
			else if(btn==buttons[4]) display.appendText("^(");
			else if(btn==buttons[5]) display.appendText("(");
			else if(btn==buttons[6]) display.appendText(")");
			else if(btn==buttons[7]) display.appendText("/");
			else if(btn==buttons[8]) display.appendText("7");
			else if(btn==buttons[9]) display.appendText("8");
			else if(btn==buttons[10]) display.appendText("9");
			else if(btn==buttons[11]) display.appendText("*");
			else if(btn==buttons[12]) display.appendText("4");
			else if(btn==buttons[13]) display.appendText("5");
			else if(btn==buttons[14]) display.appendText("6");
			else if(btn==buttons[15]) display.appendText("-");
			else if(btn==buttons[16]) display.appendText("1");
			else if(btn==buttons[17]) display.appendText("2");
			else if(btn==buttons[18]) display.appendText("3");
			else if(btn==buttons[19]) display.appendText("+");
			else if(btn==buttons[20]) display.appendText("0");
			else if(btn==buttons[21]) display.appendText(".");
			else if(btn==buttons[22]) calculate();		
		}
	}

}
