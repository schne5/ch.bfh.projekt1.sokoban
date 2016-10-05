package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 *@author:Elisa, Anna
 */
public class Floor extends GameElement {
	private static final String IMAGE = "floor.png";

	public Floor(int posX, int posY) {
		super(posX, posY);
		File pathToFile = new File(IMAGE_PATH + IMAGE);
		try {
			setImage(ImageIO.read(pathToFile));
		} catch (IOException e) {
			System.out.println("Image Floor not found");
		}
	}

	public void move(Position position) {
		this.setPosition(position);
	}
}
