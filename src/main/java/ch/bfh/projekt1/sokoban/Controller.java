package ch.bfh.projekt1.sokoban;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Controller {
	Model model;

	public Controller(Model model) {
		this.model = model;
	}

	public void move(Direction direction) {
		Position newPawnposition = GameElementUtile.getNextPosition(model
				.getPawn().getPosition(), direction);

		Activity act = model.getRules().checkRules(newPawnposition,
				model.getGameElements(), direction);
		switch (act) {
		case MOVE:
			model.getPawn().move(newPawnposition);
			break;
		case PUSH:

			Box box = (Box) GameElementUtile.getBoxByPosition(newPawnposition,
					model.getBoxes());
			model.getPawn().move(newPawnposition);
			box.move(direction);
			GameElementUtile.updateStorages(model.getStorages(),
					model.getBoxes());
			break;
		}
	}

	public List<GameElement> initWarehouse() {
		List<String> lines;
		int posX = 30;
		int posY = 30;
		try {
			lines = Files.readAllLines(Paths.get(model.getPath()
					+ model.getFileName()));

			for (String line : lines) {
				char[] charsLine = line.toCharArray();

				for (char c : charsLine) {
					if (c == '#') {
						Wall wall = new Wall(posX, posY);
						model.getWalls().add(wall);
					} else if (c == ' ') {
						// Nothing to do this is empty place

					} else if (c == '.') {
						Storage storage = new Storage(posX, posY);
						model.getStorages().add(storage);
					} else if (c == '§') {
						Box box = new Box(posX, posY);
						model.getBoxes().add(box);
					} else if (c == '@') {
						model.setPawn(new Pawn(posX, posY));
					}
					posX += GameElementUtile.WIDTH;
				}
				posX = GameElementUtile.WIDTH;
				posY += GameElementUtile.WIDTH;
			}
			model.getGameElements().addAll(model.getWalls());
			model.getGameElements().addAll(model.getStorages());
			model.getGameElements().addAll(model.getBoxes());
			model.getGameElements().add(model.getPawn());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return model.getGameElements();
	}

}
