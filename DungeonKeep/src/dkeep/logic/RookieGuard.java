package dkeep.logic;

public class RookieGuard extends Guard {

	public RookieGuard(int i, int j) {
		super(i,j);
	}

	@Override
	public void move(GameMap board) {
		
		int index = indexGuard;
		if(index <= 22)
			index++;
		else
			index = 0;
		
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
