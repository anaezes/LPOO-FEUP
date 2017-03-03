package ckeep.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dkeep.logic.Game;
import dkeep.logic.GameMap;

public class Main {

	public static void main(String[] args)
	{
		int[] guard_y = new int[] {8, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8};
		int[] guard_x = new int[] {1, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 5, 4, 3, 2};

		char[][] BoardOne = {{'X','X','X','X','X','X','X','X','X','X'},
				{'X','h',' ',' ','I', ' ', 'X', ' ', 'G', 'X'},
				{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
				{'X',' ','X',' ','I', ' ', 'X', ' ', ' ', 'X'},
				{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
				{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
				{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
				{'X','X','X',' ','X', 'X', 'X', 'X', ' ', 'X'},
				{'X',' ','I',' ','I', ' ', 'X', 'k', ' ', 'X'},
				{'X','X','X','X','X','X','X','X','X','X'}};

		char[][] BoardTwo = {{'X','X','X','X','X','X','X','X','X'},
				{'S',' ',' ',' ',' ', ' ', ' ', 'k', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','h','a',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','X','X','X','X','X','X','X','X'}};
	
		Random oj = new Random();
		int num = oj.nextInt(3);

		switch(num) {
		case 0: //in case 1 ogre
			BoardTwo[1][4] = 'O';
			BoardTwo[1][5] = '*';
			break;
		case 1: // in case 2 ogres
			BoardTwo[1][4] = 'O';
			BoardTwo[1][5] = '*';
			BoardTwo[3][3] = 'O';
			BoardTwo[3][4] = '*';
			break;
		case 2: // in case 3 ogres
			BoardTwo[1][4] = 'O';
			BoardTwo[1][5] = '*';
			BoardTwo[3][3] = 'O';
			BoardTwo[3][4] = '*';
			BoardTwo[6][5] = 'O';
			BoardTwo[6][6] = '*';
			break;	
		}

		List<GameMap> gameMaps = new ArrayList<>();
		GameMap gameMap1 = new GameMap(BoardOne);
		GameMap gameMap2 = new GameMap(BoardTwo);
		gameMaps.add(gameMap1);
		gameMaps.add(gameMap2);
		
		Game newGame = new Game(gameMaps);
		newGame.setGuardPath(guard_y, guard_x);

		InputUser inputUser = new InputUser(newGame);
		inputUser.run();
	}
}
