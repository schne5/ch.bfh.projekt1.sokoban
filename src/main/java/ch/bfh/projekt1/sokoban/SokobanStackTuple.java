package ch.bfh.projekt1.sokoban;

/*
 *@author:Elisa, Anna
 */
public class SokobanStackTuple {
	private Activity activity;
	private Direction direction;

	public SokobanStackTuple(Activity activity, Direction direction) {
		this.activity = activity;
		this.direction = direction;
	}

	public SokobanStackTuple() {
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
