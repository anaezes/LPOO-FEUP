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
	private EnumGuardType guardType;
	private int nOgres;
	private JSlider slider;
	private JRadioButton rdbtnParanoidGuard;
	private JRadioButton rdbtnRookieGuard;
	private JRadioButton rdbtnDrunkGuard;
	
	public GameOptions(JFrame parent) {
		super(parent);
		getContentPane().setForeground(Color.LIGHT_GRAY);	
		nOgres = 3;
		guardType = EnumGuardType.Drunk;
		
		setBounds(100, 100, 670, 720);
		getContentPane().setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setSize(new Dimension(250, 367));
		setResizable(false);
		setFont(new Font("Dialog", Font.BOLD, 18));
		setTitle("Options");
		getContentPane().setLayout(null);
	
		initSliderNumberOgres();
		iniLabelTypeGuard();
		initButtonExit();
		initButtonOk();
	}

	private void initSliderNumberOgres() {
		JLabel lblNumberOfOgres = new JLabel("Number of Ogres: ");
		lblNumberOfOgres.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNumberOfOgres.setBounds(12, 41, 199, 25);
		getContentPane().add(lblNumberOfOgres);
		
		slider = new JSlider();
		slider.setForeground(Color.LIGHT_GRAY);
		slider.setMinorTickSpacing(1);
		slider.setFont(new Font("Dialog", Font.BOLD, 10));
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMinimum(1);
		slider.setMaximum(5);
		slider.setValue(1);
		slider.setBounds(12, 78, 199, 33);
		slider.setMajorTickSpacing(1);
		getContentPane().add(slider);
	}

	private void iniLabelTypeGuard(){
		JLabel lblGuardType = new JLabel("Guard personality:");
		lblGuardType.setFont(new Font("Dialog", Font.BOLD, 16));
		lblGuardType.setBounds(12, 145, 167, 15);
		getContentPane().add(lblGuardType);
		
		initRadioButtonDrunkGuard();
		initRadioButtonRookieGuard();
		initRadioButtonParanoidGuard();
	}
	
	private void initRadioButtonParanoidGuard() {
		rdbtnParanoidGuard = new JRadioButton("Paranoid Guard");
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
	}

	private void initRadioButtonRookieGuard() {
		rdbtnRookieGuard = new JRadioButton("Rookie Guard");
		rdbtnRookieGuard.setFont(new Font("Dialog", Font.PLAIN, 14));
		rdbtnRookieGuard.setBounds(44, 195, 167, 23);
		getContentPane().add(rdbtnRookieGuard);
		rdbtnRookieGuard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardType = EnumGuardType.Rockie;
				rdbtnDrunkGuard.setSelected(false);
				rdbtnParanoidGuard.setSelected(false);
			}
		});
	}

	private void initRadioButtonDrunkGuard() {
		rdbtnDrunkGuard = new JRadioButton("Drunk Guard");
		rdbtnDrunkGuard.setFont(new Font("Dialog", Font.PLAIN, 14));
		rdbtnDrunkGuard.setBounds(44, 168, 167, 23);
		getContentPane().add(rdbtnDrunkGuard);
		rdbtnDrunkGuard.setSelected(true);
		rdbtnDrunkGuard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardType = EnumGuardType.Drunk;
				rdbtnRookieGuard.setSelected(false);
				rdbtnParanoidGuard.setSelected(false);
			}
		});	
	}

	private void initButtonExit() {
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(134, 293, 98, 25);
		getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				
			}
		});		
	}



	public void initButtonOk() {
		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(12, 293, 98, 25);
		getContentPane().add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				nOgres = slider.getValue();
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
