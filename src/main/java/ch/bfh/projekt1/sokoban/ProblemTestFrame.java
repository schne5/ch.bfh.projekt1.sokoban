package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ProblemTestFrame extends JFrame {
	public static String UNDO = "Undo";
	public static String REDO = "Redo";
	public static String SAVE = "Save";
	public static String REVERSE = "Reverse";

	JPanel buttonPanel;
	JButton undo;
	JButton redo;
	JButton save;
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
				this.warehouse.getController().saveGame(name);
				setFocusOnWarehouse();
			}
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
		buttonPanel.add(reverse);
		getContentPane().add(this.warehouse, BorderLayout.CENTER);
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

	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
}
