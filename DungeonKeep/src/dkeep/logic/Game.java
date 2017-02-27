package dkeep.logic;

import java.util.Random;

import ckeep.cli.InputUser.EnumMoves;

public class Game {

	private EnumGameState state;
	private Level level;
	private Hero hero;
	private Guard guard;
	private Club[] ogreClubs;
	private Ogre[] crazyOgres;

	public enum EnumGameState {
		Running,
		Win,
		Lost,
	}

	public enum EnumLevel {
		LEVELONE, 
		LEVELTWO,
	}

	public Game() {
		this.state = EnumGameState.Running;
		this.level = new Level(EnumLevel.LEVELONE);
		this.hero = new Hero();

		//init ogres and clubs
		initOgresClubs();

		//init guard
		Random oj = new Random();
		int num = oj.nextInt(3);

		System.out.print("GUARD: ");	
		switch(num) {
		case 0:
			this.guard = new RookieGuard();
			System.out.println("Rookie Guard");	
			break;
		case 1:
			this.guard = new DrunkGuard();
			System.out.println("Drunk Guard");	
			break;
		case 2:
			this.guard = new ParanoidGuard();
			System.out.println("Paranoid Guard");	
			break;
		}

	}

	public void initOgresClubs() {

		Random oj = new Random();
		int num = oj.nextInt(3)+1;

		crazyOgres = new Ogre[num];
		ogreClubs = new Club[num];
		for(int i = 0; i < num; i++)
		{
			this.crazyOgres[i] = new Ogre();
			this.ogreClubs[i] = new Club(this.crazyOgres[i].GetXCoordinate(), this.crazyOgres[i].GetYCoordinate());
		}
	}

	public EnumGameState GetGameState() {
		return state;
	}

	public void SetGameState(EnumGameState state) {
		this.state = state;
	}

	public void checkLostGame()
	{
		if(level.GetLevel() == EnumLevel.LEVELONE) {
			if(((guard.GetGuard()[guard.GetIndexGuard()][0] == (hero.GetYCoordinate()+1) || guard.GetGuard()[guard.GetIndexGuard()][0] == (hero.GetYCoordinate()-1)) && guard.GetGuard()[guard.GetIndexGuard()][1] == hero.GetXCoordinate()) || 
					(guard.GetGuard()[guard.GetIndexGuard()][1] == (hero.GetXCoordinate()+1) || guard.GetGuard()[guard.GetIndexGuard()][1] == (hero.GetXCoordinate()-1)) && guard.GetGuard()[guard.GetIndexGuard()][0] == hero.GetYCoordinate())
				state = EnumGameState.Lost;
		}
		else
		{

			for(int i = 0; i < crazyOgres.length; i++)
			{
				if(((crazyOgres[i].GetYCoordinate() == (hero.GetYCoordinate()+1) || crazyOgres[i].GetYCoordinate() == (hero.GetYCoordinate()-1)) && crazyOgres[i].GetXCoordinate() == (hero.GetXCoordinate())) || 
						(crazyOgres[i].GetXCoordinate() == (hero.GetXCoordinate()+1) || crazyOgres[i].GetXCoordinate() == (hero.GetXCoordinate()-1)) && crazyOgres[i].GetYCoordinate() == (hero.GetYCoordinate()))
					state = EnumGameState.Lost;
				
				if(((ogreClubs[i].GetYCoordinate() == (hero.GetYCoordinate()+1) || ogreClubs[i].GetYCoordinate() == (hero.GetYCoordinate()-1)) && ogreClubs[i].GetXCoordinate() == (hero.GetXCoordinate())) || 
						(ogreClubs[i].GetXCoordinate() == (hero.GetXCoordinate()+1) || ogreClubs[i].GetXCoordinate() == (hero.GetXCoordinate()-1)) && ogreClubs[i].GetYCoordinate() == (hero.GetYCoordinate()))
					state = EnumGameState.Lost;
			}
		}
	}

