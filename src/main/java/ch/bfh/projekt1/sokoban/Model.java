package ch.bfh.projekt1.sokoban;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Model implements Serializable {
	public static final String PROBLEM_PATH = "sokobanProblems/./";
	public static final int DEFAULT_PROBLEM = 1;
	public static final String PROBLEM_NAME = "problemTest";

	private String fileName;
	private String path;
	private int level;
	private List<GameElement> gameElements;
	private List<Wall> walls;
	private List<Box> boxes;
	private Position backupBoxPosition;
	private Box backupBoxReference;
	private List<Storage> storages;
	private Pawn pawn;
	private Position backupPawnPosition;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<GameElement> getGameElements() {
		return gameElements;
	}

	public void setGameElements(List<GameElement> gameElements) {
		this.gameElements = gameElements;
	}

	public List<Wall> getWalls() {
		return walls;
	}

	public void setWalls(List<Wall> walls) {
		this.walls = walls;
	}

	public List<Box> getBoxes() {
		return boxes;
	}

	public void setBoxes(List<Box> boxes) {
		this.boxes = boxes;
	}

	public List<Storage> getStorages() {
		return storages;
	}

	public void setStorages(List<Storage> storages) {
		this.storages = storages;
	}

	public Pawn getPawn() {
		return pawn;
	}

	public void setPawn(Pawn pawn) {
		this.pawn = pawn;
	}

	public Model() {
		path = PROBLEM_PATH;
		setLevel(DEFAULT_PROBLEM);
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean checkFinish() {
		return Rules.finish(storages);
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
		this.fileName = PROBLEM_NAME + level + ".txt";
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

	public Position getBackupPawnPosition() {
		return backupPawnPosition;
	}

	public void setBackupPawnPosition(Position backupPawnPosition) {
		this.backupPawnPosition = backupPawnPosition;
	}

	public Position getBackupBoxPosition() {
		return backupBoxPosition;
	}

	public void setBackupBoxPosition(Position backupBoxPosition) {
		this.backupBoxPosition = backupBoxPosition;
	}

	public Box getBackupBoxReference() {
		return backupBoxReference;
	}

	public void setBackupBoxReference(Box backupBoxReference) {
		this.backupBoxReference = backupBoxReference;
	}
}
