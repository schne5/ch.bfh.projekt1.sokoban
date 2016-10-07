package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 *@author:Elisa, Anna
 */
public class Box extends GameElement {
	private static final String IMAGE = "box.png";

	public Box(int posX, int posY) {
		super(posX, posY);
		File pathToFile = new File(IMAGE_PATH + IMAGE);
		try {
			setImage(ImageIO.read(pathToFile));
		} catch (IOException e) {
			System.out.println("Image Box not found");
		}
	}

	public Box(Position position) {
		this(position.getPosX(), position.getPosY());
	}

	public void move(Position position) {
		this.setPosition(position);
	}

	public void move(Direction direction) {
		Position newPosition = GameElementUtile.getNextPosition(getPosition(),
				direction);
		this.setPosition(newPosition);
	}

	public Box copy() {
		return new Box(this.getPosition());
	}
}
