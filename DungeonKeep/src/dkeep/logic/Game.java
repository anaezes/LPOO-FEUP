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
	
	public Level GetGameLevel() {
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



