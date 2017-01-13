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
	public static String WELCOME = "Welcome to SOKOBAN!!!";
	public static String PLAY = "Play";
	public static String DESIGN = "Problem design";
	public static String TEST = "Problem test";
	public static int WINDOW_SPACE_Y = 100;

	public static void main(String[] args) {
		showInitDialog();
	}

	public static void showInitDialog() {

		JFrame enter = new JFrame();
		JPanel buttonPanel = new JPanel();
		JLabel welcome = new JLabel(WELCOME);
		welcome.setHorizontalAlignment(JLabel.CENTER);

		JButton play = new JButton(PLAY);
		play.addActionListener(a -> {
			Controller controller = new Controller(new Model());
			Warehouse warehouse = new Warehouse(controller);
			warehouse.initGame();
			warehouse.paintInitGameArea();
			PlaySokobanFrame sokobanGame = new PlaySokobanFrame(warehouse);
			warehouse.setParent(sokobanGame, sokobanGame.movesCount,
					sokobanGame.pushesCount);
			warehouse.updateParent();
			sokobanGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			sokobanGame.setVisible(true);
			sokobanGame.pack();
			sokobanGame.setResizable(false);
			sokobanGame.setLocationRelativeTo(null);
			enter.setVisible(false);
		});

		JButton design = new JButton(DESIGN);
		design.addActionListener(a -> {
			ProblemDesigner problemDesigner = new ProblemDesigner();
			problemDesigner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			problemDesigner.setSize(ProblemDesignArea.WIDTH
					* GameElementUtile.WIDTH, ProblemDesignArea.HEIGHT
					* GameElementUtile.WIDTH + WINDOW_SPACE_Y);
			problemDesigner.setVisible(true);
			problemDesigner.setLocationRelativeTo(null);
			enter.setVisible(false);
		});

		JButton testOwnProblem = new JButton(TEST);
		testOwnProblem.addActionListener(a -> {
			Controller controller = new Controller(new Model());
			Warehouse warehouse = new Warehouse(controller);
			ProblemTestFrame frame = new ProblemTestFrame(warehouse);
			warehouse.setParent(frame, frame.movesCount, frame.pushesCount);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			frame.setSize(ProblemDesignArea.WIDTH * GameElementUtile.WIDTH,
					ProblemDesignArea.HEIGHT * GameElementUtile.WIDTH
							+ WINDOW_SPACE_Y);
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
