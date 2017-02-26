package dkeep.logic;

public class Hero extends Coordinates {
	
	private boolean onLever;
	
	public Hero() {
		super(1, 1);
		this.onLever = false;
	}
	
	public boolean GetHeroOnLeverState() {
		return onLever;
	}
	
	public void moveHero(int x, int y) {
		this.SetXCoordinate(x);
		this.SetYCoordinate(y);
	}
	
	public void SetHeroOnLever(boolean state) {
		this.onLever = state;
	}
}
