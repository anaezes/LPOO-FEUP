package dkeep.logic;

import java.util.Random;

public class ParanoidGuard extends Guard {
	private char direction;
	
	public ParanoidGuard(int i, int j) {
		super(i,j);
		this.direction = 'f';
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
			this.direction = 'f';
			break;
			// go back
		case 1:
			if(index > 0)
				index--;
			else if(index == 0)
				index = getPathSize()-1;
			else
				index = 0;
			this.direction = 'b';
			break;
		}
		this.indexGuard = index;
		this.SetXCoordinate(x_position[index]);
		this.SetYCoordinate(y_position[index]);
	}
	
	public char getDirection() {
		return direction;
	}

}
