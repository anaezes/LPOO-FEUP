package dkeep.logic;

import java.util.Random;

public class Ogre extends Vilan {

	private boolean onLever;
	private int numberTurnsLeftStunned;
	private char character;
	private Club club;

	public Ogre(int x_ogre, int y_ogre, int x_club, int y_club) {
		super(x_ogre, y_ogre);
		this.club = new Club(x_club, y_club);
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
		if(!this.isStunned())
		{	
			Random oj = new Random();
			int num = oj.nextInt(4);

			onLever = false;

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

			if(board.checkBoardLeverAbove(this.getXCoordinate(), this.getYCoordinate()))
				onLever = true;
		}
		else
			updateTurnsLeftStunned();	
	}
	
	public void checkClub(GameMap board) {

		if(getXCoordinate() == 1){
			if (getYCoordinate() == 1){
				int[] array = {1,3};
				moveClub(array, board);
			}
			else if(getYCoordinate() == 7){
				int[] array = {0,3};
				moveClub(array, board);
			}
			else{
				int[] array = {0,1,3};
				moveClub(array, board);
			}
		}

		else if (getXCoordinate() == 7){
			if (getYCoordinate()== 1){
				int[] array = {1,2};
				moveClub(array, board);
			}

			else if(getYCoordinate()==7){
				int[] array = {0,2};
				moveClub(array, board);
			}
			else{
				int[] array = {0,1,2};
				moveClub(array, board);
			}
		}

		else if(getYCoordinate() == 1) {
			int[] array = {1, 2, 3};
			moveClub(array, board);
		}

		else if(getYCoordinate() == 7) {
			int[] array = {0, 2, 3};
			moveClub(array, board);
		}
		else
		{
			int[] array = {0, 1, 2, 3 };
			moveClub(array, board);
		}
	}
	
	public void moveClub(int[] array, GameMap board) { 

		Random club_dir= new Random();

		int pos = club_dir.nextInt(array.length);
		int num = array[pos];

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
}
