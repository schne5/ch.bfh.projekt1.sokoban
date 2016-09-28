package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pawn extends GameElement {
	
	public Pawn() {
		 File pathToFile = new File("./pawn.png");
		  try {
			setImage( ImageIO.read(pathToFile));
		} catch (IOException e) {
			System.out.println("Image Pawn not found");
		}
	}

	public void move(int newPosX, int newPosY){
		setPosX(newPosX);
		setPosY(newPosY);
	}
	
	public void moveToBox(int targetX, int targetY){
		
	}
	
}
