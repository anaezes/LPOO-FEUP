package dkeep.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import dkeep.logic.*;
import dkeep.logic.Game.EnumGameState;
import dkeep.logic.Vilan.EnumVillainType;

public class TestDungeonGameLogic {

	char[][] map = {{'X', 'X', 'X', 'X', 'X'},
			{'X', 'H', ' ', 'G', 'X'},
			{'S', ' ', ' ', ' ', 'X'},
			{'S', 'k', ' ', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X'}};

	char[][] mapWithOgre = {{'X', 'X', 'X', 'X', 'X'},
			{'X', 'H', ' ', ' ', 'X'},
			{'S', ' ', 'O', '*', 'X'},
			{'S', 'k', ' ', ' ', 'X'},
			{'X', 'X', 'X', 'X', 'X'}};

	char[][] mapWithOgreAndArm = {{'X', 'X', 'X', 'X', 'X'},
			{'X', 'H', ' ', ' ', 'X'},
			{'S', 'a', 'O', '*', 'X'},
			{'S', 'k', ' ', ' ', 'X'},
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
		Game game = new Game(gameMap, true);
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
		Game game = new Game(gameMap, true);
		game.moveHero(EnumMoves.LEFT);
		String cellPosition = "(1,1)";
		assertEquals(cellPosition, game.getHero().getCordinates());
	}

	@Test
	public void testHeroIsCapturedByGuard(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap, true);
		assertFalse(game.isGameOver());
		game.moveHero(EnumMoves.RIGHT);
		assertTrue(game.isGameOver());
		assertEquals(EnumGameState.Lost, game.getGameState());
	}


	@Test
	public void testHeroMoveIntoExit(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap, true);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.LEFT);
		String cellPosition = "(2,1)";
		String doorPosition = "(2,0)";
		assertEquals(cellPosition, game.getHero().getCordinates());
		assertEquals(doorPosition, game.getExitDoors().get(0).getCoordinates());
	}


	@Test
	public void testHeroMoveIntoStairs(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap, true);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.LEFT);
		game.moveHero(EnumMoves.LEFT);
		assertTrue(game.getLever().getLeverState());
		assertFalse(game.isGameOver());
		assertEquals(EnumGameState.Win, game.getGameState());
	}

	@Test
	public void testExitDoors(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap, true);
		assertFalse(game.getLever().getLeverState());
		assertNotEquals('S', game.getExitDoors().get(0).getCharacter());
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.LEFT);
		game.moveHero(EnumMoves.LEFT);
		assertTrue(game.getLever().getLeverState());
		assertEquals('S', game.getExitDoors().get(0).getCharacter());
		assertFalse(game.isGameOver());
		assertEquals(EnumGameState.Win, game.getGameState());
		game.getHero().setOnLeverState(true);
		assertTrue(game.getHero().getHeroOnLeverState());
	}

	//Task2
	@Test
	public void testDefeatOgre(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap, true);
		game.setCanMoveGuard(false);
		game.moveHero(EnumMoves.DOWN);
		game.checkLostGame();
		assertEquals(EnumGameState.Lost, game.getGameState());
	}

	@Test
	public void testDefeatOgreWithArm(){
		GameMap gameMap = new GameMap(mapWithOgreAndArm);
		Game game = new Game(gameMap, true);
		game.setCanMoveGuard(false);
		game.moveHero(EnumMoves.DOWN);
		game.checkLostGame();
		assertNotEquals(EnumGameState.Lost, game.getGameState());
		assertEquals(EnumGameState.Running, game.getGameState());	
	}

	@Test 
	public void leverChangesRepresentation(){
		GameMap gameMap = new GameMap(map);
		Game game = new Game(gameMap, true);
		game.moveHero(EnumMoves.DOWN);
		assertEquals(game.getLever().getCharacter(),'k');
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.UP);
		assertEquals(game.getLever().getCharacter(),'K');
	}

	@Test
	public void TryToLeaveExitDoor(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap, true);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.LEFT);
		String cellPosition = "(2,1)";
		assertEquals(cellPosition, game.getHero().getCordinates());
	}

	@Test
	public void testHeroMoveAndOpensDoor(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap, true);
		game.setCanMoveGuard(false);
		char keyCharacter = 'c';
		assertEquals(keyCharacter, game.getKey().getCharacter());
		assertFalse(game.getExitDoors().get(0).getDoorExitState());
		assertEquals('h', game.getHero().getCharacter());
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.DOWN);
		game.moveHero(EnumMoves.LEFT);
		game.moveHero(EnumMoves.LEFT);
		assertTrue(game.getExitDoors().get(0).getDoorExitState());


		assertTrue(game.getKey().getKeyState());
		assertNotEquals(EnumGameState.Lost, game.getGameState());
		assertEquals(EnumGameState.Win, game.getGameState());
	}




	//Task3
	@Test(timeout=100)
	public void testOgreMove(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap, true);
		int currLevel = game.getGameLevel();
		game.nextLevel();
		assertNotEquals(game.getGameLevel(), currLevel);
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
		GameMap gameMap = new GameMap(mapWithOgre);
		Lever lever = new Lever(0,0);
		Club club = new Club(0,0);
		club.setOnLeverState(true);
		assertTrue(club.getOnLeverState());

		Club club1 = new Club(1,0);
		club.setOnLeverState(false);
		assertEquals(false, lever.getLeverState());
		lever.setLeverState(true);
		assertEquals(true, lever.getLeverState());
		assertFalse(club1.getOnLeverState());

		assertEquals('a', club.getCharacter());

		assertTrue(gameMap.checkBoardLeverAbove(3, 1));
	}		

	@Test(timeout=100)
	public void testClubMove(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap, true);
		int currLevel = game.getGameLevel();
		game.nextLevel();
		assertEquals(game.getGameLevel(), currLevel+1);
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
		Game game = new Game(gameMap,true);

		assertEquals( "(2,3)", game.getVilans().get(0).getClub().getCordinates());
		game.getVilans().get(0).getClub().setYCoordinate(2);
		assertNotEquals("(2,3)", game.getVilans().get(0).getClub().getCordinates());

		game.getVilans().get(0).checkClub(gameMap);
		assertNotEquals("(3,3)", game.getVilans().get(0).getClub().getCordinates());
	}

	@Test
	public void TestmoveVillan(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap, true);
		Ogre ogre = (Ogre) game.getVilans().get(0);
		String coord = ogre.getCordinates();
		game.moveVilans();
		assertNotEquals(coord, ogre.getCordinates());
		ogre.checkClub(gameMap);
	}

	@Test
	public void TestmoveGuardPath(){
		int i = 0;
		while(i < 10)
		{	
			char[][] mapCopy = getMapCopy();
			GameMap gameMap = new GameMap(mapCopy);
			Game game = new Game(gameMap, true);

			game.setGuardPath(guard_x, guard_y);

			String coord=game.getVilans().get(0).getCordinates();

			game.moveVilans();
			if(game.getVilans().get(0).getCharacter() != 'g')
				assertNotEquals(coord, game.getVilans().get(0).getCordinates());
			coord=game.getVilans().get(0).getCordinates();
			i++;
		}
	}

	@Test
	public void testOnLeverAndCharacter(){
		GameMap gameMap = new GameMap(mapWithOgre);
		Game game = new Game(gameMap, true);
		assertEquals(true, game.getVilans().get(0).getType() == EnumVillainType.Ogre);
		assertEquals(false, game.getVilans().get(0).GetOnLeverOgre());
		assertEquals('O', game.getVilans().get(0).getCharacter());
	}

	@Test
	public void testBoard(){
		GameMap gameMap1 = new GameMap(mapWithOgre);
		GameMap gameMap2 = new GameMap(map);
		Game game1 = new Game(gameMap1, true);
		Game game2 = new Game(gameMap2, true);
		assertNotEquals(game2.getBoard(), game1.getBoard());
	}

	@Test
	public void testHeroConstructor(){
		Hero hero = new Hero();
		String heroCord = "(2,2)";
		assertNotEquals(heroCord,  hero.getCordinates());
	}

	@Test
	public void testVillanFunctions(){
		Guard guard = new DrunkGuard(2,3);
		assertEquals(null, guard.getClub());
		guard.setPredifinedPath(true);
		assertTrue(guard.getPredifinedPath());


		int index=guard.getIndexGuard();
		assertEquals(0, index);

		Vilan vilan = new Ogre();
		assertFalse(vilan.GetOnLeverOgre());
		assertEquals(0,vilan.getIndexGuard());

	}

	@Test
	public void moveRookieGuard() {
		RookieGuard rokGuard = new RookieGuard(3, 2);
		rokGuard.setPath(guard_y, guard_x);
		GameMap gameMap = new GameMap(map);
		rokGuard.move(gameMap);
		assertEquals(rokGuard.getIndexGuard(), 1);
		assertEquals(rokGuard.getXCoordinate(), 3);
		assertEquals(rokGuard.getYCoordinate(), 2);
		rokGuard.move(gameMap);
		assertNotEquals(rokGuard.getIndexGuard(), 1);
		assertNotEquals(rokGuard.getXCoordinate(), 3);
		assertEquals(rokGuard.getYCoordinate(), 2);

	}

	@Test
	public void CreateGameWithListAndNumOgres(){
		GameMap mapOne = new GameMap(map);
		GameMap mapTwo = new GameMap(mapWithOgre);
		GameMap mapThree = new GameMap(mapWithOgreAndArm);
		List<GameMap> gameMaps = new ArrayList<>();
		gameMaps.add(mapOne);
		gameMaps.add(mapTwo);
		gameMaps.add(mapThree);

		Game game = new Game(gameMaps, 3);

		assertEquals(game.getNumOgres(), 3);
		assertEquals(game.isGameOver(), false);
		assertEquals(game.getBoard(), mapOne);

	}

	@Test
	public void CreateGameWithListGuardTypeAndOgres() {
		GameMap mapOne = new GameMap(map);
		GameMap mapTwo = new GameMap(mapWithOgre);
		GameMap mapThree = new GameMap(mapWithOgreAndArm);
		List<GameMap> gameMaps = new ArrayList<>();
		gameMaps.add(mapOne);
		gameMaps.add(mapTwo);
		gameMaps.add(mapThree);

		Game game = new Game(gameMaps, EnumGuardType.Drunk, 2);

		assertEquals(game.getNumOgres(), 2);
		assertEquals(game.isGameOver(), false);
		assertEquals(game.getBoard(), mapOne);
		assertEquals(game.getGuardType(), EnumGuardType.Drunk);
	}

	@Test
	public void moveDrunkGuardOnOtherDirection() {
		DrunkGuard guard = new DrunkGuard(2, 3);
		guard.setPath(guard_y, guard_x);
		GameMap board = new GameMap(map);
		guard.setDirection('b');
		guard.move(board);
		boolean up=false, down=false, left=false, right= false;
		while( !up && !down  && !left  && !right) {
			if(guard.getXCoordinate() == 2) {
				up = true;
			}
			else if(guard.getXCoordinate() == 3) {
				down = true;
			}
			else if(guard.getYCoordinate() == 2) {
				left = true;
			}
			else if(guard.getYCoordinate() == 1) {
				right = true;
			} else {
				fail("fail test");
			}
		}		
	}

	@Test
	public void moveDrunkGuardOnLimits() {
		DrunkGuard guard = new DrunkGuard(2, 3);
		guard.setPath(guard_y, guard_x);
		guard.setIndex(23);
		GameMap board = new GameMap(map);
		guard.move(board);
		if(guard.getCharacter() == 'g')
			assertEquals(23, guard.getIndexGuard());
		else if(((DrunkGuard)guard).getDirection() == 'f')
			assertEquals(0, guard.getIndexGuard());
		else 
			assertEquals(22, guard.getIndexGuard());
	}
	
