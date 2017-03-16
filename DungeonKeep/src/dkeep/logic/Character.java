package dkeep.logic;

public abstract class Character {
	
	protected int x;
	protected int y;
	
	public Character(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getXCoordinate() {
		return x;
	}
	
	public int getYCoordinate() {
		return y;
	}
	
	public void SetXCoordinate(int x) {
		this.x = x;
	}
	
	public void SetYCoordinate(int y) {
		this.y = y;
	}
	
	public String getCordinates() {
		return "("+x+","+y+")";
	}
	
	public abstract char getCharacter();
}
