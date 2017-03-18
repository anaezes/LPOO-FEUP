package dkeep.logic;

public class ExitDoor{
	int x;
	int y;
	char character;

	public ExitDoor(int x, int j) {
		this.x = x;
		this.y = y;
		this.character = 'I';
	}
	
	public void transformToStairs() {
		this.character = 'S';
	}
	
	public char getCharacter() {
		return character;
	}
	
	public int getYCoordinate() {
		return y;
	}

	public int getXCoordinate() {
		return x;
	}
	
	public String getCoordinates(){
		return "("+this.getXCoordinate()+","+this.getYCoordinate()+")";
	}

}
