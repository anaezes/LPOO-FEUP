package dkeep.logic;

import java.util.Random;

public class Ogre extends Coordinates {
	
	private boolean onLever;

	public Ogre() {
		super(1, 4);
		this.onLever = false;
	}
	
	public boolean GetOnLeverOgre() {
		return onLever;
	}
	
	public void movesCrazyOgre(Level level)
	{
		Random oj = new Random();
		int num = oj.nextInt(4);
		
		Board board = level.GetLevelBoard();
		
		onLever = false;
		
		switch(num) { 
		//left
		case 0:
			if(board.checkBoardColisions(this.GetXCoordinate(), this.GetYCoordinate()-1)) {
				this.SetYCoordinate(this.GetYCoordinate()-1);

			}
			break;
			//right
		case 1:
			if(board.checkBoardColisions(this.GetXCoordinate(), this.GetYCoordinate()+1)) {
				this.SetYCoordinate(this.GetYCoordinate()+1);
			}
			break;
			//up
		case 2:
			if(board.checkBoardColisions(this.GetXCoordinate()-1, this.GetYCoordinate())){
				this.SetXCoordinate(this.GetXCoordinate()-1);
			}
			break;
			//down
		case 3:
			if(board.checkBoardColisions(this.GetXCoordinate()+1, this.GetYCoordinate())){
				this.SetXCoordinate(this.GetXCoordinate()+1);
			}
			break;
		}
		
		if(board.checkBoardLeverAbove(this.GetXCoordinate(), this.GetYCoordinate())){
			onLever = true;
		}
	}
}
