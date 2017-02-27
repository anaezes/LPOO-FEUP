package dkeep.logic;

public class RookieGuard extends Guard {

	@Override
	public void moveGuard() {
		if(indexGuard < 22)
			indexGuard++;
		else
			indexGuard = 0;
	}
	
}
