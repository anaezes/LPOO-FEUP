package dkeep.logic;

import java.util.Random;

public class ParanoidGuard extends Guard {

	@Override
	public void moveGuard() {

		Random oj = new Random();
		int num = oj.nextInt(2);

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
		}
	}

}
