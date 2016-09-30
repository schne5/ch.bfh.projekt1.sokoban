package ch.bfh.projekt1.sokoban;

import java.util.List;

public class GameElementUtile {

	public static final int WIDTH = 30;

	public static GameElement getElementByPosition(Position position,
			List<GameElement> gameElements) {
		for (GameElement element : gameElements) {
			if (element.getPosition().equals(position)) {
				return element;
			}
		}
		return null;
	}

	public static GameElement getNextElement(GameElement element,
			Direction direction, List<GameElement> gameElements) {

		return getElementByPosition(
				getNextPosition(element.getPosition(), direction), gameElements);
	}

	public static GameElement getNextElement(Position position,
			Direction direction, List<GameElement> gameElements) {
		return getNextElement(getElementByPosition(position, gameElements),
				direction, gameElements);

	}

	public static void changeElementpositions(GameElement element1,
			GameElement element2) {
		Position tempPosition = element1.getPosition();
		element1.setPosition(element2.getPosition());
		element2.setPosition(tempPosition);
	}

	public static Position getNextPosition(Position position,
			Direction direction) {

		int newPosX = position.getPosX();
		int newPosY = position.getPosY();

		switch (direction) {
		case LEFT:
			newPosX -= WIDTH;
			break;
		case RIGHT:
			newPosX += WIDTH;
			break;
		case UP:
			newPosY -= WIDTH;
			break;
		case DOWN:
			newPosY += WIDTH;
			break;
		}
		return new Position(newPosX, newPosY);
	}

}
