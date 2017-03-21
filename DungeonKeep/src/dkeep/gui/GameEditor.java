package dkeep.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ckeep.cli.Print;
import dkeep.logic.Game;
import dkeep.logic.GameMap;
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
	private JPanel[][] gameBoard;
	private boolean validBoard;

	private final char[][] matrix = {{' ',' ',' ',' ',' ',' ',' ',' ',' ',' '},
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
		validBoard = false;

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
				System.out.println("pimmmmm");
				if(verifyIfMapIsComplete())
				{
					validBoard = true;
					setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(parent,
							"Map isn't valid! Please choose place for a hero, a key and close the map with walls or doors...",
							"Editor error",
							JOptionPane.ERROR_MESSAGE);
					validBoard = false;
				}
			}
		});

		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(630, 646, 98, 25);
		getContentPane().add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetGameBoard();
				validBoard = false;
			}
		});


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

		gameBoard = new JPanel[10][10];

		for(int i = 0; i < 10 ; i++ )
			for(int j = 0; j < 10; j++) {
				gameBoard[i][j] = new GameObject(board.getWidth()/10, board.getHeight()/10, ' ', i, j);
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

				if(character == 'h' && heroAlreadyExists())
					return;
				JPanel item = (JPanel) e.getSource(); 
				((GameObject)item.getComponentAt(e.getX(),e.getY())).switchType(character);
				int x = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getRow();
				int y = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getColumn();
				matrix[x][y] = character;

				//printBoard();
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
						int row = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getRow();
						int col = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getColumn();
						matrix[row][col] = character;
						//printBoard();
					}
				}
			}
		});
	}

	private boolean heroAlreadyExists() {
		for(int i = 0; i < matrix.length ; i++ )
			for(int j = 0; j < matrix[i].length; j++) {
				if(matrix[i][j] == 'h')
					return true;
			}
		return false;
	}

	private void resetGameBoard() {
		for(int i = 0; i < matrix.length ; i++ )
			for(int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = ' ';
				((GameObject)gameBoard[i][j]).switchType(' ');
			}
	}

	private boolean verifyIfMapIsComplete() {

		for(int i = 0; i < matrix.length ; i++ )
		{	
			if((matrix[i][0] != 'X' && matrix[i][0] != 'I') && (matrix[i][matrix[i].length-1] != 'X' && matrix[i][matrix[i].length-1] != 'I'))
				return false;

			for(int j = 0; j < matrix[i].length ; j++ )
				if(matrix[0][j] != 'X' && matrix[0][j] != 'I' && matrix[matrix[0].length-1][j] != 'X' && matrix[matrix[0].length-1][j] != 'I')
					return false;	
		}


		boolean existOgre = false;
		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[i].length; j++)
				if(matrix[i][j] == 'O') {
					initClubs(i, j);
				}

		boolean existHero = false;
		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[i].length; j++)
				if(matrix[i][j] == 'h')
					existHero = true;

		boolean existKey = false;
		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[i].length; j++)
				if(matrix[i][j] == 'c')
				{
					matrix[i][j] = 'k';
					existKey = true;
				}

		boolean existDoor = false;
		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[i].length; j++)
				if(matrix[i][j] == 'I')
				{
					existDoor = true;
					matrix[i][j] = 'S';
				}

		printBoard();
		return (existHero && existKey && existDoor);	
	}

	public void printBoard(){
		for(int i = 0; i < matrix.length ; i++ )
		{
			for(int j = 0; j < matrix[i].length ; j++ )
			{
				System.out.print(matrix[i][j] + " ");
			}	
			System.out.println();
		}

		System.out.println();
	}

	public char[][] getGameBoard() {
		if(validBoard)
			return matrix;
		return null;
	}


	private void initClubs(int x_ogre, int y_ogre) {

		int x_club = x_ogre;
		int y_club = y_ogre;

		if(matrix[x_ogre][y_ogre+1] == ' ')
			y_club++;
		else if(matrix[x_ogre][ y_ogre-1] == ' ')
			y_club--;
		else if(matrix[x_ogre+1][ y_ogre-1] == ' ')
			x_club++;
		else if(matrix[x_ogre-1][ y_ogre] == ' ')
			x_club--;

		matrix[x_club][y_club] = '*';

	}
	
}
