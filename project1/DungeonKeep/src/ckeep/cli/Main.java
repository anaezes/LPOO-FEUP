package ckeep.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import dkeep.logic.GameMap;
import utils.BoardUtils;

public class Main {
	public static void main(String[] args) {	
		Random oj = new Random();
		int numOgres = oj.nextInt(5)+1;

		List<GameMap> gameMaps = new ArrayList<>();
		GameMap gameMap1 = new GameMap(BoardUtils.getSimpleBoardOne());
		GameMap gameMap2 = new GameMap(BoardUtils.getSimpleBoardTwo());
		gameMaps.add(gameMap1);
		gameMaps.add(gameMap2);
		
		InputUser inputUser = new InputUser(gameMaps, numOgres, BoardUtils.getSimpleGuardXmovement(), BoardUtils.getSimpleGuardYmovement());
		inputUser.run();
	}
}
