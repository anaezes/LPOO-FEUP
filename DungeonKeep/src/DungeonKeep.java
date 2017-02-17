import java.util.Scanner;

public class DungeonKeep {
	int numberRows = 10;
	int[] hero = {1,1};
	int[][] guard = {{8,1},{7,1},{7,3},{7,4},{7,5},{6,5},{5,5},{4,5},{3,5},{2,5},
			{1,5},{1,6},{2,6},{3,6},{4,6},{5,6},{6,6},{7,6},{8,6},{8,5},{8,4},{8,3},{8,2}};
	int indexGuard = 0;
	char[][] board = {{'X','X','X','X','X','X','X','X','X','X'},
			{'X',' ',' ',' ','I', ' ', 'X', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
			{'X',' ','I',' ','I', ' ', 'X', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
			{'I',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
			{'I',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', 'X', ' ', 'X'},
			{'X',' ','I',' ','I', ' ', 'X', 'K', ' ', 'X'},
			{'X','X','X','X','X','X','X','X','X','X'}};
	boolean onLever = false;
	boolean winGame = false;
	boolean lostGame = false;


	public static void main(String[] args)
	{
		DungeonKeep game = new DungeonKeep();
		game.run();
	}

	public void run()
	{
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

		for(int i = 0; i < numberRows; i++)
		{
			for(int j = 0; j < numberRows; j++)
			{
				if(hero[0] == j && hero[1] == i)
					System.out.print('H' + " ");
				else if (guard[indexGuard][0] == j && guard[indexGuard][1] == i)
					System.out.print('G' + " ");
				else
					System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}



	public void keyPressed(String keyCode){
		char option = keyCode.charAt(0);

		switch(option) { 
		//left
		case 'a':
			if(board[hero[1]][hero[0]-1] == ' ')
			{
				hero[0]--;
				onLever = false;
			}
			else if(board[hero[1]][hero[0]-1] == 'K')
			{
				hero[0]--;
				activateLever();
				onLever = true;
			}
			else if(board[hero[1]][hero[0]-1] == 'S')
			{
				hero[0]--;
				activateLever();
				winGame = true;
			}
			break;
			//right
		case 'd':
			if(board[hero[1]][hero[0]+1] == ' ')
			{
				hero[0]++;
				onLever = false;
			}
			break;
			//up
		case 'w':
			if(board[hero[1]-1][hero[0]] == ' ')
			{
				hero[1]--;
				onLever = false;
			}
			break;
			//down
		case 's' :
			if(board[hero[1]+1][hero[0]] == ' ')
			{
				hero[1]++;
				onLever = false;
			}
			break;
		}
		
		moveGuard();
		checkLostGame();
		
	}

	public void activateLever() {
		board[5][0] = 'S';
		board[6][0] = 'S';
	}
	
	public void moveGuard() {
		if(indexGuard < 22)
			indexGuard++;
		else
			indexGuard = 0;
	}
	
	public void checkLostGame()
	{
		if(((guard[indexGuard][0] == (hero[0]+1) || guard[indexGuard][0] == (hero[0]-1)) && guard[indexGuard][1] == (hero[1])) || 
				(guard[indexGuard][1] == (hero[1]+1) || guard[indexGuard][1] == (hero[1]-1)) && guard[indexGuard][0] == (hero[0]))
			lostGame = true;
	}

}
