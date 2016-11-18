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

			GameElementType type = gameArea[newPosition.getPosX()][newPosition
					.getPosY()];
			switch (type) {
			case BOX:
			case BOX_ON_STORAGE:
				Position positionAfterBox = GameElementUtile.getNextPosition(
						gameArea, direction, newPosition);
				if (positionAfterBox == null) {
					return Activity.COLLISION;
				}
				GameElementType typeAfterBox = gameArea[positionAfterBox
						.getPosX()][positionAfterBox.getPosY()];
				switch (typeAfterBox) {
				case FLOOR:
					if (type == GameElementType.BOX_ON_STORAGE) {
						return Activity.PUSH_FROM_STORAGE;
					} else {
						return Activity.PUSH;
					}
				case STORAGE:
					if (type == GameElementType.BOX_ON_STORAGE) {
						return Activity.PUSH;
					} else {
						return Activity.PUSH_ON_STORAGE;
					}
				case WALL:
				case BOX:
				case BOX_ON_STORAGE:
					return Activity.COLLISION;
				}
			case FLOOR:
				if (gameArea[pawnPosition.getPosX()][pawnPosition.getPosY()] == GameElementType.PAWN_ON_STORAGE) {
					return Activity.MOVE_FROM_STORAGE;
				} else {
					return Activity.MOVE;
				}
			case STORAGE:
				if (gameArea[pawnPosition.getPosX()][pawnPosition.getPosY()] == GameElementType.PAWN_ON_STORAGE) {
					return Activity.MOVE;
				} else {
					return Activity.MOVE_ON_STORAGE;
				}
			}
		}
		return Activity.COLLISION;
	}

	public static boolean finish(GameElementType[][] gameArea) {
		for (int x = 0; x < gameArea.length; x++) {
			for (int y = 0; y < gameArea[x].length; y++) {
				if (gameArea[x][y] == GameElementType.STORAGE) {
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
