package dkeep.logic;

import java.util.Random;

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
		}
		this.indexGuard = index;
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
