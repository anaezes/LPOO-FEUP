import java.util.Scanner;

public class DungeonKeep {
	static int numberRows = 10;
	static int[] hero = {1,1};
	static int[] guard = {8,1};
	static char[][] board = {{'X','X','X','X','X','X','X','X','X','X'},
			{'X',' ',' ',' ','I', ' ', 'X', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
			{'X',' ','I',' ','I', ' ', 'X', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
			{'I',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
			{'I',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', 'X', ' ', 'X'},
			{'X',' ','I',' ','I', ' ', 'X', 'K', ' ', 'X'},
			{'X','X','X','X','X','X','X','X','X','X'}};

	public static void main(String[] args)
	{
		DungeonKeep game = new DungeonKeep();
		game.run();
	}

	public void run()
	{
		printBoard();

		Scanner input = new Scanner(System.in);
		String keyCode = input.nextLine();		
		input.close();		
		System.out.println(keyCode);
		
		keyPressed(keyCode);
		
		printBoard();

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



	public void keyPressed(String keyCode) {
		char option = keyCode.charAt(0);

		switch( option) { 
		//left
		case 'a':
			System.out.println(board[hero[0]-1][hero[1]]);
			if(board[hero[0]-1][hero[1]] == ' ')
				hero[0]--;
			
			break;
			//right
		case 'd':
			System.out.println(board[hero[0]+1][hero[1]]);
			if(board[hero[0]+1][hero[1]] == ' ')
				hero[0]++;
			break;
			//up
		case 'w':

			break;
			//down
		case 's' :

			break;
		}
	} 



}
