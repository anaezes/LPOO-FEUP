package dkeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import dkeep.logic.*;
import dkeep.logic.Character;
import dkeep.logic.Game.EnumGameState;
import dkeep.logic.Game.EnumLevel;
import ckeep.cli.InputUser.EnumMoves;

public class TestDungeonGameLogic {

	char[][] map = {{'X', 'X', 'X', 'X', 'X'},
			{'X', 'H', ' ', 'G', 'X'},
			{'S', ' ', ' ', ' ', 'X'},
			{'S', 'k', ' ', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X'}};
	
	char[][] mapWithOgre = {{'X', 'X', 'X', 'X', 'X'},
			{'X', 'H', ' ', ' ', 'X'},
			{'S', ' ', 'O', ' ', 'X'},
			{'S', 'k', '*', ' ', 'X'},
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
	
	@Test
	public void testLeverObject() {
		Lever lever = new Lever(true);
		assertEquals(true, lever.GetLeverState());
		lever.SetLeverState(false);
		assertEquals(false, lever.GetLeverState());
	}
	
	@Test(timeout=100)
	public void testOgreMove(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap);
		game.setGameLevel(EnumLevel.LEVELTWO);
		assertEquals(game.getGameLevel(), EnumLevel.LEVELTWO);
		Ogre ogre = new Ogre(2, 2, 3, 2);
		boolean up=false, down=false, left=false, right= false;
		while( up == false && down ==false && left == false && right == false) {
			String oldCoords = ogre.getCordinates();
			System.out.println(ogre.getCordinates());

			ogre.move(gameMap);
			
			System.out.println(ogre.getCordinates());
			String newCoords = ogre.getCordinates();
			
			assertNotEquals(oldCoords, newCoords);

			if(ogre.getXCoordinate() == 1) {
				System.out.println("UP");
				up = true;
			}
			else if(ogre.getXCoordinate() == 3) {
				System.out.println("DONW");
				down = true;
			}
			else if(ogre.getYCoordinate() == 1) {
				System.out.println("LEFT");
				left = true;
			}
			else if(ogre.getYCoordinate() == 3) {
				System.out.println("RIGHT");
				right = true;
			} else {
				fail("fail test, ogre dont move to the expected possible place");
			}
		}		
	}
	

}