package ckeep.cli;

import java.util.Scanner;

import dkeep.logic.EnumMoves;
import dkeep.logic.Game;
import dkeep.logic.Game.EnumGameState;

public class InputUser {

	private Game game;

	public InputUser(Game game) {
		this.game = game;
	}

	public void run() {
		//Print print = new Print();
		System.out.print(Print.boardToString(game));

		System.out.println("How to play? S to down - W to up - A to left - D to right");

		Scanner input = new Scanner(System.in);

		while(game.getGameState() == EnumGameState.Running && input.hasNext())
		{
			String keyCode = input.nextLine();			
			System.out.println(keyCode);
			if (keyCode.length()==1) {
				keyPressed(keyCode);
				System.out.print(Print.boardToString(game));
			}
		}

		if(game.getGameState() == EnumGameState.Win)
			System.out.println("YOU WIN!");			
		else if(game.getGameState() == EnumGameState.Lost)
			System.out.println("Game Over!");

		input.close();	
	}

	public void keyPressed(String keyCode){

		//transform string to lower case and
		//pick the first character of the string 
		char option = (keyCode.toLowerCase()).charAt(0);

		switch(option) { 
		//left
		case 'a':
			game.moveHero(EnumMoves.LEFT);
			break;
		case 'd':
			game.moveHero(EnumMoves.RIGHT);
			break;
			//up
		case 'w':
			game.moveHero(EnumMoves.UP);
			break;
			//down
		case 's':
			game.moveHero(EnumMoves.DOWN);
			break;
		default: 
			System.out.println("Press a valid key!");
		}

		game.checkLostGame();
	}


}