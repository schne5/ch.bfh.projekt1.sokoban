package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;
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
	public static String PATH_GAME_SAVE = "gameBackups/./";
	public static String PATH_PROBLEMS = "sokobanProblems/./";
	public static String PATH_CUSTOM_PROBLEMS = PATH_PROBLEMS
			+ "/customDesigned/./";

	public static String getSystemUserName() {
		return System.getProperty("user.name");
	}

	public static String getLocalDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date).toString();
	}

	public static String[] loadAllFiles(String path) {
		File folder = new File(path);
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

	public static List<String> saveAsTextFile(GraphTuple[][] gameElements) {

		List<String> lines = new ArrayList<String>();
		String line;
		for (int i = 0; i < gameElements[0].length; i++) {
			line = "";
			for (int j = 0; j < gameElements.length; j++) {
				if (gameElements[j][i].getGameElementType() == GameElementType.PAWN) {
					line += Controller.PAWN;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.BOX) {
					line += Controller.BOX;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.STORAGE) {
					line += Controller.STORAGE;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.WALL) {
					line += Controller.WALL;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.BOX_ON_STORAGE) {
					line += Controller.BOX_ON_STORAGE;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.PAWN_ON_STORAGE) {
					line += Controller.PAWN_ON_STORAGE;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.FLOOR
						|| gameElements[j][i] == null) {
					line += Controller.FLOOR;
				}
			}
			lines.add(line);
		}
		return lines;

	}

	public static void saveCustomProblems(GraphTuple[][] gameElements) {
		Path file = Paths.get(PATH_CUSTOM_PROBLEMS + getSystemUserName()
				+ getLocalDateTime() + "_custom.txt");
		try {
			Files.write(file, saveAsTextFile(gameElements),
					Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveGame(GraphTuple[][] gameElements, String userInput) {
		String systemusername = getSystemUserName();
		Path file = Paths.get(PATH_GAME_SAVE + systemusername + "_"
				+ getLocalDateTime() + "_" + userInput);
		try {
			Files.write(file, saveAsTextFile(gameElements),
					Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> loadCustomProblems(String fileName) {
		return loadTextFile(PATH_CUSTOM_PROBLEMS, fileName);
	}

	public static List<String> loadGame(String fileName) {
		return loadTextFile(PATH_GAME_SAVE, fileName);
	}

	public static List<String> loadProblem(String fileName) {
		return loadTextFile(PATH_PROBLEMS, fileName);
	}

	private static List<String> loadTextFile(String path, String fileName) {
		List<String> lines = new ArrayList<String>();
		try {
			lines = Files.readAllLines(Paths.get(path + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;

	}
}
