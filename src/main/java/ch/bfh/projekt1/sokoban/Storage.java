package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 *@author:Elisa, Anna
 */
public class Storage extends GameElement {
	private boolean occupied;
	public static final String IMAGE = "storage.png";

	public Storage(int posX, int posY) {
		super(posX, posY);
		File pathToFile = new File(IMAGE_PATH + IMAGE);
		try {
			setImage(ImageIO.read(pathToFile));
		} catch (IOException e) {
			System.out.println("Image Storage not found");
		}
	}

	public Storage(Position position) {
		this(position.getPosX(), position.getPosY());
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public Storage copy() {
		return new Storage(this.getPosition());
	}

}
