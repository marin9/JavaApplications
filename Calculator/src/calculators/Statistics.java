package calculators;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Statistics extends Pane{
	private ListView<Double> list;
	private ObservableList<Double> data;	
	private TextField display;
	private Button[] buttons;
	private ButtonListener listener;
	
	private int index=-1;
	
	public Statistics(){
		initGUI();
		setListeners();	
	}	
	
	private void initGUI(){
		data=FXCollections.observableArrayList();
		list=new ListView<Double>(data);
		list.setPrefHeight(180);		

		VBox vbox=new VBox(9);	
		vbox.setPadding(new Insets(5));
		display=new TextField("");
		display.setFocusTraversable(false);
		display.setEditable(false);
		display.setAlignment(Pos.CENTER_LEFT);
		display.setPrefColumnCount(8);
		display.setStyle("-fx-background-color: #E0E0E0;" + "-fx-font-size: 20px;" + "-fx-font-weight: bold;");
		buttons=new Button[24];
		 
		
		HBox hbox0=new HBox(9);	
		buttons[0]=new Button("add");
		buttons[1]=new Button("clr");
		buttons[2]=new Button("rm");
		buttons[3]=new Button("del");  
		buttons[4]=new Button("C");  
		hbox0.getChildren().addAll(buttons[0], buttons[1], buttons[2], buttons[3], buttons[4]);
	    
		HBox hbox1=new HBox(9);
		buttons[5]=new Button("7");
		buttons[6]=new Button("8");
		buttons[7]=new Button("9");
		buttons[8]=new Button("\u21D1");   
		buttons[9]=new Button("\u0101");  
		hbox1.getChildren().addAll(buttons[5], buttons[6], buttons[7], buttons[8], buttons[9]);
		
		HBox hbox2=new HBox(9);
		buttons[10]=new Button("4");
		buttons[11]=new Button("5");
		buttons[12]=new Button("6");
		buttons[13]=new Button("\u21D3");
		buttons[14]=new Button("\u0101\u00B2");
		hbox2.getChildren().addAll(buttons[10], buttons[11], buttons[12], buttons[13], buttons[14]);
		
		HBox hbox3=new HBox(9);
		buttons[15]=new Button("1");
		buttons[16]=new Button("2");
		buttons[17]=new Button("3");
		buttons[18]=new Button("\u2211x");
		buttons[19]=new Button("\u2211x\u00B2");  
		hbox3.getChildren().addAll(buttons[15], buttons[16], buttons[17], buttons[18], buttons[19]);
		
		HBox hbox4=new HBox(9);
		buttons[20]=new Button("0");
		buttons[21]=new Button(",");
		buttons[22]=new Button("Exp");
		buttons[23]=new Button("\u03C3");
		hbox4.getChildren().addAll(buttons[20], buttons[21], buttons[22], buttons[23]);
				
		vbox.getChildren().addAll(list, display,  hbox0, hbox1, hbox2, hbox3, hbox4);
	    getChildren().add(vbox);	    
	    
	    for(int i=0;i<24;++i){
	    	buttons[i].setPrefHeight(30);
	    	buttons[i].setPrefWidth(45);
	    	buttons[i].getStylesheets().add(getClass().getResource("/calculators/button_cyan.css").toExternalForm());
	    }
	    buttons[20].setPrefWidth(99);
	    
	    buttons[0].setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;");
	    buttons[1].setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;");
	    buttons[2].setStyle("-fx-font-size: 13px;" + "-fx-font-weight: bold;");
	    buttons[3].setStyle("-fx-font-size: 14px;" + "-fx-font-weight: bold;");
	    buttons[4].setStyle("-fx-font-size: 14px;" + "-fx-font-weight: bold;");
	    buttons[18].setStyle("-fx-font-size: 14px;" + "-fx-font-weight: bold;");
	    buttons[19].setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;");
	    buttons[22].setStyle("-fx-font-size: 12px;" + "-fx-font-weight: bold;");
	    buttons[23].setStyle("-fx-font-size: 15px;" + "-fx-font-weight: bold;");
	    
	    buttons[0].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm()); 
	    buttons[1].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm()); 
	    buttons[2].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm()); 
	    buttons[3].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm()); 	    
	    buttons[4].getStylesheets().add(getClass().getResource("/calculators/button_grey.css").toExternalForm());
	    
	    buttons[8].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[9].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[13].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[14].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[18].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[19].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[22].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    buttons[23].getStylesheets().add(getClass().getResource("/calculators/button_green.css").toExternalForm()); 
	    
	}
		
	private void setListeners(){
		listener=new ButtonListener();
		
		for(int i=0;i<buttons.length;++i){
			buttons[i].setOnAction(listener);
		}		
	}
	
	
	private class ButtonListener implements EventHandler<ActionEvent>{

		@Override
		public void handle(ActionEvent event) {
			Button btn=(Button)event.getSource();
			
			if(btn==buttons[0]) {
				data.add(Double.parseDouble(display.getText()));
				++index;
				display.setText("");
				list.getSelectionModel().select(data.size()-1);
			}
			else if(btn==buttons[1]) {
				index=-1;
				data.clear();
				display.setText("");
			}
			else if(btn==buttons[2]) {
				index=list.getSelectionModel().getSelectedIndex()-1;
				if(index<0){
					index=-1;
					return;
				}
				data.remove(index+1);
			}
			else if(btn==buttons[3]) {
				String text=display.getText();
				if(text.equals("")) return;
				
				text=text.substring(0, text.length()-1);
				display.setText(text);
				display.selectPositionCaret(text.length());
			}
			else if(btn==buttons[4]) {
				display.setText("");
			}
			else if(btn==buttons[5]) display.appendText("7");
			else if(btn==buttons[6]) display.appendText("8");
			else if(btn==buttons[7]) display.appendText("9");
			else if(btn==buttons[8]) {
				index=list.getSelectionModel().getSelectedIndex()-1;
				if(index<0){
					index=-1;
					return;
				}else{
					list.getSelectionModel().select(index);
				}
			}
			else if(btn==buttons[9]) {
				double s=0;
				for(int i=0;i<data.size();++i){
					s=s+data.get(i);
				}
				s=s/data.size();
				display.setText(""+s);
			}
			else if(btn==buttons[10]) display.appendText("4");
			else if(btn==buttons[11]) display.appendText("5");
			else if(btn==buttons[12]) display.appendText("6");
			else if(btn==buttons[13]) {
				index=list.getSelectionModel().getSelectedIndex()+1;
				if(index>=data.size()){
					index=data.size()-1;
					return;
				}else{
					list.getSelectionModel().select(index);
				}
			}
			else if(btn==buttons[14]) {
				double s=0;
				for(int i=0;i<data.size();++i){
					s=s+data.get(i)*data.get(i);
				}
				s=s/data.size();
				display.setText(""+s);
			}
			else if(btn==buttons[15]) display.appendText("1");
			else if(btn==buttons[16]) display.appendText("2");
			else if(btn==buttons[17]) display.appendText("3");
			else if(btn==buttons[18]) {
				double s=0;
				for(int i=0;i<data.size();++i){
					s=s+data.get(i);
				}
				display.setText(""+s);
			}
			else if(btn==buttons[19]) {
				double s=0;
				for(int i=0;i<data.size();++i){
					s=s+data.get(i)*data.get(i);
				}
				display.setText(""+s);
			}
			else if(btn==buttons[20]) display.appendText("0");
			else if(btn==buttons[21]) display.appendText(".");
			else if(btn==buttons[22]) display.appendText("E");	
			else if(btn==buttons[23]) {
				double s=0, a=0;
				
				for(int i=0;i<data.size();++i){
					a=a+data.get(i);
				}
				a=a/data.size();
				
				for(int i=0;i<data.size();++i){
					s=s+(data.get(i)-a)*(data.get(i)-a);
				}
				
				s=Math.sqrt(s/data.size());
				display.setText(""+s);
			}
		}
	}

}
