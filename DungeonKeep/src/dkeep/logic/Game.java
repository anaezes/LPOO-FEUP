package dkeep.logic;

import java.util.Random;


public class Game {
	
	private EnumGameState state;
	private Level level;
	private Board board;
	
	public Game() {
		this.state = EnumGameState.Running;
		this.level = new Level(EnumLevel.LEVELONE);
		this.board = level.GetLevelBoard();
	}
	
	public EnumGameState GetGameState() {
		return state;
	}
				
	public void checkLostGame()
	{
		if(level.GetLevel() == EnumLevel.LEVELONE)
		{
			if(((guard[indexGuard][0] == (hero[0]+1) || guard[indexGuard][0] == (hero[0]-1)) && guard[indexGuard][1] == hero[1]) || 
					(guard[indexGuard][1] == (hero[1]+1) || guard[indexGuard][1] == (hero[1]-1)) && guard[indexGuard][0] == hero[0])
				state = EnumGameState.Lost;
		}
		else
		{
			if(((crazyOgre[0] == (hero[0]+1) || crazyOgre[0] == (hero[0]-1)) && crazyOgre[1] == (hero[1])) || 
					(crazyOgre[1] == (hero[1]+1) || crazyOgre[1] == (hero[1]-1)) && crazyOgre[0] == (hero[0]))
				state = EnumGameState.Lost;

			if(((club[0] == (hero[0]+1) || club[0] == (hero[0]-1)) && club[1] == (hero[1])) || 
					(club[1] == (hero[1]+1) || club[1] == (hero[1]-1)) && club[0] == (hero[0]))
				state = EnumGameState.Lost;
		}
	}

}



