package ch.bfh.projekt1.sokoban;

import javax.swing.JFrame;

/*
 *@author:Elisa, Anna
 */
public class MainSokoban {

	public static void main(String[] args) {
		Sokoban sokoban = new Sokoban();
		sokoban.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sokoban.setSize(650, 600);
		sokoban.setVisible(true);
	}

}
