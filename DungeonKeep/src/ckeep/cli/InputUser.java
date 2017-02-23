package ckeep.cli;


import java.util.Random;
import java.util.Scanner;

import dkeep.logic.*;
import dkeep.logic.GameLogic.Level;

public class InputUser {

	int numberRows = 10;
	boolean onLever = false;
	boolean winGame = false;
	boolean lostGame = false;
	boolean onLeverOgre = false;
	boolean onLeverClub = false;
	public Level level;

	public enum Level{
		LEVELONE, LEVELTWO;
	}
	public static void main(String[] args)
	{
		InputUser game = new InputUser();
		game.run();
	}

	public void run() {

		level = Level.LEVELONE;	

		printBoard();

		System.out.println("How to play? S to down - W to up - A to left - D to right");

		Scanner input = new Scanner(System.in);

		while(!winGame && !lostGame && input.hasNext())
		{
			String keyCode = input.nextLine();			
			System.out.println(keyCode);
			if (keyCode.length()==1){
				keyPressed(keyCode);
				printBoard();	
			}
		}

		if(winGame)
			System.out.println("YOU WIN!");			
		else if(lostGame)
			System.out.println("Game Over!");

		input.close();	
	}


	public void printBoard()
	{
		System.out.println();

		if(level == Level.LEVELONE)
		{
			for(int i = 0; i < numberRows; i++)
			{
				for(int j = 0; j < numberRows; j++)
				{
					if(hero[0] == j && hero[1] == i)
						System.out.print('H');
					else if (guard[indexGuard][0] == j && guard[indexGuard][1] == i)
						System.out.print('G');
					else
						System.out.print(boardOne[i][j]);

					System.out.print(" ");
				}
				System.out.println();
			}
		}
		else if(level == Level.LEVELTWO)
		{
			for(int i = 0; i < numberRows-1; i++)
			{
				for(int j = 0; j < numberRows-1; j++)
				{
					if(hero[0] == j && hero[1] == i)
					{
						if(onLever)
							System.out.print('K');
						else
							System.out.print('H');
					}
					else if (crazyOgre[0] == j && crazyOgre[1] == i)
					{
						if(onLeverOgre)
							System.out.print('$');
						else
							System.out.print('0');
					}
					else if (club[0]==j && club[1]==i)
					{
						if (onLeverClub)
							System.out.print('$');
						else 
							System.out.print('*');

					}
					else
						System.out.print(boardTwo[i][j]);

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

		if(level == Level.LEVELONE)
			board = boardOne;
		else
		{
			movesCrazyOgre();
			board = boardTwo;
		}

		switch(option) { 
		//left
		case 'a':
		case 'A':
			if(board[hero[1]][hero[0]-1] == ' ')
				hero[0]--;
			else if(board[hero[1]][hero[0]-1] == 'k')
			{
				hero[0]--;
				if (level ==Level.LEVELONE){
					activateLever();
					onLever = true;
				}

			}
			else if(board[hero[1]][hero[0]-1] == 'S')
			{

				if(level == Level.LEVELONE)
				{
					hero[0] = 1;
					hero[1] = 7;
					level = Level.LEVELTWO;
					onLever = false;
				}
				else 
				{
					hero[0]--;
					winGame = true;
				}
			}
			else if(board[hero[1]][hero[0]-1] == 'I')
			{
				if(level == Level.LEVELTWO)
				{
					activateLever();
				}
			}

			break;
		case 'd':
		case 'D':
			if(board[hero[1]][hero[0]+1] == ' ')
				hero[0]++;
			else if(board[hero[1]][hero[0]+1] == 'k')
			{
				hero[0]++;
				if (level ==Level.LEVELONE){
					activateLever();
				}
				onLever = true;
			}
			break;
			//up
		case 'w':
		case 'W':
			if(board[hero[1]-1][hero[0]] == ' ')
				hero[1]--;
			else if(board[hero[1]-1][hero[0]] == 'k')
			{
				hero[1]--;
				if (level ==Level.LEVELONE){
					activateLever();
				}
				onLever = true;
			}
			break;
			//down
		case 's':
		case 'S':
			if(board[hero[1]+1][hero[0]] == ' ')
				hero[1]++;

			else if(board[hero[1]+1][hero[0]] == 'k')
			{
				hero[1]++;
				if (level ==Level.LEVELONE){
					activateLever();
				}	
				onLever = true;
			}
			break;
		default: 
			System.out.println("Press a valid key!");
		}

		if(level == Level.LEVELONE)
			moveGuard();

		checkLostGame();
	}

	
}