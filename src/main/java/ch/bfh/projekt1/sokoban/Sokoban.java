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

/*
 *@author:Elisa, Anna
 */
public class Sokoban extends JFrame {
	public static String UNDO = "Undo";
	public static String SAVE = "Save";
	public static String LOAD = "Load";
	public static String LOAD_OWN_PROBLEM = "Load Own Problem";

	JPanel buttonPanel;
	JButton undo;
	JButton save;
	JButton load;
	JButton loadOwnProblem;
	Warehouse warehouse;
	String name;

	public Sokoban() {
		warehouse = new Warehouse();
		setFocusOnWarehouse();
		buttonPanel = new JPanel();
		undo = new JButton();
		undo.setText(UNDO);
		undo.addActionListener(t -> {
			warehouse.getController().undo();
			warehouse.repaint();
			setFocusOnWarehouse();
		});

		save = new JButton();
		save.setText(SAVE);
		save.addActionListener(t -> {
			String name = getUserInput();
			if (!"".equals(name) && null != name) {
				warehouse.getController().saveGame(name);
				setFocusOnWarehouse();
			}
		});

		load = new JButton();
		load.setText(LOAD);
		load.addActionListener(t -> {
			openFileSelectionFrame(GameSaver.PATH_GAME_SAVE, true);
			setFocusOnWarehouse();
		});
		loadOwnProblem = new JButton();
		loadOwnProblem.setText(LOAD_OWN_PROBLEM);
		loadOwnProblem.addActionListener(t -> {
			openFileSelectionFrame(GameSaver.PATH_CUSTOM_PROBLEMS, false);
			setFocusOnWarehouse();
		});

		buttonPanel.add(undo);
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

	private void loadCustomProblem(String path, boolean game) {
		openFileSelectionFrame(path, game);
	}

	private void loadGame(String path, boolean game) {
		openFileSelectionFrame(path, game);
	}

	private void openFileSelectionFrame(String path, boolean game) {
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
					if (game) {
						warehouse.getController().loadGame(selected);
					} else {
						warehouse.getController().loadCustomProblem(selected);
					}

					warehouse.repaint();
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
}
