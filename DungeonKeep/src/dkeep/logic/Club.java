package dkeep.logic;

import java.util.Random;

public class Club extends Coordinates {
	
	private boolean onLever;
	
	public Club() {
		super(2, 4);
		this.onLever = false;
	}
	
	public boolean GetOnLeverState() {
		return onLever;
	}
	
	public void checkAsterisk(Level level, Coordinates actorCoordinates){

		if(actorCoordinates.GetXCoordinate() == 1){
			if (actorCoordinates.GetYCoordinate() == 1){
				int[] array = {1,3};
				moveClub(array, level, actorCoordinates);
			}
			else if(actorCoordinates.GetYCoordinate() == 7){
				int[] array = {0,3};
				moveClub(array, level, actorCoordinates);
			}
			else{
				int[] array = {0,1,3};
				moveClub(array, level, actorCoordinates);
			}
		}

		else if (crazyOgre[1] == 7){
			if (crazyOgre[0]== 1){
				int[] array = {1,2};
				moveOgreClub(array);
			}

			else if(crazyOgre[0]==7){
				int[] array = {0,2};
				moveOgreClub(array);
			}
			else{
				int[] array = {0,1,2};
				moveOgreClub(array);
			}
		}

		else if(crazyOgre[0] == 1) {
			int[] array = {1, 2, 3};
			moveOgreClub(array);
		}

		else if(crazyOgre[0] == 7) {
			int[] array = {0, 2, 3};
			moveOgreClub(array);
		}
		else
		{
			int[] array = {0, 1, 2, 3 };
			moveOgreClub(array);
		}
	}
	

	public void moveClub(int[] array, Level level, Coordinates actorCoordinates )
	{ 

		Random club_dir= new Random();

		int pos = club_dir.nextInt(array.length);
		int num = array[pos];
		
		Board board = level.GetLevelBoard();

		switch(num) { 
		//left
		case 0:
			if(board.checkBoardColisions(this.GetXCoordinate(), this.GetYCoordinate()-1)){
				this.SetXCoordinate(actorCoordinates.GetXCoordinate());
				this.SetYCoordinate(actorCoordinates.GetYCoordinate()-1);
			}
			break;
			//right
		case 1:
			if(board.checkBoardColisions(this.GetXCoordinate(), this.GetYCoordinate() +1))
			{
				this.SetXCoordinate(actorCoordinates.GetXCoordinate());
				this.SetYCoordinate(actorCoordinates.GetYCoordinate()+1);
			}
			break;
			//up
		case 2:
			if(board.checkBoardColisions(this.GetXCoordinate()-1, this.GetYCoordinate()))
			{
				this.SetXCoordinate(actorCoordinates.GetXCoordinate()-1);
				this.SetYCoordinate(actorCoordinates.GetYCoordinate());
			}
			break;
			//down
		case 3:
			if(board.checkBoardColisions(this.GetXCoordinate()+1, this.GetYCoordinate()))
			{
				this.SetXCoordinate(actorCoordinates.GetXCoordinate()+1);
				this.SetYCoordinate(actorCoordinates.GetYCoordinate());
			}
			break;
		}
		
		if(board.checkBoardLeverAbove(this.GetXCoordinate(), this.GetYCoordinate()))
		{
			onLever = true;
		}
	}
}
