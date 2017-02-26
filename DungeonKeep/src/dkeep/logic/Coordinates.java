package dkeep.logic;

public class Coordinates {
	
	private int x;
	private int y;
	
	public Coordinates(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int GetXCoordinate() {
		return x;
	}
	
	public int GetYCoordinate() {
		return y;
	}
	
	public void SetXCoordinate(int x) {
		this.x = x;
	}
	
	public void SetYCoordinate(int y) {
		this.y = y;
	}
}
