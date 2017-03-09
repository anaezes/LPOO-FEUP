package dkeep.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ckeep.cli.InputUser.EnumMoves;
import dkeep.logic.Vilan.EnumVillainType;

public class Game {

	private EnumGameState state;
	private EnumLevel level;

	//characters of game
	private Hero hero;
	private Club heroClub;
	private List<Vilan> vilans;

	//list of exits doors
	private List<ExitDoor> exitDoors;

	private boolean canMoveGuard;

	// flag of Lever in game
	private boolean activeLever;

	//list of levels
	private List<GameMap> boards;

	// index of selected board
	private int indexBoard = 0;

	// selected board
	private GameMap selectedBoard;

	//flag is a unic level or a list of levels
	private boolean byLevel;

	//saves if is the second trie to unlock the stairs (second level)
	private boolean secondTrie;
	
	public boolean getSecondTrie(){
		return secondTrie;
	}

	public enum EnumGameState {
		Running,
		Win,
		Lost,
	}

	public enum EnumLevel {
		LEVELONE, 
		LEVELTWO,
	}

	public Game(GameMap board) {
		initGame(board);
		this.selectedBoard = board;
		this.state = EnumGameState.Running;
		this.level = null;
		this.canMoveGuard = false;
		this.byLevel = false;
	}

	public Game(List<GameMap> boards) {
		this.boards = boards;
		this.selectedBoard = boards.get(indexBoard);
		initGame(selectedBoard);
		this.state = EnumGameState.Running;
		this.level = EnumLevel.LEVELONE;
		this.canMoveGuard = false;
		this.byLevel = true;
	}

	/**
	 * Identify all features from the game board
	 * */
	public void initGame(GameMap board) {

		this.activeLever = false;
		this.secondTrie = false;

		List<ExitDoor> exit = new ArrayList<>();
		List<Integer> x_ogres = new ArrayList<>();
		List<Integer> y_ogres = new ArrayList<>();
		List<Integer> x_clubs = new ArrayList<>();
		List<Integer> y_clubs = new ArrayList<>();

		this.heroClub = new Club(-1, -1);
		boolean ogres = false;

		for(int i = 0; i < board.getBoardSize(); i++) {
			for(int j = 0; j < board.getBoardSize(); j++) {

				if(board.getBoardCaracter(i, j) == 'h' || board.getBoardCaracter(i, j) == 'H') {
					this.hero = new Hero(i,j);
					board.setBoardCaracter(i, j , ' ');
				}

				else if(board.getBoardCaracter(i, j)== 'a') {			
					this.heroClub = new Club(i,j);
					board.setBoardCaracter(i, j , ' ');

				}
				else if(board.getBoardCaracter(i,j) == 'G') {
					this.vilans = initGuard(i, j);
					board.setBoardCaracter(i, j , ' ');
				}
				else if(board.getBoardCaracter(i,j) == 'O') {
					ogres = true;
					x_ogres.add(i);
					y_ogres.add(j);
					board.setBoardCaracter(i, j , ' ');
				}

				else if(board.getBoardCaracter(i,j) == '*') {
					ogres = true;
					x_clubs.add(i);
					y_clubs.add(j);
					board.setBoardCaracter(i, j , ' ');
				}

				else if(board.getBoardCaracter(i,j) == 'S') {
					exit.add(new ExitDoor(i, j));
					board.setBoardCaracter(i, j , 'X');
				}
			}
		}

		this.exitDoors = exit;
		if(ogres) 
			initOgres(x_ogres, y_ogres, x_clubs, y_clubs);
	}


	public List<Vilan> initGuard(int i, int j) {

		Random oj = new Random();
		int num = oj.nextInt(3);

		List<Vilan> v = new ArrayList<>();
		System.out.print("GUARD: ");	
		switch(num) {
		case 0:
			v.add(new RookieGuard(i, j));
			System.out.println("Rookie Guard");	
			break;
		case 1:
			v.add(new DrunkGuard(i, j));
			System.out.println("Drunk Guard");	
			break;
		case 2:
			v.add(new ParanoidGuard(i, j));
			System.out.println("Paranoid Guard");	
			break;
		}

		return v;
	}

