package dkeep.logic;

import java.io.Serializable;

/** 
 * Class Character
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */

public abstract class Character implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int x;
	protected int y;
	
	/**
	 * Class Constructor Character
	 * @param x		x-coordinate
	 * @param y		y-coordinate
	 */
	public Character(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns x coordinate of Character
	 * @return x
	 */
	public int getXCoordinate() {
		return x;
	}
	
	/**
	 * Returns y coordinate of Character
	 * @return y
	 */
	public int getYCoordinate() {
		return y;
	}
	
	/**
	 *Change x coordinate of Character
	 */
	public void SetXCoordinate(int x) {
		this.x = x;
	}
	
	/**
	 *Change y coordinate of Character
	 */
	public void SetYCoordinate(int y) {
		this.y = y;
	}
	
	/**
	 * Returns Character's coordinates
	 * @return Coordinates in format (x,y)
	 */
	public String getCordinates() {
		return "("+x+","+y+")";
	}
	
	/**
	 * Returns game's elements representation
	 * @return char Character
	 */
	public abstract char getCharacter();
}
