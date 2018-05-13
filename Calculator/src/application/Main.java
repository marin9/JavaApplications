package application;

import calculators.Basic;
import calculators.Programmer;
import calculators.Scientific;
import calculators.Statistics;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;


public class Main extends Application {
	private Stage stageMain, historyStage, helpStage;
	
	private VBox pane;
	private MenuBar menuBar;
	private Basic basic;
	private Scientific scientific;
	private Programmer programmer;
	private Statistics statistics;
	
	public static HistoryPane historyPane;
	public static HelpPane helpPane;
	

	@Override
	public void start(Stage primaryStage) {
		stageMain=primaryStage;		
		stageMain.setTitle("M Calc");
		stageMain.getIcons().add(new Image(""+getClass().getResource("/xc_icon.png")));
		stageMain.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent arg0) {
				helpStage.close();
				historyStage.close();
				stageMain.close();				
				System.exit(0);
			}			
		});		
		
		historyPane=new HistoryPane();
		helpPane=new HelpPane();
		
		historyStage=new Stage();
		historyStage.setTitle("History");
		historyStage.setScene(new Scene(historyPane));
		historyStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent arg0) {
				historyStage.hide();
			}        	
	    });
		historyStage.sizeToScene();
		historyStage.setResizable(false);
		
		helpStage=new Stage();
		helpStage.setTitle("Help");
		helpStage.setScene(new Scene(helpPane));
		helpStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent arg0) {
				helpStage.hide();
			}        	
	    });
		helpStage.sizeToScene();
		helpStage.setResizable(false);
		
		pane=new VBox(5);
		pane.setStyle("-fx-background-color: #323232");
		addMenu();		
		basic=new Basic();	
		scientific=new Scientific();
		programmer=new Programmer();
		statistics=new Statistics();

		pane.getChildren().add(basic);
				
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	
	private void addMenu(){
		menuBar=new MenuBar();
		menuBar.setStyle("-fx-background-color: linear-gradient(#A0A0A0, #707070);");
		pane.getChildren().add(menuBar);
		
		// View menu
		Menu viewMenu = new Menu("View");		
		ToggleGroup tGroup = new ToggleGroup();
	    RadioMenuItem tBasic = new RadioMenuItem("Basic");
	    RadioMenuItem tScientific = new RadioMenuItem("Scientific");
	    RadioMenuItem tProgrammer = new RadioMenuItem("Programmer");
	    RadioMenuItem tStatistics = new RadioMenuItem("Statistics");
	    tBasic.setToggleGroup(tGroup);
	    tScientific.setToggleGroup(tGroup);
	    tProgrammer.setToggleGroup(tGroup);
	    tStatistics.setToggleGroup(tGroup);
	    tBasic.setSelected(true);
	    
	    viewMenu.getItems().addAll(tBasic, tScientific, tProgrammer, tStatistics);  	    
	    menuBar.getMenus().addAll(viewMenu);
	    	    
	    // History menu
	    Menu historyMenu=new Menu("History");
	    MenuItem showHistory=new MenuItem("Show history");
	    MenuItem clearHistory=new MenuItem("Clear history");
	    
	    historyMenu.getItems().addAll(showHistory, clearHistory);  	    
	    menuBar.getMenus().addAll(historyMenu);
	    
	    // Help
	    Menu helpMenu=new Menu("Help");
	    MenuItem syntax=new MenuItem("Syntax");
	    MenuItem about=new MenuItem("About M Calc");
	    
	    helpMenu.getItems().addAll(syntax, about);
	    menuBar.getMenus().add(helpMenu);
	    
	    // Actions
	    tBasic.setOnAction(e->{  setBasicLayout(); });
	    tScientific.setOnAction(e->{  setScientificLayout(); });
	    tProgrammer.setOnAction(e->{  setProgrammerLayout(); });
	    tStatistics.setOnAction(e->{  setStatisticsLayout(); });	  
	    
	    showHistory.setOnAction(e->{ 
	    	historyStage.show();  
	    	historyStage.setX(stageMain.getX()+300);
	    	historyStage.setY(stageMain.getY());
	    });
	    clearHistory.setOnAction(e->{ historyPane.clear(); });
	    
	    syntax.setOnAction(e->{
	    	helpStage.show();  
	    	helpStage.setX(stageMain.getX()+300);
	    	helpStage.setY(stageMain.getY());
	    });
	    
	    about.setOnAction(e->{
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Info");
	    	alert.setHeaderText(null);
	    	alert.setContentText("marin.bralic  v1.0");
	    	alert.showAndWait();
	    });
	}
	
	private void setBasicLayout(){
		pane.getChildren().remove(1);
		pane.getChildren().add(basic);
		stageMain.sizeToScene();
	}
	
	private void setScientificLayout(){
		pane.getChildren().remove(1);
		pane.getChildren().add(scientific);
		stageMain.sizeToScene();
	}
	
	private void setProgrammerLayout(){
		pane.getChildren().remove(1);
		pane.getChildren().add(programmer);
		stageMain.sizeToScene();
	}

	private void setStatisticsLayout(){
		pane.getChildren().remove(1);
		pane.getChildren().add(statistics);
		stageMain.sizeToScene();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
