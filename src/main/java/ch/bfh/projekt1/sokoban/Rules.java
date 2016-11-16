package ch.bfh.projekt1.sokoban;

/*
 *@author:Elisa, Anna
 */
public class Rules {

	// private static Activity checkCollision(List<GameElement> gameElements,
	// Position position, Direction direction) {
	// GameElement element = GameElementUtile.getElementByPosition(position,
	// gameElements);
	// if (element instanceof Wall) {
	// return Activity.COLLISION;
	// } else if (element instanceof Box
	// || GameElementUtile.isOccupiedStorage(element)) {
	// GameElement next = GameElementUtile.getNextElement(element,
	// direction, gameElements);
	// if (next instanceof Wall || next instanceof Box
	// || GameElementUtile.isOccupiedStorage(next)) {
	// return Activity.COLLISION;
	// } else {
	// return Activity.PUSH;
	// }
	// } else if (element instanceof Storage) {
	//
	// }
	// return Activity.MOVE;
	//
	// }

	private static Activity checkCollision(GameElementType[][] gameArea,
			Direction direction, Position pawnPosition) {
		Position newPosition = GameElementUtile.getNextPosition(gameArea,
				direction, pawnPosition);
		if (newPosition != null) {

			GameElementType type = gameArea[newPosition.getPosY()][newPosition
					.getPosX()];
			switch (type) {
			case BOX:
				Position positionAfterBox = GameElementUtile.getNextPosition(
						gameArea, direction, newPosition);
				if (positionAfterBox == null) {
					return Activity.COLLISION;
				}
				GameElementType typeAfterBox = gameArea[positionAfterBox
						.getPosY()][positionAfterBox.getPosX()];
				switch (typeAfterBox) {
				case FLOOR:
				case STORAGE:
					return Activity.PUSH;
				case WALL:
				case BOX:
				case BOX_ON_STORAGE:
					return Activity.COLLISION;
				}
			case FLOOR:
				return Activity.MOVE;
			case STORAGE:
				return Activity.MOVE_ON_STORAGE;
			}
		}
		return Activity.COLLISION;
	}

	public static boolean finish(GameElementType[][] gameArea) {
		for (int i = 0; i < gameArea.length; i++) {
			for (int j = 0; j < gameArea[i].length; j++) {
				if (gameArea[i][j] == GameElementType.STORAGE) {
					return false;
				}
			}
		}
		return true;
	}

	// When a box is locked in a corner or when it can't be moved anymore
	private static boolean isLocked(int posX, int posY) {
		return false;
	}

	private static boolean isTimeOver() {
		return false;
	}

	// public static Activity checkRules(List<GameElement> gameElements,
	// Position position, Direction direction) {
	// return checkCollision(gameElements, position, direction);
	//
	// }

	public static Activity checkRules(GameElementType[][] gameArea,
			Direction direction, Position pawnPosition) {
		return checkCollision(gameArea, direction, pawnPosition);

	}

}
