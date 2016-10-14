package ch.bfh.projekt1.sokoban;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class GameSaver {

	public static void save(Model model, String name) {

		try {
			FileOutputStream fosb = new FileOutputStream("gameBackups/./"
					+ name + "_saveGame");
			ObjectOutputStream oosb = new ObjectOutputStream(fosb);
			oosb.writeObject(model);
			oosb.close();
			fosb.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static Model load(String name) {
		try {
			FileInputStream fisb = new FileInputStream("gameBackups/./" + name
					+ "_saveGame");
			ObjectInputStream oisb = new ObjectInputStream(fisb);
			Model model = ((Model) oisb.readObject());
			List<GameElement> gameElements = model.getGameElements();
			for (GameElement element : gameElements) {
				element.loadImage();
			}
			oisb.close();
			fisb.close();
			return model;
		} catch (IOException ioe) {
			return null;
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return null;
		}
	}
}
