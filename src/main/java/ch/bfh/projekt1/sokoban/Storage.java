package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Storage extends GameElement{
	public static final String IMAGE="storage.png";
	
	public Storage(int posX, int posY) {
		super(posX,posY);
		 File pathToFile = new File(IMAGE_PATH+IMAGE);
		  try {
			setImage( ImageIO.read(pathToFile));
		} catch (IOException e) {
			System.out.println("Image Storage not found");
		}
	}

}
