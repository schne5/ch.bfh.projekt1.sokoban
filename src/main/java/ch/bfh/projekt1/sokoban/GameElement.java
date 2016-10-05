package ch.bfh.projekt1.sokoban;

import java.awt.Image;

/*
 *@author:Elisa, Anna
 */
public abstract class GameElement {
	public static final String IMAGE_PATH = "images/./";
	private Position position;
	private Image image;

	public GameElement(int posX, int posY) {
		position = new Position(posX, posY);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
