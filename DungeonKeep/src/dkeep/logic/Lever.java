package dkeep.logic;

import java.io.Serializable;

public class Lever implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean state;
	private char character;
	private int x;
	private int y;
	
	public Lever(int x, int y){
		this.x = x;
		this.y = y;
		this.state = false;
		character = 'k';
	}
	
	public boolean getLeverState() {
		return state;
	}
	
	public void setLeverState(boolean state) {
		this.state = state;
		if(state == true)
			character = 'K';
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
