package ch.bfh.projekt1.sokoban;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

/*
 *@author:Elisa, Anna
 */
public class Box extends GameElement implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String IMAGE = "box.png";
	private static final String IMAGE_ON_STORAGE = "box_on_storage.png";

	public Box() {

	}

	public static Image loadImage() {
		File pathToFile = new File(getImagePath());
		Image image = null;
		try {
			image = ImageIO.read(pathToFile);
		} catch (IOException e) {
			System.out.println("Image Box not found");
		}
		return image;
	}

	public static Image loadImageOnStorage() {
		File pathToFile = new File(getImagePathOnStorage());
		Image image = null;
		try {
			image = ImageIO.read(pathToFile);
		} catch (IOException e) {
			System.out.println("Image Box on Storage not found");
		}
		return image;
	}

	public static String getImagePath() {
		return IMAGE_PATH + IMAGE;
	}

	public static String getImagePathOnStorage() {
		return IMAGE_PATH + IMAGE_ON_STORAGE;
	}
}
