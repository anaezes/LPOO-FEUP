package dkeep.logic;

public class Hero extends Coordinates {
	
	private boolean onLever;
	private boolean armed;
	private char character;
	
	public Hero() {
		super(1, 1);
		this.onLever = false;
		this.armed = false;
		this.character = 'H';
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
	
	public void setHeroArmed() {
		this.armed = true;
	}
	
	public boolean isHeroArmed() {
		return armed;
	}
	
	
	public void setCharacter() {
		if(onLever || (armed && onLever))
			this.character = 'K';
		else if(armed)
			this.character = 'A';
		else
			this.character = 'H';	
	}
	
	public char GetHeroCharacter() {
		return character;
	}
}
