package dkeep.logic;

import java.io.Serializable;

/** 
 * Class ExitDoor
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */


public class ExitDoor implements Serializable {
	private static final long serialVersionUID = 1L;
	int x;
	int y;
	char character;
	boolean openExit;

	/**
	 * Class Constructor ExitDoor
	 * @param x		x-coordinate
	 * @param y		y-coordinate
	 */
	public ExitDoor(int x, int y) {
		this.x = x;
		this.y = y;
		this.character = 'I';
		this.openExit = false;
	}
	
	/**
	 * Change doors's representation
	 */
	public void transformToStairs() {
		this.character = 'S';
	}
	
	/**
	 * Returns doors's representation
	 */
	public char getCharacter() {
		return character;
	}
	
	/**
	 * Returns y coordinate of door
	 * @return y
	 */
	public int getYCoordinate() {
		return y;
	}

	/**
	 * Returns x coordinate of door
	 * @return x
	 */
	public int getXCoordinate() {
		return x;
	}
	
	/**
	 * Returns doors's coordinates
	 * @return doors's in format (x,y)
	 */
	public String getCoordinates(){
		return "("+this.getXCoordinate()+","+this.getYCoordinate()+")";
	}
	
	/**
	 * Change doors's representation and open it
	 */
	public void openExitDoor() {
		this.openExit = true;
		this.character = 'P';
	}
	
	/**
	 * Check if the door is opened or closed
	 * @return		true is is opened or false otherwise
	 */
	public boolean getDoorExitState() {
		return openExit;
	}

}
