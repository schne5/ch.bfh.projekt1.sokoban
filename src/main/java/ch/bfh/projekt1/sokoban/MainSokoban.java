package ch.bfh.projekt1.sokoban;

import javax.swing.JFrame;

public class MainSokoban {

	public static void main(String[] args) {
		Sokoban sokoban = new Sokoban();
		sokoban.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sokoban.setSize(600, 600);
		sokoban.setVisible(true);
	}

}
