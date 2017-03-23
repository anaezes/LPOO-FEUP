package dkeep.logic.characters.guards;

import java.util.Random;

import dkeep.logic.GameMap;
import dkeep.logic.characters.Guard;

public class ParanoidGuard extends Guard {

	public ParanoidGuard(int i, int j) {
		super(i,j);
	}

	@Override
	public void move(GameMap board) {

		Random oj = new Random();
		int num = oj.nextInt(2);

		int index = indexGuard;
		switch(num) {
		// advance
		case 0:
			if(index < getPathSize()-1)
				index++;
			else
				index = 0;
			break;
			// go back
		case 1:
			if(index > 0 && index < getPathSize()-1)
				index--;
			else if(index == 0)
				index = 23;
			
			else
				index = 0;
			break;
		}
		this.indexGuard = index;
		this.SetXCoordinate(x_position[index]);
		this.SetYCoordinate(y_position[index]);
	}

}
