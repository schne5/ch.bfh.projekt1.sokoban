package ch.bfh.projekt1.sokoban;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GameSaver {

	public static void save(List<Box> boxes, List<Storage> storages,
			List<Wall> walls, Pawn pawn) {

		try {
			FileOutputStream fosb = new FileOutputStream(
					"gameBackups/./testSaveBoxes");
			ObjectOutputStream oosb = new ObjectOutputStream(fosb);
			oosb.writeObject(boxes);
			oosb.close();
			fosb.close();

			FileOutputStream foss = new FileOutputStream(
					"gameBackups/./testSaveStorages");
			ObjectOutputStream ooss = new ObjectOutputStream(foss);
			ooss.writeObject(storages);
			ooss.close();
			foss.close();

			FileOutputStream fosw = new FileOutputStream(
					"gameBackups/./testSaveWalls");
			ObjectOutputStream oosw = new ObjectOutputStream(fosw);
			oosw.writeObject(walls);
			oosw.close();
			fosw.close();

			FileOutputStream fosp = new FileOutputStream(
					"gameBackups/./testSavePawn");
			ObjectOutputStream oosp = new ObjectOutputStream(fosp);
			oosp.writeObject(pawn);
			oosp.close();
			fosp.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public static void load(Model model) {
		try {
			FileInputStream fisb = new FileInputStream(
					"gameBackups/./testSaveBoxes");
			ObjectInputStream oisb = new ObjectInputStream(fisb);
			model.setBoxes((ArrayList<Box>) oisb.readObject());
			oisb.close();
			fisb.close();

			FileInputStream fiss = new FileInputStream(
					"gameBackups/./testSaveStorages");
			ObjectInputStream oiss = new ObjectInputStream(fiss);
			model.setStorages((ArrayList<Storage>) oiss.readObject());
			oiss.close();
			fiss.close();

			FileInputStream fisw = new FileInputStream(
					"gameBackups/./testSaveWalls");
			ObjectInputStream oisw = new ObjectInputStream(fisw);
			model.setWalls((ArrayList<Wall>) oisw.readObject());
			oisw.close();
			fisw.close();

			FileInputStream fisp = new FileInputStream(
					"gameBackups/./testSavePawn");
			ObjectInputStream oisp = new ObjectInputStream(fisp);
			model.setPawn((Pawn) oisp.readObject());
			oisp.close();
			fisp.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
	}
}
