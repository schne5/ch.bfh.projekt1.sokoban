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
	public static String REDO = "Redo";

	JPanel buttonPanel;
	JButton undo;

	public Sokoban() {
		Warehouse warehouse = new Warehouse();
		warehouse.setFocusable(true);
		warehouse.requestFocusInWindow();
		buttonPanel = new JPanel();
		undo = new JButton();
		undo.setText(UNDO);
		undo.addActionListener(t -> {
			warehouse.getController().undo();

		});
		buttonPanel.add(undo);
		getContentPane().add(warehouse, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}
}
