package dkeep.logic;
/** 
 * Class Vilan
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */

public abstract class Vilan extends Character {

	/**
	 * <strong>EnumVillainType</strong> - Identifies possible vilans 
	 * </br>
	 * <strong>Guard</strong> - Guard is the vilan
	 * </br>
	 * <strong>Ogre</strong> - Ogre is the vilan
	 */
	public enum EnumVillainType {
		Guard,
		Ogre
	}
	
	/**
	 * Returns if the vilan is a guard or an ogre
	 * @return Vilan's type
	 */
	public abstract EnumVillainType getType();
	
	/**
	 * Treat ogre or guard's movements
	 * @param board		Current game map
	 */
	public abstract void move(GameMap board);
	
	
	/**
	 * Class Constructor Vilan
	 * @param i 	x-coordinate
	 * @param j		y-coordinate
	 */
	public Vilan(int i, int j) {
		super(i, j);
	}
	
	/**
	 * Returns x coordinate of Vilan
	 * @return x
	 */
	public int getXCoordinate() {
		return x;
	}
	
	/**
	 * Returns y coordinate of Vilan
	 * @return y
	 */
	public int getYCoordinate() {
		return y;
	}

	/**
	 * Check if ogre is above lever
	 * @return true if it is or false otherwise
	 */
	public boolean GetOnLeverOgre() {
		return false;
	}

	//public abstract char getCharacter();

	/**
	 * Returns ogre's club
	 * @return Club
	 */
	public abstract Club getClub();

	/**
	 * Check club
	 * @param board		Current game map
	 */
	public abstract void checkClub(GameMap board);
	
	/**
	 * Returns Guard's index
	 * @return integer index 
	 */
	public abstract int getIndexGuard();
	
}
