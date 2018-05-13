package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Window extends JFrame{
	public static final int[] DIGIT={0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	public static final float[] MULTIPLIER={1, 1e1f, 1e2f, 1e3f, 1e4f, 1e5f, 1e6f, 1e7f, 0.1f, 0.01f};
	public static final float[] TOLERANCE={1, 2, 0.5f, 0.25f, 0.1f, 0.05f, 5, 10};
	
	public static final Color[] digitColor={Color.BLACK, new Color(130, 70, 0), Color.RED, new Color(255, 140, 0), 
											Color.YELLOW, Color.GREEN, new Color(50, 90, 255), new Color(180, 0, 255), 
											Color.GRAY, Color.WHITE};
	
	public static final Color[] multiplierColor={Color.BLACK, new Color(130, 70, 0), Color.RED, new Color(255, 140, 0), 
												Color.YELLOW, Color.GREEN, new Color(50, 90, 255), new Color(180, 0, 255), 
												Color.WHITE, Color.WHITE};
	
	public static final Color[] toleranceColor={new Color(130, 70, 0), Color.RED, Color.GREEN,new Color(50, 90, 255), 
												new Color(180, 0, 255), Color.GRAY, Color.WHITE, Color.WHITE};
	
	

	private ResistorPanel resistorPanel;
	
	private JPanel centerPanel;
	private Font buttonFont;
	private JButton[] buttonsColor1; //first digit
	private JButton[] buttonsColor2; //second digit
	private JButton[] buttonsColor3; //third digit
	private JButton[] buttonsColor4; //multiplier
	private JButton[] buttonsColor5; //tolerance
    
	private JPanel southPanel;
	private ButtonGroup group;
	private JRadioButton rb4color, rb5color;
	
	private Listener listener;

	
	
	public Window(){
		setResizable(false);
		setTitle("Resistor Calculator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		setSize(250, 440);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());			
		      
		listener=new Listener();
		
		resistorPanel=new ResistorPanel();	
		resistorPanel.setBounds(0, 0, 250, 100);
		resistorPanel.setLayout(null);
		add(resistorPanel);
				
	    centerPanel=new JPanel();
	    centerPanel.setLayout(null);
	    
	    addButtons();
	    disableColor3();
	}
	
	private void addButtons(){
		buttonsColor1=new JButton[10];
	    buttonsColor2=new JButton[10];
	    buttonsColor3=new JButton[10];
	    buttonsColor4=new JButton[10];
	    buttonsColor5=new JButton[8];
	    
	    int bWidth=50, bHeight=30, xAlign=100;
	    buttonFont=new Font("", Font.ROMAN_BASELINE, 12);
	    
	    for(int i=0;i<10;++i){
	    	buttonsColor1[i]=new JButton();
	    	buttonsColor2[i]=new JButton();
	    	buttonsColor3[i]=new JButton();
	    	buttonsColor4[i]=new JButton();
	    	
	    	buttonsColor1[i].setActionCommand("1:"+i);
	    	buttonsColor2[i].setActionCommand("2:"+i);
	    	buttonsColor3[i].setActionCommand("3:"+i);
	    	buttonsColor4[i].setActionCommand("m:"+i);
	    	
	    	buttonsColor1[i].setBackground(digitColor[i]);
	    	buttonsColor2[i].setBackground(digitColor[i]);
	    	buttonsColor3[i].setBackground(digitColor[i]);
	    	buttonsColor4[i].setBackground(multiplierColor[i]);
	    	
	    	buttonsColor1[i].setText(""+DIGIT[i]);
	    	buttonsColor1[i].setFont(buttonFont);
	    	buttonsColor2[i].setText(""+DIGIT[i]);
	    	buttonsColor2[i].setFont(buttonFont);
	    	buttonsColor3[i].setText(""+DIGIT[i]);
	    	buttonsColor3[i].setFont(buttonFont);
	    	
	    	if(i==0) buttonsColor4[i].setText("1");
	    	else if(i==1) buttonsColor4[i].setText("10");
	    	else if(i==2) buttonsColor4[i].setText("100");
			else if(i==3) buttonsColor4[i].setText("1000");
			else buttonsColor4[i].setText("10"+(char)(0x2070+i));
			
	    	buttonsColor4[i].setFont(buttonFont);
	    	buttonsColor4[i].setBorder(null);
	    	
	    	buttonsColor1[i].setBounds(0*bWidth, bHeight*i+xAlign, bWidth, bHeight);
	    	buttonsColor2[i].setBounds(1*bWidth, bHeight*i+xAlign, bWidth, bHeight);
	    	buttonsColor3[i].setBounds(2*bWidth, bHeight*i+xAlign, bWidth, bHeight);
	    	buttonsColor4[i].setBounds(3*bWidth, bHeight*i+xAlign, bWidth, bHeight);
	    	
	    	buttonsColor1[i].addActionListener(listener);
	    	buttonsColor2[i].addActionListener(listener);
	    	buttonsColor3[i].addActionListener(listener);
	    	buttonsColor4[i].addActionListener(listener);
	    	
	    	centerPanel.add(buttonsColor1[i]);
	    	centerPanel.add(buttonsColor2[i]);
	    	centerPanel.add(buttonsColor3[i]);
	    	centerPanel.add(buttonsColor4[i]);
	    	
	    	
	    	if(i<8){	    		
		    	buttonsColor5[i]=new JButton();		    		    	
		    	buttonsColor5[i].setActionCommand("t:"+i);
		    	buttonsColor5[i].setBackground(toleranceColor[i]);
		    	buttonsColor5[i].setBounds(4*bWidth, bHeight*i+xAlign, bWidth, bHeight);
		    	buttonsColor5[i].addActionListener(listener);
		    	buttonsColor5[i].setText(""+TOLERANCE[i]+"%");
		    	buttonsColor5[i].setFont(buttonFont);
		    	buttonsColor5[i].setBorder(null);
		    	centerPanel.add(buttonsColor5[i]);
	    	}	    	
	    }

	    buttonsColor4[8].setText("0.1");
	    buttonsColor4[9].setText("0.01");
	    buttonsColor4[8].setBackground(new Color(255, 255, 90));
	    buttonsColor4[9].setBackground(new Color(210, 210, 255));
	    buttonsColor5[6].setBackground(new Color(255, 255, 90));
	    buttonsColor5[7].setBackground(new Color(210, 210, 255));

	    add(centerPanel, BorderLayout.CENTER);
	    
	    southPanel=new JPanel();
	    southPanel.setLayout(new FlowLayout());
		rb4color=new JRadioButton("4 colors");
		rb5color=new JRadioButton("5 colors");
		rb4color.addActionListener(listener);
		rb5color.addActionListener(listener);
		group=new ButtonGroup();
		group.add(rb4color);
	    group.add(rb5color);
	    rb4color.setSelected(true);
	    southPanel.add(rb4color);
	    southPanel.add(rb5color);
	    add(southPanel, BorderLayout.SOUTH);  		
	}
	
	private void enableColor3(){
		for(int i=0;i<10;++i){
			buttonsColor3[i].setEnabled(true);
			buttonsColor3[i].setBackground(digitColor[i]);
		}
		resistorPanel.setThird(0);	
	}

	private void disableColor3(){
		for(int i=0;i<10;++i){
			buttonsColor3[i].setEnabled(false);
			buttonsColor3[i].setBackground(new Color(50, 50, 50));
		}
		resistorPanel.setThird(-1);	
	}

	
	
	private class Listener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String command=e.getActionCommand().substring(0, 1);
			String value=e.getActionCommand().substring(2, e.getActionCommand().length());
			
			if(command.equals("1")) resistorPanel.setFirst(Integer.parseInt(value));
			else if(command.equals("2")) resistorPanel.setSecond(Integer.parseInt(value));
			else if(command.equals("3")) resistorPanel.setThird(Integer.parseInt(value));
			else if(command.equals("m")) resistorPanel.setMull(Integer.parseInt(value));
			else if(command.equals("t")) resistorPanel.setTolerance(Integer.parseInt(value));
			
			if(command.equals("4")) disableColor3();
			else if(command.equals("5")) enableColor3();
		}		
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				try {
					Window window=new Window();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
