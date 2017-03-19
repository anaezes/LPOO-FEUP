package dkeep.logic;

import java.util.Random;

public class DrunkGuard extends Guard {

	private boolean asleep;
	private char direction;

	public DrunkGuard(int i, int j) {
		super(i,j);
		this.asleep = false;
		this.direction = 'f';
	}

	//	@Override
	//	public void move(GameMap board) {
	//
	//		char caracter = 'G';
	//		
	//		Random oj = new Random();
	//		int num = oj.nextInt(3);
	//
	//		int index = indexGuard;
	//		switch(num) {
	//		// advance
	//		case 0:
	//			if(index < getPathSize()-1)
	//				index++;
	//			else
	//				index = 0;
	//			break;
	//			// go back
	//		case 1:
	//			if(index > 0 && index < getPathSize()-1)
	//				index--;
	//			else if(index == 0)
	//				index = getPathSize()-1;
	//			else
	//				index = 0;
	//			break;
	//			// sleeps	
	//		case 2:
	//			caracter = 'g';
	//			break;
	//		}
	//
	//		this.indexGuard = index;
	//		this.character = caracter;
	//		this.SetXCoordinate(x_position[index]);
	//		this.SetYCoordinate(y_position[index]);
	//	}

	@Override
	public void move(GameMap board) {

		char caracter = 'G';
		int num;
		Random oj = new Random();

		if(direction == 'f')
			num = oj.nextInt(2);
		else if(direction == 'b')
			num = oj.nextInt(2)+1;
		else 
			num = oj.nextInt(3);


		int index = indexGuard;
		switch(num) {
		// advance
		case 0:
			if(index < getPathSize()-1)
				index++;
			else
				index = 0;
			direction = 'f';
			break;
			// go back
		case 2:
			if(index > 0 && index < getPathSize()-1)
				index--;
			else if(index == 0)
				index = getPathSize()-1;
			else
				index = 0;
			direction = 'b';
			break;
			// sleeps	
		case 1:
			//this.character= 'g';
			direction = 'g';
			break;
		}

		this.indexGuard = index;
		this.SetXCoordinate(x_position[index]);
		this.SetYCoordinate(y_position[index]);

	}

	@Override
	public char getCharacter() {
		return character;
	}

	@Override
	public Club getClub() {
		return null;
	}

	@Override
	public void checkClub(GameMap board) {}

	public int getIndexGuard() {
		return indexGuard;
	}
}
