package ch.bfh.projekt1.sokoban;

import java.util.List;

public class Controller {
	private Model model;
	public static char PAWN = '@';
	public static char WALL = '#';
	public static char BOX = '$';
	public static char STORAGE = '.';
	public static char FLOOR = ' ';
	public static char BOX_ON_STORAGE = '*';
	public static char PAWN_ON_STORAGE = '+';

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Controller(Model model) {
		this.model = model;
	}

	public void move(Direction direction) {
		Position newPawnposition = GameElementUtile.getNextPosition(
				model.getGameArea(), direction, model.getPawnPosition());

		Activity act = Rules.checkRules(model.getGameArea(), direction,
				model.getPawnPosition());
		moveGameElementTypes(model.getGameArea(), newPawnposition, act,
				direction);
	}

	public GameElementType[][] initWarehouse(List<String> lines) {
		model.setWidth(lines.get(0).length());
		model.setHeight(lines.size());
		model.initGameElements();
		GameElementType[][] area = model.getGameArea();
		int x = 0;
		int y = 0;

		for (String line : lines) {
			char[] charsLine = line.toCharArray();

			for (char c : charsLine) {
				if (c == WALL) {
					area[x][y] = GameElementType.WALL;
				} else if (c == FLOOR) {
					area[x][y] = GameElementType.FLOOR;
				} else if (c == STORAGE) {
					area[x][y] = GameElementType.STORAGE;
				} else if (c == BOX) {
					area[x][y] = GameElementType.BOX;
				} else if (c == PAWN) {
					area[x][y] = GameElementType.PAWN;
					model.setPawnPosition(new Position(x, y));
				} else if (c == PAWN_ON_STORAGE) {
					area[x][y] = GameElementType.PAWN_ON_STORAGE;
				} else if (c == BOX_ON_STORAGE) {
					area[x][y] = GameElementType.BOX_ON_STORAGE;
				}
				x++;
				if (x >= model.getWidth()) {
					y++;
					x = 0;
				}
			}
		}
		return area;
	}

	public void backup(Box box) {
		model.setBackupPawnPosition(new Position(model.getPawn().getPosition()
				.getPosX(), model.getPawn().getPosition().getPosY()));

		if (box != null) {
			model.setBackupBoxPosition(new Position(
					box.getPosition().getPosX(), box.getPosition().getPosY()));
			model.setBackupBoxReference(box);
		}
	}

	public void undo() {
		if (model.getBackupPawnPosition() != null) {
			Position tempPosition = model.getPawn().getPosition();
			model.getPawn().setPosition(model.getBackupPawnPosition());
			model.setBackupPawnPosition(tempPosition);
		}
		if (model.getBackupBoxPosition() != null) {
			Position tempPosition = model.getBackupBoxReference().getPosition();
			model.getBackupBoxReference().setPosition(
					model.getBackupBoxPosition());
			model.setBackupBoxPosition(tempPosition);

			GameElementUtile.updateStorages(model.getStorages(),
					model.getBoxes());
		}
	}

	public GameElementType[][] moveGameElementTypes(
			GameElementType[][] gameArea, Position newPawnposition,
			Activity activity, Direction direction) {
		switch (activity) {
		case MOVE:
			GameElementUtile.changeGameElementTypes(model.getGameArea(),
					model.getPawnPosition(), newPawnposition);
			model.setPawnPosition(newPawnposition);
			break;
		case MOVE_ON_STORAGE:
			model.getGameArea()[newPawnposition.getPosX()][newPawnposition
					.getPosY()] = GameElementType.PAWN_ON_STORAGE;
			model.getGameArea()[model.getPawnPosition().getPosX()][model
					.getPawnPosition().getPosY()] = GameElementType.FLOOR;
			model.setPawnPosition(newPawnposition);
			break;
		case MOVE_FROM_STORAGE:
			model.getGameArea()[newPawnposition.getPosX()][newPawnposition
					.getPosY()] = GameElementType.PAWN;
			model.getGameArea()[model.getPawnPosition().getPosX()][model
					.getPawnPosition().getPosY()] = GameElementType.STORAGE;
			model.setPawnPosition(newPawnposition);
			break;
		case PUSH:
			Position positionAfterBox = GameElementUtile.getNextPosition(
					model.getGameArea(), direction, newPawnposition);
			if (GameElementUtile.isStorage(model.getGameArea(),
					positionAfterBox)) {
				model.getGameArea()[positionAfterBox.getPosY()][positionAfterBox
						.getPosX()] = GameElementType.BOX_ON_STORAGE;
				model.getGameArea()[newPawnposition.getPosY()][newPawnposition
						.getPosX()] = GameElementType.PAWN;
				model.getGameArea()[model.getPawnPosition().getPosY()][model
						.getPawnPosition().getPosX()] = GameElementType.FLOOR;
			} else {

				GameElementUtile.changeGameElementTypes(model.getGameArea(),
						positionAfterBox, newPawnposition);

				GameElementUtile.changeGameElementTypes(model.getGameArea(),
						model.getPawnPosition(), newPawnposition);
			}
			model.setPawnPosition(newPawnposition);
			break;
		}

		return gameArea;
	}

	// public void addGameElements() {
	// model.getGameElements().addAll(model.getWalls());
	// model.getGameElements().addAll(model.getStorages());
	// model.getGameElements().addAll(model.getBoxes());
	// model.getGameElements().add(model.getPawn());
	// }

	public static void saveCustomProblem(GameElementType[][] gameElements) {
		GameSaver.saveCustomProblems(gameElements);
	}

	public void saveGame(String name) {
		GameElementType[][] gameElements = model.getGameArea();
		GameSaver.saveGame(gameElements, name);
	}

	public GameElementType[][] loadGame(String fileName) {
		return initWarehouse(GameSaver.loadGame(fileName));
	}

	public GameElementType[][] loadGame() {
		return initWarehouse(GameSaver.loadGame(model.getFileName()));
	}

	public GameElementType[][] loadProblem() {
		return initWarehouse(GameSaver.loadProblem(model.getFileName()));
	}

	public GameElementType[][] loadCustomProblem(String fileName) {
		return initWarehouse(GameSaver.loadCustomProblems(fileName));
	}

}
