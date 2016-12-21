package ch.bfh.projekt1.sokoban;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 *@author:Elisa, Anna
 */
public class Pawn extends GameElement {
	private static final long serialVersionUID = 1L;
	private static final String NOT_FOUND_PAWN = NOT_FOUND + " Pawn";
	private static final String NOT_FOUND_PAWN_ON_STORAGE = NOT_FOUND
			+ " Pawn on Storage";
	private static final String IMAGE = "pawn.png";
	private static final String IMAGE_ON_STORAGE = "pawn_on_storage.png";

	public Pawn() {
	}

	public static Image loadImage() {
		File pathToFile = new File(getImagePath());
		Image image = null;
		try {
			image = ImageIO.read(pathToFile);
		} catch (IOException e) {
			System.out.println(NOT_FOUND_PAWN);
		}
		return image;
	}

	public static Image loadImageOnStorage() {
		File pathToFile = new File(getImagePathOnStorage());
		Image image = null;
		try {
			image = ImageIO.read(pathToFile);
		} catch (IOException e) {
			System.out.println(NOT_FOUND_PAWN_ON_STORAGE);
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
