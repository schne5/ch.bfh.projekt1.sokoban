package ch.bfh.projekt1.sokoban;

import java.util.List;

/*
 *@author:Elisa, Anna
 */
public class Rules {
	private List<GameElement> gameElements;

	private Activity checkCollision(Position position, Direction direction) {
		GameElement element = GameElementUtile.getElementByPosition(position,
				gameElements);
		if (element instanceof Wall) {
			return Activity.COLLISION;
		} else if (element instanceof Box
				|| GameElementUtile.isOccupiedStorage(element)) {
			GameElement next = GameElementUtile.getNextElement(element,
					direction, gameElements);
			if (next instanceof Wall || next instanceof Box
					|| GameElementUtile.isOccupiedStorage(next)) {
				return Activity.COLLISION;
			} else {
				return Activity.PUSH;
			}
		} else if (element instanceof Storage) {

		}
		return Activity.MOVE;

	}

	public boolean finish(List<Storage> storages) {
		boolean occupied = true;
		for (Storage storage : storages) {
			occupied &= storage.isOccupied();
		}
		return occupied;
	}

	// When a box is locked in a corner or when it can't be moved anymore
	private boolean isLocked(int posX, int posY) {
		return false;
	}

	private boolean isTimeOver() {
		return false;
	}

	public Activity checkRules(Position position,
			List<GameElement> gameElements, Direction direction) {
		this.gameElements = gameElements;
		return checkCollision(position, direction);

	}

}
