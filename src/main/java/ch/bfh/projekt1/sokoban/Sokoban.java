package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/*
 *@author:Elisa, Anna
 */
public class Sokoban extends JFrame {

	public Sokoban() {
		Warehouse warehouse = new Warehouse();
		warehouse.setFocusable(true);
		warehouse.requestFocusInWindow();
		getContentPane().add(warehouse, BorderLayout.CENTER);

	}

}
