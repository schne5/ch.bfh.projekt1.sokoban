package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 *@author:Elisa, Anna
 */
public class Sokoban extends JFrame {
	JPanel buttonPanel;
	JButton undo;
	JButton redo;

	public Sokoban() {
		Warehouse warehouse = new Warehouse();
		warehouse.setFocusable(true);
		warehouse.requestFocusInWindow();
		buttonPanel = new JPanel();
		undo = new JButton();
		undo.setText("Undo");
		undo.addActionListener(t -> {
			System.out.println("Undo");
		});
		redo = new JButton();
		redo.setText("Redo");
		redo.addActionListener(t -> {
			System.out.println("Redo");
		});
		buttonPanel.add(undo);
		buttonPanel.add(redo);
		getContentPane().add(warehouse, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}
}
