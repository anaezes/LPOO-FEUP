package dkeep.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import ckeep.cli.Print;
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
import java.io.IOException;

public class GameEditor extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel board;
	private char character;
	private JPanel[][] gameBoard;
	private boolean validBoard;
	private int matrixSize;
	private char[][] matrix = null;
	private JFrame parent;

	public GameEditor(JFrame parent, int size) {
		super(parent);
		this.
		validBoard = false;
		this.matrixSize = size;
		initMatrix();		

		initFrame();
		initButtonExit();
		initButtonOk();
		initButtonReset();
		initBoardPanel();
	}

	private void initBoardPanel() {

		board = new JPanel();
		board.setLayout(new GridLayout(10, 10));
		board.setBounds(30, 30, 600, 600);
		board.setBackground(Color.gray);
		this.getContentPane().add(board);

		initBottonHero();
		initBottonShield();
		initBottonOgre();
		initBottonWall();
		initBottonDoor();
		initBottonKey();

		initButtonSave();

		gameBoard = new JPanel[matrixSize][matrixSize];
		board.setLayout(new GridLayout(matrixSize,matrixSize));

		initBoard();
		initBoardMouseListener();
	}

	private void initBoardMouseListener() {
		board.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if((character == 'h' && heroAlreadyExists()) || (character == 'c' && keyAlreadyExists()))
					return;
				if(character == 'O' && verifyMaxOgres())
					return;
				JPanel item = (JPanel) e.getSource(); 
				((GameObject)item.getComponentAt(e.getX(),e.getY())).switchType(character);
				int x = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getRow();
				int y = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getColumn();
				matrix[x][y] = character;

				if(character == 'O')
					initClubs(x, y);
			}
		});

		board.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				if(character == 'X' ) {	
					JPanel item = (JPanel) e.getSource(); 
					int x = e.getX();
					int y = e.getY();
					if(x > 5 && y > 5 && x < board.getWidth()-5 && y < board.getHeight()-5) {
						((GameObject)item.getComponentAt(x,y)).switchType(character);
						int row = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getRow();
						int col = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getColumn();
						matrix[row][col] = character;
					}
				}
			}
		});

	}

	private void initBoard() {
		for(int i = 0; i < matrixSize ; i++ )
			for(int j = 0; j < matrixSize; j++) {
				gameBoard[i][j] = new GameObject(board.getWidth()/matrixSize, board.getHeight()/matrixSize, ' ', i, j);
				board.add(gameBoard[i][j]);
			}		
	}

	private void initButtonSave() {
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(40, 646, 98, 25);
		getContentPane().add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(verifyIfMapIsComplete()){				
					String nameFile = (String)JOptionPane.showInputDialog(
							parent,
							"Name for this game:\n",
							"Save Game",
							JOptionPane.PLAIN_MESSAGE,
							null,
							null,
							"game");
					if(nameFile != null)
						try {
							Print.boardToFile(matrix, nameFile);
							resetGameBoard();
						} catch (IOException e) {
							e.printStackTrace();
							JOptionPane.showMessageDialog(parent,
									"Error while saving a board to file",
									"Error",
									JOptionPane.INFORMATION_MESSAGE);
						}
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
	}

	private void initBottonKey() {
		JButton btnKey = new JButton("Key");
		btnKey.setBounds(707, 450, 98, 25);
		getContentPane().add(btnKey);
		btnKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				character = 'c';
			}
		});		
	}

	private void initBottonDoor() {
		JButton btnDoor = new JButton("Door");
		btnDoor.setBounds(707, 375, 98, 25);
		getContentPane().add(btnDoor);
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				character = 'S';
			}
		});		
	}

	private void initBottonWall() {
		JButton btnWall = new JButton("Wall");
		btnWall.setBounds(707, 300, 98, 25);
		getContentPane().add(btnWall);
		btnWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				character = 'X';
			}
		});
	}

	private void initBottonOgre() {
		JButton btnOgre = new JButton("Ogre");
		btnOgre.setBounds(707, 225, 98, 25);
		getContentPane().add(btnOgre);
		btnOgre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Ogre ogre = new Ogre();
				character = ogre.getCharacter();
			}
		});		
	}

	private void initBottonShield() {
		JButton btnShield = new JButton("Shield");
		btnShield.setBounds(707, 150, 98, 25);
		getContentPane().add(btnShield);
		btnShield.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				character = 'a';
			}
		});
	}

	private void initBottonHero() {
		JButton btnHero = new JButton("Hero");
		btnHero.setBounds(707, 75, 98, 25);
		getContentPane().add(btnHero);
		btnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Hero hero = new Hero();
				character = hero.getCharacter();
			}
		});
	}

	private void initButtonReset() {
		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(630, 646, 98, 25);
		getContentPane().add(btnReset);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetGameBoard();
				validBoard = false;
			}
		});	
	}

	private void initButtonOk() {
		JButton btnOk = new JButton("Ok");
		btnOk.setBounds(740, 646, 98, 25);
		getContentPane().add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
	}

	private void initButtonExit() {
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(520, 646, 98, 25);
		getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
	}

	private void initFrame() {
		setFont(new Font("Dialog", Font.BOLD, 18));
		setTitle("Game editor");
		getContentPane().setLayout(null);
		setResizable(false);
		setBounds(100, 100, 850, 730);

	}

	private void initMatrix() {
		matrix = new char[matrixSize][matrixSize];

		for(int i = 0; i < matrixSize ; i++ )
			for(int j = 0; j < matrixSize; j++) {
				matrix[i][j] = ' ' ;
			}
	}

	private boolean heroAlreadyExists() {
		for(int i = 0; i < matrixSize ; i++ )
			for(int j = 0; j < matrixSize; j++) {
				if(matrix[i][j] == 'h')
					return true;
			}
		return false;
	}

	private boolean verifyMaxOgres() {
		int nOgres = 0;
		for(int i = 0; i < matrixSize ; i++ )
			for(int j = 0; j < matrixSize; j++) {
				if(matrix[i][j] == 'O')
					nOgres++;
			}

		return (nOgres == 5);
	}

	private boolean keyAlreadyExists() {
		for(int i = 0; i < matrixSize ; i++ )
			for(int j = 0; j < matrixSize; j++) {
				if(matrix[i][j] == 'c')
					return true;
			}
		return false;
	}

	private void resetGameBoard() {
		for(int i = 0; i < matrixSize ; i++ )
			for(int j = 0; j < matrixSize; j++) {
				matrix[i][j] = ' ';
				((GameObject)gameBoard[i][j]).switchType(' ');
			}
	}

	private boolean verifyIfMapIsComplete() {
		for(int i = 0; i < matrixSize ; i++ )
		{	
			if((matrix[i][0] != 'X' && matrix[i][0] != 'S') && (matrix[i][matrix[i].length-1] != 'X' && matrix[i][matrix[i].length-1] != 'S'))
				return false;

			for(int j = 0; j < matrixSize ; j++ )
				if(matrix[0][j] != 'X' && matrix[0][j] != 'S' && matrix[matrix[0].length-1][j] != 'X' && matrix[matrix[0].length-1][j] != 'S')
					return false;	
		}

		boolean existOgre = false;
		for(int i = 0; i < matrixSize; i++)
			for(int j = 0; j < matrixSize; j++)
				if(matrix[i][j] == 'O') 
					existOgre = true;

		boolean existHero = false;
		for(int i = 0; i < matrixSize; i++)
			for(int j = 0; j < matrixSize; j++)
				if(matrix[i][j] == 'h')
					existHero = true;

		boolean existKey = false;
		for(int i = 0; i < matrixSize; i++)
			for(int j = 0; j < matrixSize; j++)
				if(matrix[i][j] == 'c') {
					matrix[i][j] = 'k';
					existKey = true;
				}

		boolean existDoor = false;
		for(int i = 0; i < matrixSize; i++)
			for(int j = 0; j < matrixSize; j++)
				if(matrix[i][j] == 'S')
					existDoor = true;

		return (existHero && existKey && existDoor && existOgre);	
	}

	public char[][] getGameBoard() {
		if(validBoard)
			return matrix;
		return null;
	}

	private void initClubs(int x_ogre, int y_ogre) {
		int x_club = x_ogre;
		int y_club = y_ogre;

		if(y_ogre+1 < matrixSize-1 && matrix[x_ogre][y_ogre+1] == ' ')
			y_club++;
		else if(y_ogre-1 > 0 && matrix[x_ogre][ y_ogre-1] == ' ')
			y_club--;
		else if(x_ogre+1 < matrixSize-1 && matrix[x_ogre+1][ y_ogre] == ' ')
			x_club++;
		else if(x_ogre-1 > 0 && matrix[x_ogre-1][ y_ogre] == ' ')
			x_club--;

		matrix[x_club][y_club] = '*';
		((GameObject) gameBoard[x_club][y_club]).switchType('*');

	}
}
