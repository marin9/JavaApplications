package application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class ChangeFont extends JFrame implements Runnable{
	private Color[] colors=new Color[]{Color.BLACK, Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.ORANGE, Color.RED, Color.PINK, Color.WHITE, Color.YELLOW};
	private String[] colorText=new String[]{"Black ", "Blue  ", "Cyan  ", "Gray  ", "Green ", "Orange", "Red   ", "Pink  ", "White ", "Yellow"};
	
	private JLabel sizeLabel;
	private JTextField fontNumber;
		
	private JLabel backColorLabel;
	private JLabel foregColorLabel;
	private JLabel indexBackColorLabel;
	private JLabel indexForegColorLabel;
	private JRadioButton[] buttons1;
	private JRadioButton[] buttons2;
	private JRadioButton[] buttons3;
	private JRadioButton[] buttons4;
	private ButtonGroup backGroup;
	private ButtonGroup foregGroup;
	private ButtonGroup backIndexGroup;
	private ButtonGroup foregIndexGroup;
		
	private JLabel fontTypeLabel;
	private JRadioButton[] buttonsFont;
	private ButtonGroup buttonsFontGroup;
	
	private JButton okButton;
	private JButton cancleButton;
		
	private Font currentFont;
	
	
	
	public ChangeFont(Font oldFont, Color background, Color foreground, Color indexBack, Color indexForeg){
		sizeLabel=new JLabel("Font size: ");
		fontNumber=new JTextField();
		backColorLabel=new JLabel("Text background: ");
		foregColorLabel=new JLabel("Text foreground: ");
		indexBackColorLabel=new JLabel("Index background: ");
		indexForegColorLabel=new JLabel("Index foreground: ");
		
		buttons1=new JRadioButton[10];
		buttons2=new JRadioButton[10];
		buttons3=new JRadioButton[10];
		buttons4=new JRadioButton[10];
		
		backGroup=new ButtonGroup();
		foregGroup=new ButtonGroup();
		backIndexGroup=new ButtonGroup();
		foregIndexGroup=new ButtonGroup();
		
		fontTypeLabel=new JLabel("Font type: ");
		buttonsFont=new JRadioButton[3];
		buttonsFontGroup=new ButtonGroup();
		okButton=new JButton("Ok");
		cancleButton=new JButton("Cancel");
		
		
		currentFont=oldFont;
		fontNumber.setText(""+oldFont.getSize());
	    for(int i=0;i<colors.length;++i){
	    	buttons1[i]=new JRadioButton();
	    	buttons2[i]=new JRadioButton();
	    	buttons3[i]=new JRadioButton();
	    	buttons4[i]=new JRadioButton();
	    }
	    
	    buttons1[8].setSelected(true);
	    buttons2[0].setSelected(true);
	    buttons3[8].setSelected(true);
	    buttons4[6].setSelected(true);		
	}

	
	
	@Override
	public void run() {
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		SpringLayout sp=new SpringLayout();
		
		setResizable(false);
		setTitle("Font and colors settings");
		setSize(650, 350);
		setLocation((int)screen.getWidth()/4, (int)screen.getHeight()/4);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		//SETTINGS OBJECTS
		setLayout(sp);
		add(sizeLabel);
		sp.putConstraint(SpringLayout.WEST, sizeLabel, 2, SpringLayout.EAST, this);
		
		fontNumber.setColumns(2);
		add(fontNumber);
		sp.putConstraint(SpringLayout.WEST, fontNumber, 5, SpringLayout.EAST, sizeLabel);
		
		add(backColorLabel);
		sp.putConstraint(SpringLayout.WEST, backColorLabel, 10, SpringLayout.EAST, fontNumber);
		
		add(foregColorLabel);
		sp.putConstraint(SpringLayout.WEST, foregColorLabel, 40, SpringLayout.EAST, backColorLabel);
		
		add(indexBackColorLabel);
		sp.putConstraint(SpringLayout.WEST, indexBackColorLabel, 40, SpringLayout.EAST, foregColorLabel);
		
		add(indexForegColorLabel);
		sp.putConstraint(SpringLayout.WEST, indexForegColorLabel, 40, SpringLayout.EAST, indexBackColorLabel);
		
		
		//COLOR BUTTONS
		int y=0;
		for(int i=0;i<colors.length;++i){
			 buttons1[i].setForeground(colors[i]); buttons1[i].setText(colorText[i]);
			 buttons2[i].setForeground(colors[i]); buttons2[i].setText(colorText[i]);
			 buttons3[i].setForeground(colors[i]); buttons3[i].setText(colorText[i]);
			 buttons4[i].setForeground(colors[i]); buttons4[i].setText(colorText[i]);
			 
		     backGroup.add(buttons1[i]);
		     foregGroup.add(buttons2[i]);
		     backIndexGroup.add(buttons3[i]);
		     foregIndexGroup.add(buttons4[i]);
		     
		     add(buttons1[i]);
		     sp.putConstraint(SpringLayout.NORTH, buttons1[i], (10+y), SpringLayout.SOUTH, sizeLabel);
		     sp.putConstraint(SpringLayout.WEST, buttons1[i], 130, SpringLayout.WEST, sizeLabel);
		     
		     add(buttons2[i]);
		     sp.putConstraint(SpringLayout.NORTH, buttons2[i], (10+y), SpringLayout.SOUTH, sizeLabel);
		     sp.putConstraint(SpringLayout.WEST, buttons2[i], 140, SpringLayout.WEST, buttons1[i]);
		     
		     add(buttons3[i]);
		     sp.putConstraint(SpringLayout.NORTH, buttons3[i], (10+y), SpringLayout.SOUTH, sizeLabel);
		     sp.putConstraint(SpringLayout.WEST, buttons3[i], 140, SpringLayout.WEST, buttons2[i]);
		     
		     add(buttons4[i]);
		     sp.putConstraint(SpringLayout.NORTH, buttons4[i], (10+y), SpringLayout.SOUTH, sizeLabel);
		     sp.putConstraint(SpringLayout.WEST, buttons4[i], 140, SpringLayout.WEST, buttons3[i]);
		     
		     y=y+20;
		}
		 
		//TEXT FONT
		add(fontTypeLabel);
		sp.putConstraint(SpringLayout.NORTH, fontTypeLabel, 230, SpringLayout.SOUTH, sizeLabel);
		sp.putConstraint(SpringLayout.WEST, fontTypeLabel, 2, SpringLayout.EAST, this);
		y=70;
		for(int i=0;i<buttonsFont.length;++i){
			buttonsFont[i]=new JRadioButton();
			buttonsFontGroup.add(buttonsFont[i]);
			add(buttonsFont[i]);
		    sp.putConstraint(SpringLayout.NORTH, buttonsFont[i], 230, SpringLayout.SOUTH, sizeLabel);
		    sp.putConstraint(SpringLayout.WEST, buttonsFont[i], y, SpringLayout.EAST, this);
		    y=y+70;  
		}
		buttonsFont[0].setText("Normal");
		buttonsFont[1].setText("Italic");
		buttonsFont[2].setText("Bold");
		if(currentFont.getStyle()==1) buttonsFont[2].setSelected(true);
		else if(currentFont.getStyle()==2) buttonsFont[1].setSelected(true);
		else buttonsFont[0].setSelected(true);
		
		
		add(okButton);
		sp.putConstraint(SpringLayout.NORTH, okButton, 270, SpringLayout.SOUTH, sizeLabel);
	    sp.putConstraint(SpringLayout.WEST, okButton, 150, SpringLayout.EAST, sizeLabel);
	    okButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {	
				JNotepad.text.setBackground(setColorBackground());
				JNotepad.text.setForeground(setColorForeground());
				JNotepad.index.setBackground(setColorIndexBackground());
				JNotepad.index.setForeground(setColorIndexForeground());
				Font font=setFont();
				JNotepad.text.setFont(font);
				JNotepad.index.setFont(font);
			   	ChangeFont.this.dispose();
			}
	    });
	     
		add(cancleButton);
		sp.putConstraint(SpringLayout.NORTH, cancleButton, 270, SpringLayout.SOUTH, sizeLabel);
	    sp.putConstraint(SpringLayout.WEST, cancleButton, 50, SpringLayout.EAST, okButton);
	    cancleButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeFont.this.dispose();
			}	    	
	    });
	 
	}
	
	
	
	private Font setFont(){
		Font font;
		if(buttonsFont[0].isSelected()) font=new Font("", Font.ROMAN_BASELINE, Integer.parseInt(fontNumber.getText()));
		else if(buttonsFont[1].isSelected()) font=new Font("", Font.HANGING_BASELINE, Integer.parseInt(fontNumber.getText()));
		else font=new Font("", Font.BOLD, Integer.parseInt(fontNumber.getText()));
		return font;
	}

	private Color setColorBackground(){
		for(int i=0;i<buttons1.length;++i){
			if(buttons1[i].isSelected()) return colors[i];
		}
		return null;
	}
	
	private Color setColorForeground(){
		for(int i=0;i<buttons2.length;++i){
			if(buttons2[i].isSelected()) return colors[i];
		}
		return null;
	}
	
	private Color setColorIndexBackground(){
		for(int i=0;i<buttons3.length;++i){
			if(buttons3[i].isSelected()) return colors[i];
		}
		return null;
	}
	
	private Color setColorIndexForeground(){
		for(int i=0;i<buttons4.length;++i){
			if(buttons4[i].isSelected()) return colors[i];
		}
		return null;
	}
	
}
