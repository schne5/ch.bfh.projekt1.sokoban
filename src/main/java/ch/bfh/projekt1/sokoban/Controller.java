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
		Position newPawnposition = GameElementUtile.getNextPosition(model
				.getPawn().getPosition(), direction);

		Activity act = Rules.checkRules(model.getGameElements(),
				newPawnposition, direction);
		switch (act) {
		case MOVE:
			model.getPawn().move(newPawnposition);
			break;
		case PUSH:
			Box box = GameElementUtile.getBoxByPosition(newPawnposition,
					model.getBoxes());
			backup(box);
			model.getPawn().move(newPawnposition);
			box.move(direction);
			GameElementUtile.updateStorages(model.getStorages(),
					model.getBoxes());
			break;
		}
	}

	public List<GameElement> initWarehouse(List<String> lines) {
		int posX = 30;
		int posY = 30;

		for (String line : lines) {
			char[] charsLine = line.toCharArray();

			for (char c : charsLine) {
				if (c == WALL) {
					Wall wall = new Wall(posX, posY);
					model.getWalls().add(wall);
				} else if (c == FLOOR) {
					// Nothing to do this is empty place
				} else if (c == STORAGE) {
					Storage storage = new Storage(posX, posY);
					model.getStorages().add(storage);
				} else if (c == BOX) {
					Box box = new Box(posX, posY);
					model.getBoxes().add(box);
				} else if (c == PAWN) {
					model.setPawn(new Pawn(posX, posY));
				} else if (c == PAWN_ON_STORAGE) {
					Storage storage = new Storage(posX, posY);
					model.getStorages().add(storage);
					model.setPawn(new Pawn(posX, posY));
				} else if (c == BOX_ON_STORAGE) {
					Storage storage = new Storage(posX, posY);
					model.getStorages().add(storage);
					Box box = new Box(posX, posY);
					model.getBoxes().add(box);
				}

				posX += GameElementUtile.WIDTH;
			}
			posX = GameElementUtile.WIDTH;
			posY += GameElementUtile.WIDTH;
		}
		addGameElements();
		return model.getGameElements();
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

	public Model load(String fileName) {
		return GameSaver.load(fileName);
	}

	public void addGameElements() {
		model.getGameElements().addAll(model.getWalls());
		model.getGameElements().addAll(model.getStorages());
		model.getGameElements().addAll(model.getBoxes());
		model.getGameElements().add(model.getPawn());
	}

	public static void saveCustomProblem(GameElementType[][] gameElements) {
		GameSaver.saveCustomProblems(gameElements);
	}

	public void saveGame(String name) {
		GameElementType[][] gameElements = new GameElementType[ProblemDesignArea.HEIGHT][ProblemDesignArea.WIDTH];
		int i;
		int j;
		for (GameElement gameElement : model.getGameElements()) {

			i = (gameElement.getPosition().getPosY() / 30) - 1;
			j = (gameElement.getPosition().getPosX() / 30) - 1;

			gameElements[i][j] = GameElementUtile
					.getGameElementTypeByGameElement(gameElement,
							model.getStorages());
		}
		GameSaver.saveGame(gameElements, name);
	}

	public List<GameElement> loadGame(String fileName) {
		return initWarehouse(GameSaver.loadGame(fileName));
	}

	public List<GameElement> loadGame() {
		return initWarehouse(GameSaver.loadGame(model.getFileName()));
	}

	public List<GameElement> loadProblem() {
		return initWarehouse(GameSaver.loadProblem(model.getFileName()));
	}

	public List<GameElement> loadCustomProblem(String fileName) {
		return initWarehouse(GameSaver.loadCustomProblems(fileName));
	}
}
