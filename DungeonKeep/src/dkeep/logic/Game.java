package dkeep.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dkeep.logic.Vilan.EnumVillainType;

public class Game {

	private EnumGameState state;
	//private EnumLevel level;
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

	public enum EnumGameState {
		Running,
		Win,
		Lost,
	}

	public Game(GameMap board, boolean isTest) {
		this.guardType = null;
		this.selectedBoard = board;
		this.numOgresInMap = true;
		this.numOgres = -1;
		this.canMoveGuard = !isTest;
		this.selectedBoard = board;
		this.state = EnumGameState.Running;
		this.level = 0;
		this.byLevel = false;

		initGame(board);
	}

	//	public Game(List<GameMap> boards) {
	//		this.boards = boards;
	//		this.selectedBoard = boards.get(indexBoard);
	//		this.guardType = null;
	//		this.numOgresPredefined = false;
	//		this.canMoveGuard = false;
	//		initGame(selectedBoard);
	//		this.state = EnumGameState.Running;
	//		this.level = 1;
	//		this.byLevel = true;
	//	}

	public Game(List<GameMap> boards, EnumGuardType guardType, int numOgres) {
		this.boards = boards;
		this.selectedBoard = boards.get(indexBoard);
		this.guardType = guardType;
		this.numOgres = numOgres;
		System.out.println("n_ogres pimmmm" + numOgres);
		this.numOgresInMap = false;
		this.canMoveGuard = false;
		this.numOgresInMap = false;
		initGame(selectedBoard);
		this.state = EnumGameState.Running;
		this.level = 1;
		this.byLevel = true;
	}

	public Game(List<GameMap> boards, int numOgres) {
		this.boards = boards;
		this.selectedBoard = boards.get(indexBoard);
		this.guardType = null;
		this.numOgres = numOgres;
		this.numOgresInMap = false;
		this.lever = null;
		this.key = null;
		this.canMoveGuard = false;
		initGame(selectedBoard);
		this.state = EnumGameState.Running;
		this.level = 1;

		this.byLevel = true;
	}

	/**
	 * Identify all features from the game board
	 * */
	public void initGame(GameMap board) {

		this.lever = null;
		this.key = null;

		List<ExitDoor> exit = new ArrayList<>();

		numberOfTries = 0;
		this.heroClub = new Club(-1, -1);
		boolean ogres = false;


		List<Integer> x_ogres = new ArrayList<>();
		List<Integer> y_ogres = new ArrayList<>();
		List<Integer> x_clubs = new ArrayList<>();
		List<Integer> y_clubs = new ArrayList<>();


		int x_ogre= 0, y_ogre = 0, x_ogre_club = -1, y_ogre_club = -1;
		int tmp_x = 0, tmp_y=0;



		for(int i = 0; i < board.getBoardSize(); i++) {
			for(int j = 0; j < board.getBoardSize(); j++) {
				//hero
				if(board.getBoardCaracter(i, j) == 'h' || board.getBoardCaracter(i, j) == 'H') {
					this.hero = new Hero(i,j);
					board.setBoardCaracter(i, j , ' ');
				}
				//lever or key
				else if(board.getBoardCaracter(i, j)== 'k' || board.getBoardCaracter(i, j) == 'c') {			
					tmp_x=i;
					tmp_y=j;
				}
				//shield 
				else if(board.getBoardCaracter(i, j)== 'a') {			
					this.heroClub = new Club(i,j);
				}
				//guard
				else if(board.getBoardCaracter(i,j) == 'G') {
					this.vilans = initGuard(i, j);
					board.setBoardCaracter(i, j , ' ');
				}
				//Ogre
				else if(board.getBoardCaracter(i,j) == 'O') {
					if(numOgresInMap)
					{
						x_ogres.add(i);
						y_ogres.add(j);
					}
					else
					{
						x_ogre = i;
						y_ogre = j;
					}
					ogres = true;
					board.setBoardCaracter(i, j , ' ');
				}

				else if(board.getBoardCaracter(i,j) == '*') {
					if(numOgresInMap) {
						x_clubs.add(i);
						y_clubs.add(j);
					}
					else {
						x_ogre_club = i;
						y_ogre_club = j;
					}
					board.setBoardCaracter(i, j , ' ');
				}

				else if(board.getBoardCaracter(i,j) == 'S' || board.getBoardCaracter(i,j) == 'I') {
					exit.add(new ExitDoor(i, j));
					board.setBoardCaracter(i, j , 'I');
				}

				else if(board.getBoardCaracter(i,j) == 'I') {
					board.setBoardCaracter(i, j , 'x');
				}
			}
		}

		this.exitDoors = exit;
		if(ogres) {
			if(numOgresInMap)
				initOgres(x_ogres, y_ogres, x_clubs, y_clubs);
			else
				initOgres(x_ogre, y_ogre, x_ogre_club, y_ogre_club);

			this.key = new Key(tmp_x, tmp_y);
		}
		else
			this.lever = new Lever(tmp_x, tmp_y);
	}

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

	public void initOgres(List<Integer> x_ogres, List<Integer> y_ogres, List<Integer> x_clubs, List<Integer> y_clubs) {

		List<Vilan> v = new ArrayList<>();
		System.out.println("x ogres" + x_ogres.size());
		System.out.println("x clubs" + x_clubs.size());
		for(int i = 0; i < x_ogres.size(); i++)
			v.add(new Ogre(x_ogres.get(i), y_ogres.get(i), x_clubs.get(i), y_clubs.get(i)));
		this.vilans = v;
	}
	
