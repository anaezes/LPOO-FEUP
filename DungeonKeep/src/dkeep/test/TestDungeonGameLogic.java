package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.*;
import dkeep.logic.Game.EnumGameState;
import ckeep.cli.InputUser.EnumMoves;

public class TestDungeonGameLogic {

	char[][] map = {{'X', 'X', 'X', 'X', 'X'},
			{'X', 'H', ' ', 'G', 'X'},
			{'S', ' ', ' ', ' ', 'X'},
			{'S', 'k', ' ', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X'}};

	@Test
	public void testMoveHeroIntoToFreeCell() {
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		String cellPosition1 = "(1,1)";
		assertEquals(cellPosition1, game.getHero().getCordinates());
		game.moveHero(EnumMoves.DOWN);
		String cellPosition2 = "(2,1)";
		assertEquals(cellPosition2, game.getHero().getCordinates());
	}

	@Test
	public void testHeroMoveIntoAWall(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		game.moveHero(EnumMoves.LEFT);
		String cellPosition = "(1,1)";
		assertEquals(cellPosition, game.getHero().getCordinates());
	}
	
	@Test
	public void testHeroIsCapturedByGuard(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		assertFalse(game.isGameOver());
		game.moveHero(EnumMoves.RIGHT);
		assertTrue(game.isGameOver());
		assertEquals(EnumGameState.Lost, game.getGameState());
	}
	

	@Test
	public void testHeroMoveIntoExit(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.LEFT);
		String cellPosition = "(2,1)";
		assertEquals(cellPosition, game.getHero().getCordinates());
	}
	
	@Test
	public void testHeroMoveIntoStairs(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.LEFT);
		game.moveHero(EnumMoves.LEFT);
		assertTrue(game.isActiveLever());
		assertFalse(game.isGameOver());
		assertEquals(EnumGameState.Win, game.getGameState());
	}
	

}