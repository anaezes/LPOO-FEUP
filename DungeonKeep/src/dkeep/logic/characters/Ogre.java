package dkeep.logic.characters;

import java.util.ArrayList;
import java.util.Random;

import dkeep.logic.GameMap;
import dkeep.logic.gameobjects.Club;

public class Ogre extends Vilan {

	private boolean onLever;
	private int numberTurnsLeftStunned;
	private char character;
	private Club club;


	public Ogre() {
		super(1, 1);
		this.club = new Club(1, 2);
		this.character = 'O';
	}

	public Ogre(int x_ogre, int y_ogre, int x_club, int y_club) {
		super(x_ogre, y_ogre);
		this.club = new Club(x_club, y_club);
		this.character = 'O';
	}

	public boolean GetOnLeverOgre() {
		return onLever;
	}

	public char getCharacter() {
		return character;
	}


	public void putStunned() {
		this.numberTurnsLeftStunned = 2;
		this.character = '8';
	}

	public void updateTurnsLeftStunned() {
		this.numberTurnsLeftStunned--;
	}

	public boolean isStunned() {
		if(numberTurnsLeftStunned == 0) {
			this.character = 'O';
			return false;
		}
		return true;
	}

	public Club getClub() {
		return club;
	}

	@Override
	public EnumVillainType getType() {
		return EnumVillainType.Ogre;
	}

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

			switch(num) { 
			//left
			case 0:
				if(board.checkBoardColisions(this.getXCoordinate(), this.getYCoordinate()-1)) 
					this.SetYCoordinate(this.getYCoordinate()-1);
				break;
				//right
			case 1:
				if(board.checkBoardColisions(this.getXCoordinate(), this.getYCoordinate()+1))
					this.SetYCoordinate(this.getYCoordinate()+1);
				break;
				//up
			case 2:
				if(board.checkBoardColisions(this.getXCoordinate()-1, this.getYCoordinate()))
					this.SetXCoordinate(this.getXCoordinate()-1);
				break;
				//down
			case 3:
				if(board.checkBoardColisions(this.getXCoordinate()+1, this.getYCoordinate()))
					this.SetXCoordinate(this.getXCoordinate()+1);
				break;
			}

			if(currentCoord != this.getCordinates())
				moved = true;
		}
	}

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

	@Override
	public int getIndexGuard() {
		return 0;
	}
}
