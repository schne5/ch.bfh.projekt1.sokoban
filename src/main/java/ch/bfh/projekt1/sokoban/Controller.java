package ch.bfh.projekt1.sokoban;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

/*
 *@author:Elisa, Anna
 */
public class Controller {
	private Model model;
	private static final String BOLCKED = "Diese Kiste wird blockiert sein. Machen Sie den Zug rueckgaengig.";

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Controller(Model model) {
		this.model = model;
	}

	public void move(Direction direction) {
		Activity activity = Rules.checkCollision(model.getGameArea(),
				direction, model.getPawnPosition(), model.isReverse(),
				model.getWidth(), model.getHeight());
		if (activity != Activity.COLLISION) {
			model.getStackUndo().push(
					new SokobanStackTuple(activity, direction));
			move(direction, activity);
		}
	}

	public void move(Direction direction, Activity activity) {
		Position newPawnposition = GameElementUtile.getNextPosition(direction,
				model.getPawnPosition(), model.getWidth(), model.getHeight());
		move(newPawnposition, activity, direction);
	}

	public void move(Position newPawnposition, Activity activity,
			Direction direction) {

		switch (activity) {
		case MOVE:
			GameElementUtile.changeGameElementTypes(model.getGameArea(),
					model.getPawnPosition(), newPawnposition, activity);
			break;
		case PUSH:
			Position positionAfterBox = GameElementUtile.getNextPosition(
					direction, newPawnposition, model.getWidth(),
					model.getHeight());
			GameElementUtile.changeGameElementTypes(model.getGameArea(),
					model.getPawnPosition(), newPawnposition, positionAfterBox,
					activity);

			if (model.isHints()
					&& Rules.isLocked(model.getGameArea(), positionAfterBox,
							direction, model.getWidth(), model.getHeight())) {
				JOptionPane.showMessageDialog(null, BOLCKED);
			}
			break;
		case PULL:
			// true, Position in die andere Richtung wird benoetigt
			Position boxposition = GameElementUtile.getNextPosition(direction,
					model.getPawnPosition(), true, model.getWidth(),
					model.getHeight());
			GameElementUtile.changeGameElementTypes(model.getGameArea(),
					model.getPawnPosition(), newPawnposition, boxposition,
					activity);
			break;
		}
		model.setPawnPosition(newPawnposition);
		updateStatistics(activity);
	}

	public GraphTuple[][] initWarehouse(List<String> lines) {
		model.setWidth(lines.get(0).length());
		model.setHeight(lines.size());
		model.setGameArea(new GraphTuple[model.getWidth()][model.getHeight()]);
		GraphTuple[][] area = model.getGameArea();
		GameAreaGraph gameAreaGraph = new GameAreaGraph();
		gameAreaGraph.createGraph(area, lines, model.getWidth());
		model.setPawnPosition(gameAreaGraph.getPawnPosition());
		return gameAreaGraph.getArea();
	}

	public void undo() {
		SokobanStackTuple tuple = model.getStackUndo().pop();
		if (tuple != null) {
			Direction oppositeDirection = GameElementUtile
					.getOppositeDiredction(tuple.getDirection());
			Activity oppositeActivity = GameElementUtile
					.getOppositeActivity(tuple.getActivity());
			move(oppositeDirection, oppositeActivity);
			model.getStackRedo().push(tuple);
		}
	}

	public void redo() {
		SokobanStackTuple tuple = model.getStackRedo().pop();
		if (tuple != null) {
			move(tuple.getDirection(), tuple.getActivity());
			model.getStackUndo().push(tuple);
		}
	}

	public static void saveCustomProblem(GraphTuple[][] gameElements,
			String userinput, String filename) {
		GameSaver.saveCustomProblems(gameElements, userinput, filename);
	}

	public void saveGame(String name) {
		GraphTuple[][] gameElements = model.getGameArea();
		GameSaver.saveGame(gameElements, name, model.getStackUndo(),
				model.getStackRedo(), model.getMoves(), model.getPushes(),
				model.getLevel());
	}

