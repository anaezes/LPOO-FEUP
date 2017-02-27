package dkeep.logic;

import dkeep.logic.Game.EnumLevel;

public class Board {
	private char[][] BoardOne = {{'X','X','X','X','X','X','X','X','X','X'},
			{'X',' ',' ',' ','I', ' ', 'X', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
			{'X',' ','I',' ','I', ' ', 'X', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', ' ', ' ', 'X'},
			{'I',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
			{'I',' ',' ',' ',' ', ' ', ' ', ' ', ' ', 'X'},
			{'X','X','X',' ','X', 'X', 'X', 'X', ' ', 'X'},
			{'X',' ','I',' ','I', ' ', 'X', 'k', ' ', 'X'},
			{'X','X','X','X','X','X','X','X','X','X'}};

	private char[][] BoardTwo = {{'X','X','X','X','X','X','X','X','X'},
			{'I',' ',' ',' ',' ', ' ', ' ', 'k', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X',' ',' ',' ',' ', ' ', ' ', ' ', 'X'},
			{'X','X','X','X','X','X','X','X','X'}};

	private char[][] SelectedBoard;

	public Board(EnumLevel level){
		if(level == EnumLevel.LEVELONE)
			this.SelectedBoard = BoardOne;
		else
			this.SelectedBoard = BoardTwo;
	}

	public void setLevelBoard(EnumLevel level) {
		if(level == EnumLevel.LEVELONE)
			this.SelectedBoard = BoardOne;
		else
			this.SelectedBoard = BoardTwo;
	}

	public char[][] GetBoard() {
		return SelectedBoard;
	}

	public void setBoardExitKeys(int x, int y) {
		SelectedBoard[x][y] = 'S';
	}

	public boolean checkBoardColisions(int x, int y) {
		if(SelectedBoard[x][y] != 'X' && SelectedBoard[x][y] != 'I' && SelectedBoard[x][y] != 'S')
			return true;
		return false;

	}

	public boolean checkBoardLeverAbove(int x, int y) {
		if(SelectedBoard[x][y] == 'k' || SelectedBoard[x][y] == 'K')
			return true;
		return false;

	}
}
