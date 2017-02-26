package dkeep.logic;

public class Hero {
	
	private int[] position;
	private boolean onLever;
	
	public Hero() {
		this.position = new int[] {1, 1};
		this.onLever = false;
	}
	
	public int[] GetHeroPosition() {
		return position;
	}
	
	public boolean GetHeroOnLeverState() {
		return onLever;
	}
}
