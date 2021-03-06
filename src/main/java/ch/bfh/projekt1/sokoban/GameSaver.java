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
	public static String PATH_GAME_SAVE_STATISTIKEN = "gameBackupsStatistiken/./";
	public static String PATH_PROBLEMS = "sokobanProblems/./";

	public static String PATH_CUSTOM_PROBLEMS = PATH_PROBLEMS
			+ "/customDesigned/./";
	private static String seperator = ":";

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
					line += GameAreaGraph.PAWN;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.BOX) {
					line += GameAreaGraph.BOX;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.STORAGE) {
					line += GameAreaGraph.STORAGE;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.WALL) {
					line += GameAreaGraph.WALL;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.BOX_ON_STORAGE) {
					line += GameAreaGraph.BOX_ON_STORAGE;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.PAWN_ON_STORAGE) {
					line += GameAreaGraph.PAWN_ON_STORAGE;
				} else if (gameElements[j][i].getGameElementType() == GameElementType.FLOOR
						|| gameElements[j][i] == null) {
					line += GameAreaGraph.FLOOR;
				}
			}
			lines.add(line);
		}
		return lines;

	}

	public static void saveCustomProblems(GraphTuple[][] gameElements,
			String path) {
		Path file = Paths.get(path);
		try {
			Files.write(file, saveAsTextFile(gameElements),
					Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveGame(GraphTuple[][] gameElements, String path,
			SokobanStack undo, SokobanStack redo, int moves, int pushes,
			int level, boolean ownProblem) {
		Path file = Paths.get(path);
		try {
			Files.write(file, saveAsTextFile(gameElements),
					Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		saveStatistic(undo, redo, moves, pushes, path, level, ownProblem);
	}

	private static void saveStatistic(SokobanStack undo, SokobanStack redo,
			int moves, int pushes, String path, int level, boolean ownProblem) {
		String filename = path.substring(path.lastIndexOf('\\'));
		Path file = Paths.get(PATH_GAME_SAVE_STATISTIKEN + filename);
		List<String> list = new ArrayList<String>();
		list.add("UNDO" + seperator + prepareSaveStack(undo));
		list.add("REDO" + seperator + prepareSaveStack(redo));
		list.add("MOVES" + seperator + moves);
		list.add("PUSHES" + seperator + pushes);
		list.add("LEVEL" + seperator + level);
		list.add("OWN" + seperator + ownProblem);
		try {
			Files.write(file, list, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String prepareSaveStack(SokobanStack stack) {
		String stackString = "";
		String direction = "";
		for (SokobanStackTuple tuple : stack.getStack()) {

			switch (tuple.getDirection()) {
			case UP:
				direction = "u";
				break;
			case DOWN:
				direction = "d";
				break;
			case LEFT:
				direction = "l";
				break;
			case RIGHT:
				direction = "r";
				break;
			}
			if (tuple.getActivity() != Activity.MOVE) {
				direction = direction.toUpperCase();
			}
			stackString += direction;
		}
		return stackString;
	}

	public static List<String> loadCustomProblems(String path) {
		return loadTextFile(path);
	}

	public static List<String> loadGame(String fileName) {
		return loadTextFile(fileName);
	}

	public static List<String> loadProblem(String fileName) {
		return loadTextFile(PATH_PROBLEMS + fileName);
	}

	public static List<String> loadStatistic(String path) {
		String filename = path.substring(path.lastIndexOf('\\'));
		return loadTextFile(PATH_GAME_SAVE_STATISTIKEN + filename);
	}

	private static List<String> loadTextFile(String path) {
		List<String> lines = new ArrayList<String>();
		try {
			lines = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;

	}

}
