package ch.bfh.projekt1.sokoban;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
		JLabel welcome = new JLabel("Welcome!!!");
		welcome.setHorizontalAlignment(JLabel.CENTER);

		JButton play = new JButton("Play");
		play.addActionListener(a -> {
			Controller controller = new Controller(new Model());
			Warehouse warehouse = new Warehouse(controller);
			warehouse.initGame();
			warehouse.paintInitGameArea();
			PlaySokobanFrame sokobanGame = new PlaySokobanFrame(warehouse);
			warehouse.setParent(sokobanGame);
			sokobanGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			sokobanGame.setVisible(true);
			sokobanGame.pack();
			sokobanGame.setResizable(false);
			enter.setVisible(false);
		});

		JButton design = new JButton("Problem design");
		design.addActionListener(a -> {
			ProblemDesigner problemDesigner = new ProblemDesigner();
			problemDesigner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			problemDesigner.setSize(620, 600);
			problemDesigner.setVisible(true);
			enter.setVisible(false);
		});

		JButton testOwnProblem = new JButton("Problem test");
		testOwnProblem.addActionListener(a -> {
			Controller controller = new Controller(new Model());
			Warehouse warehouse = new Warehouse(controller);
			ProblemTestFrame frame = new ProblemTestFrame(warehouse);
			warehouse.setParent(frame);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			// frame.pack();
				frame.setSize(600, 400);
				frame.setResizable(false);
				enter.setVisible(false);
			});

		buttonPanel.add(play);
		buttonPanel.add(design);
		buttonPanel.add(testOwnProblem);

		enter.add(welcome, BorderLayout.NORTH);
		enter.add(buttonPanel, BorderLayout.CENTER);
		enter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		enter.setSize(400, 200);
		enter.setVisible(true);
	}

	private static void openFileSelectionFrame(String path, boolean ownGame,
			boolean test, JFrame parent) {
		JFrame dialog = new JFrame();
		JComboBox<String> files = new JComboBox<String>(
				GameSaver.loadAllFiles(path));
		files.setSize(300, 100);
		JLabel label = new JLabel("Wählen Sie ein Spiel:");
		JButton ok = new JButton("OK");
		ok.setMaximumSize(new Dimension(40, 40));
		dialog.add(files, BorderLayout.CENTER);
		dialog.add(label, BorderLayout.NORTH);
		dialog.add(ok, BorderLayout.SOUTH);
		files.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

		dialog.pack();
		dialog.setVisible(true);

		ok.addActionListener(a -> {
			String selected = files.getSelectedItem().toString();
			if (!"".equals(selected) && null != selected) {
				try {
					Controller controller = new Controller(new Model());
					Warehouse warehouse = new Warehouse(controller);
					JFrame frame = new JFrame();

					warehouse.initGame(ownGame, selected);
					warehouse.paintInitGameArea();
					if (test) {
						frame = new ProblemTestFrame(warehouse);
					} else {
						frame = new PlaySokobanFrame(warehouse);
					}
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setResizable(false);
					frame.pack();
					frame.setVisible(true);
					parent.setVisible(false);
				} catch (Exception e) {
					// JOptionPane.showMessageDialog(this,
					// "Kein Spiel zu File: \"" + selected
					// + "\" gefunden.");
				}

			}
			dialog.dispose();
			// this.setVisible(true);
		});
	}
}
