package dkeep.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import dkeep.logic.Hero;
import dkeep.logic.Ogre;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameEditor extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel board;
	private char character;
	private boolean mouseIsPressed;

	private static final char[][] matrix = {{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
			{' ',' ',' ',' ',' ', ' ', ' ', ' ', ' ', ' '},
			{' ',' ',' ',' ',' ', ' ', ' ', ' ', ' ', ' '},
			{' ',' ',' ',' ',' ', ' ', ' ', ' ', ' ', ' '},
			{' ',' ',' ',' ',' ', ' ', ' ', ' ', ' ', ' '},
			{' ',' ',' ',' ',' ', ' ', ' ', ' ', ' ', ' '},
			{' ',' ',' ',' ',' ', ' ', ' ', ' ', ' ', ' '},
			{' ',' ',' ',' ',' ', ' ', ' ', ' ', ' ', ' '},
			{' ',' ',' ',' ',' ', ' ', ' ', ' ', ' ', ' '},
			{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '}};

	public GameEditor(JFrame parent) {
		super(parent);

		setFont(new Font("Dialog", Font.BOLD, 18));
		setTitle("Game editor");
		getContentPane().setLayout(null);
		//setSize(new Dimension(800, 700));
		setResizable(false);
		setBounds(100, 100, 850, 730);


		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(520, 646, 98, 25);
		getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});


		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(740, 646, 98, 25);
		getContentPane().add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(630, 646, 98, 25);
		getContentPane().add(btnReset);



		//board
		board = new JPanel();
		board.setLayout(new GridLayout(10, 10));
		board.setBounds(30, 30, 600, 600);
		board.setBackground(Color.gray);
		this.getContentPane().add(board);


		//bottoms
		//char character;

		JButton btnHero = new JButton("Hero");
		btnHero.setBounds(707, 150, 98, 25);
		getContentPane().add(btnHero);
		btnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Hero hero = new Hero();
				character = hero.getCharacter();
			}
		});

		JButton btnOgre = new JButton("Ogre");
		btnOgre.setBounds(707, 225, 98, 25);
		getContentPane().add(btnOgre);
		btnOgre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ogre ogre= new Ogre();
				character = ogre.getCharacter();
			}
		});

		JButton btnWall = new JButton("Wall");
		btnWall.setBounds(707, 300, 98, 25);
		getContentPane().add(btnWall);
		btnWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				character = 'X';
			}
		});

		JButton btnDoor = new JButton("Door");
		btnDoor.setBounds(707, 375, 98, 25);
		getContentPane().add(btnDoor);
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				character = 'I';
			}
		});

		JButton btnKey = new JButton("Key");
		btnKey.setBounds(707, 450, 98, 25);
		getContentPane().add(btnKey);
		btnKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				character = 'c';
			}
		});

		JPanel[][] gameBoard = new JPanel[10][10];

		for(int i = 0; i < 10 ; i++ )
			for(int j = 0; j < 10; j++) {
				gameBoard[i][j] = new GameObject(board.getWidth()/10, board.getHeight()/10, ' ');
				board.add(gameBoard[i][j]);
			}

		board.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				mouseIsPressed = false;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				mouseIsPressed = true;
			}
			@Override
			public void mouseEntered(MouseEvent e) {

			}
			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel item = (JPanel) e.getSource(); 
				((GameObject)item.getComponentAt(e.getX(),e.getY())).switchType(character);

			}
		});

		board.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if(character == 'X' && mouseIsPressed) {	
					JPanel item = (JPanel) e.getSource(); 
					int x = e.getX();
					int y = e.getY();
					if(x >= 0 && y >= 0 && x <= board.getWidth() && y <= board.getHeight()) {
						((GameObject)item.getComponentAt(x,y)).switchType(character);
					}
				}
			}
		});
	}
}
