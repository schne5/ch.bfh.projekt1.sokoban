package ch.bfh.projekt1.sokoban;

import java.awt.Image;
import java.io.Serializable;

/*
 *@author:Elisa, Anna
 */
public abstract class GameElement implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String IMAGE_PATH = "images/./";
	private Position position;
	transient private Image image;

	public GameElement(int posX, int posY) {
		position = new Position(posX, posY);
	}

	public GameElement() {

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

	public abstract String getImagePath();

	public abstract void loadImage();

}
