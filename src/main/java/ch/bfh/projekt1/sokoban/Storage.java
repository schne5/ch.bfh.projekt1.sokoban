package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Storage extends GameElement{
	
	public Storage() {
		 File pathToFile = new File("./storage.png");
		  try {
			setImage( ImageIO.read(pathToFile));
		} catch (IOException e) {
			System.out.println("Image Storage not found");
		}
	}

}
