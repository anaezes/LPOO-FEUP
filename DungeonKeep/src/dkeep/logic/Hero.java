package dkeep.logic;

/** 
 * Class Hero
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 */
public class Hero extends Character {
	private static final long serialVersionUID = 1L;
	private boolean withKey;
	private boolean armed;
	private char character;
	
	/**
	 * Constructor Class Hero
	 * 
	 */
	public Hero() {
		super(1, 1);
		this.withKey = false;
		this.armed = false;
		this.character = 'h';
	}

	/**
	 * Constructor Class Hero
	 * 
	 */
	public Hero(int i, int j) {
		super(i, j);
		this.withKey = false;
		this.armed = false;
		this.character = 'h';
	}

	/**
	 *Determines if hero picked up the key
	 * @return		true if he gets the key or false otherwise
	 */
	public boolean getHeroOnLeverState() {
		return withKey;
	}
	
	/**
	 * Set hero's coordinates
	 * @param x		x-coordinate
	 * @param y		y-coordinate
	 */
	public void moveHero(int x, int y) {
		this.SetXCoordinate(x);
		this.SetYCoordinate(y);
	}
	
	/**
	 * Change if hero picked up the key or not
	 * @param state		new state
	 */
	public void setHeroOnLever(boolean state) {
		this.withKey = state;
	}
	
	/**
	 * Puts an shield on the hero
	 */
	public void setHeroArmed() {
		this.armed = true;
	}
	
	/**
	 * Returns if hero is armed or not
	 * @return 	true if is armed or false otherwise
	 */
	public boolean isHeroArmed() {
		return armed;
	}
	
	/**
	 * Change hero's representation
	 */
	public void setCharacter() {
		if(withKey)
			this.character = 'H';
		if(armed)
			this.character = 'A';
		else
			this.character = 'h';	
	}
	
	/**
	 * Returns hero's representation
	 * @return char hero's character
	 */
	public char getCharacter() {
		return character;
	}
	
	/**
	 * Change Hero's representation
	 * @param c 	new representation 
	 */
	public void setCaracter(char c) {
		this.character = c;
	}

	/**
	 * Change if Hero is above the lever or not
	 * @param a		new state
	 */
	public void setOnLeverState(boolean a){
		this.withKey=a;
	}
}
