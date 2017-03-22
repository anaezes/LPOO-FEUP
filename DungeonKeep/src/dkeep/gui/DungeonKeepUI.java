package dkeep.gui;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import dkeep.logic.EnumGuardType;
import dkeep.logic.EnumMoves;
import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.Vilan.EnumVillainType;
import dkeep.logic.Game.EnumGameState;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Window.Type;


public class DungeonKeepUI{
	public static final String BOARDS_DIR = System.getProperty("user.dir") + "/boards/";

	private JFrame frmDungeonKeepGame;
	private Game game;
	private JPanel gamePanel;
	private JPanel[][] gameBoard;
	private KeyListener keyListener;
	private GameOptions gameOptions;
	private GameEditor gameEditor;
	private int currentBoardSize;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DungeonKeepUI window = new DungeonKeepUI();
					window.frmDungeonKeepGame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}});
	}

	/**
	 * Create the application.
	 */
	public DungeonKeepUI() {
		keyListener = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(game.getGameState() != EnumGameState.Running)
					return;

				boolean moved = false;
				int key = e.getKeyCode();
				switch(key) {
				case KeyEvent.VK_LEFT: 
					game.moveHero(EnumMoves.LEFT);
					moved = true;
					break;
				case KeyEvent.VK_RIGHT: 
					game.moveHero(EnumMoves.RIGHT);
					moved = true;
					break;
				case KeyEvent.VK_UP:
					game.moveHero(EnumMoves.UP);
					moved = true;
					break;
				case KeyEvent.VK_DOWN: 
					game.moveHero(EnumMoves.DOWN);
					moved = true;
				}

				if(moved) {
					updateGraphics();
					gamePanel.repaint();
					validateGameRunning();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
			}
		};
		initialize();
	}

	public void newGame() {
		Game newGame;

		int[] guard_y = new int[] {8, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8};
		int[] guard_x = new int[] {1, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 5, 4, 3, 2};

		char[][] BoardOne = {{'X','X','X','X','X','X','X','X','X','X'},
				{'X','h',' ',' ','x', ' ', 'X', ' ', 'G', 'X'},
				{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
				{'X',' ','x',' ','x', ' ', 'X', ' ', ' ', 'X'},
				{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
				{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
				{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
				{'X','X','X',' ','X', 'X', 'X', 'X', ' ', 'X'},
				{'X',' ','x',' ','x', ' ', 'X', 'k', ' ', 'X'},
				{'X','X','X','X','X','X','X','X','X','X'}};

		char[][] BoardTwo = {{'X','X','X','X','X','X','X','X','X'},
				{'S',' ',' ',' ',' ', 'O', '*', 'k', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','h','a',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','X','X','X','X','X','X','X','X'}};

		int numOfOgres = gameOptions.getNumberOfOgres();
		EnumGuardType guardType = gameOptions.getGuardType();

		List<GameMap> gameMaps = new ArrayList<>();
		GameMap gameMap1 = new GameMap(BoardOne);
		GameMap gameMap2 = new GameMap(BoardTwo);
		gameMaps.add(gameMap1);
		gameMaps.add(gameMap2);

		newGame = new Game(gameMaps, guardType, numOfOgres);
		newGame.setGuardPath(guard_y, guard_x);

		this.game = newGame;
		initJpanel();	
		initGraphics();
	}

	public void initJpanel() {
		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(game.getBoard().getBoardSize(), game.getBoard().getBoardSize()));
		gamePanel.setBounds(30, 30, 600, 600);
		gamePanel.setBackground(Color.WHITE);
		gamePanel.setFocusable(true);
		gamePanel.addKeyListener(keyListener);
		frmDungeonKeepGame.getContentPane().add(gamePanel);
		gamePanel.requestFocus();
	}

	public void newGame(char[][] board){
		GameMap gameMap = new GameMap(board);
		this.game = new Game(gameMap);
		initJpanel();	
		initGraphics();
	}

	private void initGraphics() {
		currentBoardSize = game.getBoard().getBoardSize();
		this.gameBoard = new JPanel[currentBoardSize][currentBoardSize];

		char character;
		for(int i = 0; i < currentBoardSize ; i++ )
			for(int j = 0; j < currentBoardSize; j++) {
				character = game.getBoard().getBoardCaracter(i, j);

				if(game.getHero().getXCoordinate() == i && game.getHero().getYCoordinate() == j) 
					character = game.getHero().getCharacter();

				else if(game.getLever() != null && game.getLever().getXCoordinate() == i && game.getLever().getYCoordinate() == j) 
					character = game.getLever().getCharacter();

				else if(game.getKey() != null && game.getKey().getXCoordinate() == i && game.getKey().getYCoordinate() == j) 
					character = game.getKey().getCharacter();

				else if(game.getHeroClub().getXCoordinate() == i && game.getHeroClub().getYCoordinate()  == j) 
					character = game.getHeroClub().getCharacter();

				if(game.getExitDoors().size() != 0) {
					for(int k = 0; k < game.getExitDoors().size(); k++)
						if(game.getExitDoors().get(k).getXCoordinate() == i && game.getExitDoors().get(k).getYCoordinate() == j)
							character = game.getExitDoors().get(k).getCharacter();
				}

				if(game.getVilans().size() != 0) {
					for(int k = 0; k < game.getVilans().size(); k++) {
						if(game.getVilans().get(k).getXCoordinate() == i && game.getVilans().get(k).getYCoordinate() == j)
							character = game.getVilans().get(k).getCharacter();
					}
				}

				gameBoard[i][j] = new GameObject(gamePanel.getWidth()/currentBoardSize, gamePanel.getHeight()/currentBoardSize, character, i, j);
				gamePanel.add(gameBoard[i][j]);
				frmDungeonKeepGame.repaint();
			}
	}


	private void updateGraphics() {
		currentBoardSize = game.getBoard().getBoardSize();
		char character;

		for(int i = 0; i < currentBoardSize ; i++ )
			for(int j = 0; j < currentBoardSize; j++) {
				character = game.getBoard().getBoardCaracter(i, j);

				if(character == 'X' && character == 'I')
					continue;

				else if(game.getHero().getXCoordinate() == i && game.getHero().getYCoordinate() == j) 
					character = game.getHero().getCharacter();

				else if(game.getLever() != null && game.getLever().getXCoordinate() == i && game.getLever().getYCoordinate() == j) 
					character = game.getLever().getCharacter();

				else if(game.getKey() != null && game.getKey().getXCoordinate() == i && game.getKey().getYCoordinate() == j) 
					character = game.getKey().getCharacter();

				else if(game.getHeroClub().getXCoordinate() == i && game.getHeroClub().getYCoordinate()  == j) 
					character = game.getHeroClub().getCharacter();

				else if(game.getExitDoors().size() != 0) {
					for(int k = 0; k < game.getExitDoors().size(); k++)
						if(game.getExitDoors().get(k).getXCoordinate() == i && game.getExitDoors().get(k).getYCoordinate() == j)
							character = game.getExitDoors().get(k).getCharacter();
				}

				if(game.getVilans().size() != 0) {
					for(int k = 0; k < game.getVilans().size(); k++)
						if(game.getVilans().get(k).getXCoordinate() == i && game.getVilans().get(k).getYCoordinate() == j)
							character = game.getVilans().get(k).getCharacter();
				}

				if(game.getVilans().size() != 0 && game.getVilans().get(0).getType() == EnumVillainType.Ogre) {
					for(int k = 0; k < game.getVilans().size(); k++)
						if(game.getVilans().get(k).getClub().getXCoordinate() == i && game.getVilans().get(k).getClub().getYCoordinate() == j)
							character = '*';
				}

				((GameObject)gameBoard[i][j]).switchType(character);
			}
	}

	private void validateGameRunning() {
		if(game.getGameState() == EnumGameState.Win){
			JOptionPane.showMessageDialog(frmDungeonKeepGame,
					"Congratulations! You escaped!!!",
					"Win",
					JOptionPane.INFORMATION_MESSAGE);
		}
		else if(game.getGameState() == EnumGameState.Lost){
			JOptionPane.showMessageDialog(frmDungeonKeepGame,
					"Ah ah you lose! Best luck next time..",
					"Game Over!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDungeonKeepGame = new JFrame();
		frmDungeonKeepGame.setType(Type.UTILITY);
		frmDungeonKeepGame.setTitle("Dungeon Keep Game");
		frmDungeonKeepGame.setFont(new Font("Dialog", Font.BOLD, 18));
		frmDungeonKeepGame.setResizable(false);
		frmDungeonKeepGame.setBackground(Color.GRAY);
		frmDungeonKeepGame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frmDungeonKeepGame.setAlwaysOnTop(true);
		frmDungeonKeepGame.setBounds(100, 100, 670, 720);
		frmDungeonKeepGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDungeonKeepGame.getContentPane().setLayout(null);

		gameOptions = new GameOptions(frmDungeonKeepGame);
		gameOptions.setVisible(false);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Dialog", Font.BOLD, 15));
		frmDungeonKeepGame.setJMenuBar(menuBar);

		JMenuItem mntmNewGame = new JMenuItem("       New Game");
		mntmNewGame.setHorizontalAlignment(SwingConstants.CENTER);
		mntmNewGame.setFont(new Font("Dialog", Font.BOLD, 14));
		mntmNewGame.setBackground(Color.LIGHT_GRAY);
		menuBar.add(mntmNewGame);
		mntmNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newGame();
			}
		});

		mntmNewGame.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				JMenuItem item = (JMenuItem) e.getSource();                 
				item.setFont(new Font("Dialog", Font.BOLD, 16));
				item.setBackground(Color.GRAY);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				JMenuItem item = (JMenuItem) e.getSource(); 
				item.setFont(new Font("Dialog", Font.BOLD, 15));
				item.setBackground(Color.LIGHT_GRAY);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		JMenuItem mntmOptions = new JMenuItem("          Options");
		mntmOptions.setFont(new Font("Dialog", Font.BOLD, 15));
		menuBar.add(mntmOptions);
		mntmOptions.setBackground(Color.LIGHT_GRAY);
		mntmOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameOptions.setVisible(true);
			}
		});

		JMenuItem mntmEditMap = new JMenuItem("          Edit Map");
		mntmEditMap.setFont(new Font("Dialog", Font.BOLD, 15));
		menuBar.add(mntmEditMap);
		mntmEditMap.setBackground(Color.LIGHT_GRAY);
		mntmEditMap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] sizes = {"6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};
				String size = (String)JOptionPane.showInputDialog(
						frmDungeonKeepGame,
						"Please choose the number of rows/columns:\n",				                   
						"Game Editor",
						JOptionPane.PLAIN_MESSAGE,
						null,
						sizes,
						10);
				if(size != null){
					gameEditor = new GameEditor(frmDungeonKeepGame, Integer.parseInt(size));
					gameEditor.setVisible(true);
				}
			}
		});

		JMenuItem mntmLoadGame = new JMenuItem("       Load Game");
		menuBar.add(mntmLoadGame);
		mntmLoadGame.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmLoadGame.setBackground(Color.LIGHT_GRAY);
		mntmLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			
				Object[] possibilities = listSavedMaps();
				String s = (String)JOptionPane.showInputDialog(
						frmDungeonKeepGame,
						"Please choose the game:\n",				                   
						"Load Game",
						JOptionPane.PLAIN_MESSAGE,
						null,
						possibilities,
						"Game 1");

				if(s == null)
					return;

				newGame(loadGame(s));
			}
		});
	}

	public char[][] getMapCopy(char[][] map) {
		char[][] mapCopy = new char[map.length][map[0].length];

		for(int i = 0; i < map.length; i++)
			for(int j = 0; j < map[i].length; j++)
				mapCopy[i][j] = map[i][j];

		return mapCopy;
	}

	private Object[] listSavedMaps() {
		File folder = new File(BOARDS_DIR);
		File[] listOfFiles = folder.listFiles();

		String[] listString = new String[listOfFiles.length];
		for (int i = 0; i < listOfFiles.length; i++)
			listString[i] = listOfFiles[i].getName();

		return listString;
	}

	private char[][] loadGame(String fileName){
		String path = BOARDS_DIR + fileName;

		BufferedReader br;
		char[][] board;

		try {
			br = new BufferedReader(new FileReader(path));
			String line;
			int boardSize =  Integer.valueOf(br.readLine());
			board = new char[boardSize][boardSize];

			for(int i = 0; i < boardSize; i++) {
				line = br.readLine();
				board[i] = line.toCharArray();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return board;
	}
}
