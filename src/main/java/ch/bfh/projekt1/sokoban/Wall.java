package ch.bfh.projekt1.sokoban;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 *@author:Elisa, Anna
 */
public class Wall extends GameElement {
	private static final long serialVersionUID = 1L;
	private static final String NOT_FOUND_WALL = NOT_FOUND + " WALL";
	private static final String IMAGE = "wall.png";

	public Wall(int posX, int posY) {
		loadImage();
	}

	public Wall(Position position) {
		this(position.getPosX(), position.getPosY());
	}

	public Wall() {
	}

	public static Image loadImage() {
		File pathToFile = new File(getImagePath());
		Image image = null;
		try {
			image = ImageIO.read(pathToFile);
		} catch (IOException e) {
			System.out.println(NOT_FOUND_WALL);
		}
		return image;
	}

	public static String getImagePath() {
		return IMAGE_PATH + IMAGE;
	}

}
