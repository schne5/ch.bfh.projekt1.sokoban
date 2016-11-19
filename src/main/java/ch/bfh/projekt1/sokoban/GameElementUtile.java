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

	public static Position getNextPosition(GameElementType[][] gameArea,
			Direction direction, Position position) {
		Position newPosition = new Position(position.getPosX(),
				position.getPosY());

		switch (direction) {
		case UP:
			newPosition.setPosY(newPosition.getPosY() - 1);
			break;
		case DOWN:
			newPosition.setPosY(newPosition.getPosY() + 1);
			break;
		case LEFT:
			newPosition.setPosX(newPosition.getPosX() - 1);
			break;
		case RIGHT:
			newPosition.setPosX(newPosition.getPosX() + 1);
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

	public static boolean isStorage(GameElementType[][] gameArea,
			Position position) {
		return gameArea[position.getPosX()][position.getPosY()] == GameElementType.STORAGE;
	}

	public static boolean isPawnOnStorage(GameElementType[][] gameArea,
			Position position) {
		return gameArea[position.getPosX()][position.getPosY()] == GameElementType.PAWN_ON_STORAGE;
	}

	public static GameElementType[][] changeGameElementTypes(
			GameElementType[][] gameArea, Position first, Position second) {
		return changeGameElementTypes(gameArea, first, second, null);
	}

	public static GameElementType[][] changeGameElementTypes(
			GameElementType[][] gameArea, Position first, Position second,
			Position third) {
		Activity activity = third == null ? Activity.MOVE : Activity.PUSH;
		int xFirst = first.getPosX();
		int yFirst = first.getPosY();

		int xSecond = second.getPosX();
		int ySecond = second.getPosY();

		gameArea[xFirst][yFirst] = TransitionTable
				.getTransitionByGameElementType(gameArea[xFirst][yFirst],
						activity);

		gameArea[xSecond][ySecond] = TransitionTable
				.getTransitionByGameElementType(gameArea[xSecond][ySecond],
						activity);
		if (third != null) {
			int xThird = third.getPosX();
			int yThird = third.getPosY();
			gameArea[xThird][yThird] = TransitionTable
					.getTransitionByGameElementType(gameArea[xThird][yThird],
							activity);
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
}
