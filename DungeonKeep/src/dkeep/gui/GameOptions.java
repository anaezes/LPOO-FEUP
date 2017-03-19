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
		
		nOgres = 1;
		guardType = EnumGuardType.Drunk;
		
		setForeground(Color.WHITE);
		getContentPane().setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setSize(new Dimension(250, 367));
		setResizable(false);

		getContentPane().setLayout(null);
		
		JLabel lblNumberOfOgres = new JLabel("Number of Ogres");
		lblNumberOfOgres.setFont(new Font("Dialog", Font.BOLD, 18));
		lblNumberOfOgres.setBounds(12, 41, 199, 25);
		getContentPane().add(lblNumberOfOgres);
		
		JSlider slider = new JSlider();
		slider.setFont(new Font("Dialog", Font.BOLD, 10));
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinimum(1);
		slider.setMaximum(5);
		slider.setBounds(12, 78, 199, 33);
		slider.setMajorTickSpacing(1);
		getContentPane().add(slider);
		
		JLabel lblGuardType = new JLabel("Guard personality:");
		lblGuardType.setFont(new Font("Dialog", Font.BOLD, 18));
		lblGuardType.setBounds(12, 145, 167, 15);
		getContentPane().add(lblGuardType);
		
		JRadioButton rdbtnDrunkGuard = new JRadioButton("Drunk Guard");
		rdbtnDrunkGuard.setFont(new Font("Dialog", Font.PLAIN, 16));
		rdbtnDrunkGuard.setBounds(44, 168, 167, 23);
		getContentPane().add(rdbtnDrunkGuard);
		rdbtnDrunkGuard.setSelected(true);

		
		JRadioButton rdbtnRookieGuard = new JRadioButton("Rookie Guard");
		rdbtnRookieGuard.setFont(new Font("Dialog", Font.PLAIN, 16));
		rdbtnRookieGuard.setBounds(44, 195, 167, 23);
		getContentPane().add(rdbtnRookieGuard);
		
		
		JRadioButton rdbtnParanoidGuard = new JRadioButton("Paranoid Guard");
		rdbtnParanoidGuard.setFont(new Font("Dialog", Font.PLAIN, 16));
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
