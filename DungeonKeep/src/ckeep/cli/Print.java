package ckeep.cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dkeep.gui.DungeonKeepUI;
import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.Vilan.EnumVillainType;

public class Print {

	private static String map;
	
	public static String boardToString(Game game) {
		map = game.getBoard().getBoardSize() + "\n";
		for(int i = 0; i < game.getBoard().getBoardSize(); i++) {
			for(int j = 0; j <  game.getBoard().getBoardSize(); j++) {						
				if(game.getHero().getYCoordinate() == j && game.getHero().getXCoordinate() == i)
					map += game.getHero().getCharacter();
				else if(game.getLever() != null && game.getLever().getYCoordinate() == j && game.getLever().getXCoordinate() == i)
					map += game.getLever().getCharacter();
				else if(game.getKey() != null && game.getKey().getYCoordinate() == j && game.getKey().getXCoordinate() == i)
					map += game.getKey().getCharacter();
				else if(game.getHero().getYCoordinate() == j && game.getHero().getXCoordinate() == i)
					map += game.getHero().getCharacter();
				else if((game.getHeroClub().getYCoordinate() == j && game.getHeroClub().getXCoordinate() == i) && !game.getHero().isHeroArmed())
					map += "*";
				else if(!villainsToString(game, i, j) && !clubsToString(game, i, j) && !exitDoorsToString(game, i, j))
					map += game.getBoard().getBoardCaracter(i, j);
			}
			map += "\n";
		}
		return map;
	}

	private static boolean clubsToString(Game game, int i, int j) {
		if(game.getVilans() == null)
			return true;

		for(int k = 0; k < game.getVilans().size(); k++) {
			if(game.getVilans().get(k).getType() == EnumVillainType.Ogre)	
				if (game.getVilans().get(k).getClub().getYCoordinate() == j && game.getVilans().get(k).getClub().getXCoordinate() == i)
				{
					if (game.getVilans().get(k).getClub().getOnLeverState())
						map += "$";
					else 
						map += "*";
					return true;
				}
		}
		return false;
	}

	private static boolean villainsToString(Game game, int i, int j) {
		if(game.getVilans() == null)
			return true;

		for(int k = 0; k < game.getVilans().size(); k++)
			if(game.getVilans().get(k).getXCoordinate() == i && game.getVilans().get(k).getYCoordinate() == j) {
				if(game.getVilans().get(k).GetOnLeverOgre())
					map += "$";
				else
					map += game.getVilans().get(k).getCharacter();

				return true;
			}
		return false;
	}

	private static boolean exitDoorsToString(Game game, int i, int j) {
		if(game.getExitDoors() == null)
			return true;

		for(int k = 0; k < game.getExitDoors().size(); k++) {
			if(game.getExitDoors().get(k).getXCoordinate() == i && game.getExitDoors().get(k).getYCoordinate() == j) {
				map += game.getExitDoors().get(k).getCharacter();
				return true;
			}
		}
		return false;
	}

	public static void boardToFile(char[][] board, String nameFile) throws IOException {
		File file = new File(DungeonKeepUI.BOARDS_DIR + nameFile + ".txt");
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write(boardToString(new Game(new GameMap(board))));
		writer.close();
	}
	
	
}
