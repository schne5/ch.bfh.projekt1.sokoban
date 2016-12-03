package ch.bfh.projekt1.sokoban;

import java.util.ArrayList;
import java.util.List;

public class Controller {
	private Model model;
	public static char PAWN = '@';
	public static char WALL = '#';
	public static char BOX = '$';
	public static char STORAGE = '.';
	public static char FLOOR = ' ';
	public static char BOX_ON_STORAGE = '*';
	public static char PAWN_ON_STORAGE = '+';

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
		Activity activity = Rules.checkRules(model.getGameArea(), direction,
				model.getPawnPosition(), model.isReverse());
		if (activity != Activity.COLLISION) {
			model.getStackUndo().push(
					new SokobanStackTuple(activity, direction));
			move(direction, activity);
		}
	}

	public void move(Direction direction, Activity activity) {
		Position newPawnposition = GameElementUtile.getNextPosition(direction,
				model.getPawnPosition());
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
					direction, newPawnposition);
			GameElementUtile.changeGameElementTypes(model.getGameArea(),
					model.getPawnPosition(), newPawnposition, positionAfterBox,
					activity);
			break;
		case PULL:
			// true, da das next auf der gegen�berliegenden Siete ben�tigt wird
			// wird nur f�r Undo ben�tigt
			Position boxposition = GameElementUtile.getNextPosition(direction,
					model.getPawnPosition(), true);
			GameElementUtile.changeGameElementTypes(model.getGameArea(),
					model.getPawnPosition(), newPawnposition, boxposition,
					activity);
			break;
		}
		model.setPawnPosition(newPawnposition);
	}

	public GraphTuple[][] initWarehouse(List<String> lines) {
		model.setWidth(lines.get(0).length());
		model.setHeight(lines.size());
		model.initGameElements();
		GraphTuple[][] area = model.getGameArea();
		int x = 0;
		int y = 0;

		for (String line : lines) {
			char[] charsLine = line.toCharArray();

			for (char c : charsLine) {
				if (c == WALL) {
					area[x][y] = new GraphTuple(GameElementType.WALL);
				} else if (c == FLOOR) {
					area[x][y] = new GraphTuple(GameElementType.FLOOR);
				} else if (c == STORAGE) {
					area[x][y] = new GraphTuple(GameElementType.STORAGE);
				} else if (c == BOX) {
					area[x][y] = new GraphTuple(GameElementType.BOX);
				} else if (c == PAWN) {
					area[x][y] = new GraphTuple(GameElementType.PAWN);
					model.setPawnPosition(new Position(x, y));
				} else if (c == PAWN_ON_STORAGE) {
					area[x][y] = new GraphTuple(GameElementType.PAWN_ON_STORAGE);
				} else if (c == BOX_ON_STORAGE) {
					area[x][y] = new GraphTuple(GameElementType.BOX_ON_STORAGE);
				}
				x++;
				if (x >= model.getWidth()) {
					y++;
					x = 0;
				}
			}
		}
		return area;
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

	public static void saveCustomProblem(GraphTuple[][] gameElements) {
		GameSaver.saveCustomProblems(gameElements, null);
	}

	public void saveGame(String name) {
		GraphTuple[][] gameElements = model.getGameArea();
		GameSaver.saveGame(gameElements, name);
	}

	public void saveCustomGame(String name) {
		GraphTuple[][] gameElements = model.getGameArea();
		GameSaver.saveCustomProblems(gameElements, name);
	}

	public GraphTuple[][] loadGame(String fileName) {
		return initWarehouse(GameSaver.loadGame(fileName));
	}

	public GraphTuple[][] loadGame() {
		return initWarehouse(GameSaver.loadGame(model.getFileName()));
	}

	public GraphTuple[][] loadProblem() {
		return initWarehouse(GameSaver.loadProblem(model.getFileName()));
	}

	public GraphTuple[][] loadCustomProblem(String fileName) {
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
					.getValidNeighbours(first, model.getGameArea());
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
}
