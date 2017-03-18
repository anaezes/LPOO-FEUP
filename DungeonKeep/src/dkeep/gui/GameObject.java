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
				image = ImageIO.read(new File(IMAGES_DIR + "captainAmerica2.png"));
				break;
			case 'A':
				image = ImageIO.read(new File(IMAGES_DIR + "captainAmericaShield.png"));
				break;
			case 'I':
				image = ImageIO.read(new File(IMAGES_DIR + "doorClosed.png"));
				break;
			case 'O':
				image = ImageIO.read(new File(IMAGES_DIR + "hulk.png"));
				break;
			case 'G':
				image = ImageIO.read(new File(IMAGES_DIR + "ultron.png"));
				break;
			case 'k':
				image = ImageIO.read(new File(IMAGES_DIR + "leverOff.png"));
				break;
			case 'K':
				image = ImageIO.read(new File(IMAGES_DIR + "leverOn.png"));
				break;
			case ' ':
				image = ImageIO.read(new File(IMAGES_DIR + "floor.png"));
				break;
			default:
				this.setBackground(Color.WHITE);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

}
