package ckeep.cli;

import dkeep.logic.Game;
import dkeep.logic.Vilan.EnumVillainType;

public class Print {
	
	private String map;

	public String printBoard(Game game)
	{
		map = "\n";
		//System.out.println();

		for(int i = 0; i < game.getBoard().getBoardSize(); i++) {
			map+=" ";
			for(int j = 0; j <  game.getBoard().getBoardSize(); j++) {						
				if(game.getHero().getYCoordinate() == j && game.getHero().getXCoordinate() == i)
					//System.out.print(game.getHero().getHeroCharacter());
					map += game.getHero().getCharacter();
				else if(game.getLever().getYCoordinate() == j && game.getLever().getXCoordinate() == i)
					//System.out.print(game.getHero().getHeroCharacter());
					map += game.getLever().getCharacter();
				else if(game.getHero().getYCoordinate() == j && game.getHero().getXCoordinate() == i)
					//System.out.print(game.getHero().getHeroCharacter());
					map += game.getHero().getCharacter();
				else if((game.getHeroClub().getYCoordinate() == j && game.getHeroClub().getXCoordinate() == i) && !game.getHero().isHeroArmed())
					//System.out.print('*');
					map += "*";
				else if(!printVilans(game, i, j) && !printClubs(game, i, j) && !printExitDoors(game, i, j))
					//System.out.print(game.getBoard().getBoardCaracter(i, j));
					map += game.getBoard().getBoardCaracter(i, j);

			//	System.out.print(" ");
				map += " ";
 			}
			map += "\n";
			//System.out.println();
		}
		map += "\n";
		//System.out.println();
		return map;
	}

	private boolean printClubs(Game game, int i, int j) {

		for(int k = 0; k < game.getVilans().size(); k++) {
			if(game.getVilans().get(k).getType() == EnumVillainType.Ogre)	
				if (game.getVilans().get(k).getClub().getYCoordinate() == j && game.getVilans().get(k).getClub().getXCoordinate() == i)
				{
					if (game.getVilans().get(k).getClub().getOnLeverState())
						//System.out.print('$');
						map += "$";
					else 
						//System.out.print('*');
						map += "*";
					return true;
				}
		}
		return false;
	}

	public boolean printVilans(Game game, int i, int j) {

		for(int k = 0; k < game.getVilans().size(); k++)
			if(game.getVilans().get(k).getXCoordinate() == i && game.getVilans().get(k).getYCoordinate() == j) {
				if(game.getVilans().get(k).GetOnLeverOgre())
					//System.out.print('$');
					map += "$";
				else
					//System.out.print(game.getVilans().get(k).getCharacter());
					map += game.getVilans().get(k).getCharacter();

				return true;
			}
		return false;
	}

	public boolean printExitDoors(Game game, int i, int j) {

		for(int k = 0; k < game.getExitDoors().size(); k++) {
			if(game.getExitDoors().get(k).getXCoordinate() == i && game.getExitDoors().get(k).getYCoordinate() == j) {
				//System.out.print(game.getExitDoors().get(k).getCharacter());
				map += game.getExitDoors().get(k).getCharacter();
				return true;
			}
		}
		return false;
	}
}
