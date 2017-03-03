package dkeep.logic;

public class GameMap {

	private char[][] selectedBoard;

	public GameMap(char[][] board) {
		this.selectedBoard = board;
	}

	public char[][] getSelectedBoard() {
		return selectedBoard;
	}

	public void setSelectedBoard(char[][] board) {
		this.selectedBoard = board;
	}

	public int getBoardSize() {
		return selectedBoard.length;
	}

	public char getBoardCaracter(int i, int j) {
		return selectedBoard[i][j];
	}
	
	public void setBoardCaracter(int i, int j, char c) {
		this.selectedBoard[i][j] = c;
	}

	public boolean checkBoardColisions(int x, int y) {
		if(selectedBoard[x][y] != 'X' && selectedBoard[x][y] != 'I' && selectedBoard[x][y] != 'S')
			return true;
		return false;
	}

	public boolean checkBoardLeverAbove(int x, int y) {
		if(selectedBoard[x][y] == 'k' || selectedBoard[x][y] == 'K')
			return true;
		return false;

	}
}
