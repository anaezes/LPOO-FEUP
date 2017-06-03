package dkeep.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class GameObject extends JPanel {
	private static final long serialVersionUID = 1L;
	private Image image;
	private int x;
	private int y;
	private int height;
	private int width;

	public GameObject(int width, int height, char objectType, int x, int y) {
		super();
		this.setSize(new Dimension(width, height));
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		switchType(objectType);
	}

	@Override 
	protected void paintComponent (Graphics g){
		super.paintComponent(g);
		if(image != null)
			g.drawImage(image, 0, 0, width, height, this);
	}

	public void switchType(char type) {
		GameResources instance = GameResources.getInstance();
		
		chooseLeverOrKey(type, instance);
		chooseUltron(type, instance);
		chooseHero(type, instance);
		chooseOgre(type, instance);
		chooseDoor(type, instance);
		
		switch(type) {
		case 'X': image = instance.wall; break;
		case 'a': image = instance.shield; break;
		case '*': image = instance.club; break;
		case ' ': image = instance.floor; break;
		}
		repaint();
	} 

	public void chooseLeverOrKey(char type, GameResources instance) {
		switch(type) {
		case 'k': 
			image = instance.leverOff; 
			break;
		case 'K': 
			image = instance.leverOn; 
			break;
		case 'c': 
			image = instance.key; 
			break;
		}
	}
	
	public void chooseUltron(char type, GameResources instance) {
		switch(type) {
		case 'G': 
			image = instance.ultron; 
			break;
		case 'g': 
			image = instance.ultronSleep; 
			break;
		}
	}
	
	public void chooseHero(char type, GameResources instance) {
		switch(type) {
		case 'H': 
		case 'h': 
			image = instance.captainAmerica; 
			break;
		case 'A': 
			image = instance.captainAmericaShield; 
			break;
		}
	}

	public void chooseOgre(char type, GameResources instance) {
		switch(type) {
		case 'O': 
			image = instance.hulk;
			break;
		case '$': 
			image = instance.hulkDolar;
			break;
		case '8': 
			image = instance.hulkStunned; 
			break;
		}
	}

	public void chooseDoor(char type, GameResources instance) {
		switch(type) {
		case 'I': 
		case 'x': 
			image = instance.doorClosed; 
			break;
		case 'S': 
			image = instance.doorOpen; 
			break;
		}
	}
	
	public int getRow() {
		return x;
	}

	public int getColumn() {
		return y;
	}
}
