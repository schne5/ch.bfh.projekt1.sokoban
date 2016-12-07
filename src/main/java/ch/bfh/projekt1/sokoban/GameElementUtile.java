package ch.bfh.projekt1.sokoban;

import java.util.ArrayList;

/*
 *@author:Elisa, Anna
 */
public class GameElementUtile {

	public static final int WIDTH = 30;

	public static GameElement getGameElementByType(GameElementType type) {
		switch (type) {
		case PAWN:
			return new Pawn();
		case BOX:
			return new Box();
		case FLOOR:
			return new Floor();
		case WALL:
			return new Wall();
		case STORAGE:
			return new Storage();
		case PAWN_ON_STORAGE:
			return new Storage();
		case BOX_ON_STORAGE:
			return new Storage();
		}
		return null;
	}

	public static Position getNextPosition(Direction direction,
			Position position, boolean opposite, int width, int height) {
		Position newPosition = new Position(position.getPosX(),
				position.getPosY());
		int newPosX;
		int newPosY;

		switch (direction) {
		case UP:
			newPosY = opposite == false ? (newPosition.getPosY() - 1)
					: (newPosition.getPosY() + 1);
			newPosition.setPosY(newPosY);
			break;
		case DOWN:
			newPosY = opposite == false ? (newPosition.getPosY() + 1)
					: (newPosition.getPosY() - 1);
			newPosition.setPosY(newPosY);
			break;
		case LEFT:
			newPosX = opposite == false ? (newPosition.getPosX() - 1)
					: (newPosition.getPosX() + 1);
			newPosition.setPosX(newPosX);
			break;
		case RIGHT:
			newPosX = opposite == false ? (newPosition.getPosX() + 1)
					: (newPosition.getPosX() - 1);
			newPosition.setPosX(newPosX);
			break;
		}
		if (newPosition.getPosX() < width && newPosition.getPosX() >= 0) {
			if (newPosition.getPosY() < height && newPosition.getPosY() >= 0) {
				return newPosition;
			}

		}
		return null;
	}

	public static Position getNextPosition(Direction direction,
			Position position, int width, int height) {
		return getNextPosition(direction, position, false, width, height);
	}

	public static GraphTuple[][] changeGameElementTypes(
			GraphTuple[][] gameArea, Position first, Position second,
			Activity activity) {
		return changeGameElementTypes(gameArea, first, second, null, activity);
	}

	public static GraphTuple[][] changeGameElementTypes(
			GraphTuple[][] gameArea, Position first, Position second,
			Position third, Activity activity) {
		int xFirst = first.getPosX();
		int yFirst = first.getPosY();

		int xSecond = second.getPosX();
		int ySecond = second.getPosY();

		gameArea[xFirst][yFirst]
				.setGameElementType(TransitionTable
						.getTransitionByGameElementType(
								gameArea[xFirst][yFirst].getGameElementType(),
								activity));

		gameArea[xSecond][ySecond].setGameElementType(TransitionTable
				.getTransitionByGameElementType(
						gameArea[xSecond][ySecond].getGameElementType(),
						activity));
		if (third != null) {
			int xThird = third.getPosX();
			int yThird = third.getPosY();
			gameArea[xThird][yThird].setGameElementType(TransitionTable
					.getTransitionByGameElementType(
							gameArea[xThird][yThird].getGameElementType(),
							activity));
		}
		return gameArea;
	}

	public static Direction getOppositeDiredction(Direction direction) {
		switch (direction) {
		case LEFT:
			return Direction.RIGHT;
		case RIGHT:
			return Direction.LEFT;
		case DOWN:
			return Direction.UP;
		case UP:
			return Direction.DOWN;
		}
		return null;
	}

	public static Activity getOppositeActivity(Activity activity) {
		switch (activity) {
		case MOVE:
			return Activity.MOVE;
		case PUSH:
			return Activity.PULL;
		case PULL:
			return Activity.PUSH;
		}
		return null;
	}

	public static ArrayList<Position> getValidNeighbours(Position position,
			GraphTuple[][] gameArea, boolean validWall, int width, int height) {
		ArrayList<Position> positions = new ArrayList<Position>();
		Position up = getNextPosition(Direction.UP, position, width, height);
		Position down = getNextPosition(Direction.DOWN, position, width, height);
		Position left = getNextPosition(Direction.LEFT, position, width, height);
		Position right = getNextPosition(Direction.RIGHT, position, width,
				height);

		if (up != null && canMoveOnField(up, gameArea, validWall)) {
			positions.add(up);
		}
		if (down != null && canMoveOnField(down, gameArea, validWall)) {
			positions.add(down);
		}
		if (left != null && canMoveOnField(left, gameArea, validWall)) {
			positions.add(left);
		}
		if (right != null && canMoveOnField(right, gameArea, validWall)) {
			positions.add(right);
		}

		return positions;
	}

	private static boolean canMoveOnField(Position position,
			GraphTuple[][] gameArea, boolean validWall) {
		int x = position.getPosX();
		int y = position.getPosY();
		if (position != null) {
			GameElementType type = gameArea[x][y].getGameElementType();
			if (validWall) {
				if (type == GameElementType.FLOOR
						|| type == GameElementType.STORAGE
						|| type == GameElementType.BOX
						|| type == GameElementType.BOX_ON_STORAGE) {

					return true;
				}
			} else {
				if (type == GameElementType.FLOOR
						|| type == GameElementType.STORAGE) {
					return true;

				}
			}
		}
		return false;
	}

	public static ArrayList<Position> getPath(GraphTuple[][] gameArea,
			Position start, Position end) {
		ArrayList<Position> positions = new ArrayList<Position>();
		Position current = start;
		while (!current.equals(end)) {
			positions.add(current);
			current = gameArea[current.getPosX()][current.getPosY()]
					.getPosition();
		}
		return positions;
	}

	public static GameElementType getType(GraphTuple[][] gameArea, int x, int y) {
		switch (gameArea[x][y].getGameElementType()) {
		case WALL:
			return GameElementType.WALL;
		case BOX:
			return GameElementType.BOX;
		case STORAGE:
			return GameElementType.STORAGE;
		case PAWN:
			return GameElementType.PAWN;

		case PAWN_ON_STORAGE:
			return GameElementType.PAWN_ON_STORAGE;
		case BOX_ON_STORAGE:
			return GameElementType.BOX_ON_STORAGE;
		case FLOOR:
		default:
			return GameElementType.FLOOR;
		}
	}
}
