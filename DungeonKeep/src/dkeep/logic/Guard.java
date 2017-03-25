package dkeep.logic;

/** 
 * Class Guard
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */
public abstract class Guard extends Vilan {

	protected int[] x_position;
	protected int[] y_position;
	protected int indexGuard;
	protected char character;
	protected boolean hasPredifinedPath;

	/**
	 * Class Constructor Guard
	 * @param i		x-coordinate
	 * @param j		y-coordinate
	 */
	public Guard(int i, int j) {
		super(i, j);
		this.x_position = null;
		this.y_position = null;
		this.hasPredifinedPath = false;
		this.indexGuard = 0;
		this.character = 'G';
	}

	/**
	 * Fix a predifined path
	 * @param y		int[] - array with x-coordinates 
	 * @param x		int[] - array with y-coordinates
	 */
	public void setPath(int[] y, int[] x) {
		this.hasPredifinedPath = true;
		this.x_position = x;
		this.y_position = y;
	}

	/**
	 * Returns Guard's index
	 */
	public int getIndexGuard() {
		return indexGuard;
	}

	/**
	 * Returns guard's representation
	 * @return char Character
	 */
	public char getCharacter() {
		return character;
	}

	/**
	 * Returns y coordinate of guard
	 * @return y
	 */
	public int getYCoordinate() {
		if(hasPredifinedPath)
			return y_position[indexGuard];
		else
			return y;
	}

	/**
	 * Returns x coordinate of Character
	 * @return x
	 */
	public int getXCoordinate() {
		if(hasPredifinedPath)
			return x_position[indexGuard];
		else
			return x;
	}

	/**
	 * Returns if the vilan type as Guard 
	 * @return Vilan's type
	 */
	@Override
	public EnumVillainType getType() {
		return EnumVillainType.Guard;
	}

	/**
	 * Move Guard
	 */
	public abstract void move(GameMap board);
	
	/**
	 * Change if the guard has a predifined path or not
	 * @param b - new state to be considered
	 */
	public void setPredifinedPath(boolean b){
		this.hasPredifinedPath=b;
	}
	
	/**
	 * Returns path's size
	 * @return 	path's size
	 */
	public int getPathSize() {
		return x_position.length;
	}

	/**
	 * Guard has no club - return null
	 */
	public Club getClub() {
		return null;
	}

	public void checkClub(GameMap board) {}

	/**
	 * See if guard has a predifined path
	 * @return true is has or false otherwise
	 */
	public boolean getPredifinedPath() {
		return hasPredifinedPath;
	}
	
	/**
	 * Change index guard on position's array
	 * @param i  change to this index
	 */
	public void setIndex(int i) {
		this.indexGuard = i;
	}
	
}
