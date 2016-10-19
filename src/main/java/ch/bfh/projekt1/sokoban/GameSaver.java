package ch.bfh.projekt1.sokoban;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 *@author:Elisa, Anna
 */
public class GameSaver {

	public static void save(Model model, String input) {
		try {
			String systemusername = getSystemUserName();
			FileOutputStream fosb = new FileOutputStream("gameBackups/./"
					+ systemusername + "_" + getLocalDateTime() + "_" + input);
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
			String systemusername = getSystemUserName();
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

	public static String getSystemUserName() {
		return System.getProperty("user.name");
	}

	public static String getLocalDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		// String currentDateTime = LocalDateTime.now().toString();
		// DateTimeFormatter formatter =
		// DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return dateFormat.format(date).toString();

	}
}
