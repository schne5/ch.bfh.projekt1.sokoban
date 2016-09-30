package ch.bfh.projekt1.sokoban;

import java.util.List;

public class Rules {
	private List<GameElement> gameElements;

	private Activity checkCollision(Position position, Direction direction) {
		GameElement element = GameElementUtile.getElementByPosition(position,
				gameElements);
		if (element instanceof Wall) {
			return Activity.COLLISION;
		} else if (element instanceof Box) {
			GameElement next = GameElementUtile.getNextElement(element,
					direction, gameElements);
			if (next instanceof Wall || next instanceof Box) {
				return Activity.COLLISION;
			} else {
				return Activity.PUSH;
			}
		}
		return Activity.MOVE;
	}

	private boolean isInStorage(int posX, int posY) {
		return false;
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
