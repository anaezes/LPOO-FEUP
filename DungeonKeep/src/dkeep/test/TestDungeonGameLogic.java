package dkeep.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import dkeep.logic.*;
import dkeep.logic.Character;
import dkeep.logic.Game.EnumGameState;
import dkeep.logic.Game.EnumLevel;
import ckeep.cli.InputUser;
import ckeep.cli.Print;
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
	//Task2

	@Test
	public void testDefeatOgre(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap);
		game.setGameLevel(EnumLevel.LEVELTWO);
		Ogre ogre = new Ogre(2, 2, 3, 2);
		game.moveHero(EnumMoves.DOWN);
		game.checkLostGame();
		assertEquals(EnumGameState.Lost, game.getGameState());
	}
	
	
	@Test 
	public void keyChangesRepresentation(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.DOWN);
		assertEquals(game.getHero().getHeroCharacter(),'K');
		game.moveHero(EnumMoves.UP);
		assertEquals(game.getHero().getHeroCharacter(),'K');
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
			System.out.println(game.getVilans().get(0).getCordinates());

			game.getVilans().get(0).move(gameMap);
			game.getVilans().get(0).checkClub(gameMap);
			
			String newCoords = game.getVilans().get(0).getCordinates();
			
			assertNotEquals(oldCoords, newCoords);

			if(game.getVilans().get(0).getXCoordinate() == 1) {
				System.out.println("UP");
				up = true;
			}
			else if(game.getVilans().get(0).getXCoordinate()==3) {
				System.out.println("DONW");
				down = true;
			}
			else if(game.getVilans().get(0).getYCoordinate() == 1) {
				System.out.println("LEFT");
				left = true;
			}
			else if(game.getVilans().get(0).getYCoordinate() == 3) {
				System.out.println("RIGHT");
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
	
	public void testInputUserMistakes(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap);
		Game game2 = new Game(gameMap);
		
		InputUser user = new InputUser(game);
		user.run();
		user.keyPressed("d");
		System.out.println(" hero");
		
		System.out.println(game.getHero().getCordinates());
		//assertEquals(game.getHero().getCordinates(), "(3,1)");
		
		assertNotEquals(' ', game.getBoard().getBoardCaracter(1, 1));

		assertEquals('H', game.getBoard().getBoardCaracter(1, 1));
		
		
		
	}
	
	@Test(timeout=100)
	public void testClubMove(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap);
		game.setGameLevel(EnumLevel.LEVELTWO);
		assertEquals(game.getGameLevel(), EnumLevel.LEVELTWO);
		boolean up=false, down=false, left=false, right= false;
		while( !up && !down  && !left  && !right) {
			String oldCoords = game.getVilans().get(0).getClub().getCordinates();
			System.out.println(game.getVilans().get(0).getClub().getCordinates());

			game.getVilans().get(0).move(gameMap);
			game.getVilans().get(0).checkClub(gameMap);
			String newCoords = game.getVilans().get(0).getClub().getCordinates();
			

			if(game.getVilans().get(0).getClub().getXCoordinate() == 1) {
				System.out.println("UP");
				up = true;
			}
			else if(game.getVilans().get(0).getClub().getXCoordinate()==3) {
				System.out.println("DONW");
				down = true;
			}
			else if(game.getVilans().get(0).getClub().getYCoordinate() == 1) {
				System.out.println("LEFT");
				left = true;
			}
			else if(game.getVilans().get(0).getClub().getYCoordinate() == 3) {
				System.out.println("RIGHT");
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
		
		assertEquals(game.getVilans().get(0).getClub().getCordinates(), "(3,2)");
		game.getVilans().get(0).getClub().setYCoordinate(3);
		assertNotEquals(game.getVilans().get(0).getClub().getCordinates(), "(3,2)");
		
		game.getVilans().get(0).checkClub(gameMap);
		assertNotEquals(game.getVilans().get(0).getClub().getCordinates(), "(3,3)");
		
	}

}