package dkeep.logic;

public class Hero extends Character {
	private static final long serialVersionUID = 1L;
	private boolean withKey;
	private boolean armed;
	private char character;
	
	public Hero() {
		super(1, 1);
		this.withKey = false;
		this.armed = false;
		this.character = 'h';
	}

	public Hero(int i, int j) {
		super(i, j);
		this.withKey = false;
		this.armed = false;
		this.character = 'h';
	}

	public boolean getHeroOnLeverState() {
		return withKey;
	}
	
	public void moveHero(int x, int y) {
		this.SetXCoordinate(x);
		this.SetYCoordinate(y);
	}
	
	public void setHeroOnLever(boolean state) {
		this.withKey = state;
	}
	
	public void setHeroArmed() {
		this.armed = true;
	}
	
	public boolean isHeroArmed() {
		return armed;
	}
	
	
	public void setCharacter() {
		if(withKey)
			this.character = 'H';
		if(armed)
			this.character = 'A';
		else
			this.character = 'h';	
	}
	
	public char getCharacter() {
		return character;
	}

	public void setCaracter(char c) {
		this.character = c;
	}

	public void setOnLeverState(boolean a){
		this.withKey=a;
	}
}
