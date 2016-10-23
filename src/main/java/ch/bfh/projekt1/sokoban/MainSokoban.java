package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 *@author:Elisa, Anna
 */
public class MainSokoban {

	public static void main(String[] args) {
		showInitDialog();
	}

	public static void showInitDialog() {
		JFrame enter = new JFrame();
		JPanel buttonPanel = new JPanel();
		JLabel welcome = new JLabel("Willkommen!!!");
		welcome.setHorizontalAlignment(JLabel.CENTER);
		JButton play = new JButton("Zum Spiel");
		play.addActionListener(a -> {
			Sokoban sokobanGame = new Sokoban();
			sokobanGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			sokobanGame.setSize(650, 600);
			sokobanGame.setVisible(true);
			enter.setVisible(false);
		});
		JButton design = new JButton("Problem designen");
		design.addActionListener(a -> {
			ProblemDesigner problemDesigner = new ProblemDesigner();
			problemDesigner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			problemDesigner.setSize(950, 650);
			problemDesigner.setVisible(true);
			enter.setVisible(false);
		});
		buttonPanel.add(play);
		buttonPanel.add(design);
		enter.add(welcome, BorderLayout.CENTER);
		enter.add(buttonPanel, BorderLayout.SOUTH);
		enter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		enter.setSize(400, 200);
		enter.setVisible(true);
	}
}