	public void saveCustomGame(String userinput) {
		GraphTuple[][] gameElements = model.getGameArea();
		GameSaver.saveCustomProblems(gameElements, userinput, null);
	}

	public GraphTuple[][] loadGame(String fileName) {
		List<String> lines = GameSaver.loadStatistic(fileName);
		if (lines.size() >= 5) {
			SokobanStack undo = prepareLoadStack(lines.get(0).substring(
					lines.get(0).indexOf(':') + 1));
			SokobanStack redo = prepareLoadStack(lines.get(1).substring(
					lines.get(1).indexOf(':') + 1));
			int moves = (Integer.parseInt(lines.get(2).substring(
					lines.get(2).indexOf(':') + 1)));
			int pushes = (Integer.parseInt(lines.get(3).substring(
					lines.get(3).indexOf(':') + 1)));
			int level = ((Integer.parseInt(lines.get(4).substring(
					lines.get(4).indexOf(':') + 1))));
			model.initGameElements(undo, redo, moves, pushes, level);
		}
		return initWarehouse(GameSaver.loadGame(fileName));
	}

	public GraphTuple[][] loadGame() {
		model.initGameElements();
		return initWarehouse(GameSaver.loadGame(model.getFileName()));
	}

	public GraphTuple[][] loadProblem() {
		model.initGameElements();
		return initWarehouse(GameSaver.loadProblem(model.getFileName()));
	}

	public GraphTuple[][] loadCustomProblem(String fileName) {
		model.initGameElements();
		return initWarehouse(GameSaver.loadCustomProblems(fileName));
	}

	public ArrayList<Position> getPath(Position target) {
		model.setQueue(new SokobanQueue<Position>());
		resetGameArea();
		ArrayList<Position> path = new ArrayList<Position>();
		boolean found = findPath(target);
		if (found) {
			path = GameElementUtile.getPath(model.getGameArea(), target,
					model.getPawnPosition());
		}
		return path;
	}

	public boolean findPath(Position target) {
		model.getQueue().enqueue(model.getPawnPosition());
		while (!model.getQueue().isEmpty()) {
			Position first = model.getQueue().dequeue();
			ArrayList<Position> neighbours = GameElementUtile
					.getValidNeighbours(first, model.getGameArea(), false,
							model.getWidth(), model.getHeight());
			int x;
			int y;

			for (Position neighbour : neighbours) {
				x = neighbour.getPosX();
				y = neighbour.getPosY();

				if (model.getGameArea()[x][y].getPosition() == null) {
					model.getGameArea()[x][y].setPosition(first);
					if (neighbour.equals(target)) {
						return true;
					}
					model.getQueue().enqueue(neighbour);
				}
			}
		}
		return false;
	}

	private void resetGameArea() {
		for (int x = 0; x < model.getWidth(); x++) {
			for (int y = 0; y < model.getHeight(); y++) {
				model.getGameArea()[x][y].setPosition(null);
			}
		}

	}

	private void updateStatistics(Activity activity) {
		if (activity == Activity.PUSH || activity == Activity.PULL) {
			model.incrementPushes();
		}
		model.incrementMoves();
	}

	private SokobanStack prepareLoadStack(String stackString) {
		SokobanStack stack = new SokobanStack();
		for (char c : stackString.toCharArray()) {
			SokobanStackTuple tuple = new SokobanStackTuple();
			if (Character.isUpperCase(c)) {
				tuple.setActivity(Activity.PUSH);
			} else {
				tuple.setActivity(Activity.MOVE);
			}
			if (Character.toLowerCase(c) == 'l') {
				tuple.setDirection(Direction.LEFT);
			} else if (Character.toLowerCase(c) == 'r') {
				tuple.setDirection(Direction.RIGHT);
			} else if (Character.toLowerCase(c) == 'u') {
				tuple.setDirection(Direction.UP);
			} else if (Character.toLowerCase(c) == 'd') {
				tuple.setDirection(Direction.DOWN);
			}
			stack.push(tuple);
		}
		return stack;
	}
}
