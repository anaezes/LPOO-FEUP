package dkeep.logic.characters;

import dkeep.logic.Character;
import dkeep.logic.GameMap;
import dkeep.logic.gameobjects.Club;

public abstract class Vilan extends Character {

	public enum EnumVillainType {
		Guard,
		Ogre
	}
	
	public abstract EnumVillainType getType();
	
	public abstract void move(GameMap board);
	
	public Vilan(int i, int j) {
		super(i, j);
	}
	
	public int getXCoordinate() {
		return x;
	}
	
	public int getYCoordinate() {
		return y;
	}

	public boolean GetOnLeverOgre() {
		return false;
	}

	//public abstract char getCharacter();

	public abstract Club getClub();

	public abstract void checkClub(GameMap board);
	
	public abstract int getIndexGuard();
	
}