	public void initOgres(List<Integer> x_ogres, List<Integer> y_ogres, List<Integer> x_clubs, List<Integer> y_clubs) {

		List<Vilan> v = new ArrayList<>();	
		for(int i = 0; i < x_ogres.size(); i++)
			v.add(new Ogre(x_ogres.get(i), y_ogres.get(i), x_clubs.get(i), y_clubs.get(i)));
		this.vilans = v;
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

	public EnumLevel getGameLevel() {
		return level;
	}
	public void setGameState(EnumGameState state) {
		this.state = state;
	}

	public void setGameLevel(EnumLevel level) {
		this.level = level;
	}

	public void checkLostGame()
	{
		int x_ogre;
		int y_ogre;
		boolean check = false;
		for(int i = 0; i < vilans.size(); i++) {

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
						(x_ogre== (hero.getXCoordinate()+1) || x_ogre == (hero.getXCoordinate()-1)) && y_ogre == (hero.getYCoordinate()))
					state = EnumGameState.Lost;
			}
		}
			
	}

	public void moveHero(EnumMoves movement) {

		boolean findWeapon = false;
		int x_hero = hero.getXCoordinate();
		int y_hero = hero.getYCoordinate();

		if(movement == EnumMoves.LEFT) {

			if((x_hero == heroClub.getXCoordinate()) && (y_hero-1 == heroClub.getYCoordinate())) {
				findWeapon = true;
				y_hero -= 1; 
			}

			else if(verifyIfThroughStaires(x_hero, y_hero-1) && activeLever) {

				if(byLevel && boards.size() > (indexBoard+1)) {
					this.indexBoard++;
					this.selectedBoard = boards.get(indexBoard);
					initGame(selectedBoard);
					y_hero = this.hero.getYCoordinate();
					x_hero = this.hero.getXCoordinate();
				}
				else {
					y_hero -= 1; 
					setGameState(EnumGameState.Win);
				}
			}
			else if(selectedBoard.getSelectedBoard()[x_hero][y_hero-1] == 'k') {
				activeLever = true;
				hero.setHeroOnLever(true);
				y_hero -= 1; 
				unlockStairs();
			}
			else if(selectedBoard.getSelectedBoard()[x_hero][y_hero-1] == ' ')
				y_hero -= 1; 
		}
		else if(movement == EnumMoves.RIGHT) {	

			if((x_hero == heroClub.getXCoordinate()) && (y_hero+1 == heroClub.getYCoordinate())) {
				findWeapon = true;
				y_hero += 1;
			}
			else if(selectedBoard.getSelectedBoard()[x_hero][y_hero+1] == 'k') {
				y_hero += 1;
				hero.setHeroOnLever(true);
				activeLever = true;
				unlockStairs();
			}
			else if(selectedBoard.getSelectedBoard()[x_hero][y_hero+1] == ' ') {
				y_hero += 1;
			}
		} 
		else if(movement == EnumMoves.UP) {
			if((x_hero-1 == heroClub.getXCoordinate()) && (y_hero == heroClub.getYCoordinate())) {
				findWeapon = true;
				x_hero -= 1;
			}
			else if(selectedBoard.getSelectedBoard()[x_hero-1][y_hero] == 'k') {
				x_hero -= 1;
				hero.setHeroOnLever(true);
				activeLever = true;
				unlockStairs();
			}
			else if(selectedBoard.getSelectedBoard()[x_hero-1][y_hero] == ' ')
				x_hero -= 1;

		} 
		else if(movement == EnumMoves.DOWN) {
			if((x_hero+1 == heroClub.getXCoordinate()) && (y_hero == heroClub.getYCoordinate())) {
				findWeapon = true;
				x_hero += 1;
			}
			else if(selectedBoard.getSelectedBoard()[x_hero+1][y_hero] == 'k') {
				x_hero += 1;
				hero.setHeroOnLever(true);
				activeLever = true;
				unlockStairs();
			}
			else if(selectedBoard.getSelectedBoard()[x_hero+1][y_hero] == ' ')
				x_hero += 1;
		}

		hero.moveHero(x_hero, y_hero);

		if(findWeapon)
			hero.setHeroArmed();

		hero.setCharacter();

		if(canMoveGuard)
			moveVilans();

		if(hero.getHeroOnLeverState() && secondTrie)
			for(int i = 0; i < exitDoors.size(); i++)
				exitDoors.get(i).transformToStairs();

		checkLostGame();
	}

	public boolean verifyIfThroughStaires(int x, int y) {
		for(int i = 0; i < exitDoors.size(); i++) {
			if((x == exitDoors.get(i).getXCoordinate()) && (y== exitDoors.get(i).getYCoordinate() && secondTrie))
				return true;
			else if((x == exitDoors.get(i).getXCoordinate()) && (y== exitDoors.get(i).getYCoordinate())) {
				secondTrie = true;
				break;
			}
		}
		return false;
	}

	public void unlockStairs() {
		for(int i = 0; i < exitDoors.size(); i++)
			if((hero.getXCoordinate() == exitDoors.get(i).getXCoordinate() && hero.getYCoordinate() == exitDoors.get(i).getYCoordinate()) && activeLever)
				selectedBoard.setBoardCaracter(exitDoors.get(i).getXCoordinate(), exitDoors.get(i).getYCoordinate() , ' ');
	}

	public void moveVilans() {

		for(int i = 0; i < vilans.size(); i++) {
			vilans.get(i).move(selectedBoard);
			if(vilans.get(i).getType() == EnumVillainType.Ogre)
				vilans.get(i).checkClub(selectedBoard);
		}
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
	
	public boolean isActiveLever() {
		return activeLever;
	}
	
	public void setActiveLever(boolean lever){
		this.activeLever=lever;
	}
	

}



