package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Box extends GameElement{
	private static final String IMAGE ="box.png";
	
	public Box(int posX, int posY) {
		super(posX,posY);
		 File pathToFile = new File(IMAGE_PATH +IMAGE);
		  try {
			setImage(ImageIO.read(pathToFile));
		} catch (IOException e) {
			System.out.println("Image Box not found");
		}
	}
	
	public void move(int newPosX, int newPosY){
		setPosX(newPosX);
		setPosY(newPosY);
	}

}
