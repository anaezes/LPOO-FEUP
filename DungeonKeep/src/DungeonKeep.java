import java.util.Random;
import java.util.Scanner;

public class DungeonKeep {
	int numberRows = 10;
	int[] hero = {1,1};
	int[][] guard = {{8,1},{7,1},{7,3},{7,4},{7,5},{6,5},{5,5},{4,5},{3,5},{2,5},
			{1,5},{1,6},{2,6},{3,6},{4,6},{5,6},{6,6},{7,6},{8,6},{8,5},{8,4},{8,3},{8,2}};
	int indexGuard = 0;
	char[][] boardOne = {{'X','X','X','X','X','X','X','X','X','X'},
			{'X',' ',' ',' ','I', ' ', 'X', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
			{'X',' ','I',' ','I', ' ', 'X', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
			{'I',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
			{'I',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', 'X', ' ', 'X'},
			{'X',' ','I',' ','I', ' ', 'X', 'k', ' ', 'X'},
			{'X','X','X','X','X','X','X','X','X','X'}};

	//int[] heroTwo= {1,7};
	int[] crazyOgre= {4,1};
	char[][] boardTwo = {{'X','X','X','X','X','X','X','X','X'},
			{'I',' ',' ',' ',' ', ' ', ' ', 'k', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X','X','X','X','X','X','X','X','X'}};

	boolean onLever = false;
	boolean winGame = false;
	boolean lostGame = false;
	boolean onLeverOgre = false;
	Level level;

	public enum Level{
		LEVELONE, LEVELTWO;
	}


	public static void main(String[] args)
	{
		DungeonKeep game = new DungeonKeep();
		game.run();
	}

	public void run()
	{
		level = Level.LEVELONE;

		printBoard();

		Scanner input = new Scanner(System.in);

		while(!winGame && !lostGame && input.hasNext())
		{
			String keyCode = input.nextLine();			
			System.out.println(keyCode);
			keyPressed(keyCode);
			printBoard();	
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
			if(board[hero[1]][hero[0]-1] == ' ')
				hero[0]--;
			else if(board[hero[1]][hero[0]-1] == 'k')
			{
				hero[0]--;
				activateLever();
				onLever = true;
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

			break;
			//right
		case 'd':
			if(board[hero[1]][hero[0]+1] == ' ')
				hero[0]++;
			else if(board[hero[1]][hero[0]+1] == 'k')
			{
				hero[0]++;
				activateLever();
				onLever = true;
			}
			break;
			//up
		case 'w':
			if(board[hero[1]-1][hero[0]] == ' ')
				hero[1]--;
			else if(board[hero[1]-1][hero[0]] == 'k')
			{
				hero[1]--;
				activateLever();
				onLever = true;
			}
			break;
			//down
		case 's' :
			if(board[hero[1]+1][hero[0]] == ' ')
				hero[1]++;

			else if(board[hero[1]+1][hero[0]] == 'k')
			{
				hero[1]++;
				activateLever();
				onLever = true;
			}
			break;
		}

		if(level == Level.LEVELONE)
			moveGuard();

		checkLostGame();
	}

	public void activateLever() {
		if(level == Level.LEVELONE)
		{
			boardOne[5][0] = 'S';
			boardOne[6][0] = 'S';
		}
		else if(level == Level.LEVELTWO)
			boardTwo[1][0] = 'S';

	}

	public void moveGuard() {
		if(indexGuard < 22)
			indexGuard++;
		else
			indexGuard = 0;
	}

	public void checkLostGame()
	{
		if(level == Level.LEVELONE)
		{
			if(((guard[indexGuard][0] == (hero[0]+1) || guard[indexGuard][0] == (hero[0]-1)) && guard[indexGuard][1] == hero[1]) || 
					(guard[indexGuard][1] == (hero[1]+1) || guard[indexGuard][1] == (hero[1]-1)) && guard[indexGuard][0] == hero[0])
				lostGame = true;
		}
		else
		{
			if(((crazyOgre[0] == (hero[0]+1) || crazyOgre[0] == (hero[0]-1)) && crazyOgre[1] == (hero[1])) || 
					(crazyOgre[1] == (hero[1]+1) || crazyOgre[1] == (hero[1]-1)) && crazyOgre[0] == (hero[0]))
				lostGame = true;
		}
	}


	public void movesCrazyOgre()
	{
		Random oj = new Random();
		int num = oj.nextInt(4);

		onLeverOgre = false;
		
		switch(num) { 
		//left
		case 0:
			if(boardTwo[crazyOgre[1]][crazyOgre[0]-1] == ' ')
				crazyOgre[0]--;
			
			break;
			//right
		case 1:
			if(boardTwo[crazyOgre[1]][crazyOgre[0]+1] == ' ' || boardTwo[crazyOgre[1]][crazyOgre[0]+1] == 'k' || boardTwo[crazyOgre[1]][crazyOgre[0]+1] == 'K')
				crazyOgre[0]++;
			break;
			//up
		case 2:
			if(boardTwo[crazyOgre[1]-1][crazyOgre[0]] == ' ' || boardTwo[crazyOgre[1]-1][crazyOgre[0]] == 'k' || boardTwo[crazyOgre[1]-1][crazyOgre[0]] == 'K')
				crazyOgre[1]--;
			break;
			//down
		case 3:
			if(boardTwo[crazyOgre[1]+1][crazyOgre[0]] == ' ')
				crazyOgre[1]++;
			break;
		}
			
		if(boardTwo[crazyOgre[1]][crazyOgre[0]] == 'k' || boardTwo[crazyOgre[1]][crazyOgre[0]] == 'K')
			onLeverOgre = true;
	}
}
