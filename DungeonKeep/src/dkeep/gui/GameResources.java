package dkeep.gui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameResources {
	private static final String IMAGES_DIR = System.getProperty("user.dir") + "/images/";
	private static GameResources instance;
	
	public Image wall;
	public Image captainAmerica;
	public Image captainAmericaShield;
	public Image shield;
	public Image doorClosed;
	public Image doorOpen;
	public Image hulk;
	public Image hulkDolar;
	public Image hulkStunned;
	public Image club;
	public Image ultron;
	public Image ultronSleep;
	public Image leverOff;
	public Image leverOn;
	public Image key;
	public Image floor;

	private GameResources() {
		try {
			wall = ImageIO.read(new File(IMAGES_DIR + "wall.png"));
			captainAmerica = ImageIO.read(new File(IMAGES_DIR + "captainAmerica.png"));
			captainAmericaShield = ImageIO.read(new File(IMAGES_DIR + "captainAmericaShield.png"));
			shield = ImageIO.read(new File(IMAGES_DIR + "shield.png"));
			doorClosed = ImageIO.read(new File(IMAGES_DIR + "doorClosed.png"));
			doorOpen = ImageIO.read(new File(IMAGES_DIR + "doorOpen.png"));
			hulk = ImageIO.read(new File(IMAGES_DIR + "hulk.png"));
			hulkDolar = ImageIO.read(new File(IMAGES_DIR + "hulkDolar.png"));
			hulkStunned = ImageIO.read(new File(IMAGES_DIR + "hulkStunned.png"));
			club = ImageIO.read(new File(IMAGES_DIR + "club.png"));
			ultron = ImageIO.read(new File(IMAGES_DIR + "ultron.png"));
			ultronSleep = ImageIO.read(new File(IMAGES_DIR + "ultronSleep.png"));
			leverOff = ImageIO.read(new File(IMAGES_DIR + "leverOff.png"));
			leverOn = ImageIO.read(new File(IMAGES_DIR + "leverOn.png"));
			key = ImageIO.read(new File(IMAGES_DIR + "key.png"));
			floor = ImageIO.read(new File(IMAGES_DIR + "floor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static GameResources getInstance() {
		if(instance == null)
			instance = new GameResources();
		
		return instance;
	}
}
