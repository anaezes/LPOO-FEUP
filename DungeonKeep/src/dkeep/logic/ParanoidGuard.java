package dkeep.logic;

import java.util.Random;

/** 
 * Class ParanoidGuard
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */
public class ParanoidGuard extends Guard {
	private static final long serialVersionUID = 1L;
	private char direction;
	
	/**
	 * Class Constructor ParanoidGuard
	 * @param i 	x-coordinate
	 * @param j		y-coordinate
	 */
	public ParanoidGuard(int i, int j) {
		super(i,j);
		this.direction = 'f';
	}

	/**
	 * Moves Paranoid Guard
	 * @param board 	current game map 
	 */
	@Override
	public void move(GameMap board) {
		Random oj = new Random();
		int num = oj.nextInt(2);

		int index = indexGuard;
		switch(num) {
		// advance
		case 0:
			if(index < getPathSize()-1)
				index++;
			else
				index = 0;
			this.direction = 'f';
			break;
			// go back
		case 1:
			if(index > 0)
				index--;
			else if(index == 0)
				index = getPathSize()-1;
			else
				index = 0;
			this.direction = 'b';
			break;
		}
		this.indexGuard = index;
		this.SetXCoordinate(x_position[index]);
		this.SetYCoordinate(y_position[index]);
	}
	
	
	/**
	 * Returns ParanoidDrunk's Direction Movement
	 * @return 	direction 
	 */
	public char getDirection() {
		return direction;
	}
}
