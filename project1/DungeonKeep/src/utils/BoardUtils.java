package utils;

public class BoardUtils {

	public static int[] getSimpleGuardYmovement()  {
		return new int[] {8, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8};
	}

	public static int[] getSimpleGuardXmovement() {
		return new int[] {1, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 6, 5, 4, 3, 2};
	}

	public static char[][] getSimpleBoardOne () {
		return new char[][] {{'X','X','X','X','X','X','X','X','X','X'},
			{'X','h',' ',' ','x', ' ', 'X', ' ', 'G', 'X'},
			{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
			{'X',' ','x',' ','x', ' ', 'X', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
			{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
			{'S',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', 'X', ' ', 'X'},
			{'X',' ','x',' ','x', ' ', 'X', 'k', ' ', 'X'},
			{'X','X','X','X','X','X','X','X','X','X'}};
	}

	public static char[][] getSimpleBoardTwo ()	{
		return new char[][] {{'X','X','X','X','X','X','X','X','X'},
				{'S',' ',' ',' ',' ', 'O', '*', 'k', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','h','a',' ',' ', ' ', ' ', ' ', 'X'},
				{'X','X','X','X','X','X','X','X','X'}};
	}
}
