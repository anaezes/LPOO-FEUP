package ckeep.cli;

import java.util.Scanner;

import dkeep.logic.Game;
import dkeep.logic.Game.EnumGameState;
import dkeep.logic.Game.EnumLevel;

public class InputUser {
	
	private int numberRows;
	private Game game;
	
	public enum EnumMoves {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
	
	public InputUser(int numberRows) {
		Game game = new Game();
		this.numberRows = numberRows;
		this.game = game;
	}

	public void run() {

		printBoard();

		System.out.println("How to play? S to down - W to up - A to left - D to right");

		Scanner input = new Scanner(System.in);

		while(game.GetGameState() == EnumGameState.Running && input.hasNext())
		{
			String keyCode = input.nextLine();			
			System.out.println(keyCode);
			if (keyCode.length()==1) {
				keyPressed(keyCode);
				printBoard();	
			}
		}

		if(game.GetGameState() == EnumGameState.Win)
			System.out.println("YOU WIN!");			
		else if(game.GetGameState() == EnumGameState.Lost)
			System.out.println("Game Over!");

		input.close();	
	}


	public void printBoard()
	{
		System.out.println();

		if(game.GetGameLevel().GetLevel() == EnumLevel.LEVELONE)
		{
			for(int i = 0; i < numberRows; i++)
			{
				for(int j = 0; j < numberRows; j++)
				{
					if(game.GetHero().GetYCoordinate() == j && game.GetHero().GetXCoordinate() == i)
						System.out.print('H');
					else if (game.GetGuard().GetGuard()[game.GetGuard().GetIndexGuard()][0] == j && game.GetGuard().GetGuard()[game.GetGuard().GetIndexGuard()][1] == i)
						System.out.print(game.GetGuard().getCharacterGuard());
					else
						System.out.print(game.GetGameLevel().GetLevelBoard().GetBoard()[i][j]);

					System.out.print(" ");
				}
				System.out.println();
			}
		}
		else if(game.GetGameLevel().GetLevel() == EnumLevel.LEVELTWO)
		{
			for(int i = 0; i < numberRows-1; i++)
			{
				for(int j = 0; j < numberRows-1; j++)
				{
					if(game.GetHero().GetYCoordinate() == j && game.GetHero().GetXCoordinate() == i)
					{
						if(game.GetHero().GetHeroOnLeverState())
							System.out.print('K');
						else
							System.out.print('H');
					}
					else if (game.GetOgre().GetYCoordinate() == j && game.GetOgre().GetXCoordinate() == i)
					{
						if(game.GetOgre().GetOnLeverOgre())
							System.out.print('$');
						else
							System.out.print('0');
					}
					else if (game.GetClub().GetYCoordinate()==j && game.GetClub().GetXCoordinate()==i)
					{
						if (game.GetClub().GetOnLeverState())
							System.out.print('$');
						else 
							System.out.print('*');

					}
					else
						System.out.print(game.GetGameLevel().GetLevelBoard().GetBoard()[i][j]);

					System.out.print(" ");
				}
				System.out.println();
			}
		}

		System.out.println();
	}


	public void keyPressed(String keyCode){

		//transform string to lower case and
		//pick the first character of the string 
		char option = (keyCode.toLowerCase()).charAt(0);

		game.addEnemiesGame();
		
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