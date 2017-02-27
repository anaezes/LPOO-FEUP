package dkeep.logic;

import java.util.Random;

public class DrunkGuard extends Guard {

	@Override
	public void moveGuard() {

		char caracter = 'G';

		Random oj = new Random();
		int num = oj.nextInt(3);

		switch(num) {
		// advance
		case 0:
			if(indexGuard < 22)
				indexGuard++;
			else
				indexGuard = 0;
			break;
			// go back
		case 1:
			if(indexGuard > 0 && indexGuard < 22)
				indexGuard--;
			else if(indexGuard == 0)
				indexGuard = 22;
			else
				indexGuard = 0;
			break;
			// sleeps	
		case 2:
			caracter = 'g';
			break;
		}

		this.caracter = caracter;
	}

}
