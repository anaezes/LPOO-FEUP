package dkeep.logic;

public class Level {
	
	private EnumLevel selectedLevel;
	private Board board;
	
	public Level(EnumLevel level) {
		this.selectedLevel = level;
		this.board = new Board(selectedLevel);

	}
	
	public EnumLevel GetLevel() {
		return selectedLevel;
	}
	
	public void SetLevel(EnumLevel level) {
		selectedLevel = level;
		board.setLevelBoard(level);
	}
	
	public Board GetLevelBoard(){
		return board;
	}
	
	public void activateLever() {
		if(selectedLevel == EnumLevel.LEVELONE)
		{
			board.setBoardExitKeys(5, 0);
			board.setBoardExitKeys(6, 0);
		}
		else if(selectedLevel == EnumLevel.LEVELTWO){
			board.setBoardExitKeys(1, 0);
		}
	}
}
