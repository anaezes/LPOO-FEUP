package dkeep.logic;

public abstract class Guard extends Vilan {

	protected int[] x_position;
	protected int[] y_position;
	protected int indexGuard;
	protected char character;
	protected boolean hasPredifinedPath;

	public Guard(int i, int j) {
		super(i, j);
		this.x_position = null;
		this.y_position = null;
		this.hasPredifinedPath = false;
		this.indexGuard = 0;
		this.character = 'G';
	}

	public void setPath(int[] y, int[] x) {
		this.hasPredifinedPath = true;
		this.x_position = x;
		this.y_position = y;
	}

	public int getIndexGuard() {
		return indexGuard;
	}

	public char getCharacter() {
		return character;
	}

	public int getYCoordinate() {
		if(hasPredifinedPath)
			return y_position[indexGuard];
		else
			return y;
	}

	public int getXCoordinate() {
		if(hasPredifinedPath)
			return x_position[indexGuard];
		else
			return x;
	}

	@Override
	public EnumVillainType getType() {
		return EnumVillainType.Guard;
	}

	public abstract void move(GameMap board);
	
	public void setPredifinedPath(boolean b){
		this.hasPredifinedPath=b;
	}
	
	public int getPathSize() {
		return x_position.length;
	}

	public Club getClub() {
		return null;
	}

	public void checkClub(GameMap board) {}

	public boolean getPredifinedPath() {
		return hasPredifinedPath;
	}
	
}
