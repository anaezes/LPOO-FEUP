package dkeep.logic.gameobjects;

public class ExitDoor{
	int x;
	int y;
	char character;
	boolean openExit;

	public ExitDoor(int x, int y) {
		this.x = x;
		this.y = y;
		this.character = 'I';
		this.openExit = false;
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
	
	public void openExitDoor() {
		this.openExit = true;
		this.character = 'P';
	}
	
	public boolean getDoorExitState() {
		return openExit;
	}

}
