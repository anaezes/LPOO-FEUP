package dkeep.logic;

/** 
 * Class RookieGuard
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */

public class RookieGuard extends Guard {
	
	/**
	 * Class Constructor RookieGuard
	 * @param i 	x-coordinate
	 * @param j		y-coordinate
	 */
	public RookieGuard(int i, int j) {
		super(i,j);
	}

	/**
	 * Move RookieGuard
	 */
	@Override
	public void move(GameMap board) {
		
		int index = indexGuard;
		if(index < getPathSize()-1)
			index++;
		else
			index = 0;
		
		this.indexGuard = index;
		this.SetXCoordinate(x_position[index]);
		this.SetYCoordinate(y_position[index]);
	}
}
