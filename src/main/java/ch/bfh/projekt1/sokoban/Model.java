package ch.bfh.projekt1.sokoban;

import java.io.Serializable;

/*
 *@author:Elisa, Anna
 */

public class Model implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String PROBLEM_PATH = "sokobanProblems/./";
	// public static final int DEFAULT_PROBLEM = 1;
	public static final int MAX_PROBLEM = 14;
	public static final int DEFAULT_PROBLEM = MAX_PROBLEM;
	// public static final String PROBLEM_NAME = "problemTest";
	public static final String PROBLEM_NAME = "problem";
	public static final String PROBLEM_NAME_EXTENSION = ".txt";

	private SokobanStack stackUndo;
	private SokobanStack stackRedo;
	private String fileName;
	private String path;
	private int level;
	private boolean reverse;
	private boolean hints;
	private boolean ownProblem;
	private Position pawnPosition;
	public static int width;
	public static int height;
	private GraphTuple[][] gameArea;
	private SokobanQueue<Position> queue;
	private int moves;
	private int pushes;

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
		if (level < 1 || level >= MAX_PROBLEM) {
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
		this.moves = 0;
		this.pushes = 0;
		this.ownProblem = false;
	}

	public void initGameElements(SokobanStack undo, SokobanStack redo,
			int moves, int pushes, int level, boolean ownProblem) {
		setPawnPosition(new Position(-1, -1));
		stackRedo = redo;
		stackUndo = undo;
		gameArea = new GraphTuple[width][height];
		this.moves = moves;
		this.pushes = pushes;
		this.level = level;
		this.ownProblem = ownProblem;
	}

	public void incrementMoves() {
		this.moves++;
	}

	public void incrementPushes() {
		this.pushes++;
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

	public SokobanQueue<Position> getQueue() {
		return queue;
	}

	public void setQueue(SokobanQueue<Position> queue) {
		this.queue = queue;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

	public boolean isOwnProblem() {
		return ownProblem;
	}

	public void setOwnProblem(boolean ownProblem) {
		this.ownProblem = ownProblem;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int getPushes() {
		return pushes;
	}

	public void setPushes(int pushes) {
		this.pushes = pushes;
	}

	public boolean isHints() {
		return hints;
	}

	public void setHints(boolean hints) {
		this.hints = hints;
	}
}
