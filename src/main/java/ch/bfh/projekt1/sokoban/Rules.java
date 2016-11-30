package ch.bfh.projekt1.sokoban;

/*
 *@author:Elisa, Anna
 */
public class Rules {

	private static Activity checkCollision(GraphTuple[][] gameArea,
			Direction direction, Position pawnPosition, boolean reverse) {

		Position newPosition = GameElementUtile.getNextPosition(direction,
				pawnPosition);
		if (newPosition != null) {

			GameElementType type = gameArea[newPosition.getPosX()][newPosition
					.getPosY()].getGameElementType();
			switch (type) {
			case BOX:
			case BOX_ON_STORAGE:
				if (reverse) {
					return Activity.COLLISION;
				}
				Position positionAfterBox = GameElementUtile.getNextPosition(
						direction, newPosition);
				if (positionAfterBox == null) {
					return Activity.COLLISION;
				}
				GameElementType typeAfterBox = gameArea[positionAfterBox
						.getPosX()][positionAfterBox.getPosY()]
						.getGameElementType();
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
			case STORAGE:
				if (reverse) {
					Position pos = GameElementUtile.getNextPosition(
							GameElementUtile.getOppositeDiredction(direction),
							pawnPosition);
					GameElementType typeBeforePawn = gameArea[pos.getPosX()][pos
							.getPosY()].getGameElementType();
					if (typeBeforePawn == GameElementType.BOX
							|| typeBeforePawn == GameElementType.BOX_ON_STORAGE) {
						return Activity.PULL;
					}

				}
				return Activity.MOVE;

			default:
				return Activity.COLLISION;
			}

		}
		return Activity.COLLISION;
	}

	public static boolean finish(GraphTuple[][] gameArea) {
		for (int x = 0; x < gameArea.length; x++) {
			for (int y = 0; y < gameArea[x].length; y++) {
				if (gameArea[x][y].getGameElementType() == GameElementType.STORAGE
						|| gameArea[x][y].getGameElementType() == GameElementType.PAWN_ON_STORAGE) {
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

	public static Activity checkRules(GraphTuple[][] gameArea,
			Direction direction, Position pawnPosition, boolean reverse) {
		return checkCollision(gameArea, direction, pawnPosition, reverse);

	}

}
