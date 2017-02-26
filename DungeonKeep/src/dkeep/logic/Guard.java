package dkeep.logic;

public class Guard {
	
	private int[][] position;
	private int indexGuard;
	
	public Guard() {
		this.position = new int[][] {{8,1},{7,1},{7,3},{7,4},{7,5},{6,5},{5,5},{4,5},{3,5},{2,5},
				{1,5},{1,6},{2,6},{3,6},{4,6},{5,6},{6,6},{7,6},{8,6},{8,5},{8,4},{8,3},{8,2}};
				
		this.indexGuard = 0;

	}
	
	public int[][] GetGuard() {
		return position;
	}
	
	public int GetIndexGuard() {
		return indexGuard;
	}
	
	public void moveGuard() {
		if(indexGuard < 22)
			indexGuard++;
		else
			indexGuard = 0;
	}
}
