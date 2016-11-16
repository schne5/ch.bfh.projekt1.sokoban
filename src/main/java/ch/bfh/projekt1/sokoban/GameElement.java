package ch.bfh.projekt1.sokoban;

import java.awt.Image;
import java.io.Serializable;

/*
 *@author:Elisa, Anna
 */
public abstract class GameElement implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String IMAGE_PATH = "images/./";
	transient private Image image;

	public GameElement() {

	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}
