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

public class PlaySokobanFrame extends JFrame {

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
			String name = getUserInput();
			if (null != name && !"".equals(name.trim())) {
				this.warehouse.getController().saveGame(name.trim());
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

	private void openFileSelectionFrame(String path, boolean ownGame) {
		JFrame dialog = new JFrame();
		JComboBox<String> files = new JComboBox<String>(
				GameSaver.loadAllFiles(path));
		files.setSize(300, 100);
		JLabel label = new JLabel(CHOOSE_GAME);
		JButton ok = new JButton(OK);
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
					warehouse.updateParent();
					warehouse.refresh();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, ERROR_LOAD + selected);
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
