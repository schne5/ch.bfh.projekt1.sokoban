package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Floor extends GameElement{
	
	public Floor() {
		 File pathToFile = new File("./floor.png");
		  try {
			setImage( ImageIO.read(pathToFile));
		} catch (IOException e) {
			System.out.println("Image Floor not found");
		}
	}

}
