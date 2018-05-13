package marin.bralic.application;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class StartWindow extends JFrame{
	private JTextField textField;
	private JCheckBox checkBox;

	public StartWindow(){
		setResizable(false);
		this.setTitle("Configuration");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(214, 146);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon(getClass().getResource("/media/pac_icon.png")).getImage());
		getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Double.parseDouble(textField.getText())<1.0){
					JOptionPane.showMessageDialog(null, "Resolution must be at least 1.0", "Error", JOptionPane.ERROR_MESSAGE);					   
					return;
				}
				Application app=new Application(Double.parseDouble(textField.getText()), checkBox.isSelected());
				
				StartWindow.this.dispose();
				app.setVisible(true);
			}
		});
		btnNewButton.setBounds(10, 77, 89, 23);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Exit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		btnNewButton_1.setBounds(109, 77, 89, 23);
		getContentPane().add(btnNewButton_1);
		
		JLabel lblResolution = new JLabel("Resolution: ");
		lblResolution.setBounds(10, 11, 70, 14);
		getContentPane().add(lblResolution);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setText("2.5");
		textField.setBounds(109, 8, 53, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		textField.addActionListener(e->{
			btnNewButton.doClick();
		});
		
		JLabel lblSound = new JLabel("Sound: ");
		lblSound.setBounds(10, 36, 46, 14);
		getContentPane().add(lblSound);
		
		checkBox = new JCheckBox("");
		checkBox.setSelected(true);
		checkBox.setBounds(109, 32, 97, 23);
		getContentPane().add(checkBox);
		
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				StartWindow app=new StartWindow();
				app.setVisible(true);
			}			
		});		
	}
}
