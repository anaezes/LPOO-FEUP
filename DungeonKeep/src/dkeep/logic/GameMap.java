package dkeep.logic;

import java.io.Serializable;

/** 
 * Class GameMap
 * <br>Date: 26/03/2017</br>
 * 
 * @author Ana Santos & Cristiana Ribeiro
 * 
 *
 */

public class GameMap implements Serializable {
	private static final long serialVersionUID = 1L;
	private char[][] selectedBoard;

	/**
	 * Class Constructor 
	 * <br>Initialize game's map</br>
	 * @param board		map to be used
	 */
	public GameMap(char[][] board) {
		this.selectedBoard = board;
	}
	/**
	 * Returns actual game map
	 * @return selectedBoard
	 * <br>Actual game map</br>
	 */
	public char[][] getSelectedBoard() {
		return selectedBoard;
	}

	/**
	 * Change actual game map into a new map
	 * @param board		new map to be used
	 */
	public void setSelectedBoard(char[][] board) {
		this.selectedBoard = board;
	}

	/**
	 * Get actual map's size
	 * @return map's size
	 */
	public int getBoardSize() {
		return selectedBoard.length;
	}

	/**
	 * Returns representation present on the map with i, j coordinates
	 * @param i		x - Character coordinate
	 * @param j		y - Character coordinate
	 * @return	Character
	 */
	public char getBoardCaracter(int i, int j) {
		return selectedBoard[i][j];
	}

	/**
	 * Change representation present on the map with i, j coordinates
	 * @param i		x - Character coordinate
	 * @param j		x - Character coordinate
	 * @param c		char c - actual representation on the map
	 */
	public void setBoardCaracter(int i, int j, char c) {
		this.selectedBoard[i][j] = c;
	}

	/**
	 * Check if coordinates correspond to 'X'(wall) or to 'I' / 'S' (exit doors)
	 * @param x 	x - coordinate
	 * @param y		y - coordinate
	 * @return		true if different, false otherwise
	 */
	public boolean checkBoardColisions(int x, int y) {
		if(selectedBoard[x][y] != 'X' && selectedBoard[x][y] != 'I' && selectedBoard[x][y] != 'S')
			return true;
		return false;
	}

	/**
	 * Check if coordinates correspond to 'k' or 'K' (lever)
	 * @param x		x - coordinate
	 * @param y		y - coordinate
	 * @return		true if are the same, false otherwise
	 */
	public boolean checkBoardLeverAbove(int x, int y) {
		if(selectedBoard[x][y] == 'k' || selectedBoard[x][y] == 'K')
			return true;
		return false;
	}	
}
