package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 *@author:Elisa, Anna
 */
public class GameSaver {
	private static String PATH = "gameBackups/./";
	private static String PATH_PROBLEMS = "sokobanProblems/./";
	private static String PATH_CUSTOM_PROBLEMS = PATH_PROBLEMS
			+ "/customDesigned/./";

	public static void save(Model model, String input) {
		try {
			String systemusername = getSystemUserName();
			FileOutputStream fosb = new FileOutputStream(PATH + systemusername
					+ "_" + getLocalDateTime() + "_" + input);
			ObjectOutputStream oosb = new ObjectOutputStream(fosb);
			oosb.writeObject(model);
			oosb.close();
			fosb.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static Model load(String fileName) {
		try {
			FileInputStream fisb = new FileInputStream(PATH + fileName);
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

	public static String[] loadAllFiles() {
		File folder = new File(PATH);
		File[] listOfFiles = folder.listFiles();
		String[] currentUserFiles = new String[listOfFiles.length];
		int count = 0;
		for (int i = 0; i < listOfFiles.length; i++) {

			if (listOfFiles[i].getName().contains(getSystemUserName())) {
				currentUserFiles[count] = listOfFiles[i].getName();
				count++;
			}
		}
		return currentUserFiles;
	}

	public static void saveAsTextFile(GameElement[][] gameElements) {
		Path file = Paths.get(PATH_CUSTOM_PROBLEMS + "custom.txt");
		List<String> lines = new ArrayList<String>();
		String line;
		for (int i = 0; i < gameElements.length; i++) {
			line = "";
			for (int j = 0; j < gameElements[i].length; j++) {
				if (gameElements[i][j] instanceof Pawn) {
					line += Controller.PAWN;
				} else if (gameElements[i][j] instanceof Box) {
					line += Controller.BOX;
				} else if (gameElements[i][j] instanceof Storage) {
					line += Controller.STORAGE;
				} else if (gameElements[i][j] instanceof Wall) {
					line += Controller.WALL;
				} else if (gameElements[i][j] instanceof Floor) {
					line += Controller.FLOOR;
				}
			}
			lines.add(line);
		}
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
