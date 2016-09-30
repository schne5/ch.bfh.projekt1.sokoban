package ch.bfh.projekt1.sokoban;

public class Position {
	private int posX;
	private int posY;

	public Position(int posX, int posY) {
		setPosX(posX);
		setPosY(posY);
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public boolean equals(Position position) {
		return this.posX == position.getPosX()
				&& this.posY == position.getPosY();
	}

}
