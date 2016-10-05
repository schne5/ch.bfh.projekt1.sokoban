package ch.bfh.projekt1.sokoban;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 *@author:Elisa, Anna
 */
public class Observer {
	public static final String PROBLEM_PATH = "sokobanProblems/./";
	public static final int DEFAULT_PROBLEM = 1;

	private Rules rules;
	private String fileName;
	private String path;
	private List<GameElement> gameElements;
	private List<Wall> walls;
	private List<Box> boxes;
	private List<Storage> storages;
	private Pawn pawn;
	private int level;

	public Observer() {
		rules = new Rules();
		path = PROBLEM_PATH;
		setLevel(DEFAULT_PROBLEM);
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

			Box box = (Box) GameElementUtile.getBoxByPosition(newPawnposition,
					boxes);
			pawn.move(newPawnposition);
			box.move(direction);
			GameElementUtile.updateStorages(storages, boxes);
			break;
		}
	}

	public List<GameElement> initWarehouse() {
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
			gameElements.addAll(storages);
			gameElements.addAll(boxes);
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

	public boolean checkFinish() {
		return rules.finish(storages);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		if (level < 1 || level > 50) {
			this.level = 1;
		} else {
			this.level = level;
		}
		this.fileName = "problemTest" + level + ".txt";
	}

	public void higherLevel() {
		this.setLevel(this.getLevel() + 1);
	}

	public void lowerLevel() {
		this.setLevel(this.getLevel() - 1);
	}

	public void initGameElements() {
		this.gameElements = new ArrayList<GameElement>();
		walls = new ArrayList<Wall>();
		boxes = new ArrayList<Box>();
		storages = new ArrayList<Storage>();
	}

}
