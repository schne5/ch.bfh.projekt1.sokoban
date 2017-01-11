package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 *@author:Elisa, Anna
 */
public class PlaySokobanFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final String UNDO = "Undo";
	public static final String REDO = "Redo";
	public static final String SAVE = "Save";
	public static final String LOAD = "Load";
	public static final String LOAD_OWN_PROBLEM = "Own";
	public static final String CANCEL = "Exit";
	public static final String MOVES = "Moves:";
	public static final String PUSHES = "Pushes:";
	public static final String INPUT = "Spielnamenzusatz eingeben:";
	public static final String CHOOSE_GAME = "Spiel waehlen:";
	public static final String OK = "OK";
	public static final String ERROR_LOAD = "Spiel konnte ncht geladen werden:";
	public static final String HINTS = "Hints";

	JPanel buttonPanel;
	JPanel statisticPanel;
	JButton undo;
	JButton redo;
	JButton save;
	JButton load;
	JButton loadOwnProblem;
	JButton cancel;
	JCheckBox hints;
	JLabel moves;
	JLabel pushes;
	public JLabel movesCount;
	public JLabel pushesCount;
	private Warehouse warehouse;

	public PlaySokobanFrame(Warehouse warehouse) {
		this.warehouse = warehouse;
		setFocusOnWarehouse();
		buttonPanel = new JPanel();
		statisticPanel = new JPanel();
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
			File file = FileHelper
					.openFileSaver(this, GameSaver.PATH_GAME_SAVE);
			if (file != null) {
				this.warehouse.getController().saveGame(file.getAbsolutePath());
				setFocusOnWarehouse();
			}
		});

		load = new JButton();
		load.setText(LOAD);
		load.addActionListener(t -> {
			File file = FileHelper.openFileChooser(this,
					GameSaver.PATH_GAME_SAVE);
			if (file != null) {
				String selected = file.getAbsolutePath();
				warehouse.reset();
				warehouse.initGame(false, selected);
				warehouse.paintInitGameArea();
				warehouse.getModel().setOwnProblem(false);
				warehouse.updateParent();
				warehouse.refresh();
			}
			setFocusOnWarehouse();
		});

		loadOwnProblem = new JButton();
		loadOwnProblem.setText(LOAD_OWN_PROBLEM);
		loadOwnProblem.addActionListener(t -> {
			File file = FileHelper.openFileChooser(this,
					GameSaver.PATH_CUSTOM_PROBLEMS);
			if (file != null) {
				String selected = file.getAbsolutePath();
				warehouse.reset();
				warehouse.initGame(true, selected);
				warehouse.paintInitGameArea();
				warehouse.refresh();
			}
			setFocusOnWarehouse();
		});

		cancel = new JButton(CANCEL);
		cancel.addActionListener(a -> {
			// Abbrechen und zur startseite
			this.dispose();
			MainSokoban.showInitDialog();
		});

		hints = new JCheckBox();
		hints.setText(HINTS);
		hints.addActionListener(t -> {
			this.warehouse.getModel().setHints(hints.isSelected());
			setFocusOnWarehouse();
		});

		moves = new JLabel(MOVES);
		pushes = new JLabel(PUSHES);
		movesCount = new JLabel("0");
		pushesCount = new JLabel("0");

		buttonPanel.add(undo);
		buttonPanel.add(redo);
		buttonPanel.add(save);
		buttonPanel.add(load);
		buttonPanel.add(loadOwnProblem);
		buttonPanel.add(cancel);
		buttonPanel.add(hints);
		statisticPanel.add(moves);
		statisticPanel.add(movesCount);
		statisticPanel.add(pushes);
		statisticPanel.add(pushesCount);

		getContentPane().add(warehouse, BorderLayout.CENTER);
		getContentPane().add(statisticPanel, BorderLayout.NORTH);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	private void setFocusOnWarehouse() {
		warehouse.setFocusable(true);
		warehouse.requestFocusInWindow();
	}

	public String getUserInput() {
		return JOptionPane.showInputDialog(this, INPUT, JOptionPane.OK_OPTION);

	}

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}

}
