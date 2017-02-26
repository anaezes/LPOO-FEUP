package ckeep.cli;

import java.util.Scanner;

import dkeep.logic.Game;

public class InputUser {
	
	private int numberRows;
	private Game game;
	
	public InputUser(int numberRows) {
		Game game = new Game();
		this.numberRows = numberRows;
		this.game = game;
	}

	public void run() {

		printBoard();

		System.out.println("How to play? S to down - W to up - A to left - D to right");

		Scanner input = new Scanner(System.in);

		while(!game.winGame && !game.lostGame && input.hasNext())
		{
			String keyCode = input.nextLine();			
			System.out.println(keyCode);
			if (keyCode.length()==1){
				keyPressed(keyCode);
				printBoard();	
			}
		}

		if(game.winGame)
			System.out.println("YOU WIN!");			
		else if(game.lostGame)
			System.out.println("Game Over!");

		input.close();	
	}


	public void printBoard()
	{
		System.out.println();

		if(game.level == Level.LEVELONE)
		{
			for(int i = 0; i < numberRows; i++)
			{
				for(int j = 0; j < numberRows; j++)
				{
					if(game.hero[0] == j && game.hero[1] == i)
						System.out.print('H');
					else if (game.guard[game.indexGuard][0] == j && game.guard[game.indexGuard][1] == i)
						System.out.print('G');
					else
						System.out.print(game.boardOne[i][j]);

					System.out.print(" ");
				}
				System.out.println();
			}
		}
		else if(game.level == Level.LEVELTWO)
		{
			for(int i = 0; i < numberRows-1; i++)
			{
				for(int j = 0; j < numberRows-1; j++)
				{
					if(game.hero[0] == j && game.hero[1] == i)
					{
						if(game.onLever)
							System.out.print('K');
						else
							System.out.print('H');
					}
					else if (game.crazyOgre[0] == j && game.crazyOgre[1] == i)
					{
						if(game.onLeverOgre)
							System.out.print('$');
						else
							System.out.print('0');
					}
					else if (game.club[0]==j && game.club[1]==i)
					{
						if (game.onLeverClub)
							System.out.print('$');
						else 
							System.out.print('*');

					}
					else
						System.out.print(game.boardTwo[i][j]);

					System.out.print(" ");
				}
				System.out.println();
			}
		}

		System.out.println();
	}



	public void keyPressed(String keyCode){

		char option = keyCode.charAt(0);

		char[][] board;

		if(game.level == Level.LEVELONE)
			board = game.boardOne;
		else
		{
			game.movesCrazyOgre();
			board = game.boardTwo;
		}

		switch(option) { 
		//left
		case 'a':
		case 'A':
			if(board[game.hero[1]][game.hero[0]-1] == ' ')
				game.hero[0]--;
			else if(board[game.hero[1]][game.hero[0]-1] == 'k')
			{
				game.hero[0]--;
				if (game.level ==Level.LEVELONE){
					game.activateLever();
					game.onLever = true;
				}

			}
			else if(board[game.hero[1]][game.hero[0]-1] == 'S')
			{

				if(game.level == Level.LEVELONE)
				{
					game.hero[0] = 1;
					game.hero[1] = 7;
					game.level = Level.LEVELTWO;
					game.onLever = false;
				}
				else 
				{
					game.hero[0]--;
					game.winGame = true;
				}
			}
			else if(board[game.hero[1]][game.hero[0]-1] == 'I')
			{
				if(game.level == Level.LEVELTWO)
				{
					game.activateLever();
				}
			}

			break;
		case 'd':
		case 'D':
			if(board[game.hero[1]][game.hero[0]+1] == ' ')
				game.hero[0]++;
			else if(board[game.hero[1]][game.hero[0]+1] == 'k')
			{
				game.hero[0]++;
				if (game.level ==Level.LEVELONE){
					game.activateLever();
				}
				game.onLever = true;
			}
			break;
			//up
		case 'w':
		case 'W':
			if(board[game.hero[1]-1][game.hero[0]] == ' ')
				game.hero[1]--;
			else if(board[game.hero[1]-1][game.hero[0]] == 'k')
			{
				game.hero[1]--;
				if (game.level ==Level.LEVELONE){
					game.activateLever();
				}
				game.onLever = true;
			}
			break;
			//down
		case 's':
		case 'S':
			if(board[game.hero[1]+1][game.hero[0]] == ' ')
				game.hero[1]++;

			else if(board[game.hero[1]+1][game.hero[0]] == 'k')
			{
				game.hero[1]++;
				if (game.level ==Level.LEVELONE){
					game.activateLever();
				}	
				game.onLever = true;
			}
			break;
		default: 
			System.out.println("Press a valid key!");
		}

		if(game.level == Level.LEVELONE)
			game.moveGuard();

		game.checkLostGame();
	}

	
}