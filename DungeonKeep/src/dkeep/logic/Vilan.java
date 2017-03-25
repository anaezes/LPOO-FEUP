package dkeep.logic;

public abstract class Vilan extends Character {

	private static final long serialVersionUID = 1L;

	public enum EnumVillainType {
		Guard,
		Ogre
	}
	
	public abstract EnumVillainType getType();
	
	public abstract void move(GameMap board);
	
	public Vilan(int i, int j) {
		super(i, j);
	}
	
//	public int getXCoordinate() {
//		return x;
//	}
//	
//	public int getYCoordinate() {
//		return y;
//	}

	public boolean GetOnLeverOgre() {
		return false;
	}

	public abstract Club getClub();
	
	public abstract int getIndexGuard();
	
}
