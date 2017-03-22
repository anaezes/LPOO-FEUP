package ckeep.cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import dkeep.gui.DungeonKeepUI;
import dkeep.logic.Club;
import dkeep.logic.ExitDoor;
import dkeep.logic.Game;
import dkeep.logic.GameMap;
import dkeep.logic.Hero;
import dkeep.logic.Key;
import dkeep.logic.Lever;
import dkeep.logic.Vilan;
import dkeep.logic.Vilan.EnumVillainType;

public class Print {

	private static String map;

	public static String boardToString(Game game) {
		int boardSize = game.getBoard().getBoardSize();
		Hero hero = game.getHero();
		Lever lever = game.getLever();
		Key key = game.getKey();
		Club heroClub = game.getHeroClub();
		
		map = boardSize + "\n";
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j <  boardSize; j++) {						
				if(hero.getYCoordinate() == j && hero.getXCoordinate() == i)
					map += hero.getCharacter();
				else if(lever != null && lever.getYCoordinate() == j && lever.getXCoordinate() == i)
					map += lever.getCharacter();
				else if(key != null && key.getYCoordinate() == j && key.getXCoordinate() == i)
					map += key.getCharacter();
				else if((heroClub.getYCoordinate() == j && heroClub.getXCoordinate() == i) && !hero.isHeroArmed())
					map += "*";
				else if(!villainsToString(game, i, j) && !clubsToString(game, i, j) && !exitDoorsToString(game, i, j))
					map += game.getBoard().getBoardCaracter(i, j);
			}
			map += "\n";
		}
		return map;
	}

	private static boolean clubsToString(Game game, int i, int j) {
		List<Vilan> vilans = game.getVilans();
		if(vilans == null)
			return true;

		for(int k = 0; k < vilans.size(); k++) {
			if(vilans.get(k).getType() == EnumVillainType.Ogre)	
				if (vilans.get(k).getClub().getYCoordinate() == j && vilans.get(k).getClub().getXCoordinate() == i)
				{
					if (vilans.get(k).getClub().getOnLeverState())
						map += "$";
					else 
						map += "*";
					return true;
				}
		}
		return false;
	}

	private static boolean villainsToString(Game game, int i, int j) {
		List<Vilan> vilans = game.getVilans();
		
		if(vilans == null)
			return true;

		for(int k = 0; k < vilans.size(); k++)
			if(vilans.get(k).getXCoordinate() == i && vilans.get(k).getYCoordinate() == j) {
				if(vilans.get(k).GetOnLeverOgre())
					map += "$";
				else
					map += vilans.get(k).getCharacter();
				return true;
			}
		return false;
	}

	private static boolean exitDoorsToString(Game game, int i, int j) {
		List<ExitDoor> exitsDoors = game.getExitDoors();
		
		if(exitsDoors == null)
			return true;

		for(int k = 0; k < exitsDoors.size(); k++) {
			if(exitsDoors.get(k).getXCoordinate() == i && exitsDoors.get(k).getYCoordinate() == j) {
				map += exitsDoors.get(k).getCharacter();
				return true;
			}
		}
		return false;
	}

	public static void boardToFile(char[][] board, String nameFile) throws IOException {
		File file = new File(DungeonKeepUI.BOARDS_DIR + nameFile + ".txt");
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		writer.write(toString(board));
		writer.close();
	}

	public static String toString(char[][] board) {
		String map = board.length + "\n";
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++)
				map += board[i][j];
			map+='\n';
		}

		return map;
	}


}
