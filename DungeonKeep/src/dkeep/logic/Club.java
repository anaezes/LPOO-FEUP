package dkeep.logic;

import java.io.Serializable;

/** 
 * Class Club
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */
public class Club implements Serializable {

	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private boolean onLever;
	private char character;

	/**
	 * Class Constructor Club
	 * @param x 	x-coordinates
	 * @param y		y-coordinates
	 */
	public Club(int x, int y) {
		this.x = x;
		this.y = y;
		this.character = 'a';
		this.onLever = false;
	}

	/**
	 * Check if Club is above lever
	 * @return 	true if it is or false otherwise
	 */
	public boolean getOnLeverState() {
		return onLever;
	}
	
	/**
	 * Returns x coordinate of Club
	 * @return x
	 */
	public int getXCoordinate() {
		return x;
	}
	
	/**
	 * Returns y coordinate of Club
	 * @return y
	 */
	public int getYCoordinate() {
		return y;
	}
	
	/**
	 *Change x coordinate of Club
	 */
	public void setXCoordinate(int x) {
		this.x = x;
	}
	
	/**
	 *Change x coordinate of Club
	 */
	public void setYCoordinate(int y) {
		this.y = y;
	}
	
	/**
	 * Returns Club's coordinates
	 * @return Coordinates in format (x,y)
	 */
	public String getCordinates() {
		return "("+x+","+y+")";
	}

	/**
	 * Change club's representation to ' '
	 */
	public void findClub() {
		this.character = ' ';
	}

	/**
	 * Returns club's representation 
	 * @return char Character
	 */
	public char getCharacter() {
		return character;
	}

	/**
	 * Change if Club is/isn't above lever
	 * @param b 	new state
	 */
	public void setOnLeverState(boolean b) {
		this.onLever = b;
	}
}
