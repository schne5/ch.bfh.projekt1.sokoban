package ch.bfh.projekt1.sokoban;

import java.io.Serializable;

/*
 *@author:Elisa, Anna
 */

public class Model implements Serializable {
	public static final String PROBLEM_PATH = "sokobanProblems/./";
	public static final int DEFAULT_PROBLEM = 1;
	public static final int MAX_PROBLEM = 4;
	// public static final String PROBLEM_NAME = "problemTest";
	public static final String PROBLEM_NAME = "problem";
	public static final String PROBLEM_NAME_EXTENSION = ".txt";

	private SokobanStack stackUndo;
	private SokobanStack stackRedo;
	private String fileName;
	private String path;
	private int level;
	private Position pawnPosition;
	public static int width;
	public static int height;
	private GraphTuple[][] gameArea;
	private SokobanQueue<GraphTuple> queue;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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
		return Rules.finish(gameArea);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		if (level < 1 || level > MAX_PROBLEM) {
			this.level = 1;
		} else {
			this.level = level;
		}
		this.fileName = PROBLEM_NAME + level + PROBLEM_NAME_EXTENSION;
	}

	public void higherLevel() {
		this.setLevel(this.getLevel() + 1);
	}

	public void lowerLevel() {
		this.setLevel(this.getLevel() - 1);
	}

	public void initGameElements() {
		setPawnPosition(new Position(-1, -1));
		stackRedo = new SokobanStack();
		stackUndo = new SokobanStack();
		gameArea = new GraphTuple[width][height];
		// graph = new GraphTuple[width][height];
	}

	public GraphTuple[][] getGameArea() {
		return gameArea;
	}

	public void setGameArea(GraphTuple[][] gameArea) {
		this.gameArea = gameArea;
	}

	public Position getPawnPosition() {
		return pawnPosition;
	}

	public void setPawnPosition(Position pawnPosition) {
		this.pawnPosition = pawnPosition;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		Model.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		Model.height = height;
	}

	public SokobanStack getStackUndo() {
		return stackUndo;
	}

	public void setStackUndo(SokobanStack stack) {
		this.stackUndo = stack;
	}

	public SokobanStack getStackRedo() {
		return stackRedo;
	}

	public void setStackRedo(SokobanStack stackRedo) {
		this.stackRedo = stackRedo;
	}

}
