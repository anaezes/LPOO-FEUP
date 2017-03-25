package dkeep.logic;

import java.io.Serializable;

/** 
 * Class Key
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */

public class Key implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean state;
	private char character;
	private int x;
	private int y;
	
	/**
	 * Class Constructor Key
	 * @param x 	x coordinate
	 * @param y		y coordinate
	 */
	public Key(int x, int y){
		this.x = x;
		this.y = y;
		this.state = false;
		character = 'c';
	}
	
	/**
	 * Check if key was picked up or not
	 * @return		true if was picked up or false otherwise
	 */
	public boolean getKeyState() {
		return state;
	}
	
	/**
	 * Change the state of the key (if was picked up or not)
	 * @param state  	new state
	 */
	public void setKeyState(boolean state) {
		this.state = state;
		if(state == true)
			character = ' ';
	}
	
	/**
	 * Returns x coordinate of Key
	 * @return x
	 */
	public int getXCoordinate() {
		return x;
	}
	
	/**
	 * Returns y coordinate of Key
	 * @return y
	 */
	public int getYCoordinate() {
		return y;
	}

	/**
	 * Returns Key's representation
	 * @return char character
	 */
	public char getCharacter() {
		return character;
	}
}
