package dkeep.logic;

import java.util.Random;

public class DrunkGuard extends Guard {

	public DrunkGuard(int i, int j) {
		super(i,j);
	}

	@Override
	public void move(GameMap board) {

		char caracter = 'G';
		Random oj = new Random();
		int num = oj.nextInt(3);

		int index = indexGuard;
		switch(num) {
		// advance
		case 0:
			if(index <= 22)
				index++;
			else
				index = 0;
			break;
			// go back
		case 1:
			if(index > 0 && index <= 22)
				index--;
			else if(index == 0)
				index = 23;
			else
				index = 0;
			break;
			// sleeps	
		case 2:
			caracter = 'g';
			break;
		}

		this.indexGuard = index;
		this.character = caracter;
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
}
