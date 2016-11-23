package ch.bfh.projekt1.sokoban;

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
			Position position, boolean opposite) {
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
		if (newPosition.getPosX() <= Model.width && newPosition.getPosX() >= 0) {
			if (newPosition.getPosY() <= Model.height
					&& newPosition.getPosY() >= 0) {
				return newPosition;
			}

		}
		return null;
	}

	public static Position getNextPosition(Direction direction,
			Position position) {
		return getNextPosition(direction, position, false);
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

	// public static GraphTuple[][] convertToGraph(GameElementType[][] gameArea)
	// {
	// GraphTuple[][] graph = new GraphTuple[Model.width][Model.height];
	// for (int y = 0; y < Model.height; y++) {
	// for (int x = 0; x < Model.width; x++) {
	// graph[x][y] = new GraphTuple(gameArea[x][y]);
	// }
	// }
	// return graph;
	// }
}
