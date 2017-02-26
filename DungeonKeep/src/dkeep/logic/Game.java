package dkeep.logic;

public class Game {
	
	private EnumGameState state;
	private Level level;
	private Hero hero;
	private Guard guard;
	private Ogre crazyOgre;
	private Club club;
	
	public Game() {
		this.state = EnumGameState.Running;
		this.level = new Level(EnumLevel.LEVELONE);
		this.hero = new Hero();
		this.guard = new Guard();
		this.crazyOgre = new Ogre();
		this.club = new Club();

	}
	
	public EnumGameState GetGameState() {
		return state;
	}
	
	public void SetGameState(EnumGameState state) {
		this.state = state;
	}
				
	public void checkLostGame()
	{
		if(level.GetLevel() == EnumLevel.LEVELONE)
		{
			if(((guard.GetGuard()[guard.GetIndexGuard()][0] == (hero.GetYCoordinate()+1) || guard.GetGuard()[guard.GetIndexGuard()][0] == (hero.GetYCoordinate()-1)) && guard.GetGuard()[guard.GetIndexGuard()][1] == hero.GetXCoordinate()) || 
					(guard.GetGuard()[guard.GetIndexGuard()][1] == (hero.GetXCoordinate()+1) || guard.GetGuard()[guard.GetIndexGuard()][1] == (hero.GetXCoordinate()-1)) && guard.GetGuard()[guard.GetIndexGuard()][0] == hero.GetYCoordinate())
				state = EnumGameState.Lost;
		}
		else
		{
			if(((crazyOgre.GetYCoordinate() == (hero.GetYCoordinate()+1) || crazyOgre.GetYCoordinate() == (hero.GetYCoordinate()-1)) && crazyOgre.GetXCoordinate() == (hero.GetXCoordinate())) || 
					(crazyOgre.GetXCoordinate() == (hero.GetXCoordinate()+1) || crazyOgre.GetXCoordinate() == (hero.GetXCoordinate()-1)) && crazyOgre.GetYCoordinate() == (hero.GetYCoordinate()))
				state = EnumGameState.Lost;

			if(((club.GetYCoordinate() == (hero.GetYCoordinate()+1) || club.GetYCoordinate() == (hero.GetYCoordinate()-1)) && club.GetXCoordinate() == (hero.GetXCoordinate())) || 
					(club.GetXCoordinate() == (hero.GetXCoordinate()+1) || club.GetXCoordinate() == (hero.GetXCoordinate()-1)) && club.GetYCoordinate() == (hero.GetYCoordinate()))
				state = EnumGameState.Lost;
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

		} else if(level.GetLevel() == EnumLevel.LEVELTWO) {
			crazyOgre.movesCrazyOgre(level);
			club.checkAsterisk(level, crazyOgre);
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
	
	public Ogre GetOgre() {
		return crazyOgre;
	}
	
	public Club GetClub() {
		return club;
	}
}



