package ch.bfh.projekt1.sokoban;

/*
 *@author:Elisa, Anna
 */
public class Rules {

	public static Activity checkCollision(GraphTuple[][] gameArea,
			Direction direction, Position pawnPosition, boolean reverse,
			int width, int height) {

		Position newPosition = GameElementUtile.getNextPosition(direction,
				pawnPosition, width, height);
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
						direction, newPosition, width, height);
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
							pawnPosition, width, height);
					GameElementType typeBeforePawn = gameArea[pos.getPosX()][pos
							.getPosY()].getGameElementType();
					if (GameElementUtile.isBox(typeBeforePawn)) {
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

	// When a box is locked and can't be moved anymore
	public static boolean isLocked(GraphTuple[][] gameArea, Position position,
			Direction direction, int width, int height) {
		if (gameArea[position.getPosX()][position.getPosY()]
				.getGameElementType() != GameElementType.BOX_ON_STORAGE) {
			return isInCorner(gameArea, position, width, height)
					|| isInSink(gameArea, position, width, height, direction)
					|| isInCornerWithBoxes(gameArea, position, direction,
							width, height);
		}
		return false;
	}

	private static boolean isInCorner(GraphTuple[][] gameArea,
			Position position, int width, int height) {
		boolean isLocked = false;
		Position upPosition = GameElementUtile.getNextPosition(Direction.UP,
				position, width, height);
		Position downPosition = GameElementUtile.getNextPosition(
				Direction.DOWN, position, width, height);
		Position leftPosition = GameElementUtile.getNextPosition(
				Direction.LEFT, position, width, height);
		Position rightPosition = GameElementUtile.getNextPosition(
				Direction.RIGHT, position, width, height);
		GameElementType upType = GameElementUtile.getTypeByPosition(gameArea,
				upPosition);
		GameElementType downType = GameElementUtile.getTypeByPosition(gameArea,
				downPosition);
		GameElementType leftType = GameElementUtile.getTypeByPosition(gameArea,
				leftPosition);
		GameElementType rightType = GameElementUtile.getTypeByPosition(
				gameArea, rightPosition);

		isLocked |= ((upType == GameElementType.WALL || downType == GameElementType.WALL) && (rightType == GameElementType.WALL || leftType == GameElementType.WALL));
		isLocked |= isInCornerWithBox(gameArea, upType, Direction.UP, leftType,
				rightType, upPosition, width, height);
		isLocked |= isInCornerWithBox(gameArea, downType, Direction.DOWN,
				leftType, rightType, downPosition, width, height);
		isLocked |= isInCornerWithBox(gameArea, leftType, Direction.LEFT,
				upType, downType, leftPosition, width, height);
		isLocked |= isInCornerWithBox(gameArea, rightType, Direction.RIGHT,
				upType, downType, rightPosition, width, height);

		return isLocked;
	}

	private static boolean isInCornerWithBoxes(GraphTuple[][] gameArea,
			Position position, Direction direction, int width, int height) {
		Position frontPosition;
		Position sideOnePosition;
		Position sideTwoPosition;
		frontPosition = GameElementUtile.getNextPosition(direction, position,
				width, height);
		if (Direction.DOWN == direction || Direction.UP == direction) {
			sideOnePosition = GameElementUtile.getNextPosition(Direction.LEFT,
					position, width, height);
			sideTwoPosition = GameElementUtile.getNextPosition(Direction.RIGHT,
					position, width, height);
		} else {
			sideOnePosition = GameElementUtile.getNextPosition(Direction.UP,
					position, width, height);
			sideTwoPosition = GameElementUtile.getNextPosition(Direction.DOWN,
					position, width, height);
		}
		GameElementType frontType = gameArea[frontPosition.getPosX()][frontPosition
				.getPosY()].getGameElementType();
		GameElementType sideOneType = gameArea[sideOnePosition.getPosX()][sideOnePosition
				.getPosY()].getGameElementType();
		GameElementType sideTwoType = gameArea[sideTwoPosition.getPosX()][sideTwoPosition
				.getPosY()].getGameElementType();
		return isInCornerWithBoxes(gameArea, frontType, sideOneType,
				sideTwoType, frontPosition, sideOnePosition, sideTwoPosition);

	}

	private static boolean isInCornerWithBoxes(GraphTuple[][] gameArea,
			GameElementType frontType, GameElementType sideOneType,
			GameElementType sideTwoType, Position frontPosition,
			Position sideOnePosition, Position sideTwoPosition) {
		boolean isLocked = false;
		if (GameElementUtile.isBox(frontType)) {
			if (GameElementUtile.isBox(sideOneType)) {
				isLocked |= GameElementUtile.isBox(gameArea[sideOnePosition
						.getPosX()][frontPosition.getPosY()]
						.getGameElementType());
			} else if (GameElementUtile.isBox(sideTwoType)) {
				isLocked |= GameElementUtile.isBox(gameArea[sideTwoPosition
						.getPosX()][frontPosition.getPosY()]
						.getGameElementType());
			}
		}
		return isLocked;
	}

	private static boolean isInCornerWithBox(GraphTuple[][] gameArea,
			GameElementType typeOrig, Direction direction,
			GameElementType typeFirst, GameElementType typeSecond,
			Position position, int width, int height) {
		if ((GameElementUtile.isBox(typeOrig))
				&& (typeFirst == GameElementType.WALL || typeSecond == GameElementType.WALL)) {
			if (direction == Direction.DOWN || direction == Direction.UP) {
				return areBothBoxesLocked(gameArea, position, Direction.LEFT,
						width, height);
			} else if (direction == Direction.LEFT
					|| direction == Direction.RIGHT) {
				return areBothBoxesLocked(gameArea, position, Direction.UP,
						width, height);
			}

		}
		return false;
	}

	private static boolean areBothBoxesLocked(GraphTuple[][] gameArea,
			Position position, Direction direction, int width, int height) {
		Direction oppositeDirection = GameElementUtile
				.getOppositeDiredction(direction);
		Position firstPositionOther = GameElementUtile.getNextPosition(
				direction, position, width, height);
		Position secondPositionOther = GameElementUtile.getNextPosition(
				oppositeDirection, position, width, height);
		GameElementType firstTypeOther = GameElementUtile.getTypeByPosition(
				gameArea, firstPositionOther);
		GameElementType secondTypeOther = GameElementUtile.getTypeByPosition(
				gameArea, secondPositionOther);
		if (firstTypeOther == GameElementType.WALL
				|| secondTypeOther == GameElementType.WALL) {
			return true;
		}
		return false;
	}

	private static boolean isInSink(GraphTuple[][] gameArea, Position position,
			int width, int height, Direction direction) {
		Position nextPosition = GameElementUtile.getNextPosition(direction,
				position, width, height);
		GameElementType nextType = GameElementUtile.getType(gameArea,
				nextPosition);
		boolean isInSink = true;
		if (nextType == GameElementType.WALL) {
			switch (direction) {
			case DOWN:
			case UP:
				isInSink &= isInSinkLoop(gameArea, position, width, height,
						Direction.LEFT, direction);
				isInSink &= isInSinkLoop(gameArea, position, width, height,
						Direction.RIGHT, direction);
				break;
			case LEFT:
			case RIGHT:
				isInSink &= isInSinkLoop(gameArea, position, width, height,
						Direction.UP, direction);
				isInSink &= isInSinkLoop(gameArea, position, width, height,
						Direction.DOWN, direction);
				break;
			}
			return isInSink;
		}
		return false;
	}

	private static boolean isInSinkLoop(GraphTuple[][] gameArea,
			Position position, int width, int height, Direction direction,
			Direction pushDirection) {
		Position positionToCheck = GameElementUtile.getNextPosition(direction,
				position, width, height);
		GameElementType typeToCheck = GameElementUtile.getType(gameArea,
				positionToCheck);
		while (typeToCheck != GameElementType.WALL) {
			if (typeToCheck != GameElementType.STORAGE) {
				positionToCheck = GameElementUtile.getNextPosition(direction,
						positionToCheck, width, height);
				typeToCheck = GameElementUtile.getType(gameArea,
						positionToCheck);
				GameElementType downTypeToCheck = GameElementUtile.getType(
						gameArea, GameElementUtile.getNextPosition(
								pushDirection, positionToCheck, width, height));
				if (downTypeToCheck != GameElementType.WALL) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
}
