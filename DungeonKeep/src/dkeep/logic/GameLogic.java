package dkeep.logic;

import java.util.Random;



public class GameLogic {
	
	
	public int[] hero = {1,1};
	public int[][] guard = {{8,1},{7,1},{7,3},{7,4},{7,5},{6,5},{5,5},{4,5},{3,5},{2,5},
			{1,5},{1,6},{2,6},{3,6},{4,6},{5,6},{6,6},{7,6},{8,6},{8,5},{8,4},{8,3},{8,2}};
	public int indexGuard = 0;
	public char[][] boardOne = {{'X','X','X','X','X','X','X','X','X','X'},
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
	public int[] crazyOgre= {4,1};
	public int[] club={4,2};
	 public char[][] boardTwo = {{'X','X','X','X','X','X','X','X','X'},
			{'I',' ',' ',' ',' ', ' ', ' ', 'k', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X','X','X','X','X','X','X','X','X'}};

	public boolean onLever = false;
	public boolean winGame = false;
	public boolean lostGame = false;
	public boolean onLeverOgre = false;
	public boolean onLeverClub = false;
	public Level level;

	public enum Level{
		LEVELONE, LEVELTWO;
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

			if(((club[0] == (hero[0]+1) || club[0] == (hero[0]-1)) && club[1] == (hero[1])) || 
					(club[1] == (hero[1]+1) || club[1] == (hero[1]-1)) && club[0] == (hero[0]))
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
			if(boardTwo[crazyOgre[1]][crazyOgre[0]-1] != 'X' && boardTwo[crazyOgre[1]][crazyOgre[0]-1] != 'I' && boardTwo[crazyOgre[1]][crazyOgre[0]-1] != 'S' )
				crazyOgre[0]--;
			checkAsterisk();

			break;
			//right
		case 1:
			if(boardTwo[crazyOgre[1]][crazyOgre[0]+1] != 'X' && boardTwo[crazyOgre[1]][crazyOgre[0]+1] != 'I' && boardTwo[crazyOgre[1]][crazyOgre[0]+1] != 'S'  ) //|| boardTwo[crazyOgre[1]][crazyOgre[0]+1] == 'k' || boardTwo[crazyOgre[1]][crazyOgre[0]+1] == 'K')
				crazyOgre[0]++;
			checkAsterisk();

			break;
			//up
		case 2:
			if(boardTwo[crazyOgre[1]-1][crazyOgre[0]] != 'X' && boardTwo[crazyOgre[1]-1][crazyOgre[0]] != 'I' && boardTwo[crazyOgre[1]-1][crazyOgre[0]] != 'S'  )//|| boardTwo[crazyOgre[1]-1][crazyOgre[0]] == 'k' || boardTwo[crazyOgre[1]-1][crazyOgre[0]] == 'K')
				crazyOgre[1]--;
			checkAsterisk();

			break;
			//down
		case 3:
			if(boardTwo[crazyOgre[1]+1][crazyOgre[0]] != 'X' && boardTwo[crazyOgre[1]+1][crazyOgre[0]] != 'I' && boardTwo[crazyOgre[1]+1][crazyOgre[0]] != 'S')
				crazyOgre[1]++;
			checkAsterisk();

			break;
		}

		if(boardTwo[crazyOgre[1]][crazyOgre[0]] == 'k' || boardTwo[crazyOgre[1]][crazyOgre[0]] == 'K')
			onLeverOgre = true;
	}

	public void moveOgreClub(int[] array)
	{ 
		onLeverClub = false;

		Random club_dir= new Random();

		int pos = club_dir.nextInt(array.length);
		int num = array[pos];

		//System.out.println(num);
		//System.out.println(crazyOgre[0]);
		//System.out.println(crazyOgre[1]);
		//System.out.println("club");
		//System.out.println(club[0]);
		//System.out.println(club[1]);


		switch(num) { 
		//left
		case 0:
			if(boardTwo[crazyOgre[1]][crazyOgre[0]-1] != 'X' && boardTwo[crazyOgre[1]][crazyOgre[0]-1] != 'I' && boardTwo[crazyOgre[1]][crazyOgre[0]-1] != 'S')
			{
				club[0] = crazyOgre[0] - 1;
				club[1] = crazyOgre[1];
			}

			break;
			//right
		case 1:
			if(boardTwo[crazyOgre[1]][crazyOgre[0] + 1] != 'X' && boardTwo[crazyOgre[1]][crazyOgre[0] + 1] != 'I' && boardTwo[crazyOgre[1]][crazyOgre[0] + 1] != 'S')
			{
				club[0] = crazyOgre[0] + 1;
				club[1] = crazyOgre[1];
			}
			break;
			//up
		case 2:
			if(boardTwo[crazyOgre[1]-1][crazyOgre[0]] != 'X' && boardTwo[crazyOgre[1]-1][crazyOgre[0]] != 'I' && boardTwo[crazyOgre[1]-1][crazyOgre[0]] != 'S')
			{
				club[1] = crazyOgre[1] - 1;
				club[0] = crazyOgre[0];
			}
			break;
			//down
		case 3:
			if(boardTwo[crazyOgre[1]+1][crazyOgre[0]] != 'X' && boardTwo[crazyOgre[1]+1][crazyOgre[0]] != 'I' && boardTwo[crazyOgre[1]+1][crazyOgre[0]] != 'S')
			{
				club[1] = crazyOgre[1] + 1;
				club[0] = crazyOgre[0];
			}
			break;
		}

		if(boardTwo[club[1]][club[0]] == 'k' || boardTwo[club[1]][club[0]] == 'K')
			onLeverClub = true;
	}

	public void checkAsterisk(){

		if(crazyOgre[1] == 1){
			if (crazyOgre[0] == 1){
				int[] array = {1,3};
				moveOgreClub(array);
			}

			else if(crazyOgre[0] == 7){
				int[] array = {0,3};
				moveOgreClub(array);
			}
			else{
				int[] array = {0,1,3};
				moveOgreClub(array);
			}
		}

		else if (crazyOgre[1] == 7){
			if (crazyOgre[0]== 1){
				int[] array = {1,2};
				moveOgreClub(array);
			}

			else if(crazyOgre[0]==7){
				int[] array = {0,2};
				moveOgreClub(array);
			}
			else{
				int[] array = {0,1,2};
				moveOgreClub(array);
			}
		}

		else if(crazyOgre[0] == 1) {
			int[] array = {1, 2, 3};
			moveOgreClub(array);
		}

		else if(crazyOgre[0] == 7) {
			int[] array = {0, 2, 3};
			moveOgreClub(array);
		}
		else
		{
			int[] array = {0, 1, 2, 3 };
			moveOgreClub(array);
		}
	}

}



