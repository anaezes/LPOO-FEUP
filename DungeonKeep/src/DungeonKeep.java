import java.util.Scanner;

public class DungeonKeep {
	int numberRows = 10;
	int[] hero = {1,1};
	int[] guard = {8,1};
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


	public static void main(String[] args)
	{
		DungeonKeep game = new DungeonKeep();
		game.run();
	}

	public void run()
	{
		printBoard();

		Scanner input = new Scanner(System.in);

		while(!winGame && input.hasNext())
		{
			
			String keyCode = input.nextLine();			
			System.out.println(keyCode);
			keyPressed(keyCode);
			printBoard();
			
			if(winGame)
				System.out.print("YOU WIN!");			
		}

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
				else if (guard[0] == j && guard[1] == i)
					System.out.print('G' + " ");
				else
					System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
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
		
		
	}

	public void activateLever() {
		board[5][0] = 'S';
		board[6][0] = 'S';
	}

}
