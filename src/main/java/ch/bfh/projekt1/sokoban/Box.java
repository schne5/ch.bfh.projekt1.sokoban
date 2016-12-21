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
	private static final String NOT_FOUND_BOX = NOT_FOUND + " Box";
	private static final String NOT_FOUND_BOX_ON_STORAGE = NOT_FOUND
			+ " Box on Storage";
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
			System.out.println(NOT_FOUND_BOX);
		}
		return image;
	}

	public static Image loadImageOnStorage() {
		File pathToFile = new File(getImagePathOnStorage());
		Image image = null;
		try {
			image = ImageIO.read(pathToFile);
		} catch (IOException e) {
			System.out.println(NOT_FOUND_BOX_ON_STORAGE);
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
