package dkeep.logic;

public class Key {
	private boolean state;
	private char character;
	private int x;
	private int y;
	
	public Key(int x, int y){
		this.x = x;
		this.y = y;
		this.state = false;
		character = 'c';
	}
	
	public boolean getKeyState() {
		return state;
	}
	
	public void setKeyState(boolean state) {
		this.state = state;
		if(state == true)
			character = ' ';
	}
	
	public int getXCoordinate() {
		return x;
	}
	
	public int getYCoordinate() {
		return y;
	}

	public char getCharacter() {
		return character;
	}
}
