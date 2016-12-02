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
			warehouse.paintInitGameArea();
			PlaySokobanFrame sokobanGame = new PlaySokobanFrame(warehouse);
			sokobanGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			sokobanGame.setSize(650, 600);
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
			openFileSelectionFrame(GameSaver.PATH_CUSTOM_PROBLEMS, true, true,
					enter);

		});

		JButton playSavedProblem = new JButton("Play saved Problem");
		playSavedProblem.addActionListener(a -> {
			openFileSelectionFrame(GameSaver.PATH_GAME_SAVE, false, false,
					enter);
		});

		JButton playOwnProblem = new JButton("Play own Problem");
		playOwnProblem.addActionListener(a -> {
			openFileSelectionFrame(GameSaver.PATH_CUSTOM_PROBLEMS, true, false,
					enter);
		});
		buttonPanel.add(play);
		buttonPanel.add(playSavedProblem);
		buttonPanel.add(design);
		buttonPanel.add(testOwnProblem);
		buttonPanel.add(playOwnProblem);

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
					if (!ownGame) {
						controller.loadGame(selected);
					} else {
						controller.loadCustomProblem(selected);
					}
					warehouse.paintInitGameArea();

					if (test) {
						frame = new ProblemTestFrame(warehouse);
					} else {
						frame = new PlaySokobanFrame(warehouse);
					}

					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setResizable(false);
					frame.setVisible(true);
					frame.pack();
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
