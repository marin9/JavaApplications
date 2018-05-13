package application;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

@SuppressWarnings("serial")
public class Timer555 extends JFrame {	
		private final String[] R_UNITS={"\u03A9", "k\u03A9", "M\u03A9"};
		private final String[] C_units={"F", "mF", "\u00B5F", "nF", "pF"};
		private final String[] f_units={"Hz", "kHz", "MHz"};

	
		private JPanel leftPanel;
		
		private JPanel centerPanel;
		private JTextField textInputR1;		
		private JTextField textInputR2;
		private JTextField textInputC;
		private JTextField textInputF;
		private JProgressBar dutyCycleProgressBar;
		
		private JPanel rightPanel;
		private JComboBox<String> resistor1Unit;
		private JComboBox<String> resistor2Unit;	
		private JComboBox<String> capacitorUnit;		
		private JComboBox<String> frequencyUnit;
		
		private JPanel resultPanel;
		private JButton calculateButton;
		private JTextField valueTextField;
							
		
		private double r1, r2, c, f;
		private int dutyCycle=0;
		
		
	
		public Timer555(){
			setTitle("555 Timer");
			setLocation(400, 200);
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);					
			setLayout(new BorderLayout());
						
			addIconAndImage();
			
			leftPanel=new JPanel();
			leftPanel.setLayout(new GridLayout(5, 0));
			leftPanel.add(new Label("    R1="));
			leftPanel.add(new Label("    R2="));
			leftPanel.add(new Label("     C="));
			leftPanel.add(new Label("     f="));
			leftPanel.add(new Label(" Cycle="));
			add(leftPanel, BorderLayout.WEST);
			
			centerPanel=new JPanel();
			textInputR1=new JTextField(10);
			textInputR2=new JTextField(10);
			textInputC=new JTextField(10);
			textInputF=new JTextField(10);
			dutyCycleProgressBar=new JProgressBar();
			centerPanel.setLayout(new GridLayout(5, 0));			
			textInputR1.setBackground(new Color(0, 150, 0));
			textInputR2.setBackground(new Color(0, 150, 0));
			textInputC.setBackground(new Color(90, 130, 250));
			textInputF.setBackground(new Color(250, 200, 0));
			
			centerPanel.add(textInputR1);
			centerPanel.add(textInputR2);
			centerPanel.add(textInputC);
			centerPanel.add(textInputF);
			dutyCycleProgressBar.setStringPainted(true);
			centerPanel.add(dutyCycleProgressBar);
			add(centerPanel, BorderLayout.CENTER);
			
			
			rightPanel=new JPanel();
			rightPanel.setLayout(new GridLayout(5, 0));
					
