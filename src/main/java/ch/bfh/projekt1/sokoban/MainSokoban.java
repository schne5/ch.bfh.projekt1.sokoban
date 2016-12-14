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
		JLabel welcome = new JLabel("Welcome to SOKOBAN!!!");
		welcome.setHorizontalAlignment(JLabel.CENTER);

		JButton play = new JButton("Play");
		play.addActionListener(a -> {
			Controller controller = new Controller(new Model());
			Warehouse warehouse = new Warehouse(controller);
			warehouse.initGame();
			warehouse.paintInitGameArea();
			PlaySokobanFrame sokobanGame = new PlaySokobanFrame(warehouse);
			warehouse.setParent(sokobanGame, sokobanGame.movesCount,
					sokobanGame.pushesCount);
			sokobanGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			sokobanGame.setVisible(true);
			sokobanGame.pack();
			sokobanGame.setResizable(false);
			sokobanGame.setLocationRelativeTo(null);
			enter.setVisible(false);
		});

		JButton design = new JButton("Problem design");
		design.addActionListener(a -> {
			ProblemDesigner problemDesigner = new ProblemDesigner();
			problemDesigner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			problemDesigner.setSize(620, 600);
			problemDesigner.setVisible(true);
			problemDesigner.setLocationRelativeTo(null);
			enter.setVisible(false);
		});

		JButton testOwnProblem = new JButton("Problem test");
		testOwnProblem.addActionListener(a -> {
			Controller controller = new Controller(new Model());
			Warehouse warehouse = new Warehouse(controller);
			ProblemTestFrame frame = new ProblemTestFrame(warehouse);
			warehouse.setParent(frame, frame.movesCount, frame.pushesCount);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			frame.setSize(600, 400);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			enter.setVisible(false);
		});

		buttonPanel.add(play);
		buttonPanel.add(design);
		buttonPanel.add(testOwnProblem);

		enter.add(welcome, BorderLayout.NORTH);
		enter.add(buttonPanel, BorderLayout.CENTER);
		enter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		enter.setSize(400, 100);
		enter.setLocationRelativeTo(null);
		enter.setVisible(true);
	}
}
