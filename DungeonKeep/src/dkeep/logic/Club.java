package dkeep.logic;

import java.io.Serializable;

public class Club implements Serializable {
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private boolean onLever;
	private char character;

	public Club(int x, int y) {
		this.x = x;
		this.y = y;
		this.character = 'a';
		this.onLever = false;
	}

	public boolean getOnLeverState() {
		return onLever;
	}

	public int getXCoordinate() {
		return x;
	}
	
	public int getYCoordinate() {
		return y;
	}
	
	public void setXCoordinate(int x) {
		this.x = x;
	}
	
	public void setYCoordinate(int y) {
		this.y = y;
	}
	
	public String getCordinates() {
		return "("+x+","+y+")";
	}

	public void findClub() {
		this.character = ' ';
	}

	public char getCharacter() {
		return character;
	}

	public void setOnLeverState(boolean b) {
		this.onLever = b;
	}
}
