package application;

import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

public class HelpPane extends Pane{
	private TextArea textArea;
	
	public HelpPane(){		
		textArea=new TextArea("\u221Ax     <--->  sqrt(x)\n"+
							  "sin x       <--->  sin(x)\n"+
				              "cos, tan, asin, acos, atan,\n"+
							  "fact, abs, not, ln, log...\n"+
							  "log\u2093a  <--->  nlog(x, a)\n"+
							  "\u207F\u221Ax    <--->  root(n, x)");
		
		textArea.setPrefRowCount(8);
		textArea.setPrefColumnCount(15);
		textArea.setStyle("-fx-font-size: 16px;");
		textArea.setEditable(false);
		getChildren().add(textArea);
	}

}
