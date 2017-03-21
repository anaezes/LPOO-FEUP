package dkeep.gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class GameEditor extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel board;
	private char character;
	private JPanel[][] gameBoard;
	private boolean validBoard;
	private int matrixSize;
	private char[][] matrix = null;
	private GameResources instance;

	public GameEditor(JFrame parent, int size) {
		super(parent);
		validBoard = false;
		this.matrixSize = size;
		initMatrix();
		instance = GameResources.getInstance();
		
		
		setFont(new Font("Dialog", Font.BOLD, 18));
		setTitle("Game editor");
		getContentPane().setLayout(null);
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
				Ogre ogre = new Ogre();
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

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(40, 646, 98, 25);
		getContentPane().add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(verifyIfMapIsComplete()){
					String s = (String)JOptionPane.showInputDialog(
							parent,
							"Name for this game:\n",
							"Save Game",
							JOptionPane.PLAIN_MESSAGE,
							null,
							null,
							"game");
					//saveState();	
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

		

		
		gameBoard = new JPanel[matrixSize][matrixSize];
		board.setLayout(new GridLayout(matrixSize,matrixSize));

		for(int i = 0; i < matrixSize ; i++ )
			for(int j = 0; j < matrixSize; j++) {
				System.out.println("w: " + board.getWidth());
				System.out.println("h: " + board.getHeight());
				System.out.println("size: " + size);
				gameBoard[i][j] = new GameObject(board.getWidth()/matrixSize, board.getHeight()/matrixSize, ' ', i, j);
				board.add(gameBoard[i][j]);
			}

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

				if((character == 'h' && heroAlreadyExists()) | (character == 'c' && keyAlreadyExists()))
					return;
				
				if(character == 'O' && verifyMaxOgres())
					return;
				
				JPanel item = (JPanel) e.getSource(); 
				((GameObject)item.getComponentAt(e.getX(),e.getY())).switchType(character);
				int x = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getRow();
				int y = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getColumn();
				//instance.getScaledImage(((GameObject)item.getComponentAt(x,y)).getImage(), board.getWidth()/matrixSize);
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
					if(x > 0 && y > 0 && x < board.getWidth() && y < board.getHeight()) {
						((GameObject)item.getComponentAt(x,y)).switchType(character);
						//instance.getScaledImage(((GameObject)item.getComponentAt(x,y)).getImage(), board.getWidth()/matrixSize);
						int row = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getRow();
						int col = ((GameObject)item.getComponentAt(e.getX(),e.getY())).getColumn();
						matrix[row][col] = character;
						//printBoard();
					}
				}
			}
		});
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
			if((matrix[i][0] != 'X' && matrix[i][0] != 'I') && (matrix[i][matrix[i].length-1] != 'X' && matrix[i][matrix[i].length-1] != 'I'))
				return false;

			for(int j = 0; j < matrixSize ; j++ )
				if(matrix[0][j] != 'X' && matrix[0][j] != 'I' && matrix[matrix[0].length-1][j] != 'X' && matrix[matrix[0].length-1][j] != 'I')
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
				if(matrix[i][j] == 'c')
				{
					matrix[i][j] = 'k';
					existKey = true;
				}

		boolean existDoor = false;
		for(int i = 0; i < matrixSize; i++)
			for(int j = 0; j < matrixSize; j++)
				if(matrix[i][j] == 'I')
				{
					existDoor = true;
					matrix[i][j] = 'S';
				}

		printBoard();
		return (existHero && existKey && existDoor && existOgre);	
	}

	public void printBoard(){
		for(int i = 0; i < matrixSize ; i++ )
		{
			for(int j = 0; j < matrixSize ; j++ )
				System.out.print(matrix[i][j] + " ");
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
	
//	private void reSizeImage() {
//		 Image image = icon.getImage().getScaledInstance(
//		 icon.getIconWidth() * NEW / OLD,
//		 icon.getIconHeight() * NEW / OLD,
//		          Image.SCALE_SMOOTH);
//		     icon = new ImageIcon(image, icon.getDescription());
//	}
	
//	protected void saveState() {
//        FileOutputStream fileOut;
//        try {
//            fileOut = new FileOutputStream("saveGame.ser");
//            ObjectOutputStream out = new ObjectOutputStream(fileOut);
//            out.writeObject(this);
//            out.close();
//            fileOut.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception w){
//            w.printStackTrace();
//        }
//    }
}
