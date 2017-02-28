package dkeep.logic;

import java.util.Random;

public class Ogre extends Coordinates {

	private boolean onLever;
	private int numberTurnsLeftStunned;
	private char character;

	public Ogre() {
		super(0,0);
		Random y_oj = new Random();
		int y = y_oj.nextInt(6)+1;
		int x;

		if(y < 5)
		{
			Random x_oj = new Random();
			x = x_oj.nextInt(6)+1;
		}
		else
		{
			Random x_oj = new Random();
			x = x_oj.nextInt(4)+4;
		}

		this.SetXCoordinate(x);
		this.SetYCoordinate(y);

		this.onLever = false;
		this.numberTurnsLeftStunned = 0;
		this.character = 'O';
	}

	public boolean GetOnLeverOgre() {
		return onLever;
	}
	
	public char getCharater() {
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
		if(numberTurnsLeftStunned == 0)
		{
			this.character = 'O';
			return false;
		}

		return true;
	}

	public void movesCrazyOgre(Level level)
	{
		if(!this.isStunned())
		{	
			Random oj = new Random();
			int num = oj.nextInt(4);

			Board board = level.GetLevelBoard();

			onLever = false;

			switch(num) { 
			//left
			case 0:
				if(board.checkBoardColisions(this.GetXCoordinate(), this.GetYCoordinate()-1)) 
					this.SetYCoordinate(this.GetYCoordinate()-1);
				break;
				//right
			case 1:
				if(board.checkBoardColisions(this.GetXCoordinate(), this.GetYCoordinate()+1))
					this.SetYCoordinate(this.GetYCoordinate()+1);
				break;
				//up
			case 2:
				if(board.checkBoardColisions(this.GetXCoordinate()-1, this.GetYCoordinate()))
					this.SetXCoordinate(this.GetXCoordinate()-1);
				break;
				//down
			case 3:
				if(board.checkBoardColisions(this.GetXCoordinate()+1, this.GetYCoordinate()))
					this.SetXCoordinate(this.GetXCoordinate()+1);
				break;
			}

			if(board.checkBoardLeverAbove(this.GetXCoordinate(), this.GetYCoordinate()))
				onLever = true;
		}
		else
			updateTurnsLeftStunned();
		
	}
}
