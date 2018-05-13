package application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

@SuppressWarnings("serial")
public class JNotepad extends JFrame{
	private Dimension screen;
	private String path;
	private int drty=0;
	
	private JPanel controls;
	private JMenuBar menu;
	private JToolBar tools;
	private JMenuItem menuUndo;
	private JMenuItem menuRedo;
	private JCheckBoxMenuItem menuIndex;
	private JButton undoButton;
	private JButton redoButton;
	private JButton showIndex;
	Set<String> recentItems;
	
	private JPanel textPanel;
	public static JTextArea index;
	private JScrollPane scroll;
	public static JTextArea text;
	private UndoManager manager;

	
	
	public JNotepad(){
		path=new String(File.listRoots()[0].getAbsolutePath());
		controls=new JPanel();
		menu=new JMenuBar();
		tools=new JToolBar();
		recentItems=new HashSet<String>();
		
		textPanel=new JPanel();
		index=new JTextArea();
		scroll=new JScrollPane();
		text=new JTextArea("");
		manager=new UndoManager();
		
		
		ImageIcon ic=null;
		try {
			ic=new ImageIcon(ImageIO.read(getClass().getResource("/icons/icon.png")));
			setIconImage(ic.getImage());
		} catch (Exception e2) {}
		
		screen=Toolkit.getDefaultToolkit().getScreenSize();
		setTitle("Java Notepad");
		setSize((int)screen.getWidth()/2, (int)screen.getHeight()/2);
		setLocation((int)screen.getWidth()/4, (int)screen.getHeight()/4);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent evt){
				exitAction();
			}
		});
		
		
		addMenuToolBarTextArea();
		menu.add(getFileMenu());
		menu.add(getEditMenu());
		menu.add(getSettingsMenu());
		
		try {
			getTools(tools);
		} catch (Exception e1) {
			System.out.println("No icons");
		}
		
		text.getDocument().addUndoableEditListener(manager);
		try {   
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");   
		} catch (Exception e) {}
		SwingUtilities.updateComponentTreeUI(JNotepad.this);
	}

	private void addMenuToolBarTextArea(){
		add(controls, BorderLayout.NORTH);
		controls.setLayout(new BorderLayout());
		controls.setPreferredSize(new Dimension(100, 60));
		controls.add(menu, BorderLayout.NORTH);	
		controls.add(tools, BorderLayout.CENTER);
		
		textPanel.setLayout(new BorderLayout());
		add(textPanel, BorderLayout.CENTER);
		add(scroll, BorderLayout.CENTER);  	
				
		textPanel.add(text, BorderLayout.CENTER);
		text.setPreferredSize(screen);	
		text.setForeground(Color.BLACK);
		scroll.getViewport().add(textPanel);
		
		index.setForeground(Color.RED);
		setIndex();
		textPanel.add(index, BorderLayout.WEST);	
	}
	
	//MENU
	private JMenu getFileMenu(){
		JMenu menuFile=new JMenu("File");
		
		JMenuItem menuNew=new JMenuItem("New");
		JMenuItem menuSave=new JMenuItem("Save");
		JMenuItem menuSaveAs=new JMenuItem("Save As");
		JMenuItem menuOpen=new JMenuItem("Open");
		JMenu recent=new JMenu("Recent");
		JMenuItem menuExit=new JMenuItem("Exit");
		
		//NEW
		menuNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newAction();				
			}			
		});
		menuFile.add(menuNew);

		
		//SAVE
		menuSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveAction();
			}			
		});	
		menuFile.add(menuSave);
		
		//SAVE ASS
		menuSaveAs.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveasAction();
			}			
		});	
		menuFile.add(menuSaveAs);
		
		//OPEN
		menuOpen.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				openAction();
			}			
		});	
		menuFile.add(menuOpen);
		
		//REECENT
		menuFile.add(recent);
		
		//EXIT
		menuExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitAction();			
			}			
		});	
		menuFile.add(menuExit);	
		return menuFile;
	}
	
	private JMenu getEditMenu(){		
        JMenu menuEdit=new JMenu("Edit");   
        
        menuUndo=new JMenuItem("Undo");
        menuRedo=new JMenuItem("Redo");
        menuUndo.setEnabled(false);
        menuRedo.setEnabled(false);
        
		
		//UNDO		
		menuUndo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				undoAction();
			}			
		});	
		menuEdit.add(menuUndo);
		
		//REDO		
		menuRedo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				redoAction();
			}			
		});	
		menuEdit.add(menuRedo);
		
		text.getDocument().addUndoableEditListener(new UndoableEditListener(){
			@Override
			public void undoableEditHappened(UndoableEditEvent e){
				menuUndo.setEnabled(true);
				menuRedo.setEnabled(false);
				undoButton.setEnabled(true);
				redoButton.setEnabled(false);
				setIndex();
				drty=1;
			}
		});
		
		return menuEdit;
	}
	
	private JMenu getSettingsMenu(){
		JMenu menuSettings=new JMenu("Settings");
		
		JMenu menuPersonalize=new JMenu("Personalize");
		menuSettings.add(menuPersonalize);
		
		LookAndFeelInfo[] themes=UIManager.getInstalledLookAndFeels();
		ButtonGroup group=new ButtonGroup();
		
		for(LookAndFeelInfo l: themes){
			JRadioButtonMenuItem radioButton=new JRadioButtonMenuItem(l.getName());
			menuPersonalize.add(radioButton);
			group.add(radioButton);
			radioButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						UIManager.setLookAndFeel(l.getClassName());
						SwingUtilities.updateComponentTreeUI(JNotepad.this);
					}catch(Exception e){}
				}				
			});
		}
			
		JMenuItem menuFont=new JMenuItem("Font");
		menuSettings.add(menuFont);
		menuFont.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeFont changeFrame=new ChangeFont(text.getFont(), text.getBackground(), text.getForeground(), index.getBackground(), index.getForeground());	
				SwingUtilities.invokeLater(changeFrame);
			}			
		});		
		
		menuIndex=new JCheckBoxMenuItem("Index");
		menuIndex.setSelected(true);
		menuSettings.add(menuIndex);
		menuIndex.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				switchIndexAction();
			}		
		});	
		
		return menuSettings;
	}
	

	//TOOLS BUTTONS
	private void getTools(JToolBar toolBar) throws Exception{
		JButton newButton=new JButton();
		newButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/new.png"))));
		newButton.setToolTipText("New");
		newButton.addActionListener(e->{
			newAction();
		});
		toolBar.add(newButton);
		
		JButton openButton=new JButton();
		openButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/open.png"))));
		openButton.setToolTipText("Open");
		openButton.addActionListener(e->{
			openAction();
		});
		toolBar.add(openButton);
		
		JButton saveButton=new JButton();
		saveButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/save.png"))));
		saveButton.setToolTipText("Save");
		saveButton.addActionListener(e->{
			saveAction();
		});
		toolBar.add(saveButton);
		
		JButton saveasButton=new JButton();
		saveasButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/saveas.png"))));
		saveasButton.setToolTipText("Save As");
		saveasButton.addActionListener(e->{
			saveasAction();
		});
		toolBar.add(saveasButton);

		undoButton=new JButton();
		undoButton.setEnabled(false);
		undoButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/back.png"))));
		undoButton.setToolTipText("Undo");
		undoButton.addActionListener(e->{
			undoAction();
		});
		toolBar.add(undoButton);
		
		redoButton=new JButton();
		redoButton.setEnabled(false);
		redoButton.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/forward.png"))));
		redoButton.setToolTipText("Redo");
		redoButton.addActionListener(e->{
			redoAction();
		});
		toolBar.add(redoButton);
		
		showIndex=new JButton();
		showIndex.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/icons/index.png"))));
		showIndex.setToolTipText("Index");
		showIndex.addActionListener(e->{
			if(menuIndex.isSelected()) menuIndex.setSelected(false);
			else menuIndex.setSelected(true);
			switchIndexAction();
		});
		toolBar.add(showIndex);
	}
	
	
	public static void main(String[] args){
        EventQueue.invokeLater(new Runnable(){
			@Override
			public void run() {
				JNotepad window=new JNotepad();
				window.setVisible(true);
			}        	
        });
	}
	
	private void setIndex(){
		index.setText("");
		for(int i=0;i<text.getLineCount();++i){
			index.setText(index.getText()+String.format("%3d:  \n", i));
		}
		index.setEditable(false);
	}
	
	private void writeFile(File file){
		FileOutputStream output=null;
		try {
			output=new FileOutputStream(file);
		} catch (FileNotFoundException e) {}
		
		String outputText=text.getText();
		try {
			output.write(outputText.getBytes());
		} catch (IOException e) {}		
		
	}
	
	private int askForSave(){
		Object[] options = {"Yes", "No", "Cancel"};
		int n = JOptionPane.showOptionDialog(this, "Do you want to save ?", "Save", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,  options[0]);	
		
		if(n==0){
			File file=new File(path);
			if(file.isDirectory()){
				JFileChooser fileChooser=new JFileChooser();
				int value=fileChooser.showSaveDialog(null);
				if(value==JFileChooser.APPROVE_OPTION) {
					file=fileChooser.getSelectedFile();
					path=file.getAbsolutePath();	
					writeFile(file);
					drty=0;
				}
			}else{
				writeFile(file);
				drty=0;
			}
		}	
		
		return n;
	}
	
	private void addToRecent(File recentFile){
		JMenu menuFile=menu.getMenu(0);
		JMenuItem recentMenu=menuFile.getItem(4);
		if(!recentItems.contains(recentFile.getName())){
			recentItems.add(recentFile.getName());
			
			JMenuItem item=new JMenuItem(recentFile.getName());
			if(recentMenu==null) recentMenu=new JMenu(); 
			recentMenu.add(item);
			
			item.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {			
				    path=recentFile.getAbsolutePath();
				    BufferedReader reader=null;
				    
				    try {
					    reader = new BufferedReader(new FileReader(recentFile));
				    } catch (FileNotFoundException e1) {}
				
				    text.setText("");
				    String readLine;
				    try {
					    while((readLine=reader.readLine())!=null){
						    text.setText(text.getText()+readLine+"\n");
					    }
				    } catch (IOException e2) {}
				}				
			});
			recentMenu.revalidate();
		}
	}
	
	private void newAction(){
		int valueOption=0;
		if(drty==1) valueOption=askForSave();
		if(valueOption==2) return;
		File file=null;
		JFileChooser fileChooser=new JFileChooser();
		int value=fileChooser.showSaveDialog(null);
		if(value==JFileChooser.APPROVE_OPTION) {
			file=fileChooser.getSelectedFile();
			path=file.getAbsolutePath();
            text.setText("");
		}	
	}

	private void openAction(){
		int valueOption=0;
		if(drty==1) valueOption=askForSave();
		if(valueOption==2) return;
		
		File file=null;
		JFileChooser fileChooser=new JFileChooser();				
		int value=fileChooser.showOpenDialog(null);
		if(value==JFileChooser.APPROVE_OPTION){
			file=fileChooser.getSelectedFile();				
		    path=file.getAbsolutePath();
		    addToRecent(file);
		    BufferedReader reader=null;
		    
		    try {
			    reader = new BufferedReader(new FileReader(file));
		    } catch (FileNotFoundException e1) {}
		
		    text.setText("");
		    String readLine;
		    try {
			    while((readLine=reader.readLine())!=null){
				    text.setText(text.getText()+readLine+"\n");
			    }
		    } catch (IOException e) {}
		}
	}
	
	private void saveAction(){
		File file=new File(path);
		if(file.isDirectory()){
			JFileChooser fileChooser=new JFileChooser();
			int value=fileChooser.showSaveDialog(null);
			if(value==JFileChooser.APPROVE_OPTION) {
				file=fileChooser.getSelectedFile();
				path=file.getAbsolutePath();	
				writeFile(file);
				drty=0;
			}
		}else{
			writeFile(file);
			drty=0;
		}
	}
	
	private void saveasAction(){
		File file=null;
		JFileChooser fileChooser=new JFileChooser();
		int value=fileChooser.showSaveDialog(null);
		if(value==JFileChooser.APPROVE_OPTION) {
			file=fileChooser.getSelectedFile();
			path=file.getAbsolutePath();
			writeFile(file);
			drty=0;
		}	
	}
	
	private void exitAction(){
		int value=0;
		if(drty==1) value=askForSave();
		if(value==0 || value==1) System.exit(0);
	}
	
	private void undoAction(){
		manager.undo();
		if(!manager.canUndo()){
			menuUndo.setEnabled(false);
			undoButton.setEnabled(false);
		}
		menuRedo.setEnabled(true);
		redoButton.setEnabled(true);
		setIndex();
	}
	
	private void redoAction(){
		manager.redo();
		if(!manager.canRedo()){
			menuRedo.setEnabled(false);
			redoButton.setEnabled(false);
		}
		menuUndo.setEnabled(true);	
		undoButton.setEnabled(true);
		setIndex();
	}
	
	private void switchIndexAction(){		
		if(menuIndex.isSelected()) index.setVisible(true);
		else index.setVisible(false);
	}
	
}