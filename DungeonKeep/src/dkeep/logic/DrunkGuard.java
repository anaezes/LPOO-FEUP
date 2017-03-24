package dkeep.logic;

import java.util.Random;

public class DrunkGuard extends Guard {

	private char direction;

	public DrunkGuard(int i, int j) {
		super(i,j);
		this.direction = 'f';
	}

	@Override
	public void move(GameMap board) {
		char caracter = 'G';	
		int num = computeRandomDirection();

		int index = indexGuard;
		switch(num) {
		// advance
		case 0:
			index = moveFront();
			break;
			// go back
		case 2:
			index = moveBack();
			break;
			// sleeps	
		case 1:
			caracter = 'g';
			direction = 'g';
			break;
		}

		this.indexGuard = index;
		this.character = caracter;
		this.SetXCoordinate(x_position[index]);
		this.SetYCoordinate(y_position[index]);
	}

	private int computeRandomDirection() {
		Random oj = new Random();
		int num;
		if(direction == 'f')
			num = oj.nextInt(2);
		else if(direction == 'b')
			num = oj.nextInt(2)+1;
		else 
			num = oj.nextInt(3);
		return num;
	}

	private int moveBack() {
		int index = indexGuard;
		
		if(index > 0 && index < getPathSize()-1)
			index--;
		else if(index == 0)
			index = getPathSize()-1;
		else
			index = 0;
		
		direction = 'b';
		return index;
	}


	private int moveFront() {
		int index = indexGuard;
		if(index < getPathSize()-1)
			index++;
		else
			index = 0;
		direction = 'f';
		return index;
	}

	public void setDirection(char c) {
		direction = c;		
	}
}
