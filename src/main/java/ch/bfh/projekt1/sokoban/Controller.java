package ch.bfh.projekt1.sokoban;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
			backup();
			model.getPawn().move(newPawnposition);
			break;
		case PUSH:
			Box box = GameElementUtile.getBoxByPosition(newPawnposition,
					model.getBoxes());
			Box oldBox = box.copy();
			model.getPawn().move(newPawnposition);
			box.move(direction);
			backup(oldBox, box);
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
					} else if (c == '�') {
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

	public void backup(Box boxOld, Box boxNew) {
		if (null != boxOld) {
			model.setBackupBoxOld(boxOld);
			model.setBackupBoxNew(boxNew.copy());
			List<Storage> newStorages = new ArrayList<Storage>();
			for (Storage storage : model.getStorages()) {
				newStorages.add(storage.copy());
			}
			model.setBackupStorages(newStorages);
		}
		model.setBackupPawn(model.getPawn().copy());
	}

	public void backup() {
		this.backup(null, null);
	}

	public void undo() {
		if (null != model.getBackupBoxOld()) {
			// Position der neuen Box zwischenspeichern
			Position boxNewPosition = model.getBackupBoxNew().getPosition();
			// Box in Liste wieder zurückschieben
			GameElementUtile.getBoxByPosition(
					model.getBackupBoxOld().getPosition(), model.getBoxes())
					.setPosition(boxNewPosition);
			// Positionen der Backupboxen tauschen
			model.getBackupBoxNew().setPosition(
					model.getBackupBoxOld().getPosition());
			model.getBackupBoxOld().setPosition(boxNewPosition);
			// Storage zurücksetzen
			List<Storage> tmpStorages = model.getBackupStorages();
			model.setBackupStorages(model.getStorages());
			model.setStorages(tmpStorages);
		}
		// Pawn zurücksetzen
		Pawn tmpPawn = model.getBackupPawn();
		model.setBackupPawn(model.getPawn());
		model.setPawn(tmpPawn);
	}

}
