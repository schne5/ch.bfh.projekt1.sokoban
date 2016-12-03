package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PlaySokobanFrame extends JFrame {

	public static String UNDO = "Undo";
	public static String REDO = "Redo";
	public static String SAVE = "Save";
	public static String LOAD = "Load";
	public static String LOAD_OWN_PROBLEM = "Load Own";

	JPanel buttonPanel;
	JButton undo;
	JButton redo;
	JButton save;
	JButton load;
	JButton loadOwnProblem;
	private Warehouse warehouse;

	public PlaySokobanFrame(Warehouse warehouse) {
		this.warehouse = warehouse;
		setFocusOnWarehouse();
		buttonPanel = new JPanel();
		redo = new JButton();
		redo.setText(REDO);
		redo.addActionListener(t -> {
			this.warehouse.getController().redo();
			this.warehouse.redraw();
			setFocusOnWarehouse();
		});

		undo = new JButton();
		undo.setText(UNDO);
		undo.addActionListener(t -> {
			this.warehouse.getController().undo();
			this.warehouse.redraw();
			setFocusOnWarehouse();
		});

		save = new JButton();
		save.setText(SAVE);
		save.addActionListener(t -> {
			String name = getUserInput();
			if (!"".equals(name) && null != name) {
				this.warehouse.getController().saveGame(name);
				setFocusOnWarehouse();
			}
		});

		load = new JButton();
		load.setText(LOAD);
		load.addActionListener(t -> {
			openFileSelectionFrame(GameSaver.PATH_GAME_SAVE, false);
			setFocusOnWarehouse();
			warehouse.getModel().setOwnProblem(false);
		});

		loadOwnProblem = new JButton();
		loadOwnProblem.setText(LOAD_OWN_PROBLEM);
		loadOwnProblem.addActionListener(t -> {
			openFileSelectionFrame(GameSaver.PATH_CUSTOM_PROBLEMS, true);
			setFocusOnWarehouse();
			warehouse.getModel().setOwnProblem(true);
		});

		buttonPanel.add(undo);
		buttonPanel.add(redo);
		buttonPanel.add(save);
		buttonPanel.add(load);
		buttonPanel.add(loadOwnProblem);
		getContentPane().add(warehouse, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	private void setFocusOnWarehouse() {
		warehouse.setFocusable(true);
		warehouse.requestFocusInWindow();
	}

	public String getUserInput() {
		return JOptionPane.showInputDialog(this, "Username eingeben",
				JOptionPane.OK_OPTION).trim();
	}

	private static void openFileSelectionFrame(String path, boolean ownGame,
			boolean test) {
		JFrame dialog = new JFrame();
		JComboBox<String> files = new JComboBox<String>(
				GameSaver.loadAllFiles(path));
		files.setSize(300, 100);
		JLabel label = new JLabel("Wählen Sie ein Spiel:");
		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(40, 40));
		dialog.add(files, BorderLayout.CENTER);
		dialog.add(label, BorderLayout.NORTH);
		dialog.add(ok, BorderLayout.SOUTH);
		files.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		dialog.pack();
		dialog.setVisible(true);

		ok.addActionListener(a -> {
			String selected = files.getSelectedItem().toString();
			if (!"".equals(selected) && null != selected) {
				try {
					Controller controller = new Controller(new Model());
					Warehouse warehouse = new Warehouse(controller);
					JFrame frame = new JFrame();

					warehouse.initGame(ownGame, selected);
					warehouse.paintInitGameArea();

				} catch (Exception e) {
					// JOptionPane.showMessageDialog(this,
					// "Kein Spiel zu File: \"" + selected
					// + "\" gefunden.");
				}

			}
			dialog.dispose();
			// this.setVisible(true);
		});
	}

	private void openFileSelectionFrame(String path, boolean ownGame) {
		JFrame dialog = new JFrame();
		JComboBox<String> files = new JComboBox<String>(
				GameSaver.loadAllFiles(path));
		files.setSize(300, 100);
		JLabel label = new JLabel("Wählen Sie ein Spiel:");
		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(40, 40));
		dialog.add(files, BorderLayout.CENTER);
		dialog.add(label, BorderLayout.NORTH);
		dialog.add(ok, BorderLayout.SOUTH);
		files.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		dialog.pack();
		dialog.setVisible(true);
		ok.addActionListener(a -> {
			String selected = files.getSelectedItem().toString();
			if (!"".equals(selected) && null != selected) {
				try {
					warehouse.reset();
					warehouse.initGame(ownGame, selected);
					warehouse.paintInitGameArea();
					warehouse.refresh();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this,
							"Kein Spiel zu File: \"" + selected
									+ "\" gefunden.");
				}
			}
			dialog.dispose();
			this.setVisible(true);
		});
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

}
