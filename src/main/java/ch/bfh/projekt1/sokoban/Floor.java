package ch.bfh.projekt1.sokoban;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 *@author:Elisa, Anna
 *Im moment noch nicht verwendet
 */
public class Floor extends GameElement {
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
			System.out.println("Image Floor not found");
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
