package dkeep.test;

import static org.junit.Assert.*;

import org.junit.Test;
import dkeep.logic.*;
import dkeep.logic.Game.EnumGameState;
import dkeep.logic.Game.EnumLevel;

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

	char[][] mapWithOgreAndArm = {{'X', 'X', 'X', 'X', 'X'},
			{'X', 'H', ' ', ' ', 'X'},
			{'S', 'a', 'O', ' ', 'X'},
			{'S', 'k', '*', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X'}};

	int[] guard_x = new int[] {3, 3, 2, 2, 3, 3, 2, 2, 3, 3, 2, 2, 3, 3, 2, 2, 3, 3, 2, 2, 3, 3, 2, 2};
	int[] guard_y = new int[] {1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1};

	
	public char[][] getMapCopy() {
		char[][] mapCopy = new char[map.length][map[0].length];
		
		for(int i = 0; i < map.length; i++)
			for(int j = 0; j < map[i].length; j++)
				mapCopy[i][j] = map[i][j];
		
		return mapCopy;
	}
	
	
	//Task1
	@Test
	public void testMoveHeroIntoToFreeCell() {
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		String cellPosition1 = "(1,1)";
		assertEquals(cellPosition1, game.getHero().getCordinates());
		game.moveHero(EnumMoves.DOWN);
		String cellPosition2 = "(2,1)";
		assertEquals(cellPosition2, game.getHero().getCordinates());
		game.moveHero(EnumMoves.UP);
		assertEquals(cellPosition1, game.getHero().getCordinates());
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
	public void testExitDoors(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		assertFalse(game.isActiveLever());
		assertNotEquals('S', game.getExitDoors().get(0).getCharacter());
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.LEFT);
		game.moveHero(EnumMoves.LEFT);
		assertTrue(game.isActiveLever());
		assertEquals('S', game.getExitDoors().get(0).getCharacter());
		assertFalse(game.isGameOver());
		assertEquals(EnumGameState.Win, game.getGameState());
	}

	//Task2
	@Test
	public void testDefeatOgre(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap);
		game.moveHero(EnumMoves.DOWN);
		game.checkLostGame();
		assertEquals(EnumGameState.Lost, game.getGameState());
	}

	@Test
	public void testDefeatOgreWithArm(){
		GameMap gameMap = new GameMap(mapWithOgreAndArm);
		Game game = new Game(gameMap);
		game.moveHero(EnumMoves.DOWN);
		game.checkLostGame();
		assertNotEquals(EnumGameState.Lost, game.getGameState());
		assertEquals(EnumGameState.Running, game.getGameState());	
	}

	@Test 
	public void keyChangesRepresentation(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.DOWN);
		assertEquals(game.getHero().getCharacter(),'K');
		game.moveHero(EnumMoves.UP);
		assertEquals(game.getHero().getCharacter(),'K');
	}

	@Test
	public void TryToLeaveExitDoor(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.LEFT);
		String cellPosition = "(2,1)";
		assertEquals(cellPosition, game.getHero().getCordinates());
	}

	@Test
	public void testHeroMoveAndOpensDoor(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.LEFT);
		game.moveHero(EnumMoves.LEFT);
		assertTrue(game.isActiveLever());
		assertFalse(game.isGameOver());
		assertEquals(EnumGameState.Win, game.getGameState());
	}




	//Task3
	@Test(timeout=100)
	public void testOgreMove(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap);
		game.setGameLevel(EnumLevel.LEVELTWO);
		assertEquals(game.getGameLevel(), EnumLevel.LEVELTWO);
		boolean up=false, down=false, left=false, right= false;
		while( !up && !down  && !left  && !right) {
			String oldCoords = game.getVilans().get(0).getCordinates();

			game.getVilans().get(0).move(gameMap);

			String newCoords = game.getVilans().get(0).getCordinates();

			assertNotEquals(oldCoords, newCoords);

			if(game.getVilans().get(0).getXCoordinate() == 1) {
				up = true;
			}
			else if(game.getVilans().get(0).getXCoordinate()==3) {
				down = true;
			}
			else if(game.getVilans().get(0).getYCoordinate() == 1) {
				left = true;
			}
			else if(game.getVilans().get(0).getYCoordinate() == 3) {
				right = true;
			} else {
				fail("fail test, ogre dont move to the expected possible place");
			}
		}		
	}

	//Task4
	@Test
	public void testLeverObject() {
		Lever lever = new Lever(true);
		assertEquals(true, lever.GetLeverState());
		lever.SetLeverState(false);
		assertEquals(false, lever.GetLeverState());
	}


	@Test(timeout=100)
	public void testClubMove(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap);
		game.setGameLevel(EnumLevel.LEVELTWO);
		assertEquals(game.getGameLevel(), EnumLevel.LEVELTWO);
		boolean up=false, down=false, left=false, right= false;
		while( !up && !down  && !left  && !right) {

			game.getVilans().get(0).checkClub(gameMap);

			if(game.getVilans().get(0).getClub().getXCoordinate() == 1) {
				up = true;
			}
			else if(game.getVilans().get(0).getClub().getXCoordinate()==3) {
				down = true;
			}
			else if(game.getVilans().get(0).getClub().getYCoordinate() == 1) {
				left = true;
			}
			else if(game.getVilans().get(0).getClub().getYCoordinate() == 3) {
				right = true;
			} else {
				fail("fail test, ogre dont move to the expected possible place");
			}
		}	
	}

	@Test
	public void testClubCoordinates(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap);

		assertEquals( "(3,2)", game.getVilans().get(0).getClub().getCordinates());
		game.getVilans().get(0).getClub().setYCoordinate(3);
		assertNotEquals("(3,2)", game.getVilans().get(0).getClub().getCordinates());

		game.getVilans().get(0).checkClub(gameMap);
		assertNotEquals("(3,3)", game.getVilans().get(0).getClub().getCordinates());
	}

	@Test
	public void TestmoveVillan(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap);
		Ogre ogre = (Ogre) game.getVilans().get(0);
		String coord = ogre.getCordinates();
		game.moveVilans();
		assertNotEquals(coord, ogre.getCordinates());
	}
	
	@Test
	public void TestmoveGuardPath(){
		int i = 0;
		while(i < 10)
		{	
			char[][] mapCopy = getMapCopy();
			GameMap gameMap = new GameMap(mapCopy);
			Game game = new Game(gameMap);
			
			game.setGuardPath(guard_x, guard_y);

			String coord=game.getVilans().get(0).getCordinates();

			game.moveVilans();
			if(game.getVilans().get(0).getCharacter()!= 'g')
				assertNotEquals(coord, game.getVilans().get(0).getCordinates());
			coord=game.getVilans().get(0).getCordinates();
			i++;
		}
	}

	@Test
	public void testOnLeverAndCharacter(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap);
		assertEquals(false, game.getVilans().get(0).GetOnLeverOgre());
		assertEquals('O', game.getVilans().get(0).getCharacter());
	}
	
	@Test
	public void testBoard(){
		GameMap gameMap1 = new GameMap(mapWithOgre);
		GameMap gameMap2 = new GameMap(map);
		Game game1 = new Game(gameMap1);
		Game game2 = new Game(gameMap2);
		assertNotEquals(game2.getBoard(), game1.getBoard());
	}


}