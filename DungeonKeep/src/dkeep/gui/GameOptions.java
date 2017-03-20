package dkeep.gui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;
import dkeep.logic.EnumGuardType;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class GameOptions extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private int nOgres;
	private EnumGuardType guardType;
	
	public GameOptions(JFrame parent) {
		super(parent);
		getContentPane().setForeground(Color.LIGHT_GRAY);
		//getContentPane().setBackground(Color.LIGHT_GRAY);
		
		nOgres = 1;
		guardType = EnumGuardType.Drunk;
		
		setBounds(100, 100, 670, 720);
		getContentPane().setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setSize(new Dimension(250, 367));
		setResizable(false);
		setFont(new Font("Dialog", Font.BOLD, 18));
		setTitle("Options");
		

		getContentPane().setLayout(null);
		
		JLabel lblNumberOfOgres = new JLabel("Number of Ogres: ");
		lblNumberOfOgres.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNumberOfOgres.setBounds(12, 41, 199, 25);
		getContentPane().add(lblNumberOfOgres);
		
		JSlider slider = new JSlider();
		slider.setForeground(Color.LIGHT_GRAY);
		slider.setMinorTickSpacing(1);
		//slider.setBackground(Color.LIGHT_GRAY);
		slider.setFont(new Font("Dialog", Font.BOLD, 10));
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinimum(1);
		slider.setMaximum(5);
		slider.setValue(1);
		slider.setBounds(12, 78, 199, 33);
		slider.setMajorTickSpacing(1);
		getContentPane().add(slider);
		
		JLabel lblGuardType = new JLabel("Guard personality:");
		lblGuardType.setFont(new Font("Dialog", Font.BOLD, 16));
		lblGuardType.setBounds(12, 145, 167, 15);
		getContentPane().add(lblGuardType);
		
		JRadioButton rdbtnDrunkGuard = new JRadioButton("Drunk Guard");
		//rdbtnDrunkGuard.setBackground(Color.LIGHT_GRAY);
		rdbtnDrunkGuard.setFont(new Font("Dialog", Font.PLAIN, 14));
		rdbtnDrunkGuard.setBounds(44, 168, 167, 23);
		getContentPane().add(rdbtnDrunkGuard);
		rdbtnDrunkGuard.setSelected(true);

		
		JRadioButton rdbtnRookieGuard = new JRadioButton("Rookie Guard");
		//rdbtnRookieGuard.setBackground(Color.LIGHT_GRAY);
		rdbtnRookieGuard.setFont(new Font("Dialog", Font.PLAIN, 14));
		rdbtnRookieGuard.setBounds(44, 195, 167, 23);
		getContentPane().add(rdbtnRookieGuard);
		
		
		JRadioButton rdbtnParanoidGuard = new JRadioButton("Paranoid Guard");
		//rdbtnParanoidGuard.setBackground(Color.LIGHT_GRAY);
		rdbtnParanoidGuard.setFont(new Font("Dialog", Font.PLAIN, 14));
		rdbtnParanoidGuard.setBounds(44, 222, 167, 23);
		getContentPane().add(rdbtnParanoidGuard);
		
		rdbtnParanoidGuard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardType = EnumGuardType.Paranoid;
				rdbtnRookieGuard.setSelected(false);
				rdbtnDrunkGuard.setSelected(false);
			}
		});
		
		rdbtnDrunkGuard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardType = EnumGuardType.Drunk;
				rdbtnRookieGuard.setSelected(false);
				rdbtnParanoidGuard.setSelected(false);
			}
		});
		
		rdbtnRookieGuard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardType = EnumGuardType.Rockie;
				rdbtnDrunkGuard.setSelected(false);
				rdbtnParanoidGuard.setSelected(false);
			}
		});
		
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(134, 293, 98, 25);
		getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println(nOgres);
				setVisible(false);
				
			}
		});
		
		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(12, 293, 98, 25);
		getContentPane().add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nOgres = slider.getValue();
				System.out.println(nOgres);
				setVisible(false);
			}
		});
	}

	public int getNumberOfOgres() {
		return nOgres;
	}

	public EnumGuardType getGuardType() {
		return guardType;
	}
	
	
	
}
