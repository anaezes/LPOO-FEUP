package dkeep.logic;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dkeep.logic.Vilan.EnumVillainType;

/** 
 * Class Game
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */

public class Game implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * string SAVES_DIR, a static var with the path for saved games location
	 */
	public static final String SAVES_DIR = System.getProperty("user.dir") + "/saves/";
	
	/**
	 *  int attr gameId, identifies the game
	 */
	public int gameId;
	private EnumGameState state;
	private int level;

	private EnumGuardType guardType;

	//characters of game
	private Hero hero;
	private Club heroClub;
	private List<Vilan> vilans;

	//list of exits doors
	private List<ExitDoor> exitDoors;

	private boolean canMoveGuard;

	// flag of Lever in game
	private Lever lever;

	private Key key;

	//list of levels
	private List<GameMap> boards;

	// index of selected board
	private int indexBoard = 0;

	// selected board
	private GameMap selectedBoard;

	//flag is a unic level or a list of levels
	private boolean byLevel;

	//saves if is the second trie to unlock the stairs (second level)
	private int numberOfTries;
	private static final int tries = 2;
	private int numOgres;
	private boolean numOgresInMap;

	/**
	 * <strong>EnumGameState</strong> - Identifies the state of the game
	 * </br>
	 * <strong>Running</strong> - The game is running
	 * </br>
	 * <strong>Win</strong> - The game has ended and user win the game
	 * </br>
	 * <strong>Lost</strong> - The game has ended and user lost the game
	 */
	public enum EnumGameState {
		Running,
		Win,
		Lost,
	}
	
	/**
	 * Class Constructor Game specifying the board to play
	 * 
	 * @param board		the GameMap board to be used on the game, 
	 * @param isTest	a boolean to verify if is a game or a unit test on run
	 */
	public Game(GameMap board, boolean isTest) {
		setRandomId();
		this.guardType = null;
		this.selectedBoard = board;
		this.numOgresInMap = true;
		this.numOgres = -1;
		this.canMoveGuard = !isTest;
		this.selectedBoard = board;
		this.state = EnumGameState.Running;
		this.level = 0;
		this.byLevel = false;

		initGame();
	}
	
	/**
	 *  Class Constructor Game that receives the boards to be used in the game, the type of Guard present in first level and number of ogres (and respective Clubs) that are available to play with
	 * 
	 * @param boards		List of GameMap boards 
	 * @param guardType		Identifies if the Guard is Rockie, Drunk or Paranoid
	 * @param numOgres		Number of ogres in the current game
	 */

	public Game(List<GameMap> boards, EnumGuardType guardType, int numOgres) {
		setRandomId();
		this.boards = boards;
		this.selectedBoard = boards.get(indexBoard);
		this.guardType = guardType;
		this.numOgres = numOgres;
		this.numOgresInMap = false;
		this.canMoveGuard = false;
		this.numOgresInMap = false;
		initGame();
		this.state = EnumGameState.Running;
		this.level = 1;
		this.byLevel = true;
	}

	/**
	 * Class Constructor Game that receives the boards to be used in the game and number of ogres (and respective Clubs) that are available to play with
	 * 
	 * @param boards	List of GameMap boards 
	 * @param numOgres	Number of ogres in the current game
	 * 
	 */
	public Game(List<GameMap> boards, int numOgres) {
		setRandomId();
		this.boards = boards;
		this.selectedBoard = boards.get(indexBoard);
		this.guardType = null;
		this.numOgres = numOgres;
		this.numOgresInMap = false;
		this.lever = null;
		this.key = null;
		this.canMoveGuard = false;
		initGame();
		this.state = EnumGameState.Running;
		this.level = 1;

		this.byLevel = true;
	}
	/**
	 *  Change the ID game randomly 
	 * 
	 */
	public void setRandomId(){
		Random oj = new Random();
		gameId = oj.nextInt(100000);
	}

	/**
	 * Initialize the game and verify if exist Ogres
	 * <br> If there are ogres on the game - they will take their positions on the map and put the key on board </br>
	 * <br> If there are not ogres - only the key take its position </br>
	 * */

	public void initGame() {
		this.lever = null;
		this.key = null;
		numberOfTries = 0;
		this.heroClub = new Club(-1, -1);

		if(numOgresInMap)
			initNumOgresInMap();
		else
			initNoOgresInMap();
	}


	/**
	 * <br>Set key in its position </br>
	 * <br>Set lever in its position</br>
	 * <br>Set exit doors in their position </br>
	 * <br> by position - assume position in the current game board </br>
	 */
	
	private void initNoOgresInMap() {
		boolean ogres = false;
		char character;
		int x_ogre= 0, y_ogre = 0, x_ogre_club = -1, y_ogre_club = -1;
		int tmp_x = 0, tmp_y=0;
		List<ExitDoor> exit = new ArrayList<>();

		for(int i = 0; i < selectedBoard.getBoardSize(); i++) {
			for(int j = 0; j < selectedBoard.getBoardSize(); j++) {
				character = selectedBoard.getBoardCaracter(i,j);
				if(character == 'O') {
					x_ogre = i; y_ogre = j;
					ogres = true;
					selectedBoard.setBoardCaracter(i, j , ' ');
				}
				else if(character == '*') {
					x_ogre_club = i; y_ogre_club = j;
					selectedBoard.setBoardCaracter(i, j , ' ');
				}
				else if(character == 'k' || character == 'c') 	{	
					tmp_x=i; tmp_y=j;
				}
				else
					readNonOgreObjects(character, exit, i, j);
			}
		}
		this.exitDoors = exit;
		this.exitDoors = exit;
		if(ogres) {
			initOgres(x_ogre, y_ogre, x_ogre_club, y_ogre_club);
			this.key = new Key(tmp_x, tmp_y);
		}
		else this.lever = new Lever(tmp_x, tmp_y);
	}


	/**
	 * Set the ogres in their position
	 * <br>Set key in its position </br>
	 * <br>Set lever in its position</br>
	 * <br>Set exit doors in their position </br>
	 * <br> by position - assume position in the current game board </br>
	 */
	
	private void initNumOgresInMap() {
		boolean ogres = false;
		int tmp_x = 0, tmp_y=0;
		char character;
		List<ExitDoor> exit = new ArrayList<>();
		List<Integer> x_ogres = new ArrayList<>(), y_ogres = new ArrayList<>();
		List<Integer> x_clubs = new ArrayList<>(), y_clubs = new ArrayList<>();

		for(int i = 0; i < selectedBoard.getBoardSize(); i++) {
			for(int j = 0; j < selectedBoard.getBoardSize(); j++) {
				character = selectedBoard.getBoardCaracter(i,j);
				if(character == 'O') {
					ogres = true;
					x_ogres.add(i); y_ogres.add(j);
					selectedBoard.setBoardCaracter(i, j , ' ');
				}
				else if(character == '*') {
					x_clubs.add(i); y_clubs.add(j);
					selectedBoard.setBoardCaracter(i, j , ' ');
				}
				else if(character== 'k' || character == 'c'){	
					tmp_x=i; tmp_y=j;
				}
				else
					readNonOgreObjects(character, exit, i, j);
			}
		}
		this.exitDoors = exit;
		if(ogres) {
			initOgresList(x_ogres, y_ogres, x_clubs, y_clubs);
			this.key = new Key(tmp_x, tmp_y);
		}
		else this.lever = new Lever(tmp_x, tmp_y);
	}

	private void readNonOgreObjects(char character, List<ExitDoor> exit, int i, int j){
		verifyifIsHero(i,j);
		verifyIfIsHeroClub(i, j);
		verifyIfIsGuard(i, j);
		verifyIfIsExitDoor(exit, i, j);
		if(character == 'I') 
			selectedBoard.setBoardCaracter(i, j , 'x');
	}

	private boolean verifyIfIsExitDoor(List<ExitDoor> exit, int i, int j) {
		char character = selectedBoard.getBoardCaracter(i,j);
		if(character == 'S' || character == 'I') {
			exit.add(new ExitDoor(i, j));
			selectedBoard.setBoardCaracter(i, j , 'I');
			return true;
		}
		return false;
	}

	private boolean verifyIfIsGuard(int i, int j) {
		if(selectedBoard.getBoardCaracter(i,j) == 'G') {
			this.vilans = initGuard(i, j);
			selectedBoard.setBoardCaracter(i, j , ' ');
			return true;
		}
		return false;
	}

	private boolean verifyIfIsHeroClub(int i, int j) {
		if(selectedBoard.getBoardCaracter(i, j)== 'a') {			
			this.heroClub = new Club(i,j);
			return true;
		}	
		return false;
	}

	/**
	 * Verify if the board has a character 'h' or 'H' that represents the Hero on the position [x][y]
	 * @param i		the x-coordinate
	 * @param j		the y-coordinate
	 * @return		true if Hero exists or false otherwise
	 */
	
	public boolean verifyifIsHero(int i, int j){
		char character = selectedBoard.getBoardCaracter(i,j);
		if(character == 'h' || character == 'H') {
			this.hero = new Hero(i,j);
			selectedBoard.setBoardCaracter(i, j , ' ');
			return true;
		}
		return false;
	}

	/**
	 * Adds this specific Guard to Villan's List
	 * <br>If not choosen, guardType is choose randomly</br>
	 * @param i 	the x-coordinate of guard
	 * @param j		the y-coordinate of guard
	 * @return		return Villan's ArrayList with the guard added
	 */
	public List<Vilan> initGuard(int i, int j) {

		int num = 0;
		if (this.guardType == null) {
			Random oj = new Random();
			num = oj.nextInt(3);
		} 
		else {
			if (this.guardType == EnumGuardType.Rockie) 
				num = 0;	
			else if (this.guardType == EnumGuardType.Drunk) 
				num = 1;
			else
				num = 2;	
		}

		List<Vilan> v = new ArrayList<>();
		switch(num) {
		case 0:
			v.add(new RookieGuard(i, j));
			break;
		case 1:
			v.add(new DrunkGuard(i, j));
			break;
		case 2:
			v.add(new ParanoidGuard(i, j));
			break;
		}

		return v;
	}

	/**
	 * Add various ogres and their respective clubs to Villan's ArrayList
	 * @param x_ogres		List<Integer> with x-coordinate of each ogre
	 * @param y_ogres		List<Integer> with y-coordinate of each ogre
	 * @param x_clubs		List<Integer> with x-cooydinate of each club
	 * @param y_clubs		List<Integer> with y-coordinate of each club
	 */
	
	public void initOgresList(List<Integer> x_ogres, List<Integer> y_ogres, List<Integer> x_clubs, List<Integer> y_clubs) {

		List<Vilan> v = new ArrayList<>();
		for(int i = 0; i < x_ogres.size(); i++)
			v.add(new Ogre(x_ogres.get(i), y_ogres.get(i), x_clubs.get(i), y_clubs.get(i)));
		this.vilans = v;
	}
	
	/**
	 * Add one ogre and his club to the Villans' ArrayList according their coordinates
	 * <br> If number of ogres is not defined - it will be randomly calculated</br>
	 * @param x_ogre		 x-coordinate of ogre
	 * @param y_ogre		 y-coordinate of ogre
	 * @param x_club		 x-coordinate of club
	 * @param y_club		 y-coordinate of club
	 */

	public void initOgres(int x_ogre, int y_ogre, int x_club, int y_club) {

		List<Vilan> v = new ArrayList<>();	

		if(numOgres != -1) {
			for(int i = 0; i < numOgres; i++)
				v.add(new Ogre(x_ogre, y_ogre, x_club, y_club));
		}
		else {
			Random oj = new Random();
			numOgres = oj.nextInt(5)+1;

			for(int i = 0; i < numOgres; i++)
				v.add(new Ogre(x_ogre, y_ogre, x_club, y_club));
		}

		this.vilans = v;
		this.canMoveGuard = true;
	}

	/**
	 * Set guard's path with predifined positions
	 * @param x		int[] with x-coordinates of the guard
	 * @param y		int[] with y-coordinates of the guard
	 */
	public void setGuardPath(int[] x, int[] y)  {
		if(x != null) {
			for(Vilan v : vilans) {
				if(v.getType() == EnumVillainType.Guard)
					((Guard) v).setPath(x, y);
			}
			this.canMoveGuard = true;
		}
	}
	/**
	 * Returns current game state
	 * @return		 game state
	 */
	public EnumGameState getGameState() {
		return state;
	}
	
	/**
	 * Returns current game level
	 * @return game level
	 */
	public int getGameLevel() {
		return level;
	}
	
	
	/**
	 * Set the game with state 
	 * @param state		the new state to be attributed to the game
	 */
	public void setGameState(EnumGameState state) {
		this.state = state;
	}

	/**
	 * Increments the current game state
	 */
	public void nextLevel() {
		this.level++;
	}

	/**
	 * Check if the player lost the game and update game state
	 */
	public void checkLostGame() {
		for(int i = 0; i < vilans.size(); i++) {			
			if(verifyHeroIsNextToVilan(i)){
				if(hero.isHeroArmed())
					((Ogre)vilans.get(i)).putStunned();
				else
					state = EnumGameState.Lost;
			}				
			if(vilans.get(i).getType() == EnumVillainType.Ogre) 
				verifyHeroIsNextToClub(i);		
		}
	}

	private void verifyHeroIsNextToClub(int i) {
		int x_ogre = vilans.get(i).getClub().getXCoordinate();
		int y_ogre = vilans.get(i).getClub().getYCoordinate();
		if(((y_ogre == (hero.getYCoordinate()+1) || y_ogre == (hero.getYCoordinate()-1)) && x_ogre == (hero.getXCoordinate())) || 
				(x_ogre== (hero.getXCoordinate()+1) || x_ogre == (hero.getXCoordinate()-1)) && y_ogre == (hero.getYCoordinate())
				|| (x_ogre == hero.getXCoordinate()) && y_ogre == hero.getYCoordinate())
			state = EnumGameState.Lost;
	}

	private boolean verifyHeroIsNextToVilan(int i) {
		if(((vilans.get(i).getYCoordinate() == (hero.getYCoordinate()+1) || vilans.get(i).getYCoordinate() == (hero.getYCoordinate()-1)) && vilans.get(i).getXCoordinate() == (hero.getXCoordinate())) || 
				(vilans.get(i).getXCoordinate() == (hero.getXCoordinate()+1) || vilans.get(i).getXCoordinate() == (hero.getXCoordinate()-1)) && vilans.get(i).getYCoordinate() == (hero.getYCoordinate()))
			return true;
		return false;
	}

	/**
	 * Change hero's position according the movement received
	 * @param movement 		Identify if the move is Up, Down, Left or Right
	 */
	public void moveHero(EnumMoves movement) {

		int x_hero = hero.getXCoordinate();
		int y_hero = hero.getYCoordinate();

		if(movement == EnumMoves.LEFT) 
			y_hero -= 1;
		else if(movement == EnumMoves.RIGHT)	
			y_hero += 1;
		else if(movement == EnumMoves.UP)
			x_hero -= 1;
		else if(movement == EnumMoves.DOWN)
			x_hero += 1;

		if(canMoveHero(x_hero, y_hero)) {
			hero.moveHero(x_hero, y_hero);
			hero.setCharacter();
		}

		if(canMoveGuard)
			moveVilans();

		checkLostGame();
	}

	private boolean canMoveHero(int x_hero, int y_hero) {

		if(selectedBoard.getSelectedBoard()[x_hero][y_hero] == 'X' || selectedBoard.getSelectedBoard()[x_hero][y_hero] == 'x')
			return false;

		if(selectedBoard.getSelectedBoard()[x_hero][y_hero] == ' ')
			return true;

		if(!exitDoor(x_hero, y_hero))
			return false;

		else if(verifyHeroClub(x_hero, y_hero) || verifyLever(x_hero, y_hero) || verifyKey(x_hero, y_hero))
			return true;

		return false;
	}

	private boolean verifyKey(int x_hero, int y_hero) {
		if(key != null && key.getXCoordinate() == x_hero && key.getYCoordinate() == y_hero) {		
			key.setKeyState(true);
			unlockDoors();
			return true;
		}
		return false;
	}

	private boolean verifyLever(int x_hero, int y_hero) {
		if(lever != null && lever.getXCoordinate() == x_hero && lever.getYCoordinate() == y_hero) {
			lever.setLeverState(true);
			hero.setHeroOnLever(true);
			unlockDoors();
			transformToStaires();
			return true;
		}
		return false;
	}

	private boolean verifyHeroClub(int x_hero, int y_hero) {
		if((!hero.isHeroArmed() && x_hero == heroClub.getXCoordinate() && y_hero == heroClub.getYCoordinate())) {
			getBoard().setBoardCaracter(heroClub.getXCoordinate(), heroClub.getYCoordinate() , ' ');
			hero.setHeroArmed();
			heroClub.findClub();
			return true;
		}
		return false;
	}

	private boolean exitDoor(int x_hero, int y_hero) {

		if(isExitDoor(x_hero, y_hero))
		{
			if((lever != null && lever.getLeverState()) ||
					(key != null && key.getKeyState() && isSecondTrie()))

				if(byLevel && boards.size() > (indexBoard+1)) {
					this.indexBoard++;
					this.selectedBoard = boards.get(indexBoard);
					this.level = indexBoard+1;
					initGame();
					y_hero = this.hero.getYCoordinate();
					x_hero = this.hero.getXCoordinate();
				} else {
					setGameState(EnumGameState.Win);
					hero.setCaracter(' ');
				}

			return false;
		}
		return true;
	}

	private boolean isExitDoor(int x_hero, int y_hero) {
		for(int i = 0; i < exitDoors.size(); i++)
			if(exitDoors.get(i).getXCoordinate() == x_hero && exitDoors.get(i).getYCoordinate() == y_hero)
				return true;
		return false;
	}

	private boolean isSecondTrie() {
		numberOfTries++;
		transformToStaires();
		if(numberOfTries == tries) {
			transformToStaires();
			return true;
		}
		return false;
	}


	private void transformToStaires() {
		for(int i = 0; i < exitDoors.size(); i++)
			exitDoors.get(i).transformToStairs();
	}

	/**
	 * Unlock Doors and change its representation to 'P'
	 */
	public void unlockDoors() {
		for(int i = 0; i < exitDoors.size(); i++)
			exitDoors.get(i).openExitDoor();
	}

	/**
	 * Move Vilans
	 * <br> If the vilan is an ogre, move its club too</br>
	 */
	public void moveVilans() {
		for(int i = 0; i < vilans.size(); i++) {
			vilans.get(i).move(selectedBoard);
			if(vilans.get(i).getType() == EnumVillainType.Ogre)
				vilans.get(i).checkClub(selectedBoard);
		}
	}

	/**
	 * Returns Hero
	 * @return		Hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Returns Villans 
	 * @return 		Villans
	 */
	public List<Vilan> getVilans() {
		return vilans;
	}

	/**
	 *  Returns Hero's Club
	 * @return  Club
	 */
	public Club getHeroClub() {
		return heroClub;
	}

	/**
	 * Returns game current's map
	 * @return map
	 */
	public GameMap getBoard() {
		return selectedBoard;
	}

	/**
	 * Returns Exit Doors
	 * @return exitDoors
	 */
	public List<ExitDoor> getExitDoors() {
		return exitDoors;
	}

	/**
	 * Check if the current game state is Lost
	 * @return		true if game state is equal to Lost or false otherwise
	 */
	public boolean isGameOver() {
		return this.state == EnumGameState.Lost;
	}

	/**
	 * Returns actual game's lever
	 * @return 	lever
	 */
	public Lever getLever() {
		return lever;
	}

	/**
	 * Returns actual game's key
	 * @return 	key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * Determines if guard can be moved or not
	 * @param b		boolean that identifies if guard can be moved or not
	 */
	public void setCanMoveGuard(boolean b) {
		canMoveGuard = b;		
	}

	/**
	 * Number of Ogres
	 * @return numOgres
	 */
	public int getNumOgres() {
		return numOgres;
	}

	/**
	 * Returns guard's type: Paranoid, Rockie or Drunk
	 * @return guardType
	 */
	public EnumGuardType getGuardType() {
		return guardType;
	}
	

	/**
	 * Write game's object instance into a file 
	 */
	public void serialize() {      
	      try {
	         FileOutputStream fileOut = new FileOutputStream(SAVES_DIR + "gameSaved.bin");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(this);
	         out.close();
	         fileOut.close();
	      }catch(IOException i) {
	         i.printStackTrace();
	      }   
	}
	
	/**
	 * Read the saved game from file and deserialize into game object
	 * @return 		the game object
	 */
	public static Game deserialize() {
		Game game = null;
	      try {
	         FileInputStream fileIn = new FileInputStream(SAVES_DIR + "gameSaved.bin");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         game = (Game) in.readObject();
	         in.close();
	         fileIn.close();
	         return game;
	      }catch(IOException i) {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c) {
	         c.printStackTrace();
	         return null;
	      }
	   }

}



