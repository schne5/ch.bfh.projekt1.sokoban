package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 *@author:Elisa, Anna
 */
public class Sokoban extends JFrame {
	public static String UNDO = "Undo";
	public static String SAVE = "Save";
	public static String LOAD = "Load";

	JPanel buttonPanel;
	JButton undo;
	JButton save;
	JButton load;
	Warehouse warehouse;

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
			warehouse.getController().save();
			setFocusOnWarehouse();
		});

		load = new JButton();
		load.setText(LOAD);
		load.addActionListener(t -> {
			warehouse.getController().load();
			setFocusOnWarehouse();
			warehouse.repaint();
		});
		buttonPanel.add(undo);
		buttonPanel.add(save);
		buttonPanel.add(load);
		getContentPane().add(warehouse, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	private void setFocusOnWarehouse() {
		warehouse.setFocusable(true);
		warehouse.requestFocusInWindow();
	}
}
