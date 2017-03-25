package dkeep.logic;

import java.io.Serializable;

/** 
 * Class Lever
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */
public class Lever implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean state;
	private char character;
	private int x;
	private int y;
	
	/**
	 * Class Constructor Lever
	 * @param x		x-coordinate
	 * @param y		y-coordinate
	 */
	public Lever(int x, int y){
		this.x = x;
		this.y = y;
		this.state = false;
		character = 'k';
	}
	
	/**
	 * Return if Hero is above lever or if is not
	 * @return	state indicates if hero is above lever or if is not
	 */
	public boolean getLeverState() {
		return state;
	}
	
	
	/**
	 * Change Lever'state and possibly its representation
	 * @param state		new state 
	 */
	public void setLeverState(boolean state) {
		this.state = state;
		if(state == true)
			character = 'K';
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
	 * Returns lever's representation
	 * @return char Character
	 */
	public char getCharacter() {
		return character;
	}
}
