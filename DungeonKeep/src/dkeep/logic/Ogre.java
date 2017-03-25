package dkeep.logic;

import java.util.ArrayList;
import java.util.Random;


/** 
 * Class Ogre
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */
public class Ogre extends Vilan {
	private static final long serialVersionUID = 1L;
	private boolean onLever;
	private int numberTurnsLeftStunned;
	private char character;
	private Club club;

	/**
	 * Class Constructor Ogre
	 * <br> Initialize ogre and club's positions</br>
	 * <br> Set ogre's representation</br>
	 */
	public Ogre() {
		super(1, 1);
		this.club = new Club(1, 2);
		this.character = 'O';
	}

	/**
	 * Class Constructor Ogre
	 * <br> Create Ogre and Club</br>
	 * <br> with new coordinates</br>
	 * @param x_ogre	x - ogres's coordinate
	 * @param y_ogre	y - ogres's coordinate
	 * @param x_club	x - club's coordinate
	 * @param y_club	x - club's coordinate
	 */
	public Ogre(int x_ogre, int y_ogre, int x_club, int y_club) {
		super(x_ogre, y_ogre);
		this.club = new Club(x_club, y_club);
		this.character = 'O';
	}
	
	/**
	 * Check if Ogre is above lever or not
	 * @return 	true if it is or false otherwise 
	 */
	public boolean GetOnLeverOgre() {
		return onLever;
	}

	/**
	 * Returns ogre's representation
	 * @return char Character
	 */
	public char getCharacter() {
		return character;
	}


	/**
	 * Put ogre stunned and change his representation to '8'
	 */
	public void putStunned() {
		this.numberTurnsLeftStunned = 2;
		this.character = '8';
	}

	/**
	 * Decrement the number of moves being stunned
	 */
	public void updateTurnsLeftStunned() {
		this.numberTurnsLeftStunned--;
	}

	/**
	 * Verify is ogre is stunned or not
	 * <br> if is not, change his representation to the original 'O'
	 * @return true if is or false otherwise
	 */
	public boolean isStunned() {
		if(numberTurnsLeftStunned == 0) {
			this.character = 'O';
			return false;
		}
		return true;
	}

	/**
	 * Get ogre's club
	 * @return 	club
	 */
	public Club getClub() {
		return club;
	}

	
	/**
	 * Set Vilan type to Ogre
	 * @return Vilan's type
	 */
	@Override
	public EnumVillainType getType() {
		return EnumVillainType.Ogre;
	}

	/**
	 * Moves Ogre
	 * @param board		actual game map
	 * 
	 */
	public void move(GameMap board) {
		if(this.isStunned())
			updateTurnsLeftStunned();	
		else{
			this.character = 'O';
			onLever = false;
			computeMoveOgre(board);
			if(board.checkBoardLeverAbove(this.getXCoordinate(), this.getYCoordinate())) {
				onLever = true;
				this.character = '$';
			}
		}
	}

	private void computeMoveOgre(GameMap board) {
		Random oj = new Random();
		String currentCoord;
		boolean moved = false;
		while(!moved) {
			int num = oj.nextInt(4);
			currentCoord = this.getCordinates();
			getNewPosition(board, num);
			if(currentCoord != this.getCordinates())
				moved = true;
		}
	}

	
	private void getNewPosition(GameMap board, int num) {
		switch(num) { 
		case 0:
			tryMovementToLeft(board);
			break;
		case 1:
			tryMovementToRight(board);
			break;
		case 2:
			tryMovementToUp(board);
			break;
		case 3:
			tryMovementToDown(board);
			break;
		}
	}

	private void tryMovementToDown(GameMap board) {
		if(board.checkBoardColisions(this.getXCoordinate()+1, this.getYCoordinate()))
			this.SetXCoordinate(this.getXCoordinate()+1);		
	}

	private void tryMovementToUp(GameMap board) {
		if(board.checkBoardColisions(this.getXCoordinate()-1, this.getYCoordinate()))
			this.SetXCoordinate(this.getXCoordinate()-1);
	}

	private void tryMovementToRight(GameMap board) {
		if(board.checkBoardColisions(this.getXCoordinate(), this.getYCoordinate()+1))
			this.SetYCoordinate(this.getYCoordinate()+1);		
	}

	private void tryMovementToLeft(GameMap board) {
		if(board.checkBoardColisions(this.getXCoordinate(), this.getYCoordinate()-1)) 
			this.SetYCoordinate(this.getYCoordinate()-1);	
	}

	
	/**
	 * Compute valid positions to move club
	 * @param board 	current game map
	 */
	public void checkClub(GameMap board) {
		ArrayList<Integer> validPositions = new ArrayList<>();

		if(board.checkBoardColisions(this.getXCoordinate(), this.getYCoordinate()-1)) 
			validPositions.add(0);
		if(board.checkBoardColisions(this.getXCoordinate(), this.getYCoordinate()+1))
			validPositions.add(1);
		if(board.checkBoardColisions(this.getXCoordinate()-1, this.getYCoordinate()))
			validPositions.add(2);
		if(board.checkBoardColisions(this.getXCoordinate()+1, this.getYCoordinate()))
			validPositions.add(3);

		Integer[] positions = validPositions.toArray(new Integer[validPositions.size()]);
		moveClub(positions, board);

	}

	/**
	 * Move club according the valid positions previously calculated
	 * @param positions 	valid positions to move into
	 * @param board			actual game map
	 */
	public void moveClub(Integer[] positions, GameMap board) { 
		Random club_dir= new Random();
		int pos = club_dir.nextInt(positions.length);
		int num = positions[pos];
		onLever = false;
		switch(num) { 
		//left
		case 0:
			club.setXCoordinate(getXCoordinate());
			club.setYCoordinate(getYCoordinate()-1);
			break;
			//right
		case 1:
			club.setXCoordinate(getXCoordinate());
			club.setYCoordinate(getYCoordinate()+1);
			break;
			//up
		case 2:
			club.setXCoordinate(getXCoordinate()-1);
			club.setYCoordinate(getYCoordinate());
			break;
			//down
		case 3:
			club.setXCoordinate(getXCoordinate()+1);
			club.setYCoordinate(getYCoordinate());
			break;
		}

		if(board.checkBoardLeverAbove(this.getXCoordinate(), this.getYCoordinate()))
			onLever = true;
	}

	/**
	 * Return index guard
	 * @return index guard back to the begin
	 */
	@Override
	public int getIndexGuard() {
		return 0;
	}
}
