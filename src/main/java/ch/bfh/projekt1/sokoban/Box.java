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

	public static String getImagePath() {
		return IMAGE_PATH + IMAGE;
	}
}
