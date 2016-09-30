package ch.bfh.projekt1.sokoban;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Observer {
	public static final String PROBLEM_PATH = "sokobanProblems/./";
	public static final String DEFAULT_PROBLEM = "problem1.txt";

	private Rules rules;
	private String fileName;
	private String path;
	private List<GameElement> gameElements;
	private List<Wall> walls;
	private List<Box> boxes;
	private List<Storage> storages;
	private Pawn pawn;

	public Observer() {
		rules = new Rules();
		gameElements = new ArrayList<GameElement>();
		walls = new ArrayList<Wall>();
		boxes = new ArrayList<Box>();
		storages = new ArrayList<Storage>();
		path = PROBLEM_PATH;
		fileName = DEFAULT_PROBLEM;
	}

	public void move(Direction direction) {
		Position newPawnposition = GameElementUtile.getNextPosition(
				pawn.getPosition(), direction);

		Activity act = rules.checkRules(newPawnposition, gameElements,
				direction);
		switch (act) {
		case MOVE:
			pawn.move(newPawnposition);
			break;
		case PUSH:
			Box box = (Box) GameElementUtile.getNextElement(newPawnposition,
					direction, gameElements);

			pawn.move(newPawnposition);
			box.move(direction);

			break;
		}
	}

	public List<GameElement> initWarehouse(File file) {
		List<String> lines;
		int posX = 30;
		int posY = 30;
		try {
			lines = Files.readAllLines(Paths.get(path + fileName));

			for (String line : lines) {
				char[] charsLine = line.toCharArray();

				for (char c : charsLine) {
					if (c == '#') {
						Wall wall = new Wall(posX, posY);
						walls.add(wall);
					} else if (c == ' ') {
						// Nothing to do this is empty place

					} else if (c == '.') {
						Storage storage = new Storage(posX, posY);
						storages.add(storage);
					} else if (c == '§') {
						Box box = new Box(posX, posY);
						boxes.add(box);
					} else if (c == '@') {
						pawn = new Pawn(posX, posY);
					}
					posX += GameElementUtile.WIDTH;
				}
				posX = GameElementUtile.WIDTH;
				posY += GameElementUtile.WIDTH;
			}
			gameElements.addAll(walls);
			gameElements.addAll(boxes);
			gameElements.addAll(storages);
			gameElements.add(pawn);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return gameElements;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
