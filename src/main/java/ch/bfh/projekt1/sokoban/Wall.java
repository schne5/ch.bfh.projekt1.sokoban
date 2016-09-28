package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Wall extends GameElement {
	
	public Wall() {
		 File pathToFile = new File("./wall.png");
		  try {
			setImage( ImageIO.read(pathToFile));
		} catch (IOException e) {
			System.out.println("Image wall not found");
		}
	}

}