//	@Test
//	public void moveParanoidGuardOnLimits() {
//		ParanoidGuard guard = new ParanoidGuard(2, 3);
//		guard.setPath(guard_y, guard_x);
//		guard.setIndex(22);
//		GameMap board = new GameMap(map);
//		guard.move(board);
//		if(((ParanoidGuard)guard).getDirection() == 'f')
//			assertEquals(23, guard.getIndexGuard());
//		else 
//			assertEquals(0, guard.getIndexGuard());
//	}

	@Test
	public void moveParanoidGuardWhileNotSleep() {
		DrunkGuard guard = new DrunkGuard(2, 3);
		guard.setPath(guard_y, guard_x);
		guard.setIndex(23);
		GameMap board = new GameMap(map);
		while(guard.getCharacter() != 'g'){
			int index = guard.getIndexGuard();
			guard.move(board);
			if(guard.getCharacter() != 'g')
			assertNotEquals(guard.getIndexGuard(), index);
		}
	}
	
	@Test
	public void moveParanoidGuardOnOtherDirection() {
		ParanoidGuard guard = new ParanoidGuard(2, 3);
		guard.setPath(guard_y, guard_x);
		GameMap board = new GameMap(map);
		guard.setIndex(22);
		guard.move(board);
		boolean up=false, down=false, left=false, right= false;
		while( !up && !down  && !left  && !right) {
			if(guard.getXCoordinate() == 2) {
				up = true;
			}
			else if(guard.getXCoordinate() == 3) {
				down = true;
			}
			else if(guard.getYCoordinate() == 2) {
				left = true;
			}
			else if(guard.getYCoordinate() == 1) {
				right = true;
			} else {
				fail("fail test");
			}
		}		
	}

	@Test
	public void initOgres() {
		GameMap mapThree = new GameMap(mapWithOgreAndArm);
		List<GameMap> gameMaps = new ArrayList<>();
		gameMaps.add(mapThree);
		List<Vilan> v = new ArrayList<>();	
		v.add(new Ogre(3, 2, 4, 2));


		Game game = new Game(gameMaps, 1);

		game.initOgres(3, 2, 4, 2);
		assertEquals(game.getVilans().size(), 1);
		assertEquals(game.getVilans().get(0).getCharacter(), v.get(0).getCharacter());

	}

	@Test
	public void initOgresRandom() {
		GameMap mapThree = new GameMap(mapWithOgreAndArm);
		Game game = new Game(mapThree, true);
		game.initOgres(3, 2, 4, 2);
		assertTrue(game.getVilans().size() <= 5 && game.getVilans().size() >= 1);
	}

	@Test
	public void changeGameMap() {
		GameMap map = new GameMap(mapWithOgreAndArm);
		map.setSelectedBoard(mapWithOgre);
		assertArrayEquals(map.getSelectedBoard(), mapWithOgre);

	}

}