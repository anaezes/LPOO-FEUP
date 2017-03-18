package dkeep.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import ckeep.cli.Print;
import dkeep.logic.EnumGuardType;
import dkeep.logic.EnumMoves;
import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.Vilan.EnumVillainType;
import dkeep.logic.Game.EnumGameState;
import javax.swing.text.NumberFormatter;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;


public class DungeonKeepUI {

	private JFrame frame;
	private JFormattedTextField numberOfOgres;
	private JComboBox<EnumGuardType> guardsCombo;
	private JButton DownBtn;
	private JButton RightBtn;
	private JButton LeftBtn;
	private JButton UpBtn;
	private JLabel gameStatusLabel;
	private Game game;
	private JPanel gamePanel;
	private JPanel[][] gameBoard;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DungeonKeepUI window = new DungeonKeepUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
				);
	}

	/**
	 * Create the application.
	 */
	public DungeonKeepUI() {
		initialize();
	}

	public void newGame() {
		int[] guard_y = new int[] {8, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8};
		int[] guard_x = new int[] {1, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 5, 4, 3, 2};

		char[][] BoardOne = {{'X','X','X','X','X','X','X','X','X','X'},
				{'X','h',' ',' ','I', ' ', 'X', ' ', 'G', 'X'},
				{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
				{'X',' ','I',' ','I', ' ', 'X', ' ', ' ', 'X'},
				{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
				{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
				{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
				{'X','X','X',' ','X', 'X', 'X', 'X', ' ', 'X'},
				{'X',' ','I',' ','I', ' ', 'X', 'k', ' ', 'X'},
				{'X','X','X','X','X','X','X','X','X','X'}};

		char[][] BoardTwo = {{'X','X','X','X','X','X','X','X','X'},
				{'S',' ',' ',' ',' ', ' ', ' ', 'k', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','h','a',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','X','X','X','X','X','X','X','X'}};

		int numOfOgres = Integer.parseInt(numberOfOgres.getText());
		System.out.println(numOfOgres);
		EnumGuardType guardType = EnumGuardType.valueOf(guardsCombo.getSelectedItem().toString());
		System.out.println(guardType);

		switch(numOfOgres) {
		case 1: //in case 1 ogre
			BoardTwo[1][4] = 'O';
			BoardTwo[1][5] = '*';
			break;
		case 2: // in case 2 ogres
			BoardTwo[1][4] = 'O';
			BoardTwo[1][5] = '*';
			BoardTwo[3][3] = 'O';
			BoardTwo[3][4] = '*';
			break;
		case 3: // in case 3 ogres
			BoardTwo[1][4] = 'O';
			BoardTwo[1][5] = '*';
			BoardTwo[3][3] = 'O';
			BoardTwo[3][4] = '*';
			BoardTwo[6][5] = 'O';
			BoardTwo[6][6] = '*';
			break;
		case 4: // in case 4 ogres
			BoardTwo[1][4] = 'O';
			BoardTwo[1][5] = '*';
			BoardTwo[3][3] = 'O';
			BoardTwo[3][4] = '*';
			BoardTwo[6][5] = 'O';
			BoardTwo[6][6] = '*';
			BoardTwo[2][3] = 'O';
			BoardTwo[2][4] = '*';
			break;
		case 5: // in case 5 ogres
			BoardTwo[1][4] = 'O';
			BoardTwo[1][5] = '*';
			BoardTwo[3][3] = 'O';
			BoardTwo[3][4] = '*';
			BoardTwo[6][5] = 'O';
			BoardTwo[6][6] = '*';
			BoardTwo[2][3] = 'O';
			BoardTwo[2][4] = '*';
			BoardTwo[4][1] = 'O';
			BoardTwo[4][2] = '*';
			break;	
		}

		List<GameMap> gameMaps = new ArrayList<>();
		GameMap gameMap1 = new GameMap(BoardOne);
		GameMap gameMap2 = new GameMap(BoardTwo);
		gameMaps.add(gameMap1);
		gameMaps.add(gameMap2);

		Game newGame = new Game(gameMaps, guardType);
		newGame.setGuardPath(guard_y, guard_x);
		this.game = newGame;
		setMovementButtons(true);
		setGameStatusLabelText("Prepare to figth!!! Click on buttons to move hero!");
	}

	private void initGraphics() {
		int aux = game.getBoard().getBoardSize();
		this.gameBoard = new JPanel[aux][aux];

		char character;
		for(int i = 0; i < aux ; i++ )
			for(int j = 0; j < aux; j++) {
				character = game.getBoard().getBoardCaracter(i, j);

				if(game.getHero().getXCoordinate() == i && game.getHero().getYCoordinate() == j) 
					character = game.getHero().getCharacter();
				
				else if(game.getLever().getXCoordinate() == i && game.getLever().getYCoordinate() == j) 
					character = game.getLever().getCharacter();

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
				
				
				gameBoard[i][j] = new GameObject(gamePanel.getWidth()/aux, gamePanel.getHeight()/aux, character);
				gamePanel.add(gameBoard[i][j]);	
			}
	}

	private void updateGraphics() {

		int aux = game.getBoard().getBoardSize();

		char character;
		for(int i = 0; i < aux ; i++ )
			for(int j = 0; j < aux; j++) {
				character = game.getBoard().getBoardCaracter(i, j);

				if(character == 'X' && character == 'I')
					continue;
				
				else if(game.getHero().getXCoordinate() == i && game.getHero().getYCoordinate() == j) 
					character = game.getHero().getCharacter();

				else if(game.getLever().getXCoordinate() == i && game.getLever().getYCoordinate() == j) 
					character = game.getLever().getCharacter();

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



	private void setGameStatusLabelText(String text) {
		gameStatusLabel.setText(text);
	}

	private void validateGameRunning() {
		if(game.getGameState() == EnumGameState.Win)
		{
			setGameStatusLabelText("YOU WIN!");
			setMovementButtons(false);

		}
		else if(game.getGameState() == EnumGameState.Lost){
			setGameStatusLabelText("Game Over!");
			setMovementButtons(false);
		}
	}

	private void setMovementButtons(boolean stateBtn) {
		UpBtn.setEnabled(stateBtn);
		RightBtn.setEnabled(stateBtn);
		DownBtn.setEnabled(stateBtn);
		LeftBtn.setEnabled(stateBtn);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(Color.GRAY);
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 730);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblNumberOfOgres = new JLabel("Number of Ogres");
		lblNumberOfOgres.setBounds(12, 14, 135, 15);
		frame.getContentPane().add(lblNumberOfOgres);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(5);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);

		numberOfOgres = new JFormattedTextField(formatter);
		numberOfOgres.setBounds(155, 12, 49, 19);
		frame.getContentPane().add(numberOfOgres);
		numberOfOgres.setColumns(10);
		numberOfOgres.setText("1");

		JLabel lblGuardPersonality = new JLabel("Guard personality");
		lblGuardPersonality.setBounds(235, 14, 150, 15);
		frame.getContentPane().add(lblGuardPersonality);

		guardsCombo = new JComboBox<EnumGuardType>();
		guardsCombo.setModel(new DefaultComboBoxModel<EnumGuardType>(EnumGuardType.values()));
		guardsCombo.setBounds(400, 9, 129, 20);
		frame.getContentPane().add(guardsCombo);

		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				newGame();
				gamePanel = new JPanel();
				gamePanel.setLayout(new GridLayout(game.getBoard().getBoardSize(), game.getBoard().getBoardSize()));
				gamePanel.setBounds(12, 50, 600, 600);
				gamePanel.setBackground(Color.WHITE);
				frame.getContentPane().add(gamePanel);


				initGraphics();
			}
		});
		btnNewGame.setBounds(645, 53, 115, 25);
		frame.getContentPane().add(btnNewGame);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnExit.setBounds(645, 202, 115, 25);
		frame.getContentPane().add(btnExit);

		UpBtn = new JButton("UP");
		UpBtn.setEnabled(false);
		UpBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.moveHero(EnumMoves.UP);
				updateGraphics();
				gamePanel.repaint();
				validateGameRunning();
			}
		});
		UpBtn.setBounds(667, 90, 80, 20);
		frame.getContentPane().add(UpBtn);

		DownBtn = new JButton("DOWN");
		DownBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.moveHero(EnumMoves.DOWN);
				updateGraphics();
				gamePanel.repaint();
				validateGameRunning();
			}
		});
		DownBtn.setEnabled(false);
		DownBtn.setBounds(667, 154, 80, 20);
		frame.getContentPane().add(DownBtn);

		RightBtn = new JButton("RIGHT");
		RightBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.moveHero(EnumMoves.RIGHT);
				updateGraphics();
				gamePanel.repaint();
				validateGameRunning();
			}
		});
		RightBtn.setEnabled(false);
		RightBtn.setBounds(708, 122, 80, 20);
		frame.getContentPane().add(RightBtn);

		LeftBtn = new JButton("LEFT");
		LeftBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.moveHero(EnumMoves.LEFT);
				updateGraphics();
				gamePanel.repaint();
				validateGameRunning();
			}
		});
		LeftBtn.setEnabled(false);
		LeftBtn.setBounds(622, 122, 80, 20);
		frame.getContentPane().add(LeftBtn);

		gameStatusLabel = new JLabel("MessageStatus");
		gameStatusLabel.setBounds(12, 666, 374, 15);
		frame.getContentPane().add(gameStatusLabel);

		setGameStatusLabelText("Please click New Game to start!");
	}
}
