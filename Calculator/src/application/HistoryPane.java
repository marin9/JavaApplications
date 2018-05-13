package application;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class HistoryPane extends Pane{
	private TextArea textArea;
	
	public HistoryPane(){		
		textArea=new TextArea("");
		textArea.setPrefRowCount(19);
		textArea.setEditable(false);
		getChildren().add(textArea);
	}
	
	public void clear(){
		textArea.setText("");
		requestLayout();
	}
	
	public void append(String text){
		textArea.appendText(text);
		requestLayout();
	}
}
