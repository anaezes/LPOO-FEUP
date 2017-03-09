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
