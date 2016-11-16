package ch.bfh.projekt1.sokoban;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 *@author:Elisa, Anna
 */
public class Storage extends GameElement {
	private boolean occupied;
	public static final String IMAGE = "storage.png";

	public Storage() {
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public static Image loadImage() {
		File pathToFile = new File(getImagePath());
		Image image = null;
		try {
			image = ImageIO.read(pathToFile);
		} catch (IOException e) {
			System.out.println("Image Storage not found");
		}
		return image;
	}

	public static String getImagePath() {
		return IMAGE_PATH + IMAGE;
	}

}
