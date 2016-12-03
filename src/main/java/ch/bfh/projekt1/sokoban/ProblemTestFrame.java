package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ProblemTestFrame extends JFrame {
	public static String UNDO = "Undo";
	public static String REDO = "Redo";
	public static String LOAD = "Load";
	public static String SAVE = "Save";
	public static String REVERSE = "Reverse";

	JPanel buttonPanel;
	JButton undo;
	JButton redo;
	JButton save;
	JButton loadOwnProblem;
	JCheckBox reverse;
	private Warehouse warehouse;
	String name;

	public ProblemTestFrame(Warehouse warehouse) {
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
				this.warehouse.getController().saveCustomGame(name);
				setFocusOnWarehouse();
			}
		});

		loadOwnProblem = new JButton();
		loadOwnProblem.setText(LOAD);
		loadOwnProblem.addActionListener(t -> {
			openFileSelectionFrame(GameSaver.PATH_CUSTOM_PROBLEMS, true);
			setFocusOnWarehouse();
		});

		reverse = new JCheckBox();
		reverse.setText(REVERSE);
		reverse.addActionListener(t -> {
			this.warehouse.getModel().setReverse(reverse.isSelected());
			setFocusOnWarehouse();
		});

		buttonPanel.add(undo);
		buttonPanel.add(redo);
		buttonPanel.add(save);
		buttonPanel.add(loadOwnProblem);
		buttonPanel.add(reverse);
		getContentPane().add(this.warehouse, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
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

	private void setFocusOnWarehouse() {
		warehouse.setFocusable(true);
		warehouse.requestFocusInWindow();
	}

	public String getUserInput() {
		return JOptionPane.showInputDialog(this, "Username eingeben",
				JOptionPane.OK_OPTION).trim();
	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
}
