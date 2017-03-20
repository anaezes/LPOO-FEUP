package dkeep.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GameObject extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final String IMAGES_DIR = System.getProperty("user.dir") + "/images/";
	private Image image;

	public GameObject(int width, int height, char objectType) {
		super();
		this.setSize(new Dimension(width, height));
		switchType(objectType);
	}

	@Override 
	protected void paintComponent (Graphics g){
		super.paintComponent(g);
		if(image != null)
			g.drawImage(image, 0, 0, this);
	}
	
	public void switchType(char type) {
		try {
			switch(type) {
			case 'X':
				image = ImageIO.read(new File(IMAGES_DIR + "wall.png"));
				break;
			case 'H':
			case 'h':
				image = ImageIO.read(new File(IMAGES_DIR + "captainAmerica.png"));
				break;
			case 'A':
				image = ImageIO.read(new File(IMAGES_DIR + "captainAmericaShield.png"));
				break;
			case 'a':
				image = ImageIO.read(new File(IMAGES_DIR + "shield.png"));
				break;
			case 'I':
			case 'x':
				image = ImageIO.read(new File(IMAGES_DIR + "doorClosed.png"));
				break;
			case 'S':
				image = ImageIO.read(new File(IMAGES_DIR + "doorOpen.png"));
				break;
			case 'O':
				image = ImageIO.read(new File(IMAGES_DIR + "hulk.png"));
				break;
			case '$':
				image = ImageIO.read(new File(IMAGES_DIR + "hulkDolar.png"));
				break;
			case '8':
				image = ImageIO.read(new File(IMAGES_DIR + "hulkStunned.png"));
				break;
			case '*':
				image = ImageIO.read(new File(IMAGES_DIR + "club.png"));
				break;
			case 'G':
				image = ImageIO.read(new File(IMAGES_DIR + "ultron.png"));
				break;
			case 'g':
				image = ImageIO.read(new File(IMAGES_DIR + "ultronSleep.png"));
				break;
			case 'k':
				image = ImageIO.read(new File(IMAGES_DIR + "leverOff.png"));
				break;
			case 'K':
				image = ImageIO.read(new File(IMAGES_DIR + "leverOn.png"));
				break;
			case 'c':
				image = ImageIO.read(new File(IMAGES_DIR + "key.png"));
				break;
			case ' ':
				image = ImageIO.read(new File(IMAGES_DIR + "floor.png"));
				break;
			default:
				this.setBackground(Color.WHITE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		repaint();
	} 

}
