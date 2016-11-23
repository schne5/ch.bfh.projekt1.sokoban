package ch.bfh.projekt1.sokoban;

/*
 *@author:Elisa, Anna
 */
public class GraphTuple {
	private GameElementType gameElementType;
	private Position position;

	public GraphTuple(GameElementType gameElementType) {
		this.gameElementType = gameElementType;
	}

	public GameElementType getGameElementType() {
		return gameElementType;
	}

	public void setGameElementType(GameElementType gameElementType) {
		this.gameElementType = gameElementType;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setPosition(int x, int y) {
		this.position = new Position(x, y);
	}

}