			resistor1Unit=new JComboBox<String>(R_UNITS);
			resistor2Unit=new JComboBox<String>(R_UNITS);	
			capacitorUnit=new JComboBox<String>(C_units);	
			frequencyUnit=new JComboBox<String>(f_units);
			rightPanel.add(resistor1Unit);
			rightPanel.add(resistor2Unit);
			rightPanel.add(capacitorUnit);
			rightPanel.add(frequencyUnit);			
			add(rightPanel, BorderLayout.EAST);
		
			
			resultPanel=new JPanel();
			resultPanel.setLayout(new GridLayout(0, 2));
			calculateButton=new JButton("Calculate");
			calculateButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {	
					valueTextField.setText(calculate());	
					dutyCycleProgressBar.setValue(dutyCycle);
				}				
			});
			
			valueTextField=new JTextField("?");	
			valueTextField.setFont(new Font("", Font.BOLD, 14));
			resultPanel.add(calculateButton);
			resultPanel.add(valueTextField);
			add(resultPanel, BorderLayout.SOUTH);	
			
			pack();
		}
		
		
		private void addIconAndImage(){
			try{
			  setIconImage(ImageIO.read(getClass().getResource("/icon.png")));
			  add(new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/image.png")))), BorderLayout.NORTH);
			}catch(Exception e){}
		}
		
		
		private String calculate(){
			int x=0;
			try{
			   r1=Double.parseDouble(textInputR1.getText());	
			   String unit=resistor1Unit.getSelectedItem().toString();
			   unit=unit.substring(0, 1);
			   r1=r1*getUnit(unit);
			}catch(NumberFormatException e){
			   x=1;
			}
			try{
			   r2=Double.parseDouble(textInputR2.getText());
			   String unit=resistor2Unit.getSelectedItem().toString();
			   unit=unit.substring(0, 1);
			   r2=r2*getUnit(unit);
			}catch(NumberFormatException e){
				x=2;
			}
			try{
			   c=Double.parseDouble(textInputC.getText());
			   String unit=capacitorUnit.getSelectedItem().toString();
			   unit=unit.substring(0, 1);
			   c=c*getUnit(unit);
			}catch(NumberFormatException e){
				x=3;
			}			
			try{
			   f=Double.parseDouble(textInputF.getText());
			   String unit=frequencyUnit.getSelectedItem().toString();
			   unit=unit.substring(0, 1);
			   f=f*getUnit(unit);
			}catch(NumberFormatException e){
				x=4;
			}

			if(x==1) return calculateResult("R1", (r1, r2, c, f)->(1/(Math.log(2)*c*f)-2*r2), "\u03A9");
			else if(x==2) return calculateResult("R2", (r1, r2, c, f)->(1/(2*Math.log(2)*c*f)-(r1/2)), "\u03A9");
			else if(x==3) return calculateResult("C", (r1, r2, c, f)->(1/(Math.log(2)*f*(r1+2*r2))), "F");
			else return calculateResult("f", (r1, r2, c, f)->(1/(Math.log(2)*c*(r1+2*r2))), "Hz");
		}
		
		private interface ICalculate{
			double getResult(double r1, double r2, double c, double f);
		}
		
		private String calculateResult(String name, ICalculate IC, String unit){
			dutyCycle=(int)(((r1+r2)/(r1+2*r2))*100);		
			return "  "+name+"= "+ResultFormat(IC.getResult(r1, r2, c, f), unit);
		}
		
		
		private double getUnit(String unit){	
			if(unit.equals("M")) return 1000000.0;
			else if(unit.equals("k")) return 1000.0;				
			else if(unit.equals("m")) return 0.001;
			else if(unit.equals("\u00B5")) return 0.000001;
			else if(unit.equals("n")) return 0.000000001;
			else if(unit.equals("p")) return 0.000000000001;	
			else return 1.0;
	}
		
		private String ResultFormat(double r, String unit){			
			String result="";
			DecimalFormat f=new DecimalFormat(new String("0.00"));
			
			if(r>1000000){
				r=r/1000000;
				result=f.format(r)+"  "+"M"+unit;
			}else if(r>1000){
				r=r/1000;
				result=f.format(r)+"  "+"k"+unit;
			}else if(r>=1){
				result=f.format(r)+"  "+""+unit;
			}else if(r>=0.001){
				r=r*1000;
				result=f.format(r)+"  "+"m"+unit;
			}else if(r>=0.000001){
				r=r*1000*1000;
				result=f.format(r)+"  "+"\u00B5"+unit;
			}else if(r>=0.000000001){
				r=r*1000*1000*1000;
				result=f.format(r)+"  "+"n"+unit;
			}else{
				r=r*1000*1000*1000*1000;
				result=f.format(r)+"  "+"p"+unit;
			}
			return result;
		}
		
		public static void main(String[] args){
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run() {
					try {   
						UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");   
						UIManager.getLookAndFeelDefaults().put("nimbusOrange", (new Color(255, 0, 0)));
					}catch(Exception e){}
					
					Timer555 application=new Timer555();
					application.setVisible(true);
				}
			});		
		}
		
}