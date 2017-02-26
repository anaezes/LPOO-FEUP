package dkeep.logic;

public class Lever {
	
	private boolean state;
	
	public Lever(boolean state){
		this.state = state;
	}
	
	public boolean GetLeverState() {
		return state;
	}
	
	public void SetLeverState(boolean state) {
		this.state = state;
	}
}
