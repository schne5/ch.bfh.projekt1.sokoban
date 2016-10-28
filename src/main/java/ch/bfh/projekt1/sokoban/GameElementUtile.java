package ch.bfh.projekt1.sokoban;

import java.util.List;

/*
 *@author:Elisa, Anna
 */
public class GameElementUtile {

	public static final int WIDTH = 30;

	/*
	 * Gibt das erste gefundene Element zurueck. Wenn Box und Storage selbe
	 * Position belegen, wird der Storage zurückgegeben
	 */
	public static GameElement getElementByPosition(Position position,
			List<GameElement> gameElements) {
		for (GameElement element : gameElements) {
			if (element.getPosition().equals(position)) {
				return element;
			}
		}
		return null;
	}

	public static Box getBoxByPosition(Position position, List<Box> boxes) {
		for (Box box : boxes) {
			if (box.getPosition().equals(position)) {
				return box;
			}
		}
		return null;
	}

	public static Storage getStorageByPosition(Position position,
			List<Storage> storages) {
		for (Storage storage : storages) {
			if (storage.getPosition().equals(position)) {
				return storage;
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

	public static boolean isInStorage(Position position, List<Storage> list) {
		for (Storage s : list) {
			if (s.getPosition().equals(position)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isOccupiedStorage(GameElement element) {
		return (element instanceof Storage && ((Storage) element).isOccupied());
	}

	public static void updateStorages(List<Storage> storages, List<Box> boxes) {
		for (Storage storage : storages) {
			Box box = getBoxByPosition(storage.getPosition(), boxes);
			storage.setOccupied(box != null);
		}
	}

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

	public static GameElementType getGameElementTypeByGameElement(
			GameElement gameElement, List<Storage> storages) {
		if (gameElement instanceof Pawn) {
			if (getStorageByPosition(gameElement.getPosition(), storages) != null) {
				return GameElementType.PAWN_ON_STORAGE;
			} else {
				return GameElementType.PAWN;
			}

		} else if (gameElement instanceof Box) {
			if (getStorageByPosition(gameElement.getPosition(), storages) != null) {
				return GameElementType.BOX_ON_STORAGE;
			} else {
				return GameElementType.BOX;
			}
		} else if (gameElement instanceof Wall) {
			return GameElementType.WALL;
		} else if (gameElement instanceof Storage) {
			return GameElementType.STORAGE;
		} else if (gameElement instanceof Floor) {
			return GameElementType.FLOOR;
		}
		return null;
	}

}
