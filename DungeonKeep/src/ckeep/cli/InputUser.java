package ckeep.cli;

import java.util.List;
import java.util.Scanner;

import dkeep.logic.EnumMoves;
import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.Game.EnumGameState;

public class InputUser {

	private Game game;

	public InputUser(List<GameMap> gameMaps, int numOgres, int[] guard_x, int[] guard_y) {
		game = new Game(gameMaps, numOgres);
		game.setGuardPath(guard_y, guard_x);
	}

	public void run() {
		
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
		EnumMoves moveDir = null;

		switch(option) { 
		//left
		case 'a':
			moveDir = EnumMoves.LEFT;
			break;
		case 'd':
			moveDir = EnumMoves.RIGHT;
			break;
			//up
		case 'w':
			moveDir = EnumMoves.UP;
			break;
			//down
		case 's':
			moveDir = EnumMoves.DOWN;
			break;
		default: 
			System.out.println("Press a valid key!");
		}
		if(moveDir != null){
			game.moveHero(moveDir);
			game.checkLostGame();
		}
	}
}