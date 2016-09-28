package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Box extends GameElement{
	
	public Box() {
		 File pathToFile = new File("./box.png");
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
