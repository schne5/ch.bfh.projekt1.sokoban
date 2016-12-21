package ch.bfh.projekt1.sokoban;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 *@author:Elisa, Anna
 */
public class Floor extends GameElement {
	private static final long serialVersionUID = 1L;
	private static final String NOT_FOUND_FLOOR = NOT_FOUND + " Floor";
	private static final String IMAGE = "floor_empty.png";
	private static final String IMAGE_DESIGN = "floor.png";

	public Floor() {
	}

	public static Image loadImage(boolean design) {
		File pathToFile;
		if (!design) {
			pathToFile = new File(getImagePath());
		} else {
			pathToFile = new File(getImagePathDesign());
		}
		Image image = null;
		try {
			image = ImageIO.read(pathToFile);
		} catch (IOException e) {
			System.out.println(NOT_FOUND_FLOOR);
		}
		return image;
	}

	public static String getImagePath() {
		return IMAGE_PATH + IMAGE;
	}

	public static String getImagePathDesign() {
		return IMAGE_PATH + IMAGE_DESIGN;
	}
}
