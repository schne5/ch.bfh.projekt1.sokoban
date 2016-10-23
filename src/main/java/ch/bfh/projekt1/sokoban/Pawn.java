package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 *@author:Elisa, Anna
 */
public class Pawn extends GameElement {
	private static final String IMAGE = "pawn.png";

	public Pawn(int posX, int posY) {
		super(posX, posY);
		loadImage();
	}

	public Pawn(Position position) {
		this(position.getPosX(), position.getPosY());
	}

	public Pawn() {
	}

	public void move(Position position) {
		this.setPosition(position);
	}

	public void moveToBox(Position position) {

	}

	public Pawn copy() {
		return new Pawn(this.getPosition());
	}

	public void loadImage() {
		File pathToFile = new File(IMAGE_PATH + IMAGE);
		try {
			setImage(ImageIO.read(pathToFile));
		} catch (IOException e) {
			System.out.println("Image Pawn not found");
		}
	}

	public String getImagePath() {
		return IMAGE_PATH + IMAGE;
	}

}
