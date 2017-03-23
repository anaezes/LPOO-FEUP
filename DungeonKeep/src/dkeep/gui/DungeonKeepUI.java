package dkeep.gui;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;

import dkeep.logic.Club;
import dkeep.logic.EnumGuardType;
import dkeep.logic.EnumMoves;
import dkeep.logic.ExitDoor;
import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.Hero;
import dkeep.logic.Key;
import dkeep.logic.Lever;
import dkeep.logic.Vilan;
import dkeep.logic.Vilan.EnumVillainType;
import utils.BoardUtils;
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
	private MouseListener menuBarMouseListener;

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
		int numOfOgres = gameOptions.getNumberOfOgres();
		EnumGuardType guardType = gameOptions.getGuardType();

		List<GameMap> gameMaps = new ArrayList<>();
		GameMap gameMap1 = new GameMap(BoardUtils.getSimpleBoardOne());
		GameMap gameMap2 = new GameMap(BoardUtils.getSimpleBoardTwo());
		gameMaps.add(gameMap1);
		gameMaps.add(gameMap2);

		game = new Game(gameMaps, guardType, numOfOgres);
		game.setGuardPath(BoardUtils.getSimpleGuardYmovement(), BoardUtils.getSimpleGuardXmovement());
		currentBoardSize = game.getBoard().getBoardSize();
		initJpanel();	
		initGraphics();
	}

	public void initJpanel() {
		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(currentBoardSize, currentBoardSize));
		gamePanel.setBounds(30, 30, 600, 600);
		gamePanel.setBackground(Color.WHITE);
		gamePanel.setFocusable(true);
		gamePanel.addKeyListener(keyListener);
		frmDungeonKeepGame.getContentPane().add(gamePanel);
		gamePanel.requestFocus();
	}

	public void newGame(char[][] board){
		GameMap gameMap = new GameMap(board);
		this.game = new Game(gameMap, false);
		currentBoardSize = game.getBoard().getBoardSize();
		initJpanel();	
		initGraphics();
	}

	private void initGraphics() {
		this.gameBoard = new JPanel[currentBoardSize][currentBoardSize];
		char character;

		for(int i = 0; i < currentBoardSize ; i++ )
			for(int j = 0; j < currentBoardSize; j++)
			{
				character = getObjectTypeAt(i, j);
				gameBoard[i][j] = new GameObject(gamePanel.getWidth()/currentBoardSize, gamePanel.getHeight()/currentBoardSize, character, i, j);
				gamePanel.add(gameBoard[i][j]);
			}

		frmDungeonKeepGame.repaint();
		frmDungeonKeepGame.revalidate();		
	}

	private void updateGraphics() {
		currentBoardSize = game.getBoard().getBoardSize();
		char character;

		for(int i = 0; i < currentBoardSize ; i++ )
			for(int j = 0; j < currentBoardSize; j++) {
				character = getObjectTypeAt(i, j);
				((GameObject)gameBoard[i][j]).switchType(character);
			}
	}

	private char getObjectTypeAt(int i, int j) {
		Hero hero = game.getHero();
		Lever lever = game.getLever();
		Key key = game.getKey();
		Club heroClub = game.getHeroClub();
		
		char character = game.getBoard().getBoardCaracter(i, j);

		if(hero.getXCoordinate() == i && hero.getYCoordinate() == j) 
			character = hero.getCharacter();

		else if(lever != null && lever.getXCoordinate() == i && lever.getYCoordinate() == j) 
			character = lever.getCharacter();

		else if(key != null && key.getXCoordinate() == i && key.getYCoordinate() == j) 
			character = key.getCharacter();

		else if(heroClub.getXCoordinate() == i && heroClub.getYCoordinate()  == j) 
			character = heroClub.getCharacter();

		List<ExitDoor> exitsDoors = game.getExitDoors();
		if(exitsDoors.size() != 0) {
			for(int k = 0; k < exitsDoors.size(); k++)
				if(exitsDoors.get(k).getXCoordinate() == i && exitsDoors.get(k).getYCoordinate() == j)
					character = exitsDoors.get(k).getCharacter();
		}

		char aux = vilansGraphics(i, j);
		if(aux != '\0')
			character = aux;

		return character;
	}

	private char vilansGraphics(int i, int j) {
		List<Vilan> vilans = game.getVilans();
		char character = '\0';
		if(vilans.size() != 0) {
			for(int k = 0; k < vilans.size(); k++) 
				if(vilans.get(k).getXCoordinate() == i && vilans.get(k).getYCoordinate() == j) {
					return vilans.get(k).getCharacter();
				}

			if(vilans.get(0).getType() == EnumVillainType.Ogre) 
				for(int k = 0; k < vilans.size(); k++)
					if(vilans.get(k).getClub().getXCoordinate() == i && vilans.get(k).getClub().getYCoordinate() == j) {
						return '*';
					}
		}
		return character;
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
		initFrame();
		initListeners();
		gameOptions = new GameOptions(frmDungeonKeepGame);
		gameOptions.setVisible(false);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Dialog", Font.BOLD, 15));
		frmDungeonKeepGame.setJMenuBar(menuBar);

		JMenuItem mntmNewGame = new JMenuItem("       New Game");
		menuBar.add(mntmNewGame);
		buttonNewGame(mntmNewGame);

		JMenuItem mntmOptions = new JMenuItem("          Options");
		menuBar.add(mntmOptions);
		buttonOptions(mntmOptions);

		JMenuItem mntmEditMap = new JMenuItem("          Edit Map");
		menuBar.add(mntmEditMap);
		buttonEditGame(mntmEditMap);

		JMenuItem mntmLoadGame = new JMenuItem("       Load Game");
		menuBar.add(mntmLoadGame);
		buttonLoadGame(mntmLoadGame);
	}

	private void initListeners() {
		menuBarMouseListener = new MouseListener() {
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
		};
	}

	private void initFrame() {
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

	private void buttonNewGame(JMenuItem mntmNewGame) {
		mntmNewGame.setHorizontalAlignment(SwingConstants.CENTER);
		mntmNewGame.setFont(new Font("Dialog", Font.BOLD, 14));
		mntmNewGame.setBackground(Color.LIGHT_GRAY);

		mntmNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newGame();
			}
		});

		mntmNewGame.addMouseListener(menuBarMouseListener);
	}

	private void buttonOptions(JMenuItem mntmOptions) {
		mntmOptions.setFont(new Font("Dialog", Font.BOLD, 15));
		mntmOptions.setBackground(Color.LIGHT_GRAY);
		mntmOptions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameOptions.setVisible(true);
			}
		});
		mntmOptions.addMouseListener(menuBarMouseListener);
	}


	private void buttonLoadGame(JMenuItem mntmLoadGame) {
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
		mntmLoadGame.addMouseListener(menuBarMouseListener);
	}

	private void buttonEditGame(JMenuItem mntmEditMap) {	
		mntmEditMap.setFont(new Font("Dialog", Font.BOLD, 15));
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
		mntmEditMap.addMouseListener(menuBarMouseListener);
	}
}
