package ch.bfh.projekt1.sokoban;

import java.io.Serializable;

/*
 *@author:Elisa, Anna
 */
public class Position implements Serializable {
	private static final long serialVersionUID = 1L;
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