	public void moveHero(EnumMoves movement) {
		if(movement == EnumMoves.LEFT) {

			if(level.GetLevelBoard().GetBoard()[hero.GetXCoordinate()][hero.GetYCoordinate()-1] == ' ')
				hero.moveHero(hero.GetXCoordinate(), hero.GetYCoordinate()-1);
			else if(level.GetLevelBoard().GetBoard()[hero.GetXCoordinate()][hero.GetYCoordinate()-1] == 'k')
			{
				if(level.GetLevel() == EnumLevel.LEVELONE){
					hero.moveHero(hero.GetXCoordinate(), hero.GetYCoordinate()-1);
					level.activateLever();
					hero.SetHeroOnLever(true);
				}

			}
			else if(level.GetLevelBoard().GetBoard()[hero.GetXCoordinate()][hero.GetYCoordinate()-1] == 'S')
			{
				if(level.GetLevel() == EnumLevel.LEVELONE)
				{
					hero.SetYCoordinate(1);
					hero.SetXCoordinate(7);
					level.SetLevel(EnumLevel.LEVELTWO);
					hero.SetHeroOnLever(false);
				}
				else 
				{
					hero.moveHero(hero.GetXCoordinate(), hero.GetYCoordinate()-1);
					SetGameState(EnumGameState.Win);
				}
			}
			else if(level.GetLevelBoard().GetBoard()[hero.GetXCoordinate()][hero.GetYCoordinate()-1] == 'I')
			{
				level.activateLever();
			}

		} else if(movement == EnumMoves.RIGHT) {

			if(level.GetLevelBoard().GetBoard()[hero.GetXCoordinate()][hero.GetYCoordinate()+1] == ' ')
				hero.moveHero(hero.GetXCoordinate(), hero.GetYCoordinate()+1);
			else if(level.GetLevelBoard().GetBoard()[hero.GetXCoordinate()][hero.GetYCoordinate()+1] == 'k')
			{
				hero.moveHero(hero.GetXCoordinate(), hero.GetYCoordinate()+1);
				hero.SetHeroOnLever(true);
			}
		} else if(movement == EnumMoves.UP) {

			if(level.GetLevelBoard().GetBoard()[hero.GetXCoordinate()-1][hero.GetYCoordinate()] == ' ')
				hero.moveHero(hero.GetXCoordinate()-1, hero.GetYCoordinate());
			else if(level.GetLevelBoard().GetBoard()[hero.GetXCoordinate()-1][hero.GetYCoordinate()] == 'k')
			{
				hero.moveHero(hero.GetXCoordinate()-1, hero.GetYCoordinate());
				hero.SetHeroOnLever(true);
			}

		} else if(movement == EnumMoves.DOWN) {
			if(level.GetLevelBoard().GetBoard()[hero.GetXCoordinate()+1][hero.GetYCoordinate()] == ' ')
				hero.moveHero(hero.GetXCoordinate()+1, hero.GetYCoordinate());
			else if(level.GetLevelBoard().GetBoard()[hero.GetXCoordinate()+1][hero.GetYCoordinate()] == 'k')
			{
				hero.moveHero(hero.GetXCoordinate()+1, hero.GetYCoordinate());
				hero.SetHeroOnLever(true);
			}
		}
	}

	public void addEnemiesGame() {
		if(level.GetLevel() == EnumLevel.LEVELONE ){
			guard.moveGuard();
		} 
		else if(level.GetLevel() == EnumLevel.LEVELTWO) {
			for(int i = 0; i < crazyOgres.length; i++)
			{
				crazyOgres[i].movesCrazyOgre(level);
				ogreClubs[i].checkAsterisk(level, crazyOgres[i]);
			}

		}
	}

	public Level GetGameLevel(){
		return level;
	}

	public Hero GetHero() {
		return hero;
	}

	public Guard GetGuard() {
		return guard;
	}

	public Ogre[] GetOgres() {
		return crazyOgres;
	}

	public Club[] GetClub() {
		return ogreClubs;
	}
}