	public void initOgres(int x_ogre, int y_ogre, int x_club, int y_club) {

		List<Vilan> v = new ArrayList<>();	

		if(numOgres != -1) {
			for(int i = 0; i < numOgres; i++)
			{
				v.add(new Ogre(x_ogre, y_ogre, x_club, y_club));
				System.out.println("n_ogres" + numOgres);
			}
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

	public void setGuardPath(int[] x, int[] y)  {
		if(x != null) {
			for(Vilan v : vilans) {
				if(v.getType() == EnumVillainType.Guard)
					((Guard) v).setPath(x, y);
			}
			this.canMoveGuard = true;
		}
	}

	public EnumGameState getGameState() {
		return state;
	}

	public int getGameLevel() {
		return level;
	}

	public void setGameState(EnumGameState state) {
		this.state = state;
	}

	public void nextLevel() {
		this.level++;
	}

	public void checkLostGame()
	{
		int x_ogre;
		int y_ogre;
		boolean check;
		for(int i = 0; i < vilans.size(); i++) {

			check = false;
			if(((vilans.get(i).getYCoordinate() == (hero.getYCoordinate()+1) || vilans.get(i).getYCoordinate() == (hero.getYCoordinate()-1)) && vilans.get(i).getXCoordinate() == (hero.getXCoordinate())) || 
					(vilans.get(i).getXCoordinate() == (hero.getXCoordinate()+1) || vilans.get(i).getXCoordinate() == (hero.getXCoordinate()-1)) && vilans.get(i).getYCoordinate() == (hero.getYCoordinate()))
				check = true;

			if(check && hero.isHeroArmed())
				((Ogre)vilans.get(i)).putStunned();
			else if(check)
				state = EnumGameState.Lost;

			if(vilans.get(i).getType() == EnumVillainType.Ogre) {		
				x_ogre = vilans.get(i).getClub().getXCoordinate();
				y_ogre = vilans.get(i).getClub().getYCoordinate();
				if(((y_ogre == (hero.getYCoordinate()+1) || y_ogre == (hero.getYCoordinate()-1)) && x_ogre == (hero.getXCoordinate())) || 
						(x_ogre== (hero.getXCoordinate()+1) || x_ogre == (hero.getXCoordinate()-1)) && y_ogre == (hero.getYCoordinate())
						|| (x_ogre == hero.getXCoordinate()) && y_ogre == hero.getYCoordinate())
					state = EnumGameState.Lost;
			}
		}

	}

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

		if(isExitDoor(x_hero, y_hero))
		{
			if((lever != null && lever.getLeverState()) ||
					(key != null && key.getKeyState() && isSecondTrie()))

				if(byLevel && boards.size() > (indexBoard+1)) {
					this.indexBoard++;
					this.selectedBoard = boards.get(indexBoard);
					this.level = indexBoard+1;
					initGame(selectedBoard);
					y_hero = this.hero.getYCoordinate();
					x_hero = this.hero.getXCoordinate();
				} else {
					setGameState(EnumGameState.Win);
					hero.setCaracter(' ');
				}

			return false;
		}

		else if((!hero.isHeroArmed() && x_hero == heroClub.getXCoordinate() && y_hero == heroClub.getYCoordinate()))
		{
			getBoard().setBoardCaracter(heroClub.getXCoordinate(), heroClub.getYCoordinate() , ' ');
			hero.setHeroArmed();
			heroClub.findClub();
			return true;
		}

		else if(lever != null && lever.getXCoordinate() == x_hero && lever.getYCoordinate() == y_hero) {
			lever.setLeverState(true);
			hero.setHeroOnLever(true);
			unlockDoors();
			transformToStaires();
			return true;
		}

		else if(key != null && key.getXCoordinate() == x_hero && key.getYCoordinate() == y_hero) {		
			key.setKeyState(true);
			unlockDoors();
			return true;
		}
		return false;
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

	public void unlockDoors() {
		for(int i = 0; i < exitDoors.size(); i++)
			exitDoors.get(i).openExitDoor();
	}

	public void moveVilans() {
		for(int i = 0; i < vilans.size(); i++) {
			vilans.get(i).move(selectedBoard);
			if(vilans.get(i).getType() == EnumVillainType.Ogre)
				vilans.get(i).checkClub(selectedBoard);
		}
	}

	private int getNumOgres() {
		int nOgres = 0;
		for(int i = 0; i < selectedBoard.getBoardSize() ; i++ )
			for(int j = 0; j < selectedBoard.getBoardSize(); j++) {
				if(selectedBoard.getBoardCaracter(i, j) == 'O')
					nOgres++;
			}
		return nOgres;
	}

	public Hero getHero() {
		return hero;
	}

	public List<Vilan> getVilans() {
		return vilans;
	}

	public Club getHeroClub() {
		return heroClub;
	}

	public GameMap getBoard() {
		return selectedBoard;
	}

	public List<ExitDoor> getExitDoors() {
		return exitDoors;
	}

	public boolean isGameOver() {
		return this.state == EnumGameState.Lost;
	}

	public Lever getLever() {
		return lever;
	}

	public Key getKey() {
		return key;
	}

	public void setCanMoveGuard(boolean b) {
		canMoveGuard = b;		
	}
}



